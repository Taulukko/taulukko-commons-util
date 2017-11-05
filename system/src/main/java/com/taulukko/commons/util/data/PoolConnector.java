package com.taulukko.commons.util.data;

import java.sql.Savepoint;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.taulukko.commons.TaulukkoException;

/*Utilizar o pool da apache commons*/
@Deprecated
public class PoolConnector
{

	private static int ACTIVE = 1;

	private static int IDLE = 2;

	private static int CLOSING = 4;

	protected CopyOnWriteArrayList<PseudoConnection> connections = new CopyOnWriteArrayList<PseudoConnection>();

	protected Logger logger = null;

	IFactory factory = null;

	/* config properties */

	private static int id = 0;

	// sleep Time
	// Tempo em ms de interação dos monitores
	// Default: 100
	protected int sleepTime = 100;

	// check time
	// Tempo em s para checar se a conexão esta funcionando
	// Default: 30
	protected int checkErrorTime = 30;

	// numero de conexoes idle, que a pseudoconexão fechou mas a conexão não ou
	// seja zero = sem pool
	// Default: 50
	protected int maxIdle = 0;

	// máximo de conexões em uso, ou seja que a pseudoconexão não foi fechada
	// Default: 100
	protected int maxActive = 100;

	// tempo em segundos pra considerar uma conexão ativa como abandonada
	// Default: 30
	protected int abandonnedTimeout = 30;

	// estrategia a ser adotada com conexões abandonadas
	// Opções: idle (vira idle), none (não faz nada)
	// Default: idle
	protected String abandonnedStrategy = "idle";

	// tempo em segundos aguardando nova conexão
	// Default: 60
	protected int maxWaitTimeout = 60;

	// sql de teste
	// Default: select 1 as test
	protected String sqlTest = "select 1 as test";

	private boolean closing = false;

	private Thread monitor = null;

	public PoolConnector(IFactory factory)
	{

		if (factory == null)
		{
			throw new NullPointerException();
		}

		this.factory = factory;

		monitor = new Thread(new Service(), "PoolConnectionMonitor");
		monitor.start();

	}

	public IEConector getConnection() throws TaulukkoException
	{

		if (closing)
		{
			throw new TaulukkoException("Pool closed");
		}

		double waitTime = 0;

		while (getActiveConectionsCount() >= maxActive)
		{
			try
			{
				waitTime += 0.1;
				Thread.sleep(100);
				if (waitTime > maxWaitTimeout)
				{
					throw new TaulukkoException(
							"Timeout, no connection available. Try increasing the property maxActive.");
				}
			}
			catch (InterruptedException e)
			{
				error(e);
				return null;
			}
		}

		PseudoConnection conector = null;
		if (getIdleConnectionsCount() > 0)
		{
			for (PseudoConnection con : connections)
			{
				boolean found = false;
				synchronized (con)
				{
					if (con.status == PoolConnector.IDLE)
					{
						con.status = PoolConnector.ACTIVE;
						found = true;
					}
				}
				if (found)
				{
					conector = con;
					conector.reopen(getStackTrace());
					return conector;
				}
			}
		}

		try
		{
			conector = new PseudoConnection(factory.getConnector());
			conector.reopen(getStackTrace());
			conector.status = PoolConnector.ACTIVE;
			connections.add(conector);
			return conector;
		}
		catch (TaulukkoException e)
		{
			error(e);
			closeAllConnections();
			throw new TaulukkoException("Connection cannot be created by Pool");
		}
	}

	public int getIdleConnectionsCount()
	{
		int cont = 0;
		for (PseudoConnection connection : connections)
		{
			if (connection.status == PoolConnector.IDLE)
			{
				cont++;
			}
		}

		return cont;
	}

	public int getActiveConectionsCount()
	{

		int cont = 0;
		for (PseudoConnection connection : connections)
		{
			if (connection.status == PoolConnector.ACTIVE)
			{
				cont++;
			}
		}

		return cont;
	}

	protected String getStackTrace()
	{
		String out = "";
		Thread thread = Thread.currentThread();
		StackTraceElement stackTraces[] = thread.getStackTrace();

		for (StackTraceElement stackTrace : stackTraces)
		{
			out += "\n" + stackTrace.toString();
		}
		return out;
	}

	protected void closeAllConnections()
	{
		for (PseudoConnection con : connections)
		{
			synchronized (con)
			{
				con.status = PoolConnector.CLOSING;
			}
			con.trueConnection.close();
			connections.remove(con);
		}
	}

	public void close() throws InterruptedException
	{
		closing = true;
		if (!monitor.isInterrupted())
		{
			monitor.interrupt();
		}

		Thread.sleep(sleepTime * 2);

		closeAllConnections();
	}

	protected void stackTraceInfo(String s)
	{
		info(getStackTrace() + s);
	}

