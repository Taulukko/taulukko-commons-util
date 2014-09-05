package com.taulukko.commons.util.web.beans;
/*
 * Created on 03/03/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BeanBase
{
    private boolean m_bEmpty = false;
    

    /**
     * 
     */
    public BeanBase()
    {
        super();
    }

    public  boolean getEmpty()
    {
        return m_bEmpty;
    }
    
    public  void setEmpty(boolean bEmpty)
    {
        m_bEmpty = bEmpty;
    }
}
