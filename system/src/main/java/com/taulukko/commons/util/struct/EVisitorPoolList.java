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
public class EVisitorPoolList<P> implements EIterator<P>, Enumeration<P>
{

	private EPoolList<P> m_pile=null;

    /**Autor:ecarli
     * Data:01/09/2003
     * Objetivo:
     * 
     */
    public EVisitorPoolList(EPoolList<P> pile)
    {        
		m_pile=(EPoolList<P>)pile.clone();        
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
    public P next() 
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
    	System.out.println("Pool List Not implement remove Interetor!");
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
    public P nextElement()
    {        
        return this.next();
    }

}
