/*
 * Created on 05/03/2006
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;

import com.taulukko.commons.util.web.util.EWebTools;

/**
 * @author Edson
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class RequestBase
{
	private HttpServletRequest _request;

	private HttpServletResponse _response;

	private int _method;

	private HashMap<String, Object> _parameters;

	private EWebTools _webTools = null;
	

	/**
	 * 
	 */
	public RequestBase(HashMap<String, Object> parameters,
			HttpServletRequest request, HttpServletResponse response, int method)
	{
		_parameters = parameters;
		_request = request;
		_response = response;
		_method = method;
		_webTools = new EWebTools(_request);
	}

	public abstract void run();

	public int getMethod()
	{
		return _method;
	}

	/* Ja retorna limpa de mysql e de HTML inject */
	public String getParameter(String name)
	{
		String ret = "";
		if (_parameters.containsKey(name))
		{
			ret = _parameters.get(name).toString();
			ret = (ret == null) ? "" : ret;
			ret = HtmlFilter.filter(ret);
			return ret;
		}
		return ret;

	}

	public String getParameter(String name, String ignorelist[])
	{
		String ret = "";
		if (_parameters.containsKey(name))
		{
			ret = _parameters.get(name).toString();
			ret = (ret == null) ? "" : ret;
			ret = HtmlFilter.filter(ret, ignorelist);
			return ret;
		}
		return ret;

	}

	public DiskFileItem getFile(String name)
	{
		DiskFileItem ret = null;

		if (_parameters.containsKey(name)
				&& _parameters.get(name) instanceof DiskFileItem)
		{
			ret = (DiskFileItem) _parameters.get(name);
		}
		return ret;
	}

	public HttpServletRequest getRequest()
	{
		return _request;
	}

	public HttpServletResponse getResponse()
	{
		return _response;
	}

	/*
	 * public boolean isFlood() throws IOException { int key =
	 * this.getRequest().hashCode(); if (_requests.containsKey(key)) {
	 * _response.sendRedirect(_requests.get(key).getSend()); return true; }
	 * return false; }
	 * 
	 * public void clearFlood(int timeout) { String[] keys = new
	 * String[_requests.size()]; keys = _requests.keySet().toArray(keys); for
	 * (int cont = 0; cont < keys.length; cont++) { RequestSend rs =
	 * _requests.get(keys[cont]);
	 * 
	 * if ((rs.getTime() + timeout) < System.currentTimeMillis()) {
	 * _requests.remove(keys[cont]); } } }
	 */
	public void sendRedirect(String sURL) throws IOException
	{
		/*
		 * int key = this.getRequest().hashCode(); if
		 * (!_requests.containsKey(key)) { RequestSend rs = new
		 * RequestSend(sURL); _requests.put(key, rs); }
		 */
		_response.sendRedirect(sURL);
	}

	public HashMap<String, Object> getParameters()
	{
		return _parameters;
	}

	public void setParameters(HashMap<String, Object> parameters)
	{
		_parameters = parameters;
	}

	public EWebTools getWebTools()
	{
		return _webTools;
	}
}
