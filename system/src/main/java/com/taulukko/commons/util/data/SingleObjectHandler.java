package com.taulukko.commons.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class SingleObjectHandler<T> implements ResultSetHandler<T>
{
	private String column = null;

	public SingleObjectHandler(String column)
	{
		this.column = column;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T handle(ResultSet result) throws SQLException
	{
		if (!result.next())
		{
			return null;
		}

		return (T) result.getObject(this.column);
	}
}
