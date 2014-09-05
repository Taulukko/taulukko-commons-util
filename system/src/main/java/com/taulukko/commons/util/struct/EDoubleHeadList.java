/*
 * Created on 01/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

import java.util.Enumeration;


/**
 * @author Usuario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author ecarli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDoubleHeadList 
{

	EDoubleNode m_FirstNode = null;
	EDoubleNode m_LastNode = null;
	private int m_iCount = 0;

	/**Autor:ecarli
	 * Data:01/09/2003
	 * Objetivo:
	 * 
	 */
	public EDoubleHeadList()
	{
	}

	public EDoubleHeadList(EVector vector)
	{
		//cria uma instancia de EPileList passando os elementos de vector para a pilha
		for (int i = 0; i < vector.getLength(); i++)
			{
			this.addAfter(vector.get(i));
		}
	}

	public int getLength()
	{
		return m_iCount;
	}

	public Object removeLast() throws ENoSearchException
	{
		if (this.getIsEmpty())
			{
			throw new ENoSearchException();
		}
		else
			{
			//Cria o retorno
			Object ret = m_LastNode.getInfo();
			//remove o no superior
			m_LastNode = m_LastNode.getPreviousChild();
			//atualiza a quantidade
			m_iCount--;
			return ret;
		}
	}

	public Object removeFirst() throws ENoSearchException
		{
			if (this.getIsEmpty())
				{
				throw new ENoSearchException();
			}
			else
				{
				//Cria o retorno
				Object ret = m_FirstNode.getInfo();
				//remove o no superior
				m_FirstNode= m_FirstNode.getNextChild();
				//atualiza a quantidade
				m_iCount--;
				return ret;
			}
		}
	public Object getLast() throws ENoSearchException
	{
		if (this.getIsEmpty())
			{
			throw new ENoSearchException();
		}
		else
			{
			return m_LastNode.getInfo();
		}
	}
	
	public Object getFirst() throws ENoSearchException
	{
		if (this.getIsEmpty())
			{
			throw new ENoSearchException();
		}
		else
			{
			return m_FirstNode.getInfo();
		}
	}

	public void addAfter(Object obj)
	{
		EDoubleNode after = new EDoubleNode();
		//seta a informacao para a pilha
		after.setInfo(obj);
		//seta o mapeamento
		after.setPreviousChild(m_LastNode);
		if (this.getIsEmpty())
		{
			//atualiza o primeiro
			m_FirstNode=after;
		}
		else
		{
			//atualiza o proximo
			after.getPreviousChild().setNextChild(after);			
		}
		
		//atualiza o topo
		m_LastNode= after;
		
		//atualiza o contador
		m_iCount++;
	}


	public void addBefore(Object obj)
	{
		EDoubleNode before = new EDoubleNode();
		//seta a informacao para a pilha
		before.setInfo(obj);
		//seta o mapeamento
		before.setNextChild(m_FirstNode);
		if (this.getIsEmpty())
		{
			m_LastNode=before;
		}
		else
		{
			//atualiza o anterior
			before.getNextChild().setPreviousChild(before);
		}
		
		//atualiza o topo
		m_FirstNode= before;
		//atualiza o contador
		m_iCount++;
	}


	public Object clone()
	{
		EDoubleHeadList ret = new EDoubleHeadList();

		EDoubleNode node = m_FirstNode;

		while (node != null)
			{
			//adiciona o elemento no node
			ret.addAfter(node.getInfo());
			//atualiza o node
			node = node.getNextChild();
		}

		return ret.revert();
	}

	public Enumeration getElements()
	{
		return new EVisitorDoubleHeadList(this);
	}

	public EIterator getItens()
	{
		return new EVisitorDoubleHeadList(this);
	}

	public EDoubleHeadList revert()
	{
		EDoubleHeadList ret = new EDoubleHeadList();

		EDoubleNode node = m_LastNode;

		while (node != null)
			{
			//adiciona o elemento no node
			ret.addBefore(node.getInfo());
			//atualiza o node
			node = node.getPreviousChild();
		}
		
		return ret;
	}
	/* (non-Javadoc)
	 * Autor:ecarli
	 * Data:01/09/2003
	 * Objetivo:
	 * @see br.com.vb.struct.IEList#isEmpty()
	 */
	public boolean getIsEmpty()
	{
		return m_FirstNode == null || m_LastNode == null;
	}

	public Object remove(Object obj)
	{
		try		
			{

			EDoubleHeadList lista = new EDoubleHeadList();
			long lTamanhoOriginal = this.getLength();

			//inverte a pilha 
			while (this.getLength() > 0)
				{
				Object objApagado = this.removeLast();
				if (!obj.equals(objApagado))
					{
					lista.addAfter(objApagado);
				}
			}

			while (lista.getLength() > 0)
				{
				//adiciona o item que foi removido 
				this.addAfter(lista.removeLast());
			}

			if (lTamanhoOriginal == this.getLength())
				{
				return null;
			}
			else
			{
				return obj;
			}
		}
		catch (ENoSearchException nse)
			{
			nse.printStackTrace();
			return null;
		}
		
		
	}
	
	/**Autor:Edson Vicente Carli Junior
	 * Data:06/12/2003
	 * Objetivo: Retorna um vetor com os elementos da pilha.
	 */
	public EVector toVector()
	{
		//cria a variavel de retorno
		EVector ret = new EVector(this.getLength());
		
		//captura o enumerador
		Enumeration varEnum = this.getElements();
		
		//para cada elemento em this
		for(int iIndex=0;varEnum.hasMoreElements();iIndex++)
		{
			ret.set(iIndex,varEnum.nextElement());
		}
		
		//retorna o vetor
		return ret;
	}
}