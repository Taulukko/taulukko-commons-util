package com.taulukko.commons.util.struct;

import java.util.ArrayList;
import java.util.Enumeration;

/*
 * Created on 07/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Edson Vicente Carli Junior.
 * Objetivo: Classe que cria um objeto com o conceito de conjuntos.
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ESet<O>
{
	private ArrayList<O> _vSet = new ArrayList<O>();

	public void add(O obj)
	{
		boolean bExiste = false;

		//verifica se ja existe o elemento
		int i;
		for (i = 0; i < _vSet.size() && !bExiste; i++)
		{
			bExiste = obj.equals(_vSet.get(i));
		}

		if (bExiste)
		{
			//remove a versao antiga
			_vSet.remove(--i);
		}
		//adiciona a versao nova
		_vSet.add(obj);
	}

	public O get(O obj)
	{
		boolean bExiste = false;
		O ret = null;

		//verifica se ja existe o elemento
		for (int i = 0; i < _vSet.size() && !bExiste; i++)
		{
			ret = _vSet.get(i);
			bExiste = obj.equals(ret);
		}

		return (bExiste) ? ret : null;
	}

	public O get(int iIndex)
	{
		return _vSet.get(iIndex);
	}

	public int getLength()
	{
		return _vSet.size();
	}

	public Enumeration<O> getElements()
	{
		return new EVisitiorSet<O>(this);
	}

	public O remove(int iIndex)
	{
		O ret = _vSet.get(iIndex);
		_vSet.remove(iIndex);
		return ret;
	}

	public O remove(O obj)
	{
		//verifica se ja existe o elemento
		for (int i = 0; i < _vSet.size(); i++)
		{
			if (obj.equals(_vSet.get(i)))
			{
				return remove(i);
			}
		}
		//se nao achar retorna nulo
		return null;
	}
}
