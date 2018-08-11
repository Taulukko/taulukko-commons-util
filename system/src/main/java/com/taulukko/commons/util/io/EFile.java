/*
 * Created on 31/12/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import com.taulukko.commons.util.lang.EMath;
import com.taulukko.commons.util.lang.EString;

/**
 * @author Edson
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EFile extends EAbstractFile {

	public enum SourceFileType {
		PATH(1), RESOURCE(2), URI(3), URL(4);

		int type = 0;

		SourceFileType(int type) {
			this.type = type;
		}

	}

	public static final int TP_SPLIT_SIZE_NAME_SUPORT = 3;

	private int sizeBufer = (int) TP_MEGA;

	public static void main(String argsv[]) {
		EFile file = new EFile("~/teste.htm");
		System.out.println(file.getType());

	}

	public EFile(String path) {
		this(path, SourceFileType.PATH);
	}

	public EFile(String path, SourceFileType sourceType) {
		super(loadFile(path, sourceType));
	}

	private static File loadFile(String path, SourceFileType sourceType) throws RuntimeException {
		URL url = null;
		switch (sourceType) {
		case RESOURCE: {
			// convert resource to url
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			String newpath = EFile.getClassLoaderPath() + path;

			url = classLoader.getResource(newpath);

			boolean resourceNotFound = url == null;
			boolean j2ee = resourceNotFound && new EFile(newpath).exists();

			if (resourceNotFound && !j2ee) {

				throw new RuntimeException("File [" + path + "] not found");
			}

			if (j2ee) {
				return loadFile(newpath, SourceFileType.PATH);
			}
			return loadFile(newpath, SourceFileType.URL);
		}
		case URL: { 
			try {
				if (url == null) {
					url = new URL(path);
				}
				String newpath = url.getFile();
				return loadFile(newpath, SourceFileType.PATH);
			} catch (MalformedURLException e) {
				throw new RuntimeErrorException(new Error(e));
			}
		}
		case PATH:
			// resolve path
			return new File(path);

		case URI: {
			// resolve uri
			try {
				URI uri = new URI(path);
				return new File(uri);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
		default:
			return null;
		}
	}

	public EFile(File file) {
		super(file);
	}

	public static String getWorkPath() {
		return new File(".").getAbsolutePath();
	}

	public static String getClassLoaderPath() {
		return EFile.class.getClassLoader().getResource(".").getPath();
	}

	public boolean forceDelete() {
		boolean result = emptyFolder();
		delete();
		return result;
	}

	public void split(String files[]) throws IOException {
		/*
		 * Como deve funcionar: A partir dos nomes recebidos, ele divide em partes
		 * iguais o tamanho do arquivo original, exemplo: Se o arquivo tem 100KB e foram
		 * passados 10 nomes de partes de arquivos, ento cada parte ter 10KB.
		 * 
		 * Como desenvolver: Copiar a rotina atual de split para essa rotina. Alterar
		 * para funcionar com nomes de arquivos em vez de tamanho mximo. Depois de
		 * alterar a rotina, alterar para a antiga rotina de split, chamar esta. Ai a
		 * antiga ter como nico trabalho gerar os nomes dos arquivos, suficientes para
		 * no estourar o mximo fornecido.
		 */
		System.out.println("Nao implementado ainda.");
	}

	public void join(String files[]) throws IOException {
		/*
		 * Como deve funcionar: A partir dos nomes recebidos ele une todos no arquivo
		 * atual (this).
		 * 
		 * Como desenvolver: Copiar a rotina atual de join para essa rotina. Alterar
		 * para funcionar com nomes de arquivos em vez de nome inicial. Depois de
		 * alterar a rotina, alterar para a antiga rotina de join, chamar esta. Ai a
		 * antiga ter como nico trabalho gerar os nomes dos arquivos.
		 */
		System.out.println("Nao implementado ainda.");
	}

	public String[] split(int maxSize) throws IOException {
		ArrayList<String> ret = new ArrayList<>();
		RandomAccessFile fi = new RandomAccessFile(this.getJFile(), "r");
		int len = (int) fi.length();
		int filesLeft = len / maxSize;
		int total = len / maxSize;
		int buffSize = maxSize;
		while (filesLeft >= 0) {
			String fileNumber = new EString(
					new EString("0").repeat(TP_SPLIT_SIZE_NAME_SUPORT).toString() + (total - filesLeft + 1))
							.right(TP_SPLIT_SIZE_NAME_SUPORT).toString();
			if (filesLeft-- == 0) {
				buffSize = len % maxSize;
			}
			byte buf[] = new byte[buffSize];
			fi.read(buf, 0, buffSize);
			String fileName = this.getJFile().getAbsolutePath() + "." + fileNumber;
			ret.add(fileName);
			FileOutputStream fo = new FileOutputStream(fileName);
			fo.write(buf);
			fo.close();

		}
		fi.close();
		return ret.toArray(new String[ret.size()]);
	}

	public void join(String startFile) throws IOException, NameInvalidException {

		EFile testFile = new EFile(startFile);

		if (!testFile.exists()) {
			throw new FileNotFoundException();
		}

		String fileNumber = new EString(startFile).right(TP_SPLIT_SIZE_NAME_SUPORT).toString();
		if (!EMath.isInteger(fileNumber)) {
			throw new NameInvalidException();
		}

		String fileName = startFile.substring(0, startFile.length() - TP_SPLIT_SIZE_NAME_SUPORT - 1);

		int fileActive = 0;

		FileOutputStream fo = new FileOutputStream(fileName);

		testFile = new EFile(fileName + "." + fileNumber);
		fileNumber = new EString("0").repeat(TP_SPLIT_SIZE_NAME_SUPORT)
				.concat(new EString(String.valueOf(++fileActive + 1))).right(TP_SPLIT_SIZE_NAME_SUPORT).toString();

		while (testFile.exists()) {

			RandomAccessFile fi = new RandomAccessFile(testFile.getJFile(), "r");
			// byte buf[] = new byte[(int) testFile.length()];
			byte buf[] = new byte[sizeBufer];
			int ret = fi.read(buf, 0, sizeBufer);
			// System.out.println("ret=" + ret);

			while (ret != -1) {
				// System.out.println(new String(buf));
				fo.write(buf, 0, ret);
				ret = fi.read(buf, 0, sizeBufer);
				// System.out.println("ret=" + ret);
			}

			/*
			 * int leftBytes = (int) testFile.length() % _sizeBuffer;
			 * System.out.println(leftBytes); fo.write(buf, 0, leftBytes);
			 */fi.close();

			testFile = new EFile(fileName + "." + fileNumber);
			fileNumber = new EString("0").repeat(TP_SPLIT_SIZE_NAME_SUPORT)
					.concat(new EString(String.valueOf(++fileActive + 1))).right(TP_SPLIT_SIZE_NAME_SUPORT).toString();

		}
		fo.close();
	}

	public boolean emptyFolder() {
		File source = this.getJFile();
		if (!source.isDirectory()) {
			return false;
		}
		File subfiles[] = source.listFiles();
		boolean result = true;
		for (int cont = 0; cont < subfiles.length; cont++) {
			source = subfiles[cont];
			if (source.isDirectory()) {
				// se um diretrio esvazia ele antes
				new EFile(source.getAbsolutePath()).emptyFolder();
				result = result && source.delete();
			} else {
				result = result && source.delete();
			}
		}
		return result;
	}

	public class NameInvalidException extends Exception {

		private static final long serialVersionUID = -7250901304978482431L;
	}

	public int getSizeBuffer() {
		return sizeBufer;
	}

	public void setSizeBuffer(int sizeBuffer) {
		sizeBufer = sizeBuffer;
	}

	public void copyTo(String path) throws IOException {

		File fromFile = new File(this.getAbsolutePath());
		File toFile = new File(path);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: " + this.getAbsolutePath());
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: " + this.getAbsolutePath());
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: " + this.getAbsolutePath());

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			if (!toFile.canWrite())
				throw new IOException("FileCopy: " + "destination file is unwriteable: " + path);
			System.out.print("Overwrite existing file " + toFile.getName() + "? (Y/N): ");
			System.out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String response = in.readLine();
			if (!response.equals("Y") && !response.equals("y"))
				throw new IOException("FileCopy: " + "existing file was not overwritten.");
		} else {
			String parent = toFile.getParent();
			if (parent == null)
				parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				throw new IOException("FileCopy: " + "destination directory doesn't exist: " + parent);
			if (dir.isFile())
				throw new IOException("FileCopy: " + "destination is not a directory: " + parent);
			if (!dir.canWrite())
				throw new IOException("FileCopy: " + "destination directory is unwriteable: " + parent);
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}

	public String getType() {
		String partName[] = this.getName().split("\\Q.");
		return partName[partName.length - 1];

	}

}
