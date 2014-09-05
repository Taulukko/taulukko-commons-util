package com.taulukko.commons.util.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFilter
{

	public static String[] TP_HTML_TAGS = new String[] { "A", "B", "BR",
			"DIV", "IMG", "TABLE", "TR", "TD", "THEAD",
			"TBODY", "TH", "U","UL", "LI", "OL", "TFOOD", "SPAN",
			"STRONG", "LABEL", "I", "P", "FONT", "H1" , "H2", "H3", "H4" , "H5" , "H6","EM"   };

	public static String filter(String ret)
	{
		// < + qualquer coisa + (?) parar na primeira ocorrência de >
		Pattern version = Pattern.compile("<(.*?)>");
		Matcher m = version.matcher(ret);
		// &lt; + qualquer coisa + &gt;
		return m.replaceAll("&lt;$1&gt;");

	}

	public static String filter(String ret, String[] ignorelist)
	{
		String tokens[] = ret.split("<");
		String result = null;

		for (int cont = 0; cont < tokens.length; cont++)
		{
			if (cont == 0)
			{
				result = tokens[cont];
				continue;
			}
			String inner = null;
			String ext = null;
			if (tokens[cont].charAt(0) == '>')
			{
				inner = "";

				if (tokens[cont].length() == 1)
				{
					ext = "";
				}
				else
				{
					tokens[cont].substring(1);
				}
			}
			else if (tokens[cont].charAt(tokens[cont].length() - 1) == '>')
			{
				ext = "";
				inner = tokens[cont].substring(0, tokens[cont].length() - 1);
			}
			else
			{
				String parts[] = tokens[cont].split(">");
				inner = parts[0];
				ext = "";
				for (int contp = 1; contp < parts.length; contp++)
				{
					ext += parts[contp];
				}
			}

			boolean ignoreded = false;
			
			for(String ignore:ignorelist)
			{
				//comeco de linha(vazio ou / zero ou uma vez)tag(espaço zero ou uma vez)(qualquer caracter)fim de linha
				Pattern tag = Pattern.compile("^/?" + ignore + "[ ]?.*$",Pattern.CASE_INSENSITIVE);
				Matcher m = tag.matcher(inner.trim());
				ignoreded = m.find();
				if(ignoreded)
				{
					break;
				}
			}

			if (ignoreded)
			{
				result += "<" + inner + ">" + ext;
			}
			else
			{
				result += "&lt;" + inner + "&gt;" + ext;
			}
		}

		return result;
	}
}
