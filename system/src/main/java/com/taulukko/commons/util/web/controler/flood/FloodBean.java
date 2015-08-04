package com.taulukko.commons.util.web.controler.flood;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FloodBean
{

	private Map<String, Integer> logins = new ConcurrentHashMap<String, Integer>();

	private String address = null;

	private long lastTry = System.currentTimeMillis();

	private long banTime = 0;

	public int getCountByLogin(String login)
	{
		Integer count = logins.get(login);
		return (count == null) ? 0 : count;
	}

	public void setCountByLogin(String login, int count)
	{
		lastTry = System.currentTimeMillis();
		logins.put(login, count);
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public long getBanTime()
	{
		return banTime;
	}

	public void setBanTime(long banTime)
	{
		this.banTime = banTime;
	}

	public long getLastTry()
	{
		return lastTry;
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
