/*
 * Created on 23/02/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

import com.taulukko.commons.util.lang.ESize;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ETable
{

	private ESize m_size;	
	Object m_objs [][];
	/**
	 * 
	 */
	public ETable(ESize size)
	{
		m_size=size;
	
		m_objs= new Object[size.getWidth()][size.getHeight()];	
	}

	public Object get(int iWeight, int iHeight)
	{
		return m_objs[iWeight][iHeight];
	}


	public void set(Object obj,int iWeight, int iHeight)
	{
		m_objs[iWeight][iHeight]=obj;
	}
	
	/**
	 * @return
	 */
	public ESize getSize()
	{
		return m_size;
	}

	
}
