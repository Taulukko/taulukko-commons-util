/*
 * Created on 01/01/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.config.serialization;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.taulukko.commons.util.config.EConfigException;
import com.taulukko.commons.util.config.IEConfigLoader;
import com.taulukko.commons.util.struct.ETree;

/**
 * @author Edson
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EConfigLoader implements IEConfigLoader, Serializable
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private static final String TP_CONFIG_FILE = "config.xml";

    public static ETree load() throws EConfigException
    {
        ETree ret = null;
        try
        {
            FileInputStream fi = new FileInputStream(TP_CONFIG_FILE);
            ObjectInputStream si = new ObjectInputStream(fi);
            ret = (ETree) si.readObject();
        }
        catch (Exception e)
        {
            EConfigException ece =new EConfigException(e.getMessage(),1); 
            ece.setStackTrace(e.getStackTrace());
            throw ece;
        }
        return ret;
    }
}
