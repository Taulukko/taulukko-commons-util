package com.taulukko.commons.util.struct;

public class EPoolTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		EPoolList<Integer> list = new EPoolList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		try
		{
			System.out.println(list.remove());
			System.out.println(list.remove());
			System.out.println(list.remove());
			System.out.println(list.remove());
			System.out.println(list.remove());
		}
		catch(Exception e){}
	}

}
