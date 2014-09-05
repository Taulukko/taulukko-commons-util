package com.taulukko.commons.util.struct;

import java.util.Iterator;
import java.util.Enumeration;

public class EVisitorVector implements Iterator, Enumeration
{
	int iIndexAtual=0;
	EVector m_v=null;
	
	public EVisitorVector (EVector v)
	{
		m_v = v;	
	}
	
	public boolean hasMoreElements()
	{
		return m_v != null && iIndexAtual < m_v.getLength();
	}
	
	public boolean hasNext()
	{
		return hasMoreElements();
	}
	
	public Object nextElement()
	{
		return m_v.get(iIndexAtual++);		
	}
	
	public Object next()
	{
		return nextElement();
	}
	
	public void remove()
	{
		//remove o ultimo elemento
		m_v.redimPreserve(m_v.getLength()-1);
	}
	
}