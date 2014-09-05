/*
 * Created on 22/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.window;

import com.taulukko.commons.util.exception.EException;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EDisplayModeNotSuportted extends EException
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public EDisplayModeNotSuportted()
    {
        super("Display Mode not Suportted! Read a video board manual for more instructions",1);   
    }
}
