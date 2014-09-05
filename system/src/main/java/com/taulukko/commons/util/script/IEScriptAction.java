/*
 * Created on 16/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.script;

import com.taulukko.commons.util.struct.EDataMap;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IEScriptAction
{
    public static final String TP_SOURCE = "SOURCE";

    public void run(EDataMap stackList);
}
