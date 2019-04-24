package com.taulukko.commons.util.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.util.io.EFile.SourceFileType;

public class EFileTest {
	@Test
	public void readAll() throws IOException {
		EFile efile = new EFile("efile.txt", SourceFileType.RESOURCE);
		Assert.assertEquals("test1\r\ntest2", efile.readAll());
	}
}
