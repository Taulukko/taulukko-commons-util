package com.taulukko.commons.util.web.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.taulukko.commons.util.io.EFile;

public class EWebTools
{

	private HttpServletRequest request = null;

	private List<DiskFileItem> list = null;

	private static final String TP_SHORT_MESSAGE = "ShortMessage";

	public EWebTools(HttpServletRequest request)
	{
		this.request = request;
	}

	public void setShortMessage(String message)
	{
		request.getSession().setAttribute(TP_SHORT_MESSAGE, message);
	}

	public String getShortMessage()
	{
		return (String) request.getSession().getAttribute(TP_SHORT_MESSAGE);
	}

	public String removeShortMessage()
	{
		String ret = getShortMessage();
		setShortMessage(null);
		return ret;
	}

	private Iterator<DiskFileItem> getItems() throws FileUploadException
	{
		if (list == null)
		{
			ServletFileUpload upload = new ServletFileUpload();
			upload.setFileItemFactory(new DiskFileItemFactory());
			upload.setSizeMax(100 * EFile.TP_KILO);
			// Processa os itens do upload
			list = upload.parseRequest(request);
		}
		return list.iterator();
	}

	private boolean isMultipartContent()
	{
		try
		{
			return ServletFileUpload.isMultipartContent(request);
		}
		catch (NullPointerException npe)
		{
			return false;
		}
	}

	private String getParameterGeneric(String fieldname)
	{

		if (isMultipartContent())
		{
			try
			{
				Iterator<DiskFileItem> iter = getItems();
				while (iter.hasNext())
				{
					DiskFileItem item = iter.next();

					if (item.isFormField()
							&& item.getFieldName().equals(fieldname))
					{

						return item.getString();

					}

				}
				return null;

			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
				ex.printStackTrace();
				return null;
			}
		}
		else
		{

			try
			{
				String value = request.getParameter(fieldname);
				if (value == null)
				{
					if (request.getQueryString() != null
							&& request.getQueryString().contains(fieldname))
					{
						Map<String, String> map = parseQueryStringToMap(request
								.getQueryString());
						if (map.containsKey(fieldname))
						{
							return map.get(fieldname);
						}
					}
					return null;
				}
				return new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{

				e.printStackTrace();
				return request.getParameter(fieldname);
			}
		}

	}

	public static Map<String, String> parseQueryStringToMap(String queryString)
	{
		Map<String, String> ret = new HashMap<String, String>();

		boolean containParameters = queryString.contains("?");
		if (containParameters)
		{
			queryString = queryString.split("[?]")[1];
		}

		String parameters[] = queryString.split("&");

		for (String parameter : parameters)
		{

			String parameterNamePatternGroup = "([^=]*)";
			String parameterValuePatternGroup = "([^&]*)";

			Pattern pattern = Pattern.compile(parameterNamePatternGroup + "="
					+ parameterValuePatternGroup);

			Matcher matcher = pattern.matcher(parameter);

			while (matcher.find())
			{
				String attributeName = matcher.group(1);
				String attributeValue = matcher.group(2);
				ret.put(attributeName, attributeValue);
			}
		}
		return ret;
	}

	public String getParameter(String key)
	{
		// captura o valor
		String sRet = getParameterGeneric(key);
		// corrige nulo
		sRet = (sRet == null) ? "" : sRet;
		// retorna
		return sRet;
	}

	public String getParameter(String key, String defaultValue)
	{
		// captura o valor
		String ret = getParameterGeneric(key);
		// corrige nulo
		ret = (ret == null) ? defaultValue : ret;
		// retorna
		return ret;
	}
}