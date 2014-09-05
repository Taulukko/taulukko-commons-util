/*
 * Created on 31/12/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.SizeLimitExceededException;

/**
 * @author Edson
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EZip extends EFile
{

	private ZipOutputStream _out;

	public static void main(String argsv[])
	{
		try
		{
			System.out.println("Apagando arquivos de testes anteriores");
			EFile old = new EFile("c:/movies.zip");
			if (old.exists())
			{
				old.delete();
			}
			old = new EFile("c:/movies.zip.0000000000");
			if (old.exists())
			{
				old.delete();
			}
			old = new EFile("c:/movies.zip.0000000001");
			if (old.exists())
			{
				old.delete();
			}
			old = new EFile("c:/movies.zip.0000000002");
			if (old.exists())
			{
				old.delete();
			}
			old = new EFile("c:/movies.old.zip");
			if (old.exists())
			{
				old.delete();
			}

			System.out.println("Criando arquivo zip");
			EZip zip = new EZip("c:/movies.zip");
			System.out.println("atachando primeiro arquivo");
			zip.atach("C:/DH01.mp4");
			System.out.println("atachando segundo arquivo");
			zip.atach("C:/DH02.mp4");
			zip.close();
			System.out.println("Dividindo arquivos em arquivos de 50MB");
			zip.split((int) (50 * TP_MEGA));
			System.out.println("Renomeando arquivo zip original");
			zip.rename("c:/movies.old.zip");
			System.out
					.println("Juntando os arquivos zip divididos em um unico arquivo");
			zip.join("C:/movies.zip.001");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public EZip(String sPath) throws FileNotFoundException
	{
		super(new File(sPath));
		this.open();

	}

	private void open() throws FileNotFoundException
	{
		_out = new ZipOutputStream(new FileOutputStream(this.getJFile()));
	}

	public void close() throws IOException
	{
		_out.close();
	}

	public void atach(String path) throws SizeLimitExceededException,
			IOException
	{
		File file = new File(path);

		if (!file.exists())
		{
			throw new FileNotFoundException();
		}

		if (file.length() > Integer.MAX_VALUE)
		{
			throw new SizeLimitExceededException();
		}
		
		// Cria um buffer para ler os dados do arquivo
		byte[] buf = new byte[this.getSizeBuffer()];

		// Comprime o arquivo
		FileInputStream in = new FileInputStream(file.getAbsolutePath());

		// Adiciona o arquivo ao fluxo de sa�da
		_out.putNextEntry(new ZipEntry(file.getAbsolutePath()));

		// transfere dados do arquivo para o arquivo zip
		int len;
		while ((len = in.read(buf)) > 0)
		{
			_out.write(buf, 0, len);
		}

		// Finaliza a entrada
		_out.closeEntry();
		in.close();
	}

}
