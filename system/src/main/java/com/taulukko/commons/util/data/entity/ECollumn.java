/*
 * Created on 28/10/2004
 */
package com.taulukko.commons.util.data.entity;

/**
 * @author Edson Vicente Carli Junior
 */
public class ECollumn
{
	//tipos, constantes
	public static final int TP_UNDEFINED = 0;
	public static final int TP_CHAR = 1;
	public static final int TP_STRING = 2;
	public static final int TP_BYTE = 4;
	public static final int TP_INTEGER = 8;
	public static final int TP_LONG = 16;
	public static final int TP_DOUBLE = 32;
	public static final int TP_TIMESTAMP = 64;
	public static final int TP_DATE = 128;
	public static final int TP_BINARY = 256;
	public static final int TP_AUTONUMERIC = 512;

	//propriedades
	private String m_sName = "";
	private String m_sValue = "";
	private boolean m_bKey = false;
	private int m_iType = 0;

	public ECollumn(String sName, String sValue, boolean bIsKey, int iType)
	{
		this.setName(sName);
		this.setValue(sValue);
		this.setIsKey(bIsKey);
		this.setType(iType);
	}

	/**
	 * @return
	 */
	public boolean getIsKey()
	{
		return m_bKey;
	}

	/**
	 * @return
	 */
	public int getType()
	{
		return m_iType;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return m_sName;
	}

	/**
	 * @return
	 */
	public String getValue()
	{
		return m_sValue;
	}

	/**
	 * @param sB
	 */
	public void setIsKey(boolean sB)
	{
		m_bKey = sB;
	}

	/**
	 * @param sI
	 */
	public void setType(int sI)
	{
		m_iType = sI;
	}

	/**
	 * @param sString
	 */
	public void setName(String sString)
	{
		m_sName = sString;
	}

	/**
	 * @param sString
	 */
	public void setValue(String sString)
	{
		m_sValue = sString;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof ECollumn))
		{
			return false;
		}

		ECollumn column = (ECollumn) obj;

		return column.getName().equals(this.getName());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		//gera o codigo hash do objeto
		return this.getName().hashCode();
	}

	public Object clone()
	{
		ECollumn ret =
			new ECollumn(
				this.getName(),
				this.getValue(),
				this.getIsKey(),
				this.getType());
		return ret;
	}
}
