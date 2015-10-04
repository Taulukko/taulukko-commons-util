package com.taulukko.commons.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class EResourceGet {

	private EResource resource = null;

	protected EResourceGet(EResource resource) {
		this.resource = resource;
	}

	public InputStream asInputStream() throws IOException {
		InputStream input = getClass().getResourceAsStream(resource.getPath());
		return input;
	}

	public String asString() throws IOException {
		InputStream input = getClass().getResourceAsStream(resource.getPath());
		StringWriter output = new StringWriter();
		IOUtils.copy(input, output);
		String content = output.toString();
		return content;
	}
}
