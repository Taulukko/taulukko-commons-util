package com.taulukko.commons.util.web.controler.flood;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class FloodControl
{

	private static FloodControl _me = null;

	private Map<String, FloodBean> floodInformation = new ConcurrentHashMap<>();

	private long _lastForgot = System.currentTimeMillis();

	private static Logger stdout = null;

	private FloodControl()
	{
		stdout = Logger.getRootLogger();
	}

	public static Logger getLogger()
	{
		return stdout;
	}

	public static void setLogger(Logger _stdout)
	{
		FloodControl.stdout = _stdout;
	}

	public static FloodControl getInstance()
	{
		if (null == _me)
		{
			_me = new FloodControl();
		}
		return _me;
	}
	
	public void start()
	{
		if ((_lastForgot + IFloodConstants.TP_FORGOT_TIME) < System
				.currentTimeMillis())
		{
			// hora de esquecer
			floodInformation = new HashMap<>();
			_lastForgot = System.currentTimeMillis();
		}
	}

	@Deprecated 
	/**Use start*/
	public void forgot()
	{
		this.start();
	}

	public int getTotalFlood(String remoteAddress)
	{
		FloodBean floodBean = floodInformation.get(remoteAddress);
		return (null == floodBean) ? 0 : floodBean.getTotalFlood();
	}

	// em milisegundos
	public long calcBan(String remoteAdress)
	{
		int total = (int) (getTotalFlood(remoteAdress) / IFloodConstants.TP_DIV);
		total = (int) Math.pow(total, IFloodConstants.TP_EXP);// unidades
		total *= IFloodConstants.TP_TIME_SITE_BAN; // converte em milisegundos,
													// valor contido na
													// constante
		return total;
	}

	public void clearSuccess(String remoteAddress, String login)
	{
		if (floodInformation.containsKey(remoteAddress))
		{
			FloodBean floodBean = floodInformation.get(remoteAddress);

			if (floodBean.getCountByLogin(login) > 0)
			{
				if (stdout.isDebugEnabled())
				{
					stdout.debug("before total:" + floodBean.getTotalFlood());
				}
				floodBean.setBanTime(0);
				if (stdout.isDebugEnabled())
				{
					stdout.debug(String.format("before login [%s]:%d", login,
							floodBean.getCountByLogin(login)));
				}
				floodBean.setCountByLogin(login, 0);
				if (stdout.isDebugEnabled())
				{
					stdout.debug(String.format("after login [%s]:%d", login,
							floodBean.getCountByLogin(login)));
					stdout.debug("after total:" + floodBean.getTotalFlood());
				}
			}

		}

	}

	public boolean isBanned(String remoteAddress)
	{
		FloodBean floodBean = floodInformation.get(remoteAddress);
		return floodBean != null
				&& (floodBean.getBanTime() + floodBean.getLastTry()) > System
						.currentTimeMillis();
	}

	public long remaningTime(String remoteAddress)
	{
		FloodBean floodBean = floodInformation.get(remoteAddress);
		if (null == floodBean)
		{
			return 0;
		}
		return (floodBean.getBanTime() + floodBean.getLastTry())
				- System.currentTimeMillis();
	}

	public void addSuspect(String remoteAddress, String login)
	{
		if(stdout.isDebugEnabled())
		{
			stdout.debug("Address parameter in addSuspect:" + remoteAddress);
			stdout.debug("login parameter in addSuspect:" + login);
		}
		FloodBean logins = floodInformation.get(remoteAddress);
		if (logins != null)
		{

			if(stdout.isDebugEnabled())
			{
				stdout.debug("before total:" + logins.getTotalFlood());
			}
			
			if (logins.getCountByLogin(login) > 0)
			{
				if(stdout.isDebugEnabled())
				{
					stdout.debug( String.format("before to login [%s]: %d",login,  logins.getCountByLogin(login)));
				}
				logins.setCountByLogin(login, logins.getCountByLogin(login) + 1);
				if(stdout.isDebugEnabled())
				{
					stdout.debug(String.format("after:" ,login,logins.getCountByLogin(login)));
				}
			}
			else
			{
				logins.setCountByLogin(login, 1);
			}
		}
		else
		{
			// nao tem adiciona
			logins = new FloodBean();
			logins.setAddress(remoteAddress);
			logins.setCountByLogin(login, 1);
		}
		logins.setBanTime(calcBan(remoteAddress));
		if(stdout.isDebugEnabled())
		{
			stdout.debug("Ban Time:" + logins.getBanTime());
		}
		floodInformation.put(remoteAddress, logins);
		if(stdout.isDebugEnabled())
		{
			stdout.debug("depois total:" + logins.getTotalFlood());
		}
	}

}
