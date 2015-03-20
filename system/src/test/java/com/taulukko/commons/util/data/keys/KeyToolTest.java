package com.taulukko.commons.util.data.keys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.util.data.keys.Key;
import com.taulukko.commons.util.data.keys.KeyTool;

public class KeyToolTest {


	/**
	 * Version (formato DD 2) eg 10 para versão 1.0 CLUSTER-ID (formato DDD 3) +
	 * Thread Id (formato DDD DDD DDD DDD DDD DDD DDD 21) + Random Id (formato
	 * DDD DDD 6) + nanoSeconds ( formato DDD DDD DDD DDD DDD DDD DDD 21) Total: Dx53
	 * */

	@Test
	public void stringToKeyTest() {
		String strKey = new BigInteger(
				"12003000000000000000000004000005000000000000000000006")
				.toString(KeyTool.RADIX_BASE);

		Key key = KeyTool.stringToKey(strKey);
		Assert.assertNotNull(key); 
		Assert.assertEquals(6, key.getNanos());
		Assert.assertEquals(5, key.getRandom());
		Assert.assertEquals(4, key.getThreadId());
		Assert.assertEquals(3, key.getClusterId());
		Assert.assertEquals(2, key.getMinorVersion());
		Assert.assertEquals(1, key.getMajorVersion());
	}

	@Test
	public void goAndBack() throws Exception {
		
		long startNanos = System.nanoTime();
		
		String strKeyBase36 = KeyTool.build(1);
		
		String strKeyBigInt = new BigInteger(strKeyBase36,KeyTool.RADIX_BASE).toString();
		
		System.out.println(strKeyBigInt);
		System.out.println(strKeyBase36);

		Key key = KeyTool.stringToKey(strKeyBase36);
		Assert.assertNotNull(key); 
		Assert.assertEquals(1, key.getClusterId());
		Assert.assertEquals(0, key.getMinorVersion());
		Assert.assertEquals(1, key.getMajorVersion());
		
		long endNanos = System.nanoTime();
		
		Assert.assertTrue(key.getNanos() > startNanos);
		Assert.assertTrue(key.getNanos() < endNanos );
		 
	}

	@Test
	public void testStaticFields() throws Exception {
		String strUUID = KeyTool.build(1);
		Assert.assertEquals(34, strUUID.length());
		Key uuid = KeyTool.stringToKey(strUUID);
		Assert.assertEquals(1, uuid.getClusterId());
		Assert.assertEquals(1, uuid.getMajorVersion());
		Assert.assertEquals(0, uuid.getMinorVersion());
		Assert.assertEquals(Thread.currentThread().getId(), uuid.getThreadId());
	}

	@Test
	public void testDistinct() throws Exception {
		List<String> listUUID = new ArrayList<>();

		String arrUUID[] = new String[10000];

		for (int index = 0; index < 10000; index++) {
			arrUUID[index] = KeyTool.build(1);
		}

		for (String uuid : arrUUID) {
			if (listUUID.contains(uuid)) {
				int index1 = -1;
				int index2 = -1;
				for (int index = 0; index < 10000; index++) {
					if (uuid.equals(arrUUID[index])) {
						if (index1 == -1) {
							index1 = index;
						} else if (index2 == -1) {
							index2 = index;
						}
					}
				}

				Assert.fail("UUID repeat in position " + index1 + " and "
						+ index2);
			} else {
				listUUID.add(uuid);
			}
		}

	}

	@Test
	public void testPerformance() throws Exception {
		long start = System.nanoTime();
		// kk
		int creationNumber = 1000 * 1000;

		for (int index = 0; index < creationNumber; index++) {
			KeyTool.build(1);
		}

		long totalTime = System.nanoTime() - start;
		long totalTimePerCreation = totalTime / creationNumber;
		//0.01 ms or 10k ns
		Assert.assertTrue( totalTimePerCreation < 10 * 1000  );
		System.out.println(totalTimePerCreation);

	}
}
