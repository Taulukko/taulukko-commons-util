package com.taulukko.commons.util.mesure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.lang.EDate;
import com.taulukko.commons.util.lang.datetime.ITime;
import com.taulukko.commons.util.metrics.MeasureTimer;

public class MeasureTimerFunctionTest {

	List<Long> origin = new ArrayList<>();

	@Test
	public void common() throws TaulukkoException {
		long end = 1 + 1000000 + 1000 * 1000000 + 60 * 1000 * 1000000 + 60 * 60 * 1000 * 1000000
				+ 3 * 24 * 60 * 60 * 1000 * 1000000;

		List<Long> list = Arrays.asList(0L, end);

		origin = cloneList(list);

		ITime time = () -> getPseudoTimer();

		MeasureTimer measureTimer = new MeasureTimer(time);

		Assert.assertEquals(end, measureTimer.getTime());

		list = Arrays.asList(end - EDate.TP_HOUR_IN_NS, end);

	}
	

	@Test
	public void toStringTest() throws TaulukkoException {
		
		long start = EDate.TP_MINUTE_IN_NS ;
		long end = start + EDate.TP_HOUR_IN_NS  + 1;
		
		List<Long> list = Arrays.asList(end - EDate.TP_HOUR_IN_NS - 1, end);

		origin = cloneList(list);

		ITime time = () -> getPseudoTimer();

		MeasureTimer measureTimer = new MeasureTimer(time);

		Assert.assertEquals("01:00:00.0000.000001", measureTimer.toString()); 

	}

	@Test
	public void reset() throws TaulukkoException {
		long end = 1 + 1000000 + 1000 * 1000000 + 60 * 1000 * 1000000 + 60 * 60 * 1000 * 1000000
				+ 3 * 24 * 60 * 60 * 1000 * 1000000;

		List<Long> list = Arrays.asList(0L, end - EDate.TP_HOUR_IN_NS, end);

		origin = cloneList(list);

		ITime time = () -> getPseudoTimer();

		MeasureTimer measureTimer = new MeasureTimer(time);

		measureTimer.reset();

		Assert.assertEquals(EDate.TP_HOUR_IN_NS, measureTimer.getTime());

	}

	private <T> List<T> cloneList(List<T> list) {
		List<T> copy = new ArrayList<>();
		list.forEach(i -> copy.add(i));
		return copy;
	}

	private long getPseudoTimer() {
		Long l = origin.get(0);
		origin.remove(l);
		return l;
	}

}
