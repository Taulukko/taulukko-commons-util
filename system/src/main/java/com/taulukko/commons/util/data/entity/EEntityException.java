/*
 * Created on 28/10/2004
 *
 */
package com.taulukko.commons.util.data.entity;

/**
 * @author Edson Vicente Carli Junior

 */
public class EEntityException extends Exception 
{
	
	private int m_iCode=0;
	public EEntityException(String sMsg,int icode)
	{
		super(sMsg ); 
		m_iCode=icode ;
	}
	
	/**
	 * @return
	 */
	public int getCode()
	{
		return m_iCode;
	}

	

}
