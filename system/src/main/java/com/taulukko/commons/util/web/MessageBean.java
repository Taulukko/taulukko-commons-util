package com.taulukko.commons.util.web;

import com.taulukko.commons.util.web.beans.BeanBase;

/*
 * Created on 27/02/2006
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageBean extends BeanBase
{

	private String m_sMessage = null;

	public String getMessage()
	{
		return m_sMessage;
	}

	public void setMessage(String sMessage)
	{
		m_sMessage = sMessage;
	}
}
