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

/**
 * @author ecarli 
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDate
{
	public static long TP_SECOND_IN_MS = 1000;
	public static long TP_MINUTE_IN_MS = TP_SECOND_IN_MS * 60;
	public static long TP_HOUR_IN_MS = TP_MINUTE_IN_MS * 60;
	public static long TP_DAY_IN_MS = TP_HOUR_IN_MS  * 24;
	
	private Calendar m_calendar;

	private static long m_lLastError;


	public EDate()
	{
		this(new Date());
	}

	public EDate(Calendar calendar)
	{
		m_calendar = Calendar.getInstance();
		m_calendar.setTime(calendar.getTime());
	}

	public EDate(Date date)
	{
		m_calendar = Calendar.getInstance();
		m_calendar.setTime(date);
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
		m_calendar = Calendar.getInstance();
		m_calendar.set(iYear, iMonth-1, iDay, iHour, iMinute, iSecond);
	}

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
		return m_calendar.get(Calendar.MILLISECOND);
	}

	public int getSecond()
	{
		return m_calendar.get(Calendar.SECOND);
	}

	public int getMinute()
	{
		return m_calendar.get(Calendar.MINUTE);
	}

	public int getHour()
	{
		return m_calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getDay()
	{
		return m_calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth()
	{
		return m_calendar.get(Calendar.MONTH) + 1;
	}

	public int getYear()
	{
		return m_calendar.get(Calendar.YEAR);
	}

	public long getLastError()
	{
		return m_lLastError;
	}

	public void addDays(int iDays)
	{
		m_calendar.add(Calendar.DAY_OF_MONTH, iDays);
	}

	public void addHours(int iHours)
	{
		m_calendar.add(Calendar.HOUR, iHours);
	}

	public void addMinutes(int iMinutes)
	{
		m_calendar.add(Calendar.MINUTE, iMinutes);
	}

	public EMilisecond dif(EDate date)
	{
		long lThisTime = m_calendar.getTimeInMillis();
		long lParamDate = date.toJavaCalendar().getTimeInMillis();
		return new EMilisecond(lThisTime - lParamDate);
	}

	public Date toJavaDate()
	{
		return m_calendar.getTime();
	}

	public Calendar toJavaCalendar()
	{
		Calendar ret = Calendar.getInstance();
		ret.setTime(m_calendar.getTime());
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
