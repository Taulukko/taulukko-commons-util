package com.taulukko.commons.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ERecordSet
{
	private ResultSet _rs = null;

	private Statement _st = null;

	private int _rowsCount = -1;

	private static Logger _log;

	public ERecordSet(ResultSet rs, Statement st)
	{
		_rs = rs;
		_st = st;

		try
		{
			_rs.last();
			_rowsCount = _rs.getRow();
			_rs.beforeFirst();
		}
		catch (SQLException e)
		{
			_rowsCount = -1;
		}
	}

	private void error(Throwable e)
	{
		error(e.getMessage(), e);
	}

	private void error(String s, Throwable e)
	{
		if (_log != null)
		{
			_log.error(s, e);
		}
	}

	public void close() throws SQLException
	{
		_st.close();
		_rs.close();
	}

	public void forceClose()
	{

		try
		{
			_st.close();
		}
		catch (SQLException e)
		{
			error(e);
		}

		try
		{
			_rs.close();
		}
		catch (SQLException e)
		{
			error(e);
		}

	}

	public ResultSet getResultSet()
	{
		return _rs;
	}

	public Statement getStatement()
	{
		return _st;
	}

	public int getRowsCount()
	{
		return _rowsCount;
	}

	public static Logger getLogger()
	{
		return _log;
	}

	public static void setLogger(Logger _log)
	{
		ERecordSet._log = _log;
	}
}
