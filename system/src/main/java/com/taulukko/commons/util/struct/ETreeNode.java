/*
 * Criado em 06/06/2004
 *
 
 */
package com.taulukko.commons.util.struct;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.lang.EString;

public class ETreeNode<T> extends EBase
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private HashMap<String,ETreeNode<T>> m_Nodes = new HashMap<String,ETreeNode<T>>();
	private String m_sName;
	private T m_value;
	private ETreeNode<T> m_parent;

	ETreeNode()
	{
	}

	public ETreeNode(String sName)
	{
		m_sName = sName;
	}

	public ETreeNode(String sName, T value)
	{
		m_sName = sName;
		m_value = value;
	}

	/**Adiciona ou altera uma propriedade filha para um determinado lugar especifico.
	 * <BR>sPath = caminho da propriedade, o separador de caminhos e o ponto (.)
	 * <BR>Ex: br.com.evon.nomes
	 *  <BR>value = valor da propriedade
	 * */
	public void setProperty(String sPath, T value)
	{
		String sDirs[] = new EString(sPath).split(".");

		if (sDirs.length == 0)
		{
			//nao deve ser feito nada 
			return;
		}
		else
		{
			ETreeNode<T> node = (ETreeNode<T>) m_Nodes.get(sDirs[0]);

			if (node == null)
			{
				//ela ainda nao existia, cria entao a propriedade
				node = new ETreeNode<T>(sDirs[0]);
				this.setNode(node);
			}

			//a propriedade ja existe             	
			if (sDirs.length == 1)
			{
				if (value == null)
				{
					//deve ser apagado o valor na tabela
					m_Nodes.put(sDirs[0], null);
				}
				else
				{
					//nao e sub propriedade
					//altera o valor
					node.setValue(value);
				}
			}
			else
			{
				//e sub propriedade
				node.setProperty(
					EString.join(sDirs, ".", 1, sDirs.length).toString(),
					value);

			}

		}

	}

	/**Adiciona ou altera um objeto filho.
	 * */
	public void setNode(ETreeNode<T> node)
	{
		//apaga a referencia no pai anterior,
		//isso evita que o getParent de referencia para um novo pai
		//e o antigo pai ainda tenha um filho com mesmo nome.
		if (node.getParent() != null)
		{
			node.getParent().setProperty(node.getName(), null);
		}
		//seta o pai
		node.setParent(this);
		//adiciona/altera node
		m_Nodes.put(node.getName(), node);
	}

	/**Retorna o valor de uma propriedade filha .
	 * <BR>sPath = caminho da propriedade, o separador de caminhos e o ponto (.)
	 * <BR>Ex: br.com.evon.nomes	 
	 * */
	public T getProperty(String sPath)
	{
		String sDirs[] = new EString(sPath).split(".");
		T ret = null;

		if (sDirs.length == 1)
		{
			ret = m_Nodes.get(sDirs[0]).getValue();
		}
		else if (sDirs.length > 1)
		{
			ret =
				m_Nodes.get(sDirs[0]).getProperty(
					EString.join(sDirs, ".", 1, sDirs.length).toString());
		}

		//retorna o objeto
		return ret;
	}

	/**Retorna o valor de uma propriedade filha .
	 * <BR>sPath = caminho da propriedade, o separador de caminhos e o ponto (.)
	 * <BR>Ex: br.com.evon.nomes	 
	 * */
	public ETreeNode<T> getNode(String sPath)
	{
		String sDirs[] = new EString(sPath).split(".");
		ETreeNode<T> ret = null;

		if (sDirs.length == 1)
		{
			ret = m_Nodes.get(sDirs[0]);
		}
		else if (sDirs.length > 1)
		{
			ret =
				(m_Nodes.get(sDirs[0])).getNode(
					EString.join(sDirs, ".", 1, sDirs.length).toString());
		}

		//retorna o objeto
		return ret;
	}
	public Object clone()
	{
		return this;
	}
	/**
	 * @return
	 */
	public EVector<ETreeNode<T>> getNodes()
	{
		EVector<ETreeNode<T>> ret = new EVector<ETreeNode<T>>(this.getLength());

		Iterator<ETreeNode<T>> iterator = m_Nodes.values().iterator();

		for (int iCount = 0; iterator.hasNext(); iCount++)
		{
			//adiciona ao vector    		
			ret.set(iCount, iterator.next());
		}
		return ret;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return m_sName;
	}

	/**
	 * @return
	 */
	public T getValue()
	{
		return m_value;
	}

	/**
	 * @param lString
	 */
	void setName(String lString)
	{
		m_sName = lString;
	}

	/**
	 * @param lObject
	 */
	void setValue(T lObject)
	{
		m_value = lObject;
	}

	/**
	 * @return
	 */
	public ETreeNode<T> getParent()
	{
		return m_parent;
	}

	/**
	 * @param lNode
	 */
	void setParent(ETreeNode<T> lNode)
	{
		m_parent = lNode;
	}

	public int getLength()
	{
		return m_Nodes.size();
	}

	public Enumeration<ETreeNode<T>> getElements()
	{
		return getNodes().getElements();
	}

	public Iterator<ETreeNode<T>> getItens()
	{
		return getNodes().getItens();
	}

	public T removeProperty(String sPath)
	{
		String sDirs[] = new EString(sPath).split(".");
		T ret = null;

		if (sDirs.length == 1)
		{
			ret = m_Nodes.get(sDirs[0]).getValue();
			//remove
			m_Nodes.remove(ret);
		}
		else if (sDirs.length > 1)
		{
			ret =
				m_Nodes.get(sDirs[0]).removeProperty(
					EString.join(sDirs, ".", 1, sDirs.length).toString());
		}

		//retorna o objeto
		return ret;

	}
}
