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
public class EOrderList<T>
{

	EVector<EProperty<T>> m_obj = new EVector<>(10);

	int m_iIndex = 0;

	public void add(int iKey, T obj) throws Exception
	{
		add(iKey, obj, 0, m_iIndex - 1);
	}

	private void add(int iKey, T obj, int iStart, int iEnd) throws Exception
	{
		if (iEnd == -1)
		{
			// 1-n�o existem itens cadastrados
			m_obj.set(m_iIndex, new EProperty<>("" + iKey, obj));
			m_iIndex++;
		}

		if (iStart >= iEnd)
		{
			organizerInsert(new EProperty<>("" + iKey, obj), iStart);
			return;
		}

		// capturado o node
		EProperty<T> node = (EProperty<T>) m_obj.get((iEnd - iStart) / 2);

		if (("" + iKey).equals(node.getKey()))
		{
			// 1-A chave ja existe
			throw new Exception("Duplicate Key");
		}
		else if (new Integer("" + iKey).intValue() < iKey)
		{
			// 1-A chave � menor que o indice atual

			// atualiza o start
			iStart = ((iEnd - iStart) / 2) + 1;
			// tenta adicionar
			add(iKey, obj, iStart, iEnd);
		}
		else
		{
			// 1-A chave � maior que o indice atual

			// atualiza o start
			iEnd = ((iEnd - iStart) / 2) - 1;
			// tenta adicionar
			add(iKey, obj, iStart, iEnd);
		}
	}

	private void organizerInsert(EProperty<T> obj, int iStart)
	{
		// 1-nao existe a key
		if (m_iIndex >= m_obj.getLength())
		{
			// 1-nao existe a key
			// 2-precisa redimensioanr o vetor
			m_obj.redimPreserve(m_obj.getLength() + 10);

			for (int iCont = m_iIndex; iCont > iStart; iCont--)
			{
				// movimenta os itens para frente
				m_obj.set(iCont + 1, m_obj.get(iCont));
			}

			// adiciona o item no lugar correto
			m_obj.set(iStart, obj);
		}
	}

	public EProperty<T> get(int iIndex)
	{
		return m_obj.get(iIndex);
	}

	public int getLength()
	{
		return m_iIndex;
	}

	public void remove(int iKey) throws Exception
	{
		if (iKey > m_iIndex)
		{
			throw new Exception("Inexistent key");
		}

	}

}
