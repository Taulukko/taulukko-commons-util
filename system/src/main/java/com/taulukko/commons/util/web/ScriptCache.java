package com.taulukko.commons.util.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.taulukko.commons.util.io.EFile;
import com.taulukko.commons.util.io.EFileBufferReader;
import com.taulukko.commons.util.io.EFileBufferWriter;
import com.taulukko.commons.util.lang.EString;
import com.taulukko.commons.util.web.EMin.SCRIPT_TYPE;

public class ScriptCache
{
	/*
	 * SUPORT CSS AND JS 
	 * 
	 * KNOW BUGS : 
	 * - Regular Expression maybe cause broken codes 
	 * - Not work every encode. Work fine ISO8000-1, UTF-8
	 * - Not work with UTF-8 BOM files
	 */

	private String _virtualCachePath = null;

	private static Map<String, String> scripts = new ConcurrentHashMap<String, String>();

	private ArrayList<String> _css = new ArrayList<String>();

	private ArrayList<String> _js = new ArrayList<String>();

	private String _realRootPath = null;

	private EMin.SCRIPT_TYPE scriptType = null;

	private String _instance = "";

	private long _compact = 0;

	private int _debugChars[] = new int[256];

	private boolean _filterInvalidCharacters = true;

	private FilterCharacters _filter = new UTFInvalidCharacters();

	private Logger stdout = Logger.getRootLogger();

	private int limit = -1;

	private int minifyCount = 0;

	public static void main(String argsv[])
	{
		// System.out.println(String.format("Teste:[é] [%d]","é".getBytes()[1]));
		String versionFiles = "version=0.3.1";

		for (int cont = 0; cont < 3; cont++)
		{
			if (cont == 2)
			{
				versionFiles = "version=0.4.0";
			}
			ScriptCache scriptCache = new ScriptCache(
					"/home/gandb/work/taulukko/table", "/cache",
					EMin.SCRIPT_TYPE.ALL);

			/* TAULUKKO */

			scriptCache.addCSS("/css/window/themes/spread.css?" + versionFiles);
			scriptCache.addCSS("/css/common.css?" + versionFiles);

			/* PROTOTYPE WINDOW */
			scriptCache
					.addCSS("/css/window/themes/default.css?" + versionFiles);

			scriptCache.addCSS("/css/window/themes/alert.css?" + versionFiles);
			scriptCache.addCSS("/css/window/themes/alert_lite.css?"
					+ versionFiles);
			scriptCache.addCSS("/css/window/themes/alphacube.css?"
					+ versionFiles);
			scriptCache.addCSS("/css/window/themes/debug.css?" + versionFiles);

			/*******************************************************************
			 * *SCRPTTS
			 ******************************************************************/
			/* TWITTER */
			scriptCache.addJS("/scripts/twitter.js?" + versionFiles);
			/* BROWSER SELECTOR */
			scriptCache.addJS("/scripts/css_browser_selector.js?"
					+ versionFiles);
			/* SRIPTACULOUS PROTOTYPE */
			scriptCache.addJS("/scripts/scriptaculos/prototype.js?"
					+ versionFiles);
			/* PROTOTYPE WINDOW */
			scriptCache.addJS("/scripts/scriptaculos/effects.js?"
					+ versionFiles);
			scriptCache.addJS("/scripts/window/window.js?" + versionFiles);
			scriptCache.addJS("/scripts/window/window_effects.js?"
					+ versionFiles);
			scriptCache.addJS("/scripts/window/extended_debug.js?"
					+ versionFiles);
			scriptCache.addJS("/scripts/window/debug.js?" + versionFiles);
			/* TAULUKKO */
			scriptCache.addJS("/scripts/common.js?" + versionFiles);
			scriptCache.addJS("/scripts/metrics.js?" + versionFiles);

			System.out.println(scriptCache.flush());
		}

	}

	public static void clear()
	{
		scripts.clear();
	}

	public ScriptCache(Logger logger, String realRootPath,
			String virtualCachePath, EMin.SCRIPT_TYPE scriptType, int limit)
	{
		this(logger, realRootPath, virtualCachePath, scriptType);
		this.limit = limit;

	}

	public ScriptCache(Logger logger, String realRootPath,
			String virtualCachePath, EMin.SCRIPT_TYPE scriptType)
	{
		this(realRootPath, virtualCachePath, scriptType);
		stdout = logger;

	}

	public ScriptCache(String realRootPath, String virtualCachePath,
			EMin.SCRIPT_TYPE scriptType)
	{
		this.scriptType = scriptType;
		_virtualCachePath = virtualCachePath;
		if (!new EString(_virtualCachePath).left(1).toString().equals("/")
				&& new EString(_virtualCachePath).right(1).toString()
						.equals("\\"))
		{
			_virtualCachePath = "/" + _virtualCachePath;
		}
		if (!new EString(_virtualCachePath).right(1).toString().equals("/")
				&& new EString(_virtualCachePath).right(1).toString()
						.equals("\\"))
		{
			_virtualCachePath += "/";
		}
		_realRootPath = realRootPath;
		if (!new EString(_realRootPath).right(1).toString().equals("/")
				&& new EString(_realRootPath).right(1).toString().equals("\\"))
		{
			_realRootPath += "/";
		}

		if (scripts.isEmpty())
		{
			EFile file = new EFile(_realRootPath + _virtualCachePath);
			if (file.exists())
			{
				new EFile(_realRootPath + _virtualCachePath).forceDelete();
			}
		}

	}

