package com.taulukko.commons.util.data.keys;

import java.math.BigInteger;

import com.taulukko.commons.util.game.EDiceKit;
import com.taulukko.commons.util.lang.EString;

public class KeyTool {
	 

	protected static int RADIX_BASE = 36;
 
	public static String VERSION = "10"; 

	public static Key stringToKey(String strKey) {
		Key key = new Key();
		BigInteger bigIntKey = new BigInteger(strKey, RADIX_BASE);
		key.setNanos(bigIntKey.mod(new BigInteger("1000000000000000000000")).longValue());
		bigIntKey = bigIntKey.divide(new BigInteger("1000000000000000000000"));
		key.setRandom(bigIntKey.mod(new BigInteger("1000000")).intValue());
		bigIntKey = bigIntKey.divide(new BigInteger("1000000"));
		key.setThreadId(bigIntKey.mod(new BigInteger("1000000000000000000000")).longValue());
		bigIntKey = bigIntKey.divide(new BigInteger("1000000000000000000000"));
		key.setClusterId(bigIntKey.mod(new BigInteger("1000")).intValue());
		bigIntKey = bigIntKey.divide(new BigInteger("1000"));
		key.setMinorVersion(bigIntKey.mod(new BigInteger("10")).intValue());
		bigIntKey = bigIntKey.divide(new BigInteger("10"));
		key.setMajorVersion(bigIntKey.mod(new BigInteger("10")).intValue());
		return key;
	}

	/**
	 * Version (formato DD 2) eg 10 para versão 1.0
	 * CLUSTER-ID (formato DDD 3) + Thread Id (formato DDD DDD DDD DDD DDD DDD
	 * DDD 21) + Random Id (formato DDD DDD 6) + nanoSeconds ( formato DDD DDD
	 * DDD DDD DDD DDD DDD 21)  
	 * Total: Dx53
	 * @throws Exception 
	 * */

	public static String build(int iCluster) throws Exception {
		
		if(iCluster>=1000 | iCluster<0)
		{
			throw new Exception("Cluster must be between 0-999");
		}
		
		StringBuilder strKey = new StringBuilder();
		EString cluster = new EString("00" + String.valueOf(iCluster));
		strKey.append(VERSION);
		strKey.append(cluster.right(3).toString());

		EString threadid = new EString("00000000000000000000"
				+ String.valueOf(Thread.currentThread().getId()));
		strKey.append(threadid.right(21).toString());

		EString random = new EString("000000"
				+ String.valueOf(EDiceKit.rool(1, 1000000, -1)));
		strKey.append(random.right(6).toString());

		EString nanoTime = new EString("00000"
				+ String.valueOf(System.nanoTime()));
		strKey.append(nanoTime.right(21).toString());

		BigInteger key = new BigInteger(strKey.toString());

		return key.toString(RADIX_BASE);
	}

}
