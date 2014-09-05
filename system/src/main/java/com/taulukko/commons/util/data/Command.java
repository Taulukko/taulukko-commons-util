package com.taulukko.commons.util.data;

public class Command
{

	private String sql = null;

	private Object parameters[] = null;

	public Command(String sql, Object... args)
	{
		this.sql = sql;
		this.parameters = args;
	}

	public String getSql()
	{
		return sql;
	}

	public Object[] getParameters()
	{
		return parameters;
	}

}
