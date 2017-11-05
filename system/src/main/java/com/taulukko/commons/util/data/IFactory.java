package com.taulukko.commons.util.data;

import javax.sql.DataSource;

import com.taulukko.commons.TaulukkoException;

public interface IFactory
{
	
	@Deprecated
	/***Use getDataSource().getConnection()*/
	public IEConector getConnector() throws TaulukkoException;
	public DataSource getDataSource() throws TaulukkoException;
}
