package com.taulukko.commons.util.struct;

import java.util.Enumeration;

public abstract class EAbstractList<V> implements IEList<V>
{
	public abstract int getLength();
	public abstract V remove() throws ENoSearchException;
	public abstract V get() throws ENoSearchException;
	public abstract void add(V obj);
	public abstract Object clone();
	public abstract Enumeration getElements();
	public abstract EIterator getItens();
	public abstract IEList revert();
	public abstract boolean getIsEmpty();
	public abstract V remove(V obj);
		
	public boolean getIsElement(V obj)
	{
		Enumeration varEnum = this.getElements();
		
		while(varEnum.hasMoreElements())
		{
			if(varEnum.nextElement().equals(obj))
			{
				//rodou por todos os elementos e achou o item
				return true;
			}
		}
		//rodou por todos os elementos e nao achou o item
		return false;
	}
	
}

