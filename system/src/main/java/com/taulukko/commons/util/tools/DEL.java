package com.taulukko.commons.util.tools;
import java.io.File;

import com.taulukko.commons.util.io.EFile;

public class DEL 
{
	public static void main(String argsv[])
	{
		if( argsv.length < 2 || (argsv.length > 0 && (argsv[0].equals("/?") || argsv[0].equals("--HELP" ))))
		{			
			System.out.println("Apaga arquivo ou diretrio especfico no caminho fornecido e seus subdiretrios.");
			System.out.println("SINTAXE: java DEL path name [/S]");
			System.out.println();
			System.out.println("path   Especifica o caminho a iniciar a busca dos diretrios a serem apagados.");
			System.out.println("name   Especifica o nome do diretrio que deve ser apagado do caminho solicitado e seus subdiretrios.");
			System.out.println("/S     Exibe o nome do diretrio quando apagado.");
			return;	
		}
		
		String path = argsv[0];
		String name = argsv[1];
		boolean show = argsv.length>2 && argsv[2].equals("/S");
		int times = delete(new EFile(path ),name,show,0);
		System.out.println("Total de arquivos apagados " + times);
	}
	
	private static int delete(EFile file,String name,boolean show,int times)
	{
		File subfiles[] = file.listFiles();
		for(int cont=0;cont<subfiles.length;cont++)
		{
			file = new EFile(subfiles[cont]);
			if(file.getName().equals(name))
			{
				//limpa diretorio ou arquivo antes
				
				boolean result = empty(file);
				file.delete();
				
				if(result)
				{
					times++;
				}
				
				if(show)
				{
					if(result)
					{
						System.out.println("Arquivo " + file.getPath() + " apagado!");
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
				/**@todo: Ele ta procurando em links como se fosse diretorios, o q pode dar redundancia ciclica*/
				times = delete(file,name,show,times);
			}
		}
		//devolve os arquivos totais apagados
		return times;
	}
	
	private static boolean empty(EFile file)
	{
		File subfiles[] = file.listFiles();
		boolean result = true;
		for(int cont=0;subfiles!= null &&  cont < subfiles.length;cont++)
		{
			file = new EFile(subfiles[cont]);
			if(file.isDirectory())
			{
				//se  um diretrio esvazia ele antes
				empty(file);
				result = result && file.delete();
			
			}
			else
			{				
				result = result && file.delete();
			}
		}
		
		return result;
	}
}
