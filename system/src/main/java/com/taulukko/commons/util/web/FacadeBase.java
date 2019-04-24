/*
 * Created on 05/03/2006
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.taulukko.commons.util.io.EFile;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class FacadeBase extends HttpServlet
{
	private static Logger _log;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int TP_GET = 1;

	public static final int TP_POST = 2;

	public static final int TP_FAIL_FILE_SIZE_CODE = 1;

	private String _messageURL = "";
 

	public static final String TP_MSG_BEAN = "MSG_BEAN";

	public static final long _maxFileSize = 100 * EFile.TP_KILO;

	private static FacadeBase _instance = null;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		executeActionBase(request, response, TP_GET);
	}

	public void sendMessage(int code, HttpServletRequest request,
			HttpServletResponse response)
	{
		switch (code)
		{
			case TP_FAIL_FILE_SIZE_CODE:
			{
				this.sendMessage(
						"Arquivo muito grande! Volte e selecione um arquivo de no máximo "
								+ (_maxFileSize / EFile.TP_KILO) + "KB",
						request, response);
				break;
			}
		}
	}

	public void sendMessage(String sMsg, HttpServletRequest request,
			HttpServletResponse response)
	{
		try
		{
			// esta faltando dados, falha de seguranca
			MessageBean messageBean = new MessageBean();
			messageBean.setMessage(sMsg);
			request.getSession().setAttribute(TP_MSG_BEAN, messageBean);
			response.sendRedirect(_messageURL);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		executeActionBase(request, response, TP_POST);
	}

	@SuppressWarnings("deprecation")
	protected void executeActionBase(HttpServletRequest request,
			HttpServletResponse response, int method) throws ServletException,
			IOException
	{

		try
		{
			// configura os parametros iniciais do sistema
			configParameters(request, response);

			int actionSwitch = -1;
			HashMap<String, Object> parameters = new HashMap<>();

			if (ServletFileUpload.isMultipartContent(request))
			{

				ServletFileUpload upload = new ServletFileUpload();
				upload.setFileItemFactory(new DiskFileItemFactory());

				// upload.setFileItemFactory(new DiskFileItemFactory());
				upload.setSizeMax(_maxFileSize);

				try
				{
					List items = upload.parseRequest(request);
					// Processa os itens do upload
					Iterator<DiskFileItem> iter = items.iterator();// FileItem
					while (iter.hasNext())
					{
						DiskFileItem item = iter.next();

						if (item.isFormField())
						{
							if (_log != null)
							{
								_log.info("Name:" + item.getFieldName());
								_log.info("Value:" + item.getString());
							}
							if (item.getFieldName().equals("actionSwitch"))
							{
								actionSwitch = Integer.parseInt(item
										.getString());
							}
							else
							{
								parameters.put(item.getFieldName(), item
										.getString());
							}

						}
						else
						{
							parameters.put(item.getFieldName(), item);
							/*
							 * String nome2 = item.getName(); StringBuffer bn =
							 * new StringBuffer(); bn.append("C:/");
							 * bn.append(nome2); File uploadedFile = new
							 * File(bn.toString()); item.write(uploadedFile);
							 */
						}
					}

				}
				catch (FileUploadException fuex)
				{
					String className = fuex.getClass().getName();
					if (className
							.equals("org.apache.commons.fileupload.FileUploadBase$SizeLimitExceededException"))
					{

						sendMessage(TP_FAIL_FILE_SIZE_CODE, request, response);
						return;
					}
					else
					{
						System.out.println(fuex.getMessage());
						System.out.println(fuex.getClass().getName());
						System.out
								.println(fuex.getCause().getClass().getName());
						fuex.printStackTrace();
					}
				}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
			else
			{

				Enumeration<String> items = request.getParameterNames();

				while (items.hasMoreElements())
				{
					String name = items.nextElement();
					if (name.equals("actionSwitch"))
					{
						actionSwitch = Integer.parseInt(request
								.getParameter("actionSwitch"));
					}
					else
					{
						parameters.put(name, new String(request.getParameter(name).getBytes("ISO-8859-1"), "UTF-8") );
					}
				}
			}

			executeAction(parameters, request, response, actionSwitch, method);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public abstract void executeAction(HashMap<String, Object> parameters,
			HttpServletRequest request, HttpServletResponse response,
			int actionSwitch, int method) throws Exception;

	public abstract void configParameters(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 
	 */
	public FacadeBase( )
	{
		super(); 
		_instance = this;
	}

	public static String getRealPath(String path)
	{
		String ret = null;

		try
		{
			if (_instance != null)
			{
				ServletContext context = _instance.getServletContext();
				ret = context.getRealPath(path);
			}
		}
		catch (NullPointerException e)
		{
			// fine
		}
		return ret;
	}

	public String getMessageURL()
	{
		return _messageURL;
	}

	public void setMessageURL(String messageURL)
	{
		_messageURL = messageURL;
	}

	public static long getMaxFileSize()
	{
		return _maxFileSize;
	}

	public static Logger getLog()
	{
		return _log;
	}

	public static void setLog(Logger log)
	{
		_log = log;
	}

}
