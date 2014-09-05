package com.taulukko.commons.util.data;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface IFactory
{
	public IEConector getConnector() throws SQLException;
	public DataSource getDataSource() throws SQLException;
}
