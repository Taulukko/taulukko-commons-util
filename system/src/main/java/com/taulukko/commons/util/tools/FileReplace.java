package com.taulukko.commons.util.tools;

import java.io.File;

import com.taulukko.commons.util.lang.EString;

/**
 * Renomeia arquivo no caminho fornecido e seus subdiretrios
 */

public class FileReplace
{
	public static void main(String argsv[])
	{
		if (argsv.length < 2
				|| (argsv.length > 0 && (argsv[0].equals("/?") || argsv[0]
						.equals("--HELP"))))
		{
			System.out
					.println("Renomeia arquivo no caminho fornecido e seus subdiretrios.");
			System.out
					.println("SINTAXE: java FileReplace path find replace [/S]");
			System.out.println();
			System.out
					.println("path    Especifica o caminho a iniciar a busca dos diretrios a serem apagados.");
			System.out.println("find    Texto procurado");
			System.out.println("replace Texto substituto");
			System.out
					.println("/S      Exibe o nome do diretrio quando apagado.");
			return;
		}

		String path = argsv[0];
		String find = argsv[1];
		String replace = argsv[2];
		boolean show = argsv.length > 3 && argsv[3].equals("/S");
		int times = replace(new File(path), find, replace, show, 0);
		System.out.println("Total de arquivos renomeados " + times);
	}

	private static int replace(File file, String find, String replace,
			boolean show, int times)
	{
		File subfiles[] = file.listFiles();
		for (int cont = 0; cont < subfiles.length; cont++)
		{
			file = subfiles[cont];
			if (file.getName().indexOf(find) != -1)
			{
				EString newNamePath = new EString(file.getAbsolutePath());
				newNamePath = newNamePath.replace(new EString(find), new EString(replace));
				File newName = new File(newNamePath.toString());
				file.renameTo(newName);
				times++;

				if (show)
				{
					System.out.println("Arquivo " + file.getPath()
							+ " apagado!");
				}

			}
		}
		//devolve os arquivos totais apagados
		return times;
	}
}
