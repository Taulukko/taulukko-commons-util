/*
 * Created on 01/01/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.exception;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EException extends Exception {	
	
	private int m_iCode=0;
	
	
	public EException(String sMsg, int iCode)
	{
		super(sMsg);
		m_iCode=iCode;
	}
	
	public int getCode()
	{
		return m_iCode;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {		
		return super.getMessage() + " - Code:" + this.getCode();
	}

}
