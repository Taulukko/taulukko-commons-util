/*
 * Created on 25/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import java.sql.Savepoint;

import com.taulukko.commons.TaulukkoException;

/**
 * @author ecarli
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IEConector {
	public boolean getIsOpen();

	public ERecordSet getRecordSet(String sSQL) throws TaulukkoException;

	public void execute(String sSQL) throws TaulukkoException;

	public void beginTrans() throws TaulukkoException;

	public void commitTrans() throws TaulukkoException;

	public void rollbackTrans() throws TaulukkoException;

	public void close();

	public void open() throws TaulukkoException;

	public Object clone();

	public void setCreateLog(boolean bCreateLog);

	public boolean getCreateLog();

	public int getAffectedRows();

	public void forceClose();

	public void setMaxSleep(long time);

	public long getMaxSleep();

	public long getSleepTime();

	public int getLastKey();

	public String clearString(String value);

	public boolean exist(String sql) throws TaulukkoException;

	public Savepoint createSavePoint() throws TaulukkoException;

	public void removeSavePoint(Savepoint save) throws TaulukkoException;

	public void rollbackTrans(Savepoint save) throws TaulukkoException;

	public void commitTrans(Savepoint save) throws TaulukkoException;

	public boolean inTransaction() throws TaulukkoException;

	public void keepalive();
}
