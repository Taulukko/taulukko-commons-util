package com.taulukko.commons.util.web.beans;


public class ValueBean extends BeanBase
{
	private String m_sName = null;

	private String m_sValue=null;

	public String getName()
	{
		return m_sName;
	}

	public void setName(String iName)
	{
		m_sName = iName;
	}

	public String getValue()
	{
		return m_sValue;
	}

	public void setValue(String iValue)
	{
		m_sValue = iValue;
	}

	
}
