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
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.SizeLimitExceededException;

/**
 * @author Edson
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EAbstractFile {
	public static final long TP_BIT = 1;

	public static final long TP_BYTE = 8;

	public static final long TP_KILO = 1024;

	public static final long TP_MEGA = TP_KILO * TP_KILO;

	public static final long TP_GIGA = TP_MEGA * TP_KILO;

	public static final long TP_TERA = TP_GIGA * TP_KILO;

	private File javaFile;

	public EAbstractFile(File file) {
		javaFile = file;
	}

	public BufferedWriter createFile() throws IOException {
		return new BufferedWriter(new FileWriter(javaFile.getAbsolutePath()));
	}

	public BufferedReader openFile() throws IOException {
		return new BufferedReader(new FileReader(javaFile.getAbsolutePath()));
	}

	public boolean exists() {
		return javaFile.exists();
	}

	public boolean getIsHidden() {
		return javaFile.isHidden();
	}

	public boolean rename(String sNewPath) {
		return javaFile.renameTo(new File(sNewPath));
	}

	public boolean setReadOnly() {
		return javaFile.setReadOnly();
	}

	public String getName() {
		return javaFile.getName();
	}

	public String getParent() {
		return javaFile.getParent();
	}

	public File getParentFile() {
		return javaFile.getParentFile();
	}

	public String getPath() {
		return javaFile.getPath();
	}

	public boolean isDirectory() {
		return javaFile.isDirectory();
	}

	public boolean isFile() {
		return javaFile.isFile();
	}

	public boolean isHidden() {
		return javaFile.isHidden();
	}

	public long length() {
		return javaFile.length();
	}

	public boolean renameTo(File dest) {
		return javaFile.renameTo(dest);
	}

	public void toZip(String path) throws SizeLimitExceededException, IOException {
		if (javaFile.length() > Integer.MAX_VALUE) {
			throw new SizeLimitExceededException();
		}
		int size = (int) javaFile.length();
		// Cria um buffer para ler os dados do arquivo
		byte[] buf = new byte[size];

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));

		// Comprime o arquivo
		FileInputStream in = new FileInputStream(javaFile.getAbsolutePath());

		// Adiciona o arquivo ao fluxo de sada
		out.putNextEntry(new ZipEntry(javaFile.getAbsolutePath()));

		// transfere dados do arquivo para o arquivo zip
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		// Finaliza a entrada
		out.closeEntry();
		in.close();

		// Completa o arquivo zip
		out.close();

	}

	public String[] list() {
		return javaFile.list();
	}

	public String[] list(FilenameFilter filter) {
		return javaFile.list(filter);
	}

	public File[] listFiles() {
		return javaFile.listFiles();
	}

	public File[] listFiles(FileFilter filter) {
		return javaFile.listFiles(filter);
	}

	public File[] listFiles(FilenameFilter filter) {
		return javaFile.listFiles(filter);
	}

	public boolean mkdir() {
		return javaFile.mkdir();
	}

	public boolean mkdirs() {
		return javaFile.mkdirs();
	}

	public boolean canExecute() {
		return javaFile.canExecute();
	}

	public boolean canRead() {
		return javaFile.canRead();
	}

	public boolean canWrite() {
		return javaFile.canWrite();
	}

	public boolean delete() {
		return javaFile.delete();
	}

	public void deleteOnExit() {
		javaFile.deleteOnExit();
	}

	public File getAbsoluteFile() {
		return javaFile.getAbsoluteFile();
	}

	public String getAbsolutePath() {
		return javaFile.getAbsolutePath();
	}

	public File getJFile() {
		return javaFile;
	}

	public void setJFile(File file) {
		javaFile = file;
	}

	public String readAll() throws IOException {		
		String content = new String(Files.readAllBytes(this.javaFile.toPath()));
		return content;

	}

}
