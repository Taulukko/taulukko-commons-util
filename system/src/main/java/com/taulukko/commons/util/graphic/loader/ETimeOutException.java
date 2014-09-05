/*
 * Created on 18/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.graphic.loader;

import com.taulukko.commons.util.exception.EException;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ETimeOutException extends EException
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public ETimeOutException()
    {
        super("Time Out in Load",2);
    }
}
