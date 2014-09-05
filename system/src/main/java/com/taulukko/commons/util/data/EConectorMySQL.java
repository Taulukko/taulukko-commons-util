/*
 * Created on 25/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.taulukko.commons.util.lang.EString;

/**
 * @author ecarli
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EConectorMySQL extends EConnector implements IEConector
{
	private String _userName = null;

	private String _password = null;

	private String _host = null;

	private String _catalog = null;

	static
	{

		try
		{
			// The newInstance() call is a work around for some
			// broken Java implementations
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception ex)
		{
			// handle the error
			ex.printStackTrace();
		}
	}

	public EConectorMySQL(String sUsername, String sPassword, String sHost,
			String sCatalog)
	{
		this._catalog = sCatalog;
		this._password = sPassword;
		this._host = sHost;
		this._userName = sUsername;
	}

	public void open() throws SQLException
	{
		// jdbc URL
		String sDbUrl = "jdbc:mysql://" + this._host + "/" + this._catalog;

		// driver a ser usado
		String sDbClass = "com.mysql.jdbc.Driver";

		// cria o log
		if (this.getCreateLog())
		{
			System.out.println("Loading JDBC driver '" + sDbClass + "'");
			System.out.println("Open Connection to " + this._userName);
		}

		try
		{
			// se conecta
			Connection con = DriverManager.getConnection(sDbUrl,
					this._userName, this._password);
			if (con == null)
			{
				throw new SQLException(
						"User not have permission for this catalog!");
			}
			else if (con.isClosed())
			{
				throw new SQLException("DataBase Server is bussnes!");
			}
			else
			{
				this.setJConnector(con);
			}
			
		}
		catch (Exception ex)
		{
			SQLException sqle = new SQLException(ex.getMessage());
			/*
			 * NAO FUNCIONA NO WEBSPHERE sqle.setStackTrace(ex.getStackTrace());
			 */
			ex.printStackTrace();
			throw sqle;
		}

	}

	public EConectorMySQL(String sUsername, String sPassword, String sCatalog)
	{
		this(sUsername, sPassword, "", sCatalog);
	}

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

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#CommitTrans()
	 */
	public void commitTrans() throws SQLException
	{
		this.getJConnector().commit();
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#execute(java.lang.String)
	 */
	public void execute(String sSQL) throws SQLException
	{
		// gera a clausula
		Statement st = this.getJConnector().createStatement();

		// executa a clausula
		st.execute(sSQL, Statement.RETURN_GENERATED_KEYS); // fecha o statenent
		_affectedRows = st.getUpdateCount(); 
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next())
		{
			_lastKey = rs.getInt(1);
		}
		else
		{
			_lastKey = -1;
		}
		st.close();
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#getRecordSet(java.lang.String)
	 */
	public ERecordSet getRecordSet(String sSQL) throws SQLException
	{
		Connection con = this.getJConnector();

		if (con == null)
		{
			throw new SQLException("Connector fail!");
		}

		// gera a clausula
		Statement st = con.createStatement();

		if (st == null)
		{
			throw new SQLException("Connector fail!");
		}

		// cria o resultset
		ResultSet rs = st.executeQuery(sSQL);

		// selecionando itens
		return new ERecordSet(rs, st);
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#RollbackTrans()
	 */
	public void rollbackTrans() throws SQLException
	{
		this.getJConnector().rollback();

	}

	public void close()
	{
		try
		{
			Connection con = this.getJConnector();
			if (!con.isClosed())
			{
				con.close();

				// cria log
				if (this.getCreateLog())
				{
					System.out.println("Close Connection to "
							+ this._userName);
				}
			}
		}
		catch (Exception e)
		{
			/*
			 * 
			 * em caso de erro ignora( porque a conexao ja esta fechada, e
			 * fechar em exesso no considerado erro no Conector
			 */

		}
	}

	public Object clone()
	{
		// retorna uma cpia
		return new EConectorMySQL(_userName, _password, _host, _catalog);
	}

	public long getSleepTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String clearString(String value)
	{
		return EDataUtilMySQL.clearString(value);
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
		return !this.getJConnector().getAutoCommit();
	}

	@Override
	public void keepalive()
	{
		// TODO Auto-generated method stub
		
	}
}
