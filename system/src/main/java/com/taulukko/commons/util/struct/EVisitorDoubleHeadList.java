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
public class EVisitorDoubleHeadList implements EIterator, Enumeration
{

	private EDoubleHeadList m_list=null;

    /**Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * 
     */
    public EVisitorDoubleHeadList(EDoubleHeadList list)
    {        
		m_list=(EDoubleHeadList)list.clone();        
    }
    
    

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see br.com.vb.struct.EIterator#hasNext()
     */
    public boolean hasNext()
    {	
        return !m_list.getIsEmpty();
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * @see br.com.vb.struct.EIterator#next()
     */
    public Object next() 
    {        
    	try
    	{
			return m_list.removeFirst();
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
    	System.out.println("Double Head List Not implement remove Interetor!");
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
    public Object nextElement()
    {        
        return this.next();
    }

}
