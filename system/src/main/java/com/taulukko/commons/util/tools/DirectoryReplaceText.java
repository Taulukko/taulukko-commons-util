package com.taulukko.commons.util.tools;

import java.io.File;
import java.io.IOException;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.io.EFileBufferWriter;
import com.taulukko.commons.util.lang.EString;

/**
 * Substitui textos em arquivos no caminho fornecido e seus subdiretrios
 */

public class DirectoryReplaceText
{
	public static void main(String argsv[])
	{
		if (argsv.length < 2
				|| (argsv.length > 0 && (argsv[0].equals("/?") || argsv[0]
						.equals("--HELP"))))
		{
			System.out
					.println("Substitui textos em arquivos no caminho fornecido e seus subdiretrios");
			System.out
					.println("SINTAXE: java DirectoryReplaceText path find replace filter [/S || /D] ");
			System.out.println();
			System.out
					.println("path    Especifica o caminho a iniciar a busca do diretrio a ser corrigido.");
			System.out.println("find    Texto procurado");
			System.out.println("replace Texto substituto");
			System.out.println("filter Tipos de arquivos a serem buscados por extensões. * para todos ou extensões separadas por vírgula (ex: htm,php)");
			System.out
					.println("/S   Modo show, exibe o nome do arquivo quando modificado.");
			System.out
					.println("/D   Modo demo, apenas exibe o arquivo a ser modificado mas não faz nada.");
			return;
		}

		String path = argsv[0];
		String find = argsv[1];
		String replace = argsv[2];
		String filter[] = argsv[3].split(",");
		boolean show = argsv.length > 4 && argsv[4].equals("/S");
		boolean demo = argsv.length > 4 && argsv[4].equals("/D");
		int times = replace(new File(path), find, replace, filter, show, demo, 0);
		System.out
				.println("Total de arquivos que continham o texto:  " + times);
	}

	private static int replace(File file, String find, String replace,String filter[],
			boolean show, boolean demo, int times)
	{
		File subfiles[] = file.listFiles();

		for (int cont = 0; subfiles != null && cont < subfiles.length; cont++)
		{
			file = subfiles[cont];
			EString path = new EString(file.getAbsolutePath());

			if (file.isDirectory())
			{

				times = replace(file, find, replace, filter,show, demo, times);

			}
			else
			{
				boolean filtred = false;
				
				for(int contF = 0 ; !filtred && contF < filter.length ; contF++)
				{
					String type = new EFile(file).getType();
					filtred=filter[contF].toLowerCase().equals(type.toLowerCase()); 
				}

				if(!filtred)
				{
				//	System.out.println("Arquivo " + path + " não consta no filtro e por isto foi ignorado...");
					continue;
				}
				String content = null;
				EFileBufferReader reader = null;
				try
				{
					reader = new EFileBufferReader(path.toString());
					content = reader.toString();
					reader.close();

				}
				catch (IOException e)
				{
					System.out.println("Erro ao acessar o arquivo " + path);
					e.printStackTrace();
					continue;
				}

				if (content.indexOf(find) >= 0)
				{
					times++;

					if (show || demo)
					{
						System.out
								.println("[" + path + "] contém a sequência!");
					}
				}
				boolean securityCopy = demo;
				
				if(!demo)
				{
					securityCopy =  file.renameTo(new File(path + ".bkp"));
				}
				
				if (securityCopy && !demo)
				{
					EString econtent = new EString(content);
					econtent = econtent.replace(find, replace);
					EFileBufferWriter writer = null;
					try
					{
						writer = new EFileBufferWriter(path.toString());
						writer.print(econtent.toString());
						writer.close();
						new EFile(path + ".bkp").forceDelete();
						file = new File(path.toString());

					}
					catch (IOException e)
					{
						System.out.println("Erro ao gravar no arquivo " + path);
						e.printStackTrace();
						continue;
					}

				}
			}

		}
		// devolve os arquivos totais apagados
		return times;
	}
}
