/*
 * Created on 31/12/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
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
public class EAbstractFile
{
	public static final long TP_BIT = 1;

	public static final long TP_BYTE = 8;

	public static final long TP_KILO = 1024;

	public static final long TP_MEGA = TP_KILO * TP_KILO;

	public static final long TP_GIGA = TP_MEGA * TP_KILO;

	public static final long TP_TERA = TP_GIGA * TP_KILO;

	private File _file;

	public EAbstractFile(File file)
	{
		_file = file;
	}

	public BufferedWriter createFile() throws IOException
	{
		return new BufferedWriter(new FileWriter(_file.getAbsolutePath()));
	}

	public BufferedReader openFile() throws IOException
	{
		return new BufferedReader(new FileReader(_file.getAbsolutePath()));
	}

	public boolean exists()
	{
		return _file.exists();
	}

	public boolean getIsHidden()
	{
		return _file.isHidden();
	}

	public boolean rename(String sNewPath)
	{
		return _file.renameTo(new File(sNewPath));
	}

	public boolean setReadOnly()
	{
		return _file.setReadOnly();
	}

	

	public String getName()
	{
		return _file.getName();
	}

	public String getParent()
	{
		return _file.getParent();
	}

	public File getParentFile()
	{
		return _file.getParentFile();
	}

	public String getPath()
	{
		return _file.getPath();
	}
 

	public boolean isDirectory()
	{
		return _file.isDirectory();
	}

	public boolean isFile()
	{
		return _file.isFile();
	}

	public boolean isHidden()
	{
		return _file.isHidden();
	}

	public long length()
	{
		return _file.length();
	}

	public boolean renameTo(File dest)
	{
		return _file.renameTo(dest);
	}

	public void toZip(String path) throws SizeLimitExceededException,
			IOException
	{
		if (_file.length() > Integer.MAX_VALUE)
		{
			throw new SizeLimitExceededException();
		}
		int size = (int) _file.length();
		// Cria um buffer para ler os dados do arquivo
		byte[] buf = new byte[size];

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));

		// Comprime o arquivo
		FileInputStream in = new FileInputStream(_file.getAbsolutePath());

		// Adiciona o arquivo ao fluxo de sada
		out.putNextEntry(new ZipEntry(_file.getAbsolutePath()));

		// transfere dados do arquivo para o arquivo zip
		int len;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}

		// Finaliza a entrada
		out.closeEntry();
		in.close();

		// Completa o arquivo zip
		out.close();

	}

	public String[] list()
	{
		return _file.list();
	}

	public String[] list(FilenameFilter filter)
	{
		return _file.list(filter);
	}

	public File[] listFiles()
	{
		return _file.listFiles();
	}

	public File[] listFiles(FileFilter filter)
	{
		return _file.listFiles(filter);
	}

	public File[] listFiles(FilenameFilter filter)
	{
		return _file.listFiles(filter);
	}

	public boolean mkdir()
	{
		return _file.mkdir();
	}

	public boolean mkdirs()
	{
		return _file.mkdirs();
	}

	public boolean canExecute()
	{
		return _file.canExecute();
	}

	public boolean canRead()
	{
		return _file.canRead();
	}

	public boolean canWrite()
	{
		return _file.canWrite();
	}

	public boolean delete()
	{
		return _file.delete();
	}

	public void deleteOnExit()
	{
		_file.deleteOnExit();
	}

	public File getAbsoluteFile()
	{
		return _file.getAbsoluteFile();
	}

	public String getAbsolutePath()
	{
		return _file.getAbsolutePath();
	}

	public File getJFile()
	{
		return _file;
	}

	public void setJFile(File file)
	{
		_file = file;
	}

}
