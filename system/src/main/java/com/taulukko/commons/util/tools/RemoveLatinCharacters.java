package com.taulukko.commons.util.tools;

import java.io.File;
import java.io.IOException;

import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.io.EFileBufferWriter;
import com.taulukko.commons.util.lang.EString;

public class RemoveLatinCharacters
{
	public static void main(String argsv[])
	{
		if( argsv.length < 2 || (argsv.length > 0 && (argsv[0].equals("/?") || argsv[0].equals("--HELP" ))))
		{			
			System.out.println("Remove caracteres latinos de arquivos ou diretrio especfico no caminho fornecido e seus subdiretrios.");
			System.out.println("SINTAXE: java RemoveLatinCharacters path [/S]");
			System.out.println();
			System.out.println("path   Especifica o caminho a iniciar a busca dos diretrios a serem apagados.");
			System.out.println("/S     Exibe o nome do diretrio quando apagado.");
			return;	
		}
		
		String path = argsv[0];
		boolean show = argsv.length>1 && argsv[1].equals("/S");
		int times = remove(new File(path ),show,0);
		System.out.println("Total de arquivos alterados " + times);
	}
	
	private static int remove(File file,  boolean show,int times)
	{
		File subfiles[] = file.listFiles();
		for(int cont=0;cont<subfiles.length;cont++)
		{
			file = subfiles[cont];
			if(file.isFile())
			{
				//limpa arquivo antes
				
				boolean result = clear(file);

				if(result)
				{
					times++;
				}
				
				if(show)
				{
					if(result)
					{
						System.out.println("Arquivo " + file.getPath() + " limpo!");
					}
					else
					{
						System.out.println(file.getPath() + " no pode ser apagado pois esta em uso ou  readonly.");
					}	
				}
			
			}
			else if(file.isDirectory())
			{
				//procura em seus subdiretorios
				times = remove(file,show,times);
			}
		}
		//devolve os arquivos totais apagados
		return times;
	}
	
	private static boolean clear(File file)
	{
		try
		{
			EFileBufferReader in = new EFileBufferReader(file.getPath());
			EString content = new  EString(in.toString());
			in.close();
			if(file.delete())
			{
				content = content.replace('à','a');
				content = content.replace('á','a');
				content = content.replace('ã','a');
				content = content.replace('â','a');
				content = content.replace('ä','a');
				content = content.replace('é','e');
				content = content.replace('è','e');
				content = content.replace('ê','e');
				content = content.replace('ẽ','e');
				content = content.replace('ë','e');
				content = content.replace('í','i');
				content = content.replace('ì','i');
				content = content.replace('ĩ','i');
				content = content.replace('î','i');
				content = content.replace('ï','i');
				content = content.replace('ó','o');
				content = content.replace('ò','o');
				content = content.replace('õ','o');
				content = content.replace('ô','o');
				content = content.replace('ö','o');
				content = content.replace('ú','u');
				content = content.replace('ù','u');
				content = content.replace('ũ','u');
				content = content.replace('û','u');
				content = content.replace('ü','u');
				content = content.replace('ç','c');
				content = content.replace('Á','A');
				content = content.replace('À','A');
				content = content.replace('Ã','A');
				content = content.replace('Â','A');
				content = content.replace('Ä','A');
				content = content.replace('É','E');
				content = content.replace('È','E');
				content = content.replace('Ẽ','E');
				content = content.replace('Ê','E');
				content = content.replace('Ë','E');
				content = content.replace('Í','I');
				content = content.replace('Ì','I');
				content = content.replace('Ĩ','I');
				content = content.replace('Î','I');
				content = content.replace('Ï','I');
				content = content.replace('Ó','O');
				content = content.replace('Ò','O');
				content = content.replace('Õ','O');
				content = content.replace('Ô','O');
				content = content.replace('Ö','O');
				content = content.replace('Ú','U');
				content = content.replace('Ù','U');
				content = content.replace('Ũ','U');
				content = content.replace('Û','U');
				content = content.replace('Ü','U');
				content = content.replace('Ç','C');
				EFileBufferWriter out = new EFileBufferWriter(file.getPath());
				out.print(content.toString());
				out.close();
			}
			
			
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

}
