package com.taulukko.commons.util.window.component.grid;
/*
 * Created on 27/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EColumn
{
 
 	private String m_sTitle="";
 	private int m_iWidth=50;
 	private boolean m_bResizable=false;
	
	public EColumn(String sTitle, int iWidth, boolean bResizable)
	{
		m_sTitle=sTitle;
		m_iWidth=iWidth;
		m_bResizable=bResizable ;
	}
	/**
	 * @return
	 */
	public boolean getIsResizable()
	{
		return m_bResizable;
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return m_iWidth;
	}

	/**
	 * @return
	 */
	public String getTitle()
	{
		return m_sTitle;
	}

	/**
	 * @param sB
	 */
	public void setIsResizable(boolean sB)
	{
		m_bResizable = sB;
	}

	/**
	 * @param sI
	 */
	public void setWidth(int sI)
	{
		m_iWidth = sI;
	}

	/**
	 * @param sString
	 */
	public void setTitle(String sString)
	{
		m_sTitle = sString;
	}

}
