package com.taulukko.commons.util.httpserver;

import java.io.InputStream;

public class ListnnerResponse
{
	private byte _data[] = null;

	private boolean _ignore = false;

	private boolean _notFound = false;

	private boolean _replace = false;

	private String _replacePath = null;

	private InputStream _input = null;

	private boolean _useInput = false;

	public InputStream getInput()
	{
		return _input;
	}

	public void setInput(InputStream input)
	{
		_input = input;
	}

	public boolean getUseInput()
	{
		return _useInput;
	}

	public void setUseInput(boolean useInput)
	{
		_useInput = useInput;
	}

	public byte[] getData()
	{
		return _data;
	}

	public void setData(byte[] date)
	{
		_data = date;
	}

	public boolean getIgnore()
	{
		return _ignore;
	}

	public void setIgnore(boolean ignore)
	{
		_ignore = ignore;
	}

	public boolean getNotFound()
	{
		return _notFound;
	}

	public void setNotFound(boolean notFound)
	{
		_notFound = notFound;
	}

	public boolean getReplace()
	{
		return _replace;
	}

	public void setReplace(boolean replace)
	{
		_replace = replace;
	}

	public String getReplacePath()
	{
		return _replacePath;
	}

	public void setReplacePath(String path)
	{
		_replacePath = path;
	}
}
