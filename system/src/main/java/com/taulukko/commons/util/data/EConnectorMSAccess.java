package com.taulukko.commons.util.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.taulukko.commons.util.lang.EString;

public class EConnectorMSAccess extends EConnector implements IEConector
{

	private String _path = null;
	
	public EConnectorMSAccess(String sPath)
	{
		_path = sPath;
	}

	public void execute(String sClausule) throws SQLException 
	{
		Statement st = this.getJConnector().createStatement();
		st.execute(sClausule);
	}

	public ERecordSet getRecordSet(String sClausule) throws SQLException
	{
			if (!this.getIsOpen())
			{
				throw new SQLException("Connection is Closed!");
			}
			
			Statement st = this.getJConnector().createStatement();

			return new ERecordSet(st.executeQuery(sClausule), st);
	}

	public void close()
	{
		try
		{
			Connection con = this.getJConnector();
			if(!con.isClosed())
			{
				con.close();
			}
		}
		catch (Exception e)
		{
		}
	}

	public void beginTrans() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public void commitTrans() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public void rollbackTrans() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public void open() throws SQLException
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = 
				DriverManager.getConnection(
					"jdbc:odbc:Driver="
						+ "{Microsoft Access Driver (*.mdb)};"
						+ "DBQ="
						+ _path,
					"",
					"");
			
			this.setJConnector(con);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public Object clone()
	{
		return new EConnectorMSAccess(_path);
	}

	public long getSleepTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String clearString(String value)
	{
		EString ret = new EString(value.trim());
		ret = ret.replace(new EString("\\"), new EString("\\\\"));
		ret = ret.replace(new EString("'"), new EString("''"));
		return ret.toString();
	}

	public Savepoint createSavePoint() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSavePoint(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public void rollbackTrans(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public void commitTrans(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	public boolean inTransaction() throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keepalive()
	{
		// TODO Auto-generated method stub
		
	}

}