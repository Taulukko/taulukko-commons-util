/*
 * Created on 08/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.xml;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.struct.EVector;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ENode extends EBase implements org.w3c.dom.Node
{
    private EVector<Node> m_childs = new EVector<Node>(0);

    private String m_sValue = "";

    private String m_sName = "";

    private short m_iType = 0;

    private EDocument m_owner;

    private ENode m_parent = null;

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    protected ENode(EDocument owner, ENode parent)
    {
        m_owner = owner;
        m_parent = parent;
    }

    // retorna os filhos existentes para este node
    public NodeList getChildNodes()
    {
        return new ENodeList(m_childs);
    }

    public Node getFirstChild()
    {
        if ( m_childs.getLength() == 0 )
        {
            throw new DOMException(DOMException.NOT_FOUND_ERR,
                    "No exist child.");
        }
        // retorna o primeiro filho
        return m_childs.get(0);
    }

    public Node getLastChild()
    {
        if ( m_childs.getLength() == 0 )
        {
            throw new DOMException(DOMException.NOT_FOUND_ERR,
                    "No exist child.");
        }
        // retorna o �ltimo filho
        return m_childs.get(m_childs.getLength() - 1);
    }

    public String getNodeName()
    {
        return m_sName;
    }

    public short getNodeType()
    {
        return m_iType;
    }

    public String getNodeValue() throws DOMException
    {
        return m_sValue;
    }

    public Document getOwnerDocument()
    {
        return m_owner;
    }

    public Node getParentNode()
    {
        return m_parent;
    }

    public String getTextContent() throws DOMException
    {
        String sRet = "";

        // monta o cabecalho
        sRet = "<" + m_sName + ">";

        // monta o corpo
        NodeList list = this.getChildNodes();
        for (int iCont = 0; iCont < list.getLength(); iCont++)
        {
            sRet = sRet + list.item(iCont).getTextContent();
        }

        // monta o valor
        sRet = sRet + m_sValue;
        // monta o rodape
        sRet = "</" + m_sName + ">";

        return sRet;
    }

    public boolean hasChildNodes()
    {
        return m_childs.getLength() > 0;
    }

    public boolean isEqualNode(Node node)
    {
        return m_sName.equals(node.getNodeName())
                && m_sValue.equals(node.getNodeValue())
                && m_iType == node.getNodeType();
    }

    public boolean isSameNode(Node node)
    {
        return this == node;
    }

    public Node removeChild(Node node) throws DOMException
    {
        Node ret=null;
        
        for (int iCont=0;iCont < m_childs.getLength();iCont++)
        {
            //procura pelo node correto
            Node thisNode = m_childs.get(iCont);
            if(thisNode.equals(node) && ret==null)
            {
                //igual, separa para devolver depois
                ret = thisNode;
            }
            
            if (ret !=null && iCont != (m_childs.getLength()-1))
            {
                //retroce 
                m_childs.set(iCont,m_childs.get(iCont+1));
            }
            else if(ret !=null && iCont == (m_childs.getLength()-1))
            {
                //destroi o ultimo 
                m_childs.redimPreserve(m_childs.getLength()-1);
            }
        }
        return ret;
    }

    public Node replaceChild(Node nodeFind, Node nodeReplace) throws DOMException
    {
        Node ret=null;
        
        for (int iCont=0;iCont < m_childs.getLength();iCont++)
        {
            //procura pelo node correto
            Node thisNode = m_childs.get(iCont);
            if(thisNode.equals(nodeFind) && ret==null)
            {
                //igual, separa para devolver depois
                ret = thisNode;
                //substitui o primeiro node achado
                m_childs.set(iCont,nodeReplace);
            }            
        }
        return ret;
    }

    public void setNodeValue(String sValue) throws DOMException
    {
        m_sValue = sValue;
    }

    public void setPrefix(String sArg0) throws DOMException
    {
    }

    public void setTextContent(String sArg0) throws DOMException
    {
        
    }

    public Object setUserData(String sArg0, Object sArg1, UserDataHandler sArg2)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Object clone() throws CloneNotSupportedException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Node appendChild(org.w3c.dom.Node sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Node cloneNode(boolean sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");

    }

    public short compareDocumentPosition(Node sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public NamedNodeMap getAttributes()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public String getBaseURI()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Object getFeature(String sArg0, String sArg1)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public String getLocalName()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public String getNamespaceURI()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Node getNextSibling()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public String getPrefix()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Node getPreviousSibling()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public Object getUserData(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public boolean hasAttributes()
    {
        return false;
    }

    public Node insertBefore(Node before, Node node) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public boolean isDefaultNamespace(String sArg0)
    {
        return false;
    }

    public boolean isSupported(String sArg0, String sArg1)
    {
        return false;
    }

    public String lookupNamespaceURI(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public String lookupPrefix(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Is not suported command.");
    }

    public void normalize()
    {
    }

}
