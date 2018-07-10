package com.taulukko.commons.util.lang.datetime;

public interface ISliceOfTime {

	public long getNanoseconds();

	public default long getMiliseconds()
	{
		return getNanoseconds()/1000000;
	}

	public default long getSeconds()
	{
		return getMiliseconds()/1000;
	}

	public default long getMinutes()
	{
		return getSeconds()/60;
	}

	public default long getHours()
	{
		return getMinutes()/60;
	}
 
	public default long getDays()
	{
		return getHours()/24;
	}

}