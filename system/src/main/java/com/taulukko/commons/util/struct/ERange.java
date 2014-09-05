/*
 * Created on 23/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.struct;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ERange extends EBase {
	
	private int m_iMinnor =0;
	private int m_iMajor =0;
	
	public ERange (int iMinnor, int iMajor)
	{
		m_iMinnor = iMinnor;
		m_iMajor = iMajor;
	}

	
	public boolean contains(int iValue)
	{
		return iValue >= m_iMinnor && iValue <= m_iMajor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {		
		return new ERange(m_iMinnor,m_iMajor);
	}

}
