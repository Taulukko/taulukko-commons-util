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
 * Classe tipo fila.
 */
/**
 * @author ecarli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EPoolList<V> extends EAbstractList<V> implements IEList<V>
{

	ENode<V> m_baseNode = null;
	ENode<V> m_topNode = null;
	private int m_iCount = 0;

	/**Autor:ecarli
	 * Data:01/09/2003
	 * Objetivo:
	 * 
	 */
	public EPoolList()
	{
	}

	public EPoolList(EVector<V> vector)
	{
		//cria uma instancia de EPileList passando os elementos de vector para a pilha
		for (int i = 0; i < vector.getLength(); i++)
		{
			this.add(vector.get(i));
		}
	}

	public int getLength()
	{
		return m_iCount;
	}

	public V remove() throws ENoSearchException
	{
		if (this.getIsEmpty())
		{
			throw new ENoSearchException();
		}
		else
		{
			//Cria o retorno
			V ret = m_baseNode.getInfo();
			//remove o no superior
			m_baseNode = m_baseNode.getParent();
			//atualiza a quantidade
			m_iCount--;
			return ret;
		}
	}

	public V get() throws ENoSearchException
	{
		if (this.getIsEmpty())
		{
			throw new ENoSearchException();
		}
		else
		{
			return m_baseNode.getInfo();
		}
	}

	public void add(V obj)
	{
		ENode<V> top = new ENode<>();
		//seta a informacao para a fila
		top.setInfo(obj);
		//seta o mapeamento
		top.setChild(m_topNode);
		if (!this.getIsEmpty())
		{
			m_topNode.setParent(top);
			//atualiza o topo
			m_topNode = top;

		}
		else
		{
			//atualiza o topo
			m_topNode = top;
			//atualiza a base			
			m_baseNode = top;
		}
						
		//atualiza o contador
		m_iCount++;
	}

	public Object clone()
	{
		EPoolList<V> ret = new EPoolList<>();

		ENode<V> node = m_baseNode;

		while (node != null)
		{
			//adiciona o elemento no node
			ret.add(node.getInfo());
			//atualiza o node
			node = node.getChild();
		}

		return ret;
	}

	public Enumeration<V> getElements()
	{
		return new EVisitorPoolList<>(this);
	}

	public EIterator<V> getItens()
	{
		return new EVisitorPoolList<>(this);
	}

	public IEList<V> revert()
	{
		EStackList<V> lista = new EStackList<>();
		EPoolList<V> ret = new EPoolList<>();

		ENode<V> node = m_baseNode;

		while (node != null)
		{
			//adiciona o elemento no node
			lista.add(node.getInfo());
			//atualiza o node
			node = node.getChild();
		}

		Enumeration<V> varEnum = lista.getElements();

		while (varEnum.hasMoreElements())
		{
			ret.add(varEnum.nextElement());
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
		return m_baseNode == null;
	}

	public V remove(V obj)
	{
		try
		{

			EPoolList<V> lista = new EPoolList<>();
			long lTamanhoOriginal = this.getLength();

			//inverte a pilha 
			while (this.getLength() > 0)
			{
				V objApagado = this.remove();
				if (!obj.equals(objApagado))
				{
					lista.add(objApagado);
				}
			}

			while (lista.getLength() > 0)
			{
				//adiciona o item que foi removido 
				this.add(lista.remove());
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
	public EVector<V> toVector()
	{
		//cria a variavel de retorno
		EVector<V> ret = new EVector<>(this.getLength());

		//captura o enumerador
		Enumeration<V> varEnum = this.getElements();

		//para cada elemento em this
		for (int iIndex = 0; varEnum.hasMoreElements(); iIndex++)
		{
			ret.set(iIndex, varEnum.nextElement());
		}

		//retorna o vetor
		return ret;
	}
}