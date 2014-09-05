/*
 * Created on 16/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.struct;

import java.util.HashMap;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EDataMap<K, V> extends HashMap<K, V>
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public void add(K k, V v)
    {
        this.put(k, v);
    }

    public V get(Object o)
    {
        return super.get(o);
    }

    public int getLength()
    {
        return super.size();
    }

    public V remove(Object o)
    {
        return super.remove(o);
    }
    
    public EVector<V> toVector()
    {
        EVector<V> ret = new EVector<V>(this.getLength());
        Object[] values = this.values().toArray();
        for(int iCont=0;iCont<values.length;iCont++)
        {
            ret.set(iCont,(V)values[iCont]);
        }
        //retorna a conversao
        return ret;
    }
    
    public V get(int iIndex)
    {
        return (V)this.values().toArray()[iIndex];
    }
}
