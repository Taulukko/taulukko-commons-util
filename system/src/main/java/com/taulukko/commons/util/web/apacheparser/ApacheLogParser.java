package com.taulukko.commons.util.web.apacheparser;

import java.io.IOException;
import java.util.HashMap;

import com.taulukko.commons.util.io.EFileBufferReader;

public class ApacheLogParser
{
	public static final int NUM_FIELDS = 9;

	public static void main(String argsv[])
	{
		long max = 10000000;
		try
		{
			EFileBufferReader log = new EFileBufferReader(
					"C:/wamp/www/parser/logs/awbr-access.log");//example.log");//
			String line = log.readLine();
			long totalsize = 0;
			System.out.println("Analisando");
			int turn = 0;
			int wait = 10000;
			int waitnewline = 100;

			HashMap<String, Long> founds = new HashMap<String, Long>();
			HashMap<String, Long> notFounds = new HashMap<String, Long>();

			while (line != null)
			{
				turn++;
				if ((turn % wait) == 0)
				{

					if ((turn % (wait * waitnewline)) == 0)
					{
						System.out.println("\n");

					}
					else
					{
						System.out.print(".");
					}
				}

				LineParser lineParser = LineParser.parse(line);
				//System.out.println(max+"-"+lineParser.toString());
				totalsize += lineParser.getSize();
				String fullPath = lineParser.getFullPath();
				if (lineParser.getExit() == 404)
				{
					if (notFounds.containsKey(fullPath))
					{
						notFounds.put(fullPath, notFounds.remove(fullPath) + 1);
					}
					else
					{
						notFounds.put(fullPath, Long.valueOf(1));
					}
				}
				else
				{
					if (founds.containsKey(fullPath))
					{
						founds.put(fullPath, founds.remove(fullPath) + 1);
					}
					else
					{
						founds.put(fullPath, Long.valueOf(1));
					}

				}

				line = log.readLine();
				if (--max < 0)
				{
					line = null;
				}

			}
			System.out.println("\nLargura de banda usada por esse dominio:"
					+ (totalsize / (1024 * 1024 * 1024) + "G"));
			System.out.println("Arquivos encontrados (like excel):");

			Object keys[] = founds.keySet().toArray();
			for (int cont = 0; cont < keys.length; cont++)
			{
				String url = keys[cont].toString();
				long band = founds.get(url);
				//excel friendly
				System.out.println("\"" + url + "\"	\"" + band + "\"");
			}
			
			System.out.println("Arquivos nao encontrados (like excel):");

			keys = notFounds.keySet().toArray();
			for (int cont = 0; cont < keys.length; cont++)
			{
				String url = keys[cont].toString();
				long band = notFounds.get(url);
				//excel friendly
				System.out.println("\"" + url + "\"	\"" + band + "\"");
			}
			

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
