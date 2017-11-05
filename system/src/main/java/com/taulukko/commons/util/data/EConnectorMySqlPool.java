package com.taulukko.commons.util.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.taulukko.commons.TaulukkoException;

/**
 * @author ecarli
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EConnectorMySqlPool extends EConnector implements IEConector, Runnable {
	private String _catalog;

	private Thread _thread = null;

	private long _sleepTime = 0;

	private boolean _exit = false;

	public EConnectorMySqlPool(String conection) {
		_catalog = conection;
	}

	public void open() throws TaulukkoException {

		if (this.getMaxSleep() > 0) {
			_sleepTime = System.currentTimeMillis();
			_thread = new Thread(this);
			_thread.start();
		}

		try {

			// Obtm a raiz da hierarquia de nomes
			InitialContext contexto = new InitialContext();

			// Obtm a origem dos dados
			Context envContext = (Context) contexto.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(_catalog);
			Connection conn = ds.getConnection();

			// Retorna a conexo
			this.setJConnector(conn);
			_sleepTime = System.currentTimeMillis();

		} catch (Exception ex) {
			TaulukkoException sqle = new TaulukkoException(ex.getMessage());
			/*
			 * NAO FUNCIONA NO WEBSPHERE sqle.setStackTrace(ex.getStackTrace());
			 */
			ex.printStackTrace();
			throw sqle;
		}
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#execute(java.lang.String)
	 */
	public void execute(String sSQL) throws TaulukkoException {
		_sleepTime = System.currentTimeMillis();
		// gera a clausula
		Statement st;
		try {
			st = this.getJConnector().createStatement();

			st.execute(sSQL, Statement.RETURN_GENERATED_KEYS); // fecha o statenent
			_affectedRows = st.getUpdateCount();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				_lastKey = rs.getInt(1);
			} else {
				_lastKey = -1;
			}
			st.close();
			_sleepTime = System.currentTimeMillis();
		} catch (SQLException e) {
			throw new TaulukkoException(e);
		}
	}

	/*
	 * (non-Javadoc) Autor:ecarli Data:25/08/2003 Objetivo:
	 * 
	 * @see br.com.vb.data.IConector#getRecordSet(java.lang.String)
	 */
	public ERecordSet getRecordSet(String sSQL) throws TaulukkoException {
		_sleepTime = System.currentTimeMillis();
		Connection con = this.getJConnector();

		if (con == null) {
			throw new TaulukkoException("Connector fail!");
		}

		// gera a clausula
		Statement st;
		try {
			st = con.createStatement();

			if (st == null) {
				throw new TaulukkoException("Connector fail!");
			}

			// cria o resultset
			ResultSet rs = st.executeQuery(sSQL);
			_sleepTime = System.currentTimeMillis();
			// selecionando itens
			return new ERecordSet(rs, st);
		} catch (SQLException e) {
			throw new TaulukkoException(e);
		}
	}

	public void close() {
		try {
			_sleepTime = System.currentTimeMillis();
			Connection con = this.getJConnector();
			if (!con.isClosed()) {
				con.close();

				// cria log
				if (this.getCreateLog()) {
					System.out.println("Close Connection ");
				}
			}
			_sleepTime = System.currentTimeMillis();
			_exit = true;
		} catch (Exception e) {
			/*
			 * 
			 * em caso de erro ignora( porque a conexao ja esta fechada, e fechar em exesso
			 * no considerado erro no Conector
			 */

		}
	}

	public Object clone() {
		_sleepTime = System.currentTimeMillis();
		// retorna uma cpia
		return new EConnectorMySqlPool(_catalog);
	}

	public long getSleepTime() {
		return _sleepTime;
	}

	public String clearString(String value) {
		return EDataUtilMySQL.clearString(value);
	}

	public void run() {
		try {
			while (_thread.isAlive() && !_exit) {
				long diff = System.currentTimeMillis() - _sleepTime;
				if (diff > this.getMaxSleep()) {
					this.forceClose();
					// System.out.println("Conexo fechada a fora por timeout.");
				}
				if (!_exit) {
					Thread.sleep(this.getMaxSleep());
				}

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Savepoint createSavePoint() throws TaulukkoException {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSavePoint(Savepoint save) throws TaulukkoException {
		// TODO Auto-generated method stub

	}

	public void rollbackTrans(Savepoint save) throws TaulukkoException {
		// TODO Auto-generated method stub

	}

	public void commitTrans(Savepoint save) throws TaulukkoException {
		// TODO Auto-generated method stub

	}

	public boolean inTransaction() throws TaulukkoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void keepalive() {
		// TODO Auto-generated method stub

	}
}
