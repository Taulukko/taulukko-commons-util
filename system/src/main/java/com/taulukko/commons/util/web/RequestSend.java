package com.taulukko.commons.util.web;

public class RequestSend
{
	private String _send;

	private long _time;

	public RequestSend(String send)
	{
		_send = send;
		_time = System.currentTimeMillis();
	}

	public String getSend()
	{
		return _send;
	}

	public void setSend(String send)
	{
		_send = send;
	}

	public long getTime()
	{
		return _time;
	}
}