	public void addJS(String url)
	{
		stdout.debug(String.format("JS script add [%s]", url));
		add(EMin.SCRIPT_TYPE.JS, url);
	}

	public void addCSS(String url)
	{
		stdout.debug(String.format("CSS script add [%s]", url));
		add(EMin.SCRIPT_TYPE.CSS, url);
	}

	private void add(EMin.SCRIPT_TYPE type, String url)
	{
		_instance += url;
		ArrayList<String> script = (EMin.SCRIPT_TYPE.JS == type) ? _js : _css;
		if (!script.contains(url))
		{
			script.add(url);
		}
		cacheInsert(url, type);
	}

	private void cacheInsert(String url, EMin.SCRIPT_TYPE type)
	{
		if (type == EMin.SCRIPT_TYPE.ALL || type == EMin.SCRIPT_TYPE.OFF)
		{
			stdout.error("Type invalid");
			return;
		}

		if (stdout.isDebugEnabled() && scripts.containsKey(url))
		{
			stdout.debug("URL IN CACHE:" + url);
			return;
		}

		try
		{
			String trueUrl = new EString(url).split("?")[0];
			EFileBufferReader reader = new EFileBufferReader(_realRootPath
					+ trueUrl);
			String script = reader.toString();


			reader.close();

			int originalSize = script.length();

			boolean minify = scriptType == SCRIPT_TYPE.ALL;
			minify = minify || scriptType == type;
			minify = minify && (limit == -1 || minifyCount < limit);

			if (minify)
			{
				minifyCount++;
				ByteArrayOutputStream os = new ByteArrayOutputStream();

				EMin min = new EMin(
						new ByteArrayInputStream(script.getBytes()), os, type);
				min.flush();
				byte scriptArray[] = os.toByteArray();

				if (_filterInvalidCharacters)
				{
					scriptArray = _filter.filter(scriptArray);
				}

				if (stdout.isDebugEnabled())
				{
					for (int contArray = 0; contArray < scriptArray.length; contArray++)
					{
						_debugChars[scriptArray[contArray] + 128]++;
					}
				}

				script = new String(scriptArray);

			}
			
			//remove caracteres estranhos (o correto seria alertar o arquivo, disparar um evento por ex pra mandar um sms coisas assim)
			script = script.replaceAll("[?]{3,10}", "");

			scripts.put(url, script);

			if (stdout.isDebugEnabled())
			{
				stdout.debug("URL INSERIDA:" + url + " TAMANHO REDUZIDO EM "
						+ (script.length() - originalSize) + " BYTES");
				_compact += (script.length() - originalSize);
			}
		}
		catch (Exception ex)
		{
			stdout.error("Error in flush script [" + url + "]" + url, ex);
		}

	}

	public String flush()
	{
		String ret = jsFlush() + cssFlush();

		if (stdout.isDebugEnabled())
		{
			stdout.debug("minify total:" + _compact);

			for (int byteCont = 0; byteCont < 256; byteCont++)
			{
				int times = _debugChars[byteCont];
				if (times > 0)
				{
					byte byteString = (byte) (byteCont - 128);
					String charString = new String(new byte[] { byteString });

					stdout.debug(String.format(
							"Caracter '%s' [%d] appear [%d] times.",
							charString, byteString, times));
				}
			}
		}

		return ret;
	}

	public String cssFlush()
	{
		if (_css.size() == 0)
		{
			return "";
		}
		Iterator<String> keys = _css.iterator();
		String outPath = null;

		outPath = _virtualCachePath + "/" + Math.abs(_instance.hashCode())
				+ ".css";
		buildScript(keys, outPath);
		return "<link href=\"" + outPath
				+ "\" rel=\"stylesheet\" type=\"text/css\"/>";
	}

	public String jsFlush()
	{
		if (_js.size() == 0)
		{
			return "";
		}
		Iterator<String> keys = _js.iterator();
		String outPath = null;

		outPath = _virtualCachePath + "/" + Math.abs(_instance.hashCode())
				+ ".js";

		buildScript(keys, outPath);
		return "<script type=\"text/javascript\" src=\"" + outPath
				+ "\"> </script>";
	}

	private void buildScript(Iterator<String> keys, String outPath)
	{
		EFile cacheFolder = new EFile(_realRootPath + "/" + _virtualCachePath);

		if (!new EFile(cacheFolder.getAbsolutePath()).exists())
		{
			cacheFolder.mkdirs();
		}
		if (!new EFile(_realRootPath + "/" + outPath).exists())
		{
			String key = null;
			try
			{
				EFileBufferWriter writer = new EFileBufferWriter(_realRootPath
						+ "/" + outPath);
				while (keys.hasNext())
				{
					key = keys.next();
					String script = scripts.get(key);
					writer.print(script);
				}

				writer.close();
			}
			catch (IOException e)
			{
				stdout.error("Error in parse script [" + key + "]", e);
			}
		}
	}

}
