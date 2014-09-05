/*
 * Created on 25/10/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

/**
 * @author Usuario
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EProperty<T>
{
	String _key = "";

	T _obj = null;

	/**
	 * Desenvolvedor: Usuario Data:25/10/2003
	 * 
	 */
	public EProperty(String key, T obj)
	{
		_key = key;
		_obj = obj;

	}

	/**
	 * Autor: Usuario Data: 25/10/2003 Dependencias: Objetivo:
	 * 
	 * @return
	 */
	public T getValue()
	{
		return _obj;
	}

	/**
	 * Autor: Usuario Data: 25/10/2003 Dependencias: Objetivo:
	 * 
	 * @return
	 */
	public String getKey()
	{
		return _key;
	}

	/**
	 * Autor: Usuario Data: 25/10/2003 Dependencias: Objetivo:
	 * 
	 * @param object
	 */
	public void setValue(T object)
	{
		_obj = object;
	}

	/**
	 * Autor: Usuario Data: 25/10/2003 Dependencias: Objetivo:
	 * 
	 * @param string
	 */
	public void setKey(String string)
	{
		_key = string;
	}

}