	protected void stackTraceError(String s)
	{
		error(getStackTrace() + s);
	}

	protected void info(String s)
	{
		if (logger == null)
		{
			return;
		}
		logger.info(s);
	}

	protected void debug(String s)
	{
		if (logger == null)
		{
			return;
		}
		logger.debug(s);
	}

	protected void error(String s, Exception e)
	{
		if (logger == null)
		{
			return;
		}
		logger.error(s, e);
	}

	protected void error(String s)
	{
		if (logger == null)
		{
			return;
		}
		logger.error(s);
	}

	protected void error(Exception e)
	{
		error(e.getMessage(), e);
	}

	public void setLogger(Logger stdout)
	{
		ERecordSet.setLogger(stdout);
		this.logger = stdout;
	}

	public int getSleepTime()
	{
		return sleepTime;
	}

	public void setSleepTime(int sleepTime)
	{
		this.sleepTime = sleepTime;
	}

	public int getMaxIdle()
	{
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public int getMaxActive()
	{
		return maxActive;
	}

	public void setMaxActive(int maxActive)
	{
		this.maxActive = maxActive;
	}

	public int getAbandonnedTimeout()
	{
		return abandonnedTimeout;
	}

	public void setAbandonnedTimeout(int abandonnedTimeout)
	{
		this.abandonnedTimeout = abandonnedTimeout;
	}

	public String getAbandonnedStrategy()
	{
		return abandonnedStrategy;
	}

	public void setAbandonnedStrategy(String abandonnedStrategy)
	{
		this.abandonnedStrategy = abandonnedStrategy;
	}

	public int getMaxWaitTimeout()
	{
		return maxWaitTimeout;
	}

	public void setMaxWaitTimeout(int maxWaitTimeout)
	{
		this.maxWaitTimeout = maxWaitTimeout;
	}

	public String getSqlTest()
	{
		return sqlTest;
	}

	public void setSqlTest(String sqlTest)
	{
		this.sqlTest = sqlTest;
	}

	public Logger getLogger()
	{
		return logger;
	}

	public class PseudoConnection implements IEConector
	{

		private IEConector trueConnection = null;

		private boolean reopen = true;

		private boolean likeNew = true;

		private long live = 0;

		private int status = 0;

		private String openStackTrace = null;

		protected PseudoConnection(IEConector connector)
		{
			trueConnection = connector;
		}

		protected IEConector getTrueConnection()
		{
			return trueConnection;
		}

		protected void setTrueConnection(IEConector connector)
		{
			trueConnection = connector;
		}

		public void beginTrans() throws TaulukkoException
		{
			debug("beginTrans");
			// trueConnection.beginTrans();
		}

		public String clearString(String arg0)
		{
			debug("clearString");
			return trueConnection.clearString(arg0);
		}

		public Object clone()
		{
			info("Connection call clone, pool not use clone");
			return null;
		}

		public void close()
		{
			debug("close");
			this.live = System.currentTimeMillis();
			synchronized (this)
			{
				try
				{
					if (trueConnection.inTransaction())
					{
						trueConnection.rollbackTrans();
					}
				}
				catch (TaulukkoException e)
				{
					error(e);
				}
				this.status = PoolConnector.IDLE;
			}
		}

		public void commitTrans() throws TaulukkoException
		{
			debug("commitTrans");
			// trueConnection.commitTrans();
		}

		public void execute(String arg0) throws TaulukkoException
		{
			debug("execute[" + arg0 + "]");
			this.live = System.currentTimeMillis();
			likeNew = false;
			trueConnection.execute(arg0);
		}

		public boolean exist(String arg0) throws TaulukkoException
		{
			debug("exist");
			this.live = System.currentTimeMillis();
			likeNew = false;
			return trueConnection.exist(arg0);
		}

		public void forceClose()
		{
			debug("forceClose");
			this.live = System.currentTimeMillis();
			this.close();
		}

		public int getAffectedRows()
		{
			debug("getAffectedRows");
			if (likeNew)
			{
				return 0;
			}
			return trueConnection.getAffectedRows();
		}

		public boolean getCreateLog()
		{
			return trueConnection.getCreateLog();
		}

		public boolean getIsOpen()
		{
			debug("getIsOpen");
			return this.status == ACTIVE;
		}

		public int getLastKey()
		{
			debug("getLastKey");
			return trueConnection.getLastKey();
		}

		public long getMaxSleep()
		{
			stackTraceInfo("Connection call getMaxSleep, pool not use getMaxSleep");
			return -1;
		}

		public ERecordSet getRecordSet(String arg0) throws TaulukkoException
		{
			debug("getRecordSet[" + arg0 + "]");
			this.live = System.currentTimeMillis();
			likeNew = false;
			return trueConnection.getRecordSet(arg0);
		}

		public long getSleepTime()
		{
			stackTraceInfo("Connection call getSleepTime, pool not use getSleepTime");
			return -1;
		}

		public void open() throws TaulukkoException
		{
			stackTraceInfo("Connection call open, pool not use open");
		}

		public void rollbackTrans() throws TaulukkoException
		{
			debug("rollbackTrans");
			trueConnection.rollbackTrans();
		}

		public void setCreateLog(boolean arg0)
		{
			trueConnection.setCreateLog(arg0);
		}

		public void setMaxSleep(long arg0)
		{
			stackTraceInfo("Connection call setMaxSleep, pool not use setMaxSleep");
		}

		public void reopen(String openStackTrace)
		{
			this.openStackTrace = openStackTrace;
			this.live = System.currentTimeMillis();
			this.reopen = true;
			this.likeNew = true;
		}

		public long getReopenTime()
		{
			if (reopen)
			{
				return System.currentTimeMillis() - live;
			}
			return -1;
		}

		public Savepoint createSavePoint() throws TaulukkoException
		{
			debug("createSavePoint");
			return trueConnection.createSavePoint();
		}

		public void removeSavePoint(Savepoint save) throws TaulukkoException
		{
			debug("removeSavePoint");
			trueConnection.removeSavePoint(save);

		}

		public void rollbackTrans(Savepoint save) throws TaulukkoException
		{
			debug("rollback(Savepoint save) ");
			trueConnection.rollbackTrans(save);
		}

		public void commitTrans(Savepoint save) throws TaulukkoException
		{
			debug("commit(Savepoint save) ");
			trueConnection.commitTrans(save);
		}

		public boolean inTransaction() throws TaulukkoException
		{
			debug("inTransaction");
			return trueConnection.inTransaction();
		}

		@Override
		public void keepalive()
		{
			debug("keepalve");
			this.live = System.currentTimeMillis();
			likeNew = false;
		}
	}

	public class Service implements Runnable
	{

		public void run()
		{
			long checkErrorTimeAcummuled = 0;
			fixInvalidValues();
			while (!Thread.interrupted() && !closing)
			{
				checkErrorTimeAcummuled += sleepTime;
				if ((checkErrorTimeAcummuled / 1000) > checkErrorTime)
				{
					checkErrorTimeAcummuled = 0;
					checkErrorsConnections();
				}

				checkAbandonnedConnections();
				checkIdleExceeded();
				try
				{
					Thread.sleep(sleepTime);
				}
				catch (InterruptedException e)
				{
					info("PoolConnector.Service has interrupted");

					if (!closing)
					{
						try
						{
							close();
						}
						catch (InterruptedException ie)
						{
							info("PoolConnector.Service has interrupted and have a error");
						}
					}
				}
			}
		}

		private void checkErrorsConnections()
		{
			for (PseudoConnection connection : connections)
			{
				synchronized (connection)
				{
					if (connection.status == PoolConnector.IDLE)
					{
						ERecordSet recordset = null;
						try
						{
							recordset = connection.getRecordSet(sqlTest);
							recordset.close();
							connection.close();
						}
						catch (Exception e)
						{

							error("Connection refused. No aproved in test : "
									+ e.getMessage(), e);
							synchronized (connection)
							{
								connection.status = PoolConnector.CLOSING;
								IEConector trueConnection = connection
										.getTrueConnection();
								trueConnection.forceClose();
								connections.remove(connection);
							}
						}

					}
				}
			}
		}

		private void fixInvalidValues()
		{
			if (maxActive < 1)
			{
				maxActive = 1;
			}
			if (maxIdle < 1)
			{
				maxIdle = 1;
			}
		}

		private void checkIdleExceeded()
		{
			int willBeClosed = getIdleConnectionsCount() - maxIdle;
			if (willBeClosed > 0)
			{
				for (PseudoConnection connection : connections)
				{

					boolean found = false;
					synchronized (connection)
					{
						if (connection.status == PoolConnector.IDLE)
						{
							connection.status = PoolConnector.CLOSING;
							found = true;
						}
					}
					if (found)
					{
						synchronized (connection)
						{
							IEConector trueConnection = connection
									.getTrueConnection();
							trueConnection.forceClose();
							connections.remove(connection);
						}
						willBeClosed--;
						if (willBeClosed == 0)
						{
							return;
						}
					}
				}
			}
		}

		private void checkAbandonnedConnections()
		{
			for (PseudoConnection connection : connections)
			{
				if (connection.status == PoolConnector.ACTIVE)
				{
					if (connection.getReopenTime() > (abandonnedTimeout * 1000))
					{
						error("Abandonned Connection Found!"
								+ connection.openStackTrace);
						if (abandonnedStrategy.toLowerCase().equals("idle"))
						{
							connection.forceClose();
						}
					}
				}
			}
		}
	}
}
