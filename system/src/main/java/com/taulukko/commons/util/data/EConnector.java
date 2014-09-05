package com.taulukko.commons.util.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EConnector implements IEConector
{

	private long _maxSleep = 0;
	
	private boolean _createLog = false;

	private Connection _con = null;

	protected int _affectedRows = 0;

	protected int _lastKey = -1;

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo: Inicializa uma
	 * transao.
	 * 
	 * @see br.com.vb.data.IConector#BeginTrans()
	 */
	public void beginTrans() throws SQLException
	{
		this.getJConnector().setAutoCommit(false);
	}

	public abstract void close();

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#CommitTrans()
	 */
	public void commitTrans() throws SQLException
	{
		this.getJConnector().commit();
		this.getJConnector().setAutoCommit(true);
	}

	public abstract void execute(String ssql) throws SQLException;

	public abstract ERecordSet getRecordSet(String ssql) throws SQLException;

	public abstract void open() throws SQLException;
	
	 
	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#RollbackTrans()
	 */
	public void rollbackTrans() throws SQLException
	{
		this.getJConnector().rollback();
		this.getJConnector().setAutoCommit(true);

	}

	

	public void setMaxSleep(long maxSleep)
	{
		_maxSleep = maxSleep;
	}

	public long getMaxSleep()
	{
		return _maxSleep;
	}

	protected Connection getJConnector()
	{
		return _con;
	}

	protected void setJConnector(Connection con)
	{
		_con = con;
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:11/09/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IVBConector#getCreateLog()
	 */
	public boolean getCreateLog()
	{
		return this._createLog;
	}

	public boolean getIsOpen()
	{
		try
		{
			return !_con.isClosed();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:11/09/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IVBConector#setCreateLog(boolean)
	 */
	public void setCreateLog(boolean createLog)
	{
		_createLog = createLog;
	} 

	public int getAffectedRows()
	{
		return _affectedRows;
	}

	public abstract Object clone();

	public void forceClose()
	{ 
		
		try
		{
			if(!this.getJConnector().getAutoCommit())
			{
				this.rollbackTrans();
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			// fine
			System.out.println("FINE:Rollback-Connection use forceClose.");
		}
		try
		{
			if(this.getIsOpen())
			{
				this.close();
			}
		}
		catch (Exception e)
		{
			// fine
			System.out.println("FINE:Close-Connection use forceClose.");
		}
	}

	public int getLastKey()
	{
		return _lastKey;
	}
	
	public boolean exist(String sql) throws SQLException
	{
		
		ERecordSet recordset = getRecordSet(sql);
		ResultSet rs = recordset.getResultSet();
		boolean ret = rs.next();
		recordset.close();
		return ret;
	}

}
