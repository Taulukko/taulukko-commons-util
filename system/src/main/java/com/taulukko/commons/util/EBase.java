/*
 * Created on 24/07/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util;

import java.io.Serializable;

import com.taulukko.commons.util.exception.EException;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EBase implements Serializable
{
    public EException m_lastException = null;
	public abstract Object clone() throws CloneNotSupportedException;
    public EException getLastException()
    {
        return m_lastException;
    }
    protected void setLastException(EException e)
    {
        m_lastException = e;
    }
}
