package com.taulukko.commons.util.mmrpg;

import java.util.Date;

/*
 * Created on 14/06/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Sofia
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Log
{
	private String _className;

	private Log()
	{
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	private void log(String value, String cause)
	{
		 System.out.println(new Date().toString() + " " + _className + " [" +
		 cause + "] " + value);
		
	}

	public void debug(String value)
	{
		this.log(value, "DEBUG");
	}

	public void warning(String value)
	{
		this.log(value, "WARNING");
	}

	public void info(String value)
	{
		this.log(value, "INFO");
	}

	public void error(String value)
	{
		this.log(value, "ERROR");
	}

	public void error(Exception e)
	{
		this.log(e.getMessage(), "ERROR");
		e.printStackTrace();
	}
	
	public static Log create()
	{
		return new Log();
	}
}
