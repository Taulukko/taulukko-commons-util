/*
 * Created on 16/01/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.struct;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDoubleHeadListTest
{

	public static void main(String[] args)
	{
		EDoubleHeadList list = new EDoubleHeadList();
		list.addAfter(new Integer(3));
		list.addAfter(new Integer(4));
		list.addBefore(new Integer(2));
		list.addBefore(new Integer(1));

		try
		{
			System.out.println("Tamanho:" + list.getLength());
			System.out.println(list.getFirst().toString());
			list.removeFirst();
			System.out.println(list.removeFirst().toString());
			System.out.println(list.removeFirst().toString());
			System.out.println(list.getLast().toString());
			list.removeLast();
			System.out.println("Tamanho:" + list.getLength());
		} catch (Exception e)
		{

		}
	}
}
