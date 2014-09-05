package com.taulukko.commons.util.httpserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.lang.EString;

public class HttpRequestHandler implements Runnable
{
	final static String CRLF = "\r\n";

	Socket socket;

	InputStream input;

	OutputStream output;

	BufferedReader br;

	String _path;

	RequestListenner _requestListenner = null;

	// Constructor
	public HttpRequestHandler(Socket socket, String path) throws Exception
	{
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		EString epath = new EString(path);
		epath = epath.replace('\\', '/');
		if (epath.right(1).toString().equals("/"))
		{
			path = epath.left(epath.getLength() - 1).toString();
		}
		_path = path;
	}

	// Implement the run() method of the Runnable interface.
	public void run()
	{
		try
		{
			processRequest();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception
	{
		while (true)
		{

			String headerLine = br.readLine();
			System.out.println(headerLine);
			if (headerLine.equals(CRLF) || headerLine.equals(""))
				break;

			StringTokenizer s = new StringTokenizer(headerLine);
			String temp = s.nextToken();

			if (temp.equals("GET"))
			{

				// Open the requested file.
				InputStream fis = null;

				// Construct the response message.
				String serverLine = "Server: fpont simple java httpServer";
				String statusLine = null;
				String contentTypeLine = null;
				String entityBody = null;
				String contentLengthLine = "error";

				String fileName = s.nextToken();
				fileName = _path + fileName;
				boolean fileExists = false;

				byte data[] = null;

				ListnnerResponse response = null;

				if (_requestListenner != null)
				{

					response = _requestListenner.getResponse(fileName);
				}

				if (response != null && response.getReplace())
				{
					fileName = response.getReplacePath();
				}

				if (response == null
						|| (response.getIgnore() && !response.getUseInput()))
				{
					if (new EFile(fileName).exists())
					{
						fis = new FileInputStream(fileName);
						fileExists = true;
					}

				}
				else if (response != null && response.getUseInput())
				{
					//usa input como se tivesse lido do arquivo
					fis = response.getInput();
					response.setUseInput(true);
					fileExists = true;
				}
				 

				if (fileExists && (response == null || response.getIgnore()))
				{
					statusLine = "HTTP/1.0 200 OK" + CRLF;
					contentTypeLine = "Content-type: " + contentType(fileName)
							+ CRLF;
					contentLengthLine = "Content-Length: "
							+ (new Integer(fis.available())).toString() + CRLF;
				}
				else if (response == null || response.getNotFound())
				{
					System.out.println("File [" + fileName + "] not exist.");

					statusLine = "HTTP/1.0 404 Not Found" + CRLF;
					contentTypeLine = "Content-type: "
							+ contentType("error.htm");
					entityBody = "<HTML>"
							+ "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
							+ "<BODY>404 Not Found" + "<BR>Evon Server"
							+ "<BR>Error: Page not found!"
							+ "<br>usage:http://host:port/"
							+ "fileName.html</BODY></HTML>";
					contentLengthLine = "Content-Length: "
							+ entityBody.length() + CRLF;
				}
				else if (response != null && !response.getIgnore())
				{
					statusLine = "HTTP/1.0 200 OK" + CRLF;
					contentTypeLine = "Content-type: " + contentType(fileName)
							+ CRLF;
					contentLengthLine = "Content-Length: " + data.length + CRLF;
				}

				// Send the status line.
				output.write(statusLine.getBytes());

				// Send the server line.
				output.write(serverLine.getBytes());

				// Send the content type line.
				output.write(contentTypeLine.getBytes());

				// Send the Content-Length
				output.write(contentLengthLine.getBytes());

				// Send a blank line to indicate the end of the header lines.
				output.write(CRLF.getBytes());

				// Send the entity body.
				if (fileExists && (response == null || response.getIgnore()))
				{
					sendBytes(fis, output);
					fis.close();

				}
				else if (response != null && !response.getNotFound()
						&& !response.getIgnore())
				{
					output.write(data);
				}
				else
				{
					output.write(entityBody.getBytes());
				}
			}
		}

		try
		{
			output.close();
			br.close();
			socket.close();
		}
		catch (Exception e)
		{
		}
	}

	private static void sendBytes(InputStream fis, OutputStream os)
			throws Exception
	{
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;

		// Copy requested file into the socket's output stream.
		while ((bytes = fis.read(buffer)) != -1)
		{
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName)
	{
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")
				|| fileName.endsWith(".txt"))
		{
			return "text/html";
		}

		return "";

	}

	public RequestListenner getRequestListenner()
	{
		return _requestListenner;
	}

	public void setRequestListenner(RequestListenner requestListenner)
	{
		_requestListenner = requestListenner;
	}

}