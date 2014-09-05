/*
 * Created on 25/08/2003
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

/**
 * @author ecarli
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDataUtilMSSQL extends EDataUtil
{
	private static EDataUtilMSSQL m_this = null;

	private EDataUtilMSSQL()
	{
		super();
	}

	public static String clearString(String sString)
	{
		String sRet = sString.trim();
		sRet = sRet.replaceAll("'", "''");
		return sRet;
	}

	public static EDataUtil getInstance()
	{
		if(null==m_this)
		{
			m_this = new EDataUtilMSSQL();
		}
		
		return m_this;
	}

}
