/*
 * Created on 05/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.thread;

import java.util.Iterator;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.exception.EException;
import com.taulukko.commons.util.struct.EDataMap;

/**
 * @author Edson
 * 
 */
public class EThreadNode extends EBase implements IERunnable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private int m_iTime = 0;

	private Thread m_thread = null;

	private EDataMap<String, IEScriptActionThread> m_threads = new EDataMap<String, IEScriptActionThread>();

	private boolean m_bIsAlive = true;

	public EThreadNode(int iTime) {
		m_iTime = iTime;
		m_thread = new Thread(this);
		m_thread.start();
	}

	private EThreadNode(EDataMap<String, IEScriptActionThread> dataMap,
			Thread thread, int iTime) {
		m_threads = dataMap;
		m_iTime = iTime;

	}

	public void add(String sName, IEScriptActionThread script) {
		if (m_threads.containsKey(sName)) {
			m_threads.remove(sName);
		}
		m_threads.add(sName, script);
	}

	public void remove(String sName) {
		m_threads.remove(sName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.evon.EBase#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return new EThreadNode(m_threads, m_thread, m_iTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (m_bIsAlive) {
			try {
				Thread.sleep(m_iTime);
				Iterator<String> iterator = m_threads.keySet().iterator();

				while (iterator.hasNext()) {

					// para cada script, o executa
					String sKey = iterator.next();
					IEScriptActionThread script = m_threads.get(sKey);
					if (null != script) {
						script.run(null);
						
						if(script.getIsUniqueRun())
						{
							m_threads.remove(sKey);
						}
					}
				}

			} catch (Exception e) {
				EException ee = new EException(e.getMessage(), 1);
				ee.setStackTrace(e.getStackTrace());

				this.setLastException(ee);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.evon.thread.IERunnable#getIsAlive()
	 */
	public boolean getIsAlive() {
		return m_bIsAlive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.evon.thread.IERunnable#destroy()
	 */
	public void destroy() {
		m_bIsAlive = false;
		m_threads.clear();
	}

}
