/*
 * Created on 23/02/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

import java.util.Enumeration;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EVisitiorSet<O> implements Enumeration
{

	private ESet<O> _set;

	private int _count = 0;

	/**
	 * 
	 */
	public EVisitiorSet(ESet<O> set)
	{
		_set = set;
	}

	/* (non-Javadoc)
	 * @see java.util.Enumeration#hasMoreElements()
	 */
	public boolean hasMoreElements()
	{
		return _count < _set.getLength();
	}

	/* (non-Javadoc)
	 * @see java.util.Enumeration#nextElement()
	 */
	public O nextElement()
	{
		return _set.get(_count++);
	}

}
