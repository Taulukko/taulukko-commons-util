/*
 * Created on 16/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.thread;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IERunnable extends Runnable {
	public boolean getIsAlive();
	public void destroy();
}
