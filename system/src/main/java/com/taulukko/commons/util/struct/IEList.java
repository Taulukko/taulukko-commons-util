/*
 * Created on 01/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

import java.util.Enumeration;

/**
 * @author ecarli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IEList<V> 
{
    public abstract int getLength();
    public abstract V remove() throws ENoSearchException;
    public abstract V get() throws ENoSearchException;
    public abstract void add(V obj);
    public abstract Object clone();
    public abstract Enumeration getElements();
    public abstract EIterator getItens();
    public abstract IEList<V> revert();
	public abstract boolean getIsEmpty();
	public abstract boolean getIsElement(V obj);
	public abstract V remove(V obj);
}