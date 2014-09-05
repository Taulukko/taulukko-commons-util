package com.taulukko.commons.util.web.util;

/*
 * This is a simply wget.
 * You can test your URL
 *
 * $Log$
 */

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version $Revision$
 * PRECISA DE SEVEROS TESTES, AO QUE PARECE NAO FUNCIONA CORRETAMENTE COM ARQUIVOS MAIORES QUE 1MB, 
 * TESTAR COM UM FILME OU IMAGEM DE TAMANHO GRANDE
 */
public class WGet
{
	static OutputStream _out = System.out;

	static final PrintStream _defaultOut = System.out;

	static final String _commandName = WGet.class.getName();

	static int _count;

	static boolean _verb;

	static boolean _output = true;

	public static void main(String args[]) throws IOException
	{
		try
		{
			while (args[0].charAt(0) == '-')
			{
				String argument = args[0].substring(1);
				if (argument.equals("v"))
				{
					_verb = true;
				}
				else if (argument.equals("np"))
				{
					_output = false;
				}
				else if (argument.equals("t"))
				{
					download(
							"http://www.adenworld.com.br/aden/downloads/videos/AWBR_0001.wmv",
							"c:/AWBR_0001.wmv");
				}
				args[0] = args[1];
				if (args.length > 2)
				{
					args[1] = args[2];
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			_defaultOut.println("USAGE: " + _commandName + " [-v] [-np] <url>");
			_defaultOut.println("\t-v  : verbose all action.");
			_defaultOut.println("\t-np : don't print the data from the URL.");
			System.exit(1);
		}
		catch (Exception e)
		{
			_defaultOut.println("UNKNOW EXCEPTION");
			System.exit(1);
		}

		URLConnection url = (new URL(args[0])).openConnection();

		if (url instanceof HttpURLConnection)
		{
			readHttpURL((HttpURLConnection) url);
		}
		else
		{
			readURL(url);
		}
		System.exit(0);
	}

	public static final void download(String urlPath, String path)
			throws Exception
	{
		download(urlPath, new FileOutputStream(path));
	}

	public static final void download(String urlPath, OutputStream out)
			throws Exception
	{
		URLConnection url = (new URL(urlPath)).openConnection();
		_out = out;

		if (url instanceof HttpURLConnection)
		{
			readHttpURL((HttpURLConnection) url);
		}
		else
		{
			readURL(url);
		}
	}

	public static final void readURL(URLConnection url) throws IOException
	{
		url.setReadTimeout(30000);

		DataInputStream in = new DataInputStream(url.getInputStream());
		printHeader(url);

		try
		{
			while (true)
			{
				writeChar((char) in.readUnsignedByte());
			}
		}
		catch (EOFException e)
		{
			if (_output)
				verbose("\n");
			verbose(_commandName + ": Read " + _count + " bytes from "
					+ url.getURL());
		}
		catch (IOException e)
		{
			_defaultOut.println(e + ": " + e.getMessage());
			if (_output)
				verbose("\n");
			verbose(_commandName + ": Read " + _count + " bytes from "
					+ url.getURL());
		}
	}

	public static final void readHttpURL(HttpURLConnection url)
			throws IOException
	{

		long before, after;

		url.setAllowUserInteraction(true);
		url.setReadTimeout(30000);
		url.setConnectTimeout(30000);

		verbose(_commandName + ": Contacting the URL ...");
		url.connect();

		verbose(_commandName + ": Connect. Waiting for reply ...");
		before = System.currentTimeMillis();
		DataInputStream in = new DataInputStream(url.getInputStream());
		after = System.currentTimeMillis();
		verbose(_commandName + ": The reply takes "
				+ ((int) (after - before) / 1000) + " seconds");

		before = System.currentTimeMillis();

		try
		{
			if (url.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				_defaultOut.println(_commandName + ": "
						+ url.getResponseMessage());
			}
			else
			{
				printHeader(url);
				while (true)
				{
					writeChar((char) in.readUnsignedByte());
				}
			}
		}
		catch (EOFException e)
		{
			after = System.currentTimeMillis();
			int milliSeconds = (int) (after - before);
			if (_output)
				verbose("\n");
			verbose(_commandName + ": Read " + _count + " bytes from "
					+ url.getURL());
			verbose(_commandName + ": HTTP/1.0 " + url.getResponseCode() + " "
					+ url.getResponseMessage());
			url.disconnect();

			verbose(_commandName + ": It takes " + (milliSeconds / 1000)
					+ " seconds" + " (at "
					+ round(_count / (float) milliSeconds) + " K/sec).");
			if (url.usingProxy())
			{
				verbose(_commandName + ": This URL uses a proxy");
			}
		}
		catch (IOException e)
		{
			_defaultOut.println(e + ": " + e.getMessage());
			if (_output)
				verbose("\n");
			verbose(_commandName + ": I/O Error : Read " + _count
					+ " bytes from " + url.getURL());
			_defaultOut.println(_commandName + ": I/O Error "
					+ url.getResponseMessage());
		}
		
	}

	public static final void printHeader(URLConnection url)
	{
		verbose(WGet.class.getName() + ": Content-Length   : "
				+ url.getContentLength());
		verbose(WGet.class.getName() + ": Content-Type     : "
				+ url.getContentType());
		if (url.getContentEncoding() != null)
			verbose(WGet.class.getName() + ": Content-Encoding : "
					+ url.getContentEncoding());
		if (_output)
			verbose("");
	}

	public final static void writeChar(char c) throws IOException
	{
		if (_output)
			_out.write(c);
		_count++;
	}

	public static final void verbose(String s)
	{
		if (_verb)
			_defaultOut.println(s);
	}

	public static final float round(float f)
	{
		return Math.round(f * 100) / (float) 100;
	}
}