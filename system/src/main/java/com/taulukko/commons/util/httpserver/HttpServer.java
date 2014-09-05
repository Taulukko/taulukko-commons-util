package com.taulukko.commons.util.httpserver;

//***************************************
// HTTP Server Simples, nao usa nio
//***************************************

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{

	private int _port = 0;

	private String _www = "";

	private RequestListenner _requestListenner = null;

	private boolean _continue = false;

	private Thread _thread = null;
	
	private ServerSocket _serverSocket = null;

	public static void main(String args[])
	{

		int port;

		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch (Exception e)
		{
			port = 4444;
		}

		try
		{
			HttpServer server = new HttpServer("c:/teste", port);
			server.setRequestListenner(new RLTest());
			server.start();
		}
		catch (BindException e)
		{
			System.out.println("Port " + port + " is not free");
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public HttpServer(String www, int port)
	{
		_www = www;
		_port = port;
	}

	public void start() throws IOException
	{

		//print out the port number for user
		_serverSocket = new ServerSocket(_port);
		System.out.println("httpServer running on port "
				+ _serverSocket.getLocalPort());

		_continue = true;
		// server infinite loop
		while (_continue)
		{
			Socket socket = _serverSocket.accept();
			System.out.println("New connection accepted "
					+ socket.getInetAddress() + ":" + socket.getPort());

			// Construct handler to process the HTTP request message.
			try
			{
				HttpRequestHandler request = new HttpRequestHandler(socket,
						_www);
				request.setRequestListenner(_requestListenner);
				// Create a new thread to process the request.
				_thread = new Thread(request);

				// Start the thread.
				_thread.start();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}

	}

	public void stop() throws Exception
	{
		if (_thread != null)
		{
			_thread.interrupt();
		}
		_continue = false;
		if(_serverSocket!=null)
		{
			_serverSocket.close();
			while(!_serverSocket.isClosed())
			{
				Thread.sleep(200);
			}
		}
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
