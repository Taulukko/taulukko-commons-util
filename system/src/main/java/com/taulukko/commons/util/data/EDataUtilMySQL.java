/*
 * Created on 25/08/2003
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import com.taulukko.commons.util.lang.EString;

/**
 * @author ecarli
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDataUtilMySQL extends EDataUtil
{
	private static EDataUtilMySQL m_this = null;

	private EDataUtilMySQL()
	{
		super();
	}

	
	public static String clearString(String value)
	{
		EString ret = new EString(value.trim());
		ret = ret.replace(new EString("\\"), new EString("\\\\"));
		ret = ret.replace(new EString("'"), new EString("''"));
		return ret.toString();
	}

	public static EDataUtil getInstance()
	{
		if (null == m_this)
		{
			m_this = new EDataUtilMySQL();
		}

		return m_this;
	}

}
