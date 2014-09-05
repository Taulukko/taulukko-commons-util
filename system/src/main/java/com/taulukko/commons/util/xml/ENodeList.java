/*
 * Created on 08/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.struct.EVector;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ENodeList extends EBase implements NodeList
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private EVector<Node>  m_list;

    //recebe um vetor contendo uma lista de nodes
    protected ENodeList(EVector<Node> list)
    {
        m_list = list;
    }
    
    //devolve o tamanho dos nodes
    public int getLength()
    {        
        return m_list.getLength();
    }
    
    //devolve um node pelo index
    public Node item(int index)
    {
        return m_list.get(index);
    }
    
    //devolve uma copia rasa do objeto
    public Object clone() throws CloneNotSupportedException
    {        
        return new ENodeList(m_list);
    }
}
