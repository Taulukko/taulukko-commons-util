/*
 * Created on 25/08/2003
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.lang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import com.taulukko.commons.util.lang.datetime.EMilisecond;

/**
 * @author ecarli 
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDate
{
	
	public static long TP_MILISECOND_IN_MS = 1;
	public static long TP_SECOND_IN_MS = TP_MILISECOND_IN_MS * 1000;
	public static long TP_MINUTE_IN_MS = TP_SECOND_IN_MS * 60;
	public static long TP_HOUR_IN_MS = TP_MINUTE_IN_MS * 60;
	public static long TP_DAY_IN_MS = TP_HOUR_IN_MS  * 24;
	

	public static long TP_MILISECOND_IN_NS =  1000000;
	public static long TP_SECOND_IN_NS = TP_SECOND_IN_MS * TP_MILISECOND_IN_NS;
	public static long TP_MINUTE_IN_NS =TP_MINUTE_IN_MS* TP_MILISECOND_IN_NS;
	public static long TP_HOUR_IN_NS = TP_HOUR_IN_MS * TP_MILISECOND_IN_NS;
	public static long TP_DAY_IN_NS = TP_DAY_IN_MS  * TP_MILISECOND_IN_NS;
	
	private Calendar calendar;

	private static long m_lLastError;


	public EDate()
	{
		this(new Date());
	}

	public EDate(Calendar calendar)
	{
		calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
	}

	public EDate(Date date)
	{
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	public EDate(int iYear, int iMonth, int iDay)
	{
		this(iYear, iMonth, iDay, 0, 0, 0);
	}

	public EDate(int iYear, int iMonth, int iDay, int iHour, int iMinute)
	{
		this(iYear, iMonth, iDay, iHour, iMinute, 0);
	}

	public EDate(int iYear, int iMonth, int iDay, int iHour, int iMinute,
			int iSecond)
	{
		calendar = Calendar.getInstance();
		calendar.set(iYear, iMonth-1, iDay, iHour, iMinute, iSecond);
	}

	@Deprecated
	public static String getValue(ResultSet rs, String sField)
			throws SQLException
	{
		String sRet = rs.getString(sField);
		sRet = (sRet == null) ? "" : sRet;
		return sRet;
	}

	public String getCodeDate()
	{

		return (this.getYear() + ""
				+ new EString("00" + this.getMonth()).right(2).toString() + ""
				+ new EString("00" + this.getDay()).right(2).toString() + ""
				+ new EString("00" + this.getHour()).right(2).toString() + ""
				+ new EString("00" + this.getMinute()).right(2).toString() + "" + new EString(
				"00" + this.getSecond()).right(2).toString());

	}

	public int getMilisecond()
	{
		return calendar.get(Calendar.MILLISECOND);
	}

	public int getSecond()
	{
		return calendar.get(Calendar.SECOND);
	}

	public int getMinute()
	{
		return calendar.get(Calendar.MINUTE);
	}

	public int getHour()
	{
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getDay()
	{
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth()
	{
		return calendar.get(Calendar.MONTH) + 1;
	}

	public int getYear()
	{
		return calendar.get(Calendar.YEAR);
	}

	public long getLastError()
	{
		return m_lLastError;
	}

	public void addDays(int iDays)
	{
		calendar.add(Calendar.DAY_OF_MONTH, iDays);
	}

	public void addHours(int iHours)
	{
		calendar.add(Calendar.HOUR, iHours);
	}

	public void addMinutes(int iMinutes)
	{
		calendar.add(Calendar.MINUTE, iMinutes);
	}

	 
	public  EMilisecond diff(EDate date)
	{
		long lThisTime = calendar.getTimeInMillis();
		long lParamDate = date.toJavaCalendar().getTimeInMillis();
		return new  EMilisecond(lThisTime - lParamDate);
	}

	
	@Deprecated
	/*Use diff, correct EMilesecond class*/
	public com.taulukko.commons.util.lang.EMilisecond dif(EDate date)
	{
		long lThisTime = calendar.getTimeInMillis();
		long lParamDate = date.toJavaCalendar().getTimeInMillis();
		return new com.taulukko.commons.util.lang.EMilisecond(lThisTime - lParamDate);
	}

	public Date toJavaDate()
	{
		return calendar.getTime();
	}

	public Calendar toJavaCalendar()
	{
		Calendar ret = Calendar.getInstance();
		ret.setTime(calendar.getTime());
		return ret;
	}

	public String toString()
	{
		return new EString("00" + this.getDay()).right(2) + "-"
				+ new EString("00" + this.getMonth()).right(2) + "-"
				+ this.getYear() + " "
				+ new EString("00" + this.getHour()).right(2) + ":"
				+ new EString("00" + this.getMinute()).right(2) + ":"
				+ new EString("00" + this.getSecond()).right(2);
	}
}
