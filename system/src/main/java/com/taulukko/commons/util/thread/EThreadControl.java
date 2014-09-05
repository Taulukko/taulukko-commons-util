/*
 * Created on 05/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.thread;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.struct.EDataMap;

/**
 * @author Edson
 * Controla a criacao de Threads, 
 * ajuda a diminuir o numero de Threads criadas no sistema
 */
public class EThreadControl extends EBase
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private EDataMap<Integer,EThreadNode> m_threads = new EDataMap<Integer,EThreadNode>();
    
    private static EThreadControl m_threadControl;
    
    private EThreadControl()
    {}
    
    public static EThreadControl getInstance()
    {
        if (null == m_threadControl)
        {
            //s ainda nao foi criado uma thread control ele cria
            m_threadControl = new EThreadControl();
        }
        //devolve a unica instancia possivel de ser criada
        return m_threadControl;
    }
    
    public void add(String sName, IEScriptActionThread script, int iMs)
    {
    	EThreadNode tn = null;
    	//se existir pega a antiga thread node
        if(m_threads.containsKey(iMs))
        {
        	tn = m_threads.get(iMs);        	        	
        }
        else
        {
        	tn = new EThreadNode(iMs);  
        }
        //adiciona nova thread
        tn.add(sName,script);
    }
    
	public void removeString(String sName, int iMs) {
		EThreadNode tn = m_threads.get(iMs);
    	tn.remove(sName);
	}

    /* (non-Javadoc)
     * @see br.com.evon.EBase#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        return EThreadControl.getInstance();
    }

}
