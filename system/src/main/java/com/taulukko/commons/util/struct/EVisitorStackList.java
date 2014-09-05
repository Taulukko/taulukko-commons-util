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
public class EVisitorStackList<V> implements EIterator, Enumeration<V>
{

	private EStackList<V> m_pile=null;

    /**Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * 
     */
    public EVisitorStackList(EStackList<V> pile)
    {        
		m_pile=(EStackList<V>)pile.clone();        
    }
    
    

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see br.com.vb.struct.EIterator#hasNext()
     */
    public boolean hasNext()
    {	
        return !m_pile.getIsEmpty();
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see br.com.vb.struct.EIterator#next()
     */
    public V next() 
    {        
    	try
    	{
			return m_pile.remove();
    	}
    	catch(ENoSearchException e)    	
    	{
    		return null;
    	}		        
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see br.com.vb.struct.EIterator#remove()
     */
    public void remove()
    {
    	System.out.println("Pile List Not implement remove Interetor!");
        this.next();
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see java.util.Enumeration#hasMoreElements()
     */
    public boolean hasMoreElements()
    {        
        return this.hasNext();
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see java.util.Enumeration#nextElement()
     */
    public V nextElement()
    {        
        return this.next();
    }

}
