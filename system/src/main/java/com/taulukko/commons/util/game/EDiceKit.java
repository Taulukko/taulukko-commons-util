package com.taulukko.commons.util.game;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EDiceKit
{
	private int _count = 0;

	private int _faces = 0;

	private int _bonus = 0;

	private static Long _seed = System.currentTimeMillis();

	public EDiceKit(int iCount, int iFaces, int iBonus)
	{
		_count = iCount;
		_faces = iFaces;
		_bonus = iBonus;
	}

	//para gerar um random conforme os bits recebidos (32 para inteiro)
	private static int random(int bits)
	{
		synchronized (_seed)
		{
			_seed = (_seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
		}
		return (int) (_seed >>> (48 - bits));
	}

	//para gerar um random de 0  a 1 flutuante
	private static double porcent()
	{
		double ret = (double) (random(Integer.SIZE)) / Integer.MAX_VALUE;
		if (ret < 0)
		{
			ret *= -1;
		}
		return ret;
	}

	public int rool(long seed)
	{
		EDiceKit.setSeed(seed);
		return this.rool();
	}

	/*Para que as chances sejam equivalentes, 
	 * junta-se a metade final com a metade inicial 
	 * Resultado 	Chance
	 * 0			0.5 / (m_ifaces+1)
	 * 1			1 / (m_ifaces+1)
	 * 2			1 / (m_ifaces+1)
	 * 3			1 / (m_ifaces+1)
	 * ...			1 / (m_ifaces+1)
	 * m_ifaces			1 / (m_ifaces+1)
	 * m_ifaces+1			0.5 / (m_ifaces+1)	 
	 * */
	public synchronized int rool()
	{
		return rool(_count, _faces, _bonus);
	}
	public static String roolWithDesc(int times, int faces, int bonus)
	{
		return  roolWithDesc(times, faces, bonus, false);
	}
	
	public static String roolWithDesc(int times, int faces, int bonus, boolean bonusPerRoll)
	{
		String ret = "{";
		int sum = 0;
		for (int count = 0; count < times; count++)
		{
			int thisTime = rool(1, faces, 0);
			if (count > 0)
			{
				ret += ", ";
			}
			if(bonusPerRoll)
			{
				thisTime+=bonus;
			}
			ret += thisTime;
			sum += thisTime;
		}
		if(!bonusPerRoll)
		{
			sum += bonus;
		}
		ret += "} ";
		if(bonus>0 && !bonusPerRoll)
		{
			ret += "+ " + bonus + " ";
		}
		else if(bonus<0  && !bonusPerRoll)
		{
			ret += " " + bonus + " ";
		}
		ret += "= " + sum;
		return ret;
	}

	public static int rool(int times, int faces, int bonus)
	{
		int ret = 0;
		for (int count = 0; count < times; count++)
		{
			int thisTime = 0;
			//valor para teste
			thisTime = (int) (EDiceKit.porcent() * faces);
			//soma-se as metades
			ret += (thisTime == 0) ? faces : thisTime;
		}
		return ret + bonus;
	}

	/**
	 * @return
	 */
	public int getBonus()
	{
		return _bonus;
	}

	/**
	 * @return
	 */
	public int getCount()
	{
		return _count;
	}

	/**
	 * @return
	 */
	public int getFaces()
	{
		return _faces;
	}

	/**
	 * @param m_sI
	 */
	public void setBonus(int m_sI)
	{
		_bonus = m_sI;
	}

	/**
	 * @param m_sI
	 */
	public void setCount(int m_sI)
	{
		_count = m_sI;
	}

	/**
	 * @param m_sI
	 */
	public void setFaces(int m_sI)
	{
		_faces = m_sI;
	}

	public Object clone()
	{
		return new EDiceKit(_count, _faces, _bonus);
	}

	public static long getSeed()
	{
		return _seed;
	}

	public static void setSeed(long _seed)
	{
		EDiceKit._seed = _seed;
	}
}
