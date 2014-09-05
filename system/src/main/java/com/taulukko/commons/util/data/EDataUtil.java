/*
 * Created on 25/08/2003
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.taulukko.commons.util.lang.EString;

/**
 * @author ecarli
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDataUtil
{
	private static EDataUtil m_this = null;

	EDataUtil()
	{
	}

	public static String getValue(ResultSet rs, String sField)
			throws SQLException
	{
		String sRet = rs.getString(sField);
		sRet = (sRet == null) ? "" : sRet;
		return sRet;
	}

	public static String clearString(String sString)
	{
		EString eret = new EString (sString.trim());
		eret = eret.replace("\\","\\\\");
		eret = eret.replace("\'", "\\\'");
		return eret.toString();
	}

	public static EDataUtil getInstance()
	{
		if (m_this == null)
		{
			m_this = new EDataUtil();
		}
		return m_this;
	}

}
