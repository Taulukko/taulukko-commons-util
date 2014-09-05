/*
 * Created on 25/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author ecarli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IEConector
{
	public boolean getIsOpen();
	public ERecordSet getRecordSet(String sSQL)throws SQLException;
	public void execute(String sSQL) throws SQLException;
	public void beginTrans() throws SQLException;
	public void commitTrans() throws SQLException;
	public void rollbackTrans() throws SQLException;
	public void close();
	public void open() throws SQLException;
	public Object clone();
	public void setCreateLog(boolean bCreateLog);
	public boolean getCreateLog();
	public int 	getAffectedRows();
	public void forceClose();
	public void setMaxSleep(long time);
	public long getMaxSleep();
	public long getSleepTime();
	public int getLastKey();
	public String clearString(String value);
	public boolean exist(String sql) throws SQLException;
	public Savepoint createSavePoint() throws SQLException;
	public void removeSavePoint(Savepoint save) throws SQLException;
	public void rollbackTrans(Savepoint save) throws SQLException;
	public void commitTrans(Savepoint save) throws SQLException;
	public boolean inTransaction() throws SQLException;
	public void keepalive();
}
