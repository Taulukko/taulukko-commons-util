package com.taulukko.commons.util.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taulukko.commons.util.lang.EDate;

public class CookieControler
{
	private static HashMap<String, ArrayList<Cookie>> _sessions = new HashMap<>();

	private static HashMap<String, Boolean> _ends = new HashMap<>();

	public static ArrayList<Cookie> getCookies(HttpServletRequest request)
	{
		return getCookies((request.getSession() != null) ? request.getSession()
				.getId() : "");
	}

	private static ArrayList<Cookie> getCookies(String sid)
	{
		ArrayList<Cookie> cookies = null;

		if (_sessions.containsKey(sid))
		{
			cookies = _sessions.get(sid);
		}
		else
		{
			cookies = new ArrayList<>();
			_sessions.put(sid,cookies);
		}
		return cookies;
	}

	public static void addCookie(String sid, String name, String value, int time)
	{
		ArrayList<Cookie> cookies = getCookies(sid);
		Cookie item = new Cookie(name, value);
		item.setMaxAge(time);
		cookies.add(item);
	}

	public static void addCookie(String sid, String name, String value)
	{
		addCookie(sid, name, value, (int) (EDate.TP_DAY_IN_MS * 365 / 1000));
	}

	public static int validCount(HttpServletRequest request)
	{
		Cookie cookies[] = request.getCookies();
		int ret = 0;
		for (int cont = 0; cookies != null && cont < cookies.length; cont++)
		{
			if (cookies[cont].getValue() != null)
			{
				ret++;
			}
		}
		return ret;
	}
	
	private static void delete(HttpServletRequest request,	String name)
	{
		Cookie cookies[] = request.getCookies();
		for (int cont = 0; cookies != null && cont < cookies.length; cont++)
		{
			if (cookies[cont].getName().equals(name))
			{
				cookies[cont].setMaxAge(0);
				cookies[cont].setValue(null);
			}
		}
	}

	
	public static void start(HttpServletRequest request,
			HttpServletResponse response)
	{
		String sid = request.getSession().getId();

		if (endIsNear(sid, response))
		{
			// foi solicitado clenup
			clear(request, response);
		}

		ArrayList<Cookie> cookies = getCookies(sid);
		// manda para o client os valores atualizados
		for (int cont = 0; cont < cookies.size(); cont++)
		{
			//remove o anterior
			if(getFromClient(request, cookies.get(cont).getName(), null)==null)
			{
				response.addCookie(cookies.get(cont));
				System.out.println("Adicionado cookie"
						+ cookies.get(cont).getName() + " para sessao " + sid);
			}
		}

		if (endIsNear(sid, response))
		{
			// destroi o ultimo cook
			request.getSession().invalidate();
			_sessions.remove(sid);
			_ends.remove(sid);
		}
	}

	public static String get(HttpServletRequest request, String name,
			String defaultValue)
	{
		// primeiro busca no servidor
		String ret = getFromServer(request, name, null);

		if (ret == null)
		{
			// se n�o achar busca no cliente
			ret = getFromClient(request, name, null);
		}

		if (ret == null)
		{
			// se n�o achar devolve o default
			return defaultValue;
		}

		return ret;
	}

	public static void debug(HttpServletRequest request)
	{
		if (request.getCookies() != null)
		{
			System.out.println("<!-- COUNT = " + request.getCookies().length
					+ "-->");
			// busca os que est�o na maquina
			for (int cont = 0; cont < request.getCookies().length; cont++)
			{
				System.out.println("<!-- NAME= "
						+ request.getCookies()[cont].getName() + " TIME="
						+ request.getCookies()[cont].getMaxAge() + " VALUE="
						+ request.getCookies()[cont].getValue() + "-->");
			}
		}
	}

	public static String getFromClient(HttpServletRequest request, String name,
			String defaultValue)
	{
		// busca os que estao na maquina
		for (int cont = 0; request.getCookies() != null
				&& cont < request.getCookies().length; cont++)
		{
			if (request.getCookies()[cont].getName().equals(name)
					&& request.getCookies()[cont].getValue() != null)
			{
				return request.getCookies()[cont].getValue();
			}
		}
		// retorna o valor default
		return defaultValue;
	}

	public static String getFromServer(HttpServletRequest request, String name,
			String defaultValue)
	{
		String sid = request.getSession().getId();
		ArrayList<Cookie> cookies = getCookies(sid);

		// primeiro busca nos que est�o no servidor ainda
		for (int cont = 0; cont < cookies.size(); cont++)
		{
			if (cookies.get(cont).getName().equals(name)
					&& cookies.get(cont).getValue() != null)
			{
				return cookies.get(cont).getValue();
			}
		}
		// retorna o valor default
		return defaultValue;
	}

	public static boolean endIsNear(HttpServletRequest request,
			HttpServletResponse response)
	{
		return endIsNear(request.getSession().getId(), response);
	}

	public static boolean endIsNear(String sid, HttpServletResponse response)
	{
		boolean ret = false;

		Boolean value = _ends.get(sid);
		if (value != null)
		{
			ret = value;
		}
		return ret;
	}

	public static void scheduleClenup(HttpServletRequest request)
	{
		scheduleClenup(request.getSession().getId());
	}

	public static void scheduleClenup(String sid)
	{
		_ends.put(sid, true);
	}

	public static void abortClenup(HttpServletRequest request)
	{
		abortClenup(request.getSession().getId());
	}

	public static void abortClenup(String sid)
	{
		_ends.put(sid, false);
	}

	private static void clear(HttpServletRequest request,
			HttpServletResponse response)
	{
		Cookie cookies[] = request.getCookies();

		for (int cont = 0; cont < cookies.length; cont++)
		{
			delete(request,cookies[cont].getName());
		}
	}
}
