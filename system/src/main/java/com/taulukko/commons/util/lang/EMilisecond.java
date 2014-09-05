package com.taulukko.commons.util.lang;

public class EMilisecond
{
	private long m_lMilis = 0;

	public EMilisecond(long lMilis)
	{
		m_lMilis = lMilis;
	}

	public long getMilisecond()
	{
		return m_lMilis;
	}

	public void setMilisecond(long iMilis)
	{
		m_lMilis = iMilis;
	}
	
	public long getSeconds()
	{
		return m_lMilis/1000;
	}
	
	public long getMinutes()
	{
		return getSeconds()/60;
	}
	
	public long getHours()
	{
		return getMinutes()/60;
	}
	
	public long getDays()
	{
		return getHours()/24;
	}
	
	public long getMonth()
	{
		return getDays()/30;
	}
	
	public long getYear()
	{
		return (long)(getDays()/365.25);
	}
}
