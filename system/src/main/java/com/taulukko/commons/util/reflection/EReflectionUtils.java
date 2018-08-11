/*
 * Created on 03/12/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.reflection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.taulukko.commons.TaulukkoException;

/**
 * @author Usuario
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EReflectionUtils extends ClassLoader {
	
	

 
	public static String getCaller() throws TaulukkoException {
		return Thread.currentThread().getStackTrace()[2].toString();
	}

	public static String getCaller(int index) throws TaulukkoException {
		return Thread.currentThread().getStackTrace()[index].toString();
	}

	

	public static List<Class<?>> getClassesFromPackage(String pckgname) throws TaulukkoException {

		// Get a File object for the package
		File directory = null;
		try {
			directory = new File(
					Thread.currentThread().getContextClassLoader().getResource(pckgname.replace('.', '/')).getFile());
		} catch (NullPointerException x) {
			throw new TaulukkoException(pckgname + " does not appear to be a valid package1");
		}

		if (!directory.exists()) {
			throw new TaulukkoException(pckgname + " does not appear to be a valid package name");
		}

		Predicate<String> filterClassFiles = f -> f.endsWith(".class");
		Function<String, Class<?>> maperClassNameToClass = name -> {
			try {
				return (Class<?>) Class.forName(pckgname + "." + name.substring(0, name.length() - 6));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		};

		List<String> packageFileNames = Arrays.asList(directory.list());

		return packageFileNames.stream().filter(filterClassFiles).map(maperClassNameToClass)
				.collect(Collectors.toList());

	}

	public Class<?> loadClass(String sClassName, String sPath) throws TaulukkoException {
		try {

			byte[] data;
			// o endereco da classe que nao pertence ao classloader
			data = loadClassData(sPath);
			// setando a referencia
			Class<?> cls = defineClass(sClassName, data, 0, data.length, null);
			// resolve
			// resolveClass(cls);

			if (cls == null) {
				throw new TaulukkoException("Data class invalid!");
			}
			// carregando a classe
			return loadClass(sClassName, true);
		} catch (ClassNotFoundException ioe) {
			throw new TaulukkoException("Path invalid!");
		}
	}

	private byte[] loadClassData(String filename) throws TaulukkoException {

		// Create a file object relative to directory provided
		File f = new File(filename);

		// Get size of class file
		int size = (int) f.length();

		// Reserve space to read
		byte buff[] = new byte[size];

		// Get stream to read from
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);

			// Read in data
			dis.readFully(buff);

			// close stream
			dis.close();

			// return data
			return buff;
		} catch (IOException e) {
			throw new TaulukkoException(e);

		}
	}

}
