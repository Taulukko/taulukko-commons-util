/*
 * Created on 27/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.lang;

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EMath
{

	/**
	 * @param arg0
	 * @return
	 */
	public static boolean isDecimal(String value)
	{

		try
		{
			Double.parseDouble(value);
			//o valor e numerico e inteiro
			return true;
		}
		catch (NumberFormatException err)
		{
			//o valor e numerico e decimal
			return false;
		}
	}

	/**
	 * @param arg0
	 * @return
	 */
	public static boolean isInteger(String value)
	{
		try
		{
			Long.parseLong(value,10);
			//o valor e numerico e inteiro
			return true;
		}
		catch (NumberFormatException err)
		{
			//o valor e numerico e decimal
			return false;
		}
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double abs(double arg0)
	{
		return Math.abs(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static float abs(float arg0)
	{
		return Math.abs(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static int abs(int arg0)
	{
		return Math.abs(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static long abs(long arg0)
	{
		return Math.abs(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double acos(double arg0)
	{
		return Math.acos(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double asin(double arg0)
	{
		return Math.asin(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double atan(double arg0)
	{
		return Math.atan(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static double atan2(double arg0, double arg1)
	{
		return Math.atan2(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double ceil(double arg0)
	{
		return Math.ceil(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double cos(double arg0)
	{
		return Math.cos(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double exp(double arg0)
	{
		return Math.exp(arg0);
	}

	public static String onlyDecimalNumber(String value)
	{
		String avaiableChars = "01234567890+-.";
		String ret = "";
		boolean hasDecimal = false;
		boolean hasSignal = false;
		
		for(int cont=0;cont <  value.length();cont++)
		{
			if(avaiableChars.indexOf(value.charAt(cont))==-1)
			{
				continue;
			}
			if(value.charAt(cont)=='.')
			{
				if(hasDecimal)
				{
					continue;
				}
				else
				{
					hasDecimal = true;
				}
			}
			
			if(value.charAt(cont)=='+' || value.charAt(cont)=='-')
			{
				if(hasSignal)
				{
					ret = "";
				}
				else
				{
					hasSignal = true;
				}
			}
			ret += value.charAt(cont);
		}
		
		return ret;
	}
	
	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double floor(double arg0)
	{
		return Math.floor(arg0);
	}
	

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static double IEEEremainder(double arg0, double arg1)
	{
		return Math.IEEEremainder(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double log(double arg0)
	{
		return Math.log(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static double max(double arg0, double arg1)
	{
		return Math.max(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static float max(float arg0, float arg1)
	{
		return Math.max(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static int max(int arg0, int arg1)
	{
		return Math.max(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static long max(long arg0, long arg1)
	{
		return Math.max(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static double min(double arg0, double arg1)
	{
		return Math.min(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static float min(float arg0, float arg1)
	{
		return Math.min(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static int min(int arg0, int arg1)
	{
		return Math.min(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static long min(long arg0, long arg1)
	{
		return Math.min(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public strictfp static double pow(double arg0, double arg1)
	{
		return Math.pow(arg0, arg1);
	}

	/**
	 * @return
	 */
	public strictfp static double random()
	{
		return Math.random();
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double rint(double arg0)
	{
		return Math.rint(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static long round(double arg0)
	{
		return Math.round(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static int round(float arg0)
	{
		return Math.round(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double sin(double arg0)
	{
		return Math.sin(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double sqrt(double arg0)
	{
		return Math.sqrt(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double tan(double arg0)
	{
		return Math.tan(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double toDegrees(double arg0)
	{
		return Math.toDegrees(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 */
	public strictfp static double toRadians(double arg0)
	{
		return Math.toRadians(arg0);
	}

}
