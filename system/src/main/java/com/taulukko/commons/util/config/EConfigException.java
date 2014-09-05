/*
 * Created on 01/01/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.config;

import com.taulukko.commons.util.exception.EException;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EConfigException extends EException{
	public EConfigException(String sMsg, int iCode)
	{
		super(sMsg,iCode);
	}
}
