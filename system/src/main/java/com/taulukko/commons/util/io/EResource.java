package com.taulukko.commons.util.io;

public class EResource {

	private String path;

	public EResource(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public EResourceGet get() {
		return new EResourceGet(this);
	}
}
