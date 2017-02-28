package com.taulukko.commons.util.data;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface IFactory
{
	
	@Deprecated
	/***Use getDataSource().getConnection()*/
	public IEConector getConnector() throws SQLException;
	public DataSource getDataSource() throws SQLException;
}
