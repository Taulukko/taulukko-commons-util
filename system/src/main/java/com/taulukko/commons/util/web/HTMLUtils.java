package com.taulukko.commons.util.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

import com.taulukko.commons.TaulukkoException;

public class HTMLUtils {

	public static String encodeFileToBase64Binary(File file ) throws TaulukkoException {
 
		byte[] bytes;
		try {
			bytes = loadFile(file);

			byte[] encoded = Base64.encodeBase64(bytes);
			String encodedString = new String(encoded);

			return encodedString;
		} catch (IOException e) {
			throw new TaulukkoException(e);
		}
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}
 
}
