/*
 * Created on 07/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EDocument extends EBase implements org.w3c.dom.Document
{    

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String m_sVersion = "1.0.0.0";
    private Node m_patern = null; 
    
    public Node adoptNode(Node sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "adoptNode(Node sArg0) is not suported command.");
    }

    public Attr createAttribute(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "adoptNode(Node sArg0) is not suported command.");
    }

    public Attr createAttributeNS(String sArg0, String sArg1)
            throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createAttributeNS(String sArg0, String sArg1) is not suported command.");
    }

    public CDATASection createCDATASection(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createCDATASection(String sArg0) is not suported command.");
    }

    public Comment createComment(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createComment(String sArg0) is not suported command.");
    }

    public DocumentFragment createDocumentFragment()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createDocumentFragment() is not suported command.");
    }

    public Element createElement(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createElement(String sArg0) is not suported command.");
    }

    public Element createElementNS(String sArg0, String sArg1)
            throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "createElementNS(String sArg0, String sArg1) is not suported command.");
    }

    public EntityReference createEntityReference(String sArg0)
            throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public ProcessingInstruction createProcessingInstruction(String sArg0,
            String sArg1) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Text createTextNode(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public DocumentType getDoctype()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Element getDocumentElement()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getDocumentURI()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public DOMConfiguration getDomConfig()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Element getElementById(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public NodeList getElementsByTagName(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public NodeList getElementsByTagNameNS(String sArg0, String sArg1)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public DOMImplementation getImplementation()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getInputEncoding()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public boolean getStrictErrorChecking()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public String getXmlEncoding()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public boolean getXmlStandalone()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public String getXmlVersion()
    {
        return m_sVersion;
    }

    public Node importNode(Node sArg0, boolean sArg1) throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public void normalizeDocument()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public Node renameNode(Node sArg0, String sArg1, String sArg2)
            throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public void setDocumentURI(String sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public void setStrictErrorChecking(boolean sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public void setXmlStandalone(boolean sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public void setXmlVersion(String sVersion) throws DOMException
    {
        m_sVersion = sVersion;
    }

    public Node appendChild(Node sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node cloneNode(boolean sArg0)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public short compareDocumentPosition(Node sArg0) throws DOMException
    {
        return 0;
    }

    public NamedNodeMap getAttributes()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getBaseURI()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public NodeList getChildNodes()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Object getFeature(String sArg0, String sArg1)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node getFirstChild()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node getLastChild()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getLocalName()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getNamespaceURI()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node getNextSibling()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getNodeName()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public short getNodeType()
    {

        return 0;
    }

    public String getNodeValue() throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public org.w3c.dom.Document getOwnerDocument()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node getParentNode()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getPrefix()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node getPreviousSibling()
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public String getTextContent() throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Object getUserData(String sArg0)
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public boolean hasAttributes()
    {

        return false;
    }

    public boolean hasChildNodes()
    {

        return false;
    }

    public Node insertBefore(Node sArg0, Node sArg1) throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public boolean isDefaultNamespace(String sArg0)
    {

        return false;
    }

    public boolean isEqualNode(Node sArg0)
    {

        return false;
    }

    public boolean isSameNode(Node sArg0)
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
                "Not suported command.");
    }

    public String lookupPrefix(String sArg0)
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public void normalize()
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public Node removeChild(Node sArg0) throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Node replaceChild(Node sArg0, Node sArg1) throws DOMException
    {

        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public void setNodeValue(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public void setPrefix(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public void setTextContent(String sArg0) throws DOMException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
        "Not suported command.");
    }

    public Object setUserData(String sArg0, Object sArg1, UserDataHandler sArg2)
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }

    public Object clone() throws CloneNotSupportedException
    {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                "Not suported command.");
    }
}
