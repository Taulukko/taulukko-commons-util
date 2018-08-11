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
public class EStackList<V> extends EAbstractList<V>
{

	ENode<V> m_topNode = null;
	private int m_iCount = 0;

	/**Autor:ecarli
	 * Data:01/09/2003
	 * Objetivo:
	 * 
	 */
	public EStackList()
	{
	}

	public EStackList(EVector<V> vector)
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
			V ret = m_topNode.getInfo();
			//remove o no superior
			m_topNode = m_topNode.getChild();
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
			return m_topNode.getInfo();
		}
	}

	public void add(V obj)
	{
		ENode<V> top = new ENode<>();
		//seta a informacao para a pilha
		top.setInfo(obj);
		//seta o mapeamento
		top.setChild(m_topNode);
		if (!this.getIsEmpty())
			{
			m_topNode.setParent(top);
		}

		//atualiza o topo
		m_topNode = top;
		//atualiza o contador
		m_iCount++;
	}

	public Object clone()
	{
		EStackList<V> ret = new EStackList<>();

		ENode<V> node = m_topNode;

		while (node != null)
			{
			//adiciona o elemento no node
			ret.add(node.getInfo());
			//atualiza o node
			node = node.getChild();
		}

		return ret.revert();
	}

	public Enumeration<V> getElements()
	{
		return new EVisitorStackList<>(this);
	}

	public EIterator<V> getItens()
	{
		return new EVisitorStackList<V>(this);
	}

	public IEList<V> revert()
	{
		EStackList<V> ret = new EStackList<>();

		ENode<V> node = m_topNode;

		while (node != null)
			{
			//adiciona o elemento no node
			ret.add(node.getInfo());
			//atualiza o node
			node = node.getChild();
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
		return m_topNode == null;
	}

   
	public V remove(V obj)
	{
		try		
			{

			EStackList<V> lista = new EStackList<>();
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
		int iIndex=this.getLength()-1;
		while(varEnum.hasMoreElements())
		{
			ret.set(iIndex--,varEnum.nextElement());
		}
		
		//retorna o vetor
		return ret;
	}
}