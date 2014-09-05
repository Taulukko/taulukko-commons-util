package com.taulukko.commons.util.httpserver;

public interface RequestListenner
{
	public ListnnerResponse getResponse(String path);
}
