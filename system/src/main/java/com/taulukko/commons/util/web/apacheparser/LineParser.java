package com.taulukko.commons.util.web.apacheparser;

import com.taulukko.commons.util.lang.EDate;
import com.taulukko.commons.util.lang.EString;

public class LineParser
{
	String _clientIP = null;

	EDate _date = null;

	String _method = null;

	int _exit = 0;

	String _browser = null;

	long _size = 0;

	String _file = null;

	String _path = null;

	private LineParser()
	{
	}

	public static LineParser parse(String line)
	{
		LineParser ret = new LineParser();

		//ip
		String tokens[] = new EString(line.trim()).split(" ");
		ret.setClientIP(tokens[0].trim());

		//date
		EString token = new EString(tokens[3].trim());
		token.replace("[", "");
		token.replace("]", "");
		String partDate[] = token.toString().split("[/:]");
		EDate date = new EDate(Integer.valueOf(partDate[2]),
				getMonth(partDate[1]), Integer.valueOf(partDate[0]), Integer
						.valueOf(partDate[3]), Integer.valueOf(partDate[4]),
				Integer.valueOf(partDate[5]));
		ret.setDate(date);

		//method
		token = new EString(tokens[5].trim());
		token = token.replace('\"', ' ').trim();
		ret.setMethod(token.toString());

		//file and path
		token = new EString(tokens[6].trim());

		String partFile[] = token.toString().split("/");
		String path = "";
		for (int cont = 0; cont < (partFile.length - 1); cont++)
		{
			path += "/" + partFile[cont];
		}
		ret.setPath(path);
		if (partFile.length > 0)
		{
			partFile = partFile[partFile.length - 1].split("\\?");
			ret.setFile(partFile[0].replace('"', ' ').trim());
		}
		else
		{
			ret.setFile("");
		}

		//return
		token = new EString(tokens[8].trim());
		ret.setExit(token.toInt());

		//size
		token = new EString(tokens[9].trim());
		ret.setSize(token.toLong());

		//path and fullpath
		//token = new EString(tokens[10].trim());
		//ret.setPath(token.toString().replace('"', ' ').trim());

		//browser
		String browser = "";
		for (int cont = 11; cont < tokens.length; cont++)
		{
			browser += " " + tokens[cont];
		}

		ret.setBrowser(browser);
		return ret;
	}

	public String toString()
	{
		return "ClientIP:" + this.getClientIP() + "-Date:"
				+ this.getDate().getCodeDate() + "-Method:" + this.getMethod()
				+ "-Return:" + this.getExit() + "-Size:" + this.getSize()
				+ "-FullPath:" + this.getFullPath() + "-Size:" + this.getSize()
				+ "-Browser:" + this.getBrowser();
	}

	private static int getMonth(String month)
	{
		if (month.toUpperCase().equals("JAN"))
		{
			return 1;
		}
		else if (month.toUpperCase().equals("FEB"))
		{
			return 2;
		}
		else if (month.toUpperCase().equals("MAR"))
		{
			return 3;
		}
		else if (month.toUpperCase().equals("APR"))
		{
			return 4;
		}
		else if (month.toUpperCase().equals("MAI"))
		{
			return 5;
		}
		else if (month.toUpperCase().equals("JUN"))
		{
			return 6;
		}
		else if (month.toUpperCase().equals("JUL"))
		{
			return 7;
		}
		else if (month.toUpperCase().equals("AGO"))
		{
			return 8;
		}
		else if (month.toUpperCase().equals("SEP"))
		{
			return 9;
		}
		else if (month.toUpperCase().equals("OCT"))
		{
			return 10;
		}
		else if (month.toUpperCase().equals("NOV"))
		{
			return 11;
		}
		else if (month.toUpperCase().equals("DEC"))
		{
			return 12;
		}
		else
		{
			return -1;
		}
	}

	public String getClientIP()
	{
		return _clientIP;
	}

	public void setClientIP(String clientIP)
	{
		this._clientIP = clientIP;
	}

	public EDate getDate()
	{
		return _date;
	}

	public void setDate(EDate date)
	{
		this._date = date;
	}

	public String getMethod()
	{
		return _method;
	}

	public void setMethod(String method)
	{
		this._method = method;
	}

	public int getExit()
	{
		return _exit;
	}

	public void setExit(int exit)
	{
		this._exit = exit;
	}

	public String getBrowser()
	{
		return _browser;
	}

	public void setBrowser(String browser)
	{
		this._browser = browser;
	}

	public long getSize()
	{
		return _size;
	}

	public void setSize(long size)
	{
		this._size = size;
	}

	public String getFile()
	{
		return _file;
	}

	public void setFile(String file)
	{
		_file = file;
	}

	public String getPath()
	{
		return _path;
	}

	public void setPath(String path)
	{
		_path = path;
	}

	public String getFullPath()
	{
		String separator = "/";
		if (_path.length() > 0 && (_path.charAt(_path.length() - 1) == '/'
				|| _path.charAt(_path.length() - 1) == '\\'))
		{
			separator = "";
		}
		return _path + separator + _file;
	}

}
