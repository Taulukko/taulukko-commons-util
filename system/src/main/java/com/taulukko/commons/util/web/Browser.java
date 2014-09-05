package com.taulukko.commons.util.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

//http://www.useragentstring.com

public class Browser
{
	private Float version = null;

	private String name = null;
	
	private Logger logger = null;

	public Browser(String s)
	{
		s = s.toLowerCase();
		if (s.contains("chrome") && s.contains("gecko"))
		{			
			/**
			 *  mozilla/5.0 (windows; u; windows nt 6.1; en-us) applewebkit/534.13 (khtml, like gecko) chrome/9.0.597.98 safari/534.13 
			 * */
			name = "Chrome";
			parseVersion(s,"chrome/");
		}
		else if (s.contains("apple"))
		{
			/**
			 *  mozilla/5.0 (windows; u; windows nt 6.1; pt-br) applewebkit/533.19.4 (khtml, like gecko) version/5.0.3 safari/533.19.4
			 */
			name = "Safari";
			parseVersion(s,"version/");
		}
		else if (s.contains("opera"))
		{
			/**
			 * Opera/9.80 (Windows NT 6.0; U; en) Presto/2.8.99 Version/11.10 
			 * Opera/9.63 (X11; Linux x86_64; U; ru) Presto/2.1.1
			 * Opera/4.02 (Windows 98; U) [en]
			 */
			name = "Opera";
			if(s.contains("version"))
			{
				parseVersion(s,"version/");
			}
			else
			{
				parseVersion(s,"opera/");
			}
		}
		else if (s.contains("firefox"))
		{
			/**
			 * mozilla/5.0 (windows nt 6.1; wow64; rv:2.0b10) gecko/20100101 firefox/4.0b10
			 * mozilla/5.0 (windows; u; windows nt 6.1; pt-br; rv:1.9.2.13) gecko/20101203 firefox/3.6.13
			 */
			name = "Firefox";
			parseVersion(s,"firefox/");
		}
		else if (s.contains("msie"))
		{
			/**
			 * mozilla/5.0 (compatible; msie 9.0; windows nt 6.1; win64; x64; trident/5.0)
			 */
			name = "Internet Explorer";
			//msie + espaço
			parseVersion(s,"msie\\s");
		}
		else
		{
			name = "unknow";
			version = 1f;
		}
	}

	private void parseVersion(String s,String pattern)
	{
		//o texto que vier + .qualquer caracter*0 ou mais vezes?uma vez antes de (espaço ou término de string)
		//ou seja (repare no final) "version/3.43.23 " "version/3.43.23" com patter version/ funciona  
		Pattern version = Pattern.compile(pattern + ".*?(\\s|$)");
		Matcher m = version.matcher(s);
		String versionChrome = null;
		if (m.find())
		{
			//extrai apenas o pattern mais a versão, ex :chrome/9.0.597.98
			versionChrome = m.group();
			//captura apenas os números
			version = Pattern.compile("(\\d+)");
			m = version.matcher(versionChrome);
			versionChrome = null;
			while (m.find())
			{
				//fica {9,0,597,98}
				if(versionChrome==null)
				{
					versionChrome = m.group() + ".";
				}
				else
				{
					versionChrome += m.group();
				}
			}
			versionChrome+="0";

		}
		this.version = (versionChrome == null) ? 1f : Float
				.parseFloat(versionChrome);
	}

	public String getName()
	{
		return name;
	}

	public Float getVersion()
	{
		return version;
	}

	public boolean isIE()
	{
		return name.equals("Internet Explorer");
	}

	public void setStdout(Logger logger)
	{
		this.logger = logger;
	}
}
