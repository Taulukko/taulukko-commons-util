package com.taulukko.commons.util.web.controler.flood;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FloodBean
{

	private Map<String, Integer> logins = new ConcurrentHashMap<String, Integer>();

	private String _address = null;

	private long _lastTry = System.currentTimeMillis();

	private long _banTime = 0;

	public int getCountByLogin(String login)
	{
		Integer count = logins.get(login);
		return (count == null) ? 0 : count;
	}

	public void setCountByLogin(String login, int count)
	{
		_lastTry = System.currentTimeMillis();
		logins.put(login, count);
	}

	public String getAddress()
	{
		return _address;
	}

	public void setAddress(String address)
	{
		_address = address;
	}

	public long getBanTime()
	{
		return _banTime;
	}

	public void setBanTime(long banTime)
	{
		_banTime = banTime;
	}

	public long getLastTry()
	{
		return _lastTry;
	}

	// retorna sempre atualizado
	public int getTotalFlood()
	{
		int ret = 0;
		for(Integer part : logins.values())
		{
 			ret += part;
		}
		return ret;
	}
}
