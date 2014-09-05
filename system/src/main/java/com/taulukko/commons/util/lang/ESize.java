/*
 * Created on 12/10/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.lang;



/**
 * @author Usuario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ESize 
{
	private int m_iHeight=0;
	private int m_iWidth=0;
	
	public ESize (int iWidth, int iHeight )
	{
		m_iHeight=iHeight;
		m_iWidth=iWidth;
	}
    /** Autor: Usuario
     * Data: 12/10/2003
     * Dependencias:
     * Objetivo: 
     * @return
     */
    public int getHeight()
    {
        return m_iHeight;
    }

    /** Autor: Usuario
     * Data: 12/10/2003
     * Depend�ncias:
     * Objetivo: 
     * @return
     */
    public int getWidth()
    {
        return m_iWidth;
    }

    /** Autor: Usuario
     * Data: 12/10/2003
     * Dependencias:
     * Objetivo: 
     * @param i
     */
    public void setHeight(int i)
    {
        m_iHeight = i;
    }

    /** Autor: Usuario
     * Data: 12/10/2003
     * Dependencias:
     * Objetivo: 
     * @param i
     */
    public void setWidth(int i)
    {
        m_iWidth = i;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
		throw new CloneNotSupportedException();
    }

}
