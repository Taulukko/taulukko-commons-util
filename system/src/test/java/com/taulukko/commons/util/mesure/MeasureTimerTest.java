package com.taulukko.commons.util.mesure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.lang.EDate;
import com.taulukko.commons.util.lang.datetime.ITime;
import com.taulukko.commons.util.metrics.FunctionPreToString;
import com.taulukko.commons.util.metrics.MeasureTimer;

public class MeasureTimerTest {

	List<Long> origin = new ArrayList<>();

	@Test
	public void toStringTest() throws TaulukkoException {

		long start = EDate.TP_MINUTE_IN_NS;
		long end = start + EDate.TP_HOUR_IN_NS + 1;

		List<Long> list = Arrays.asList(end - EDate.TP_HOUR_IN_NS - 1, end);

		origin = cloneList(list);

		ITime time = () -> getPseudoTimer();

		MeasureTimer measureTimer = new MeasureTimer(time);

		FunctionPreToString<MeasureTimer> functionPreToString = new FunctionPreToString<>(measureTimer);

		Assert.assertEquals("com.taulukko.commons.util.mesure.MeasureTimerTest.toStringTest(MeasureTimerTest.java:36) : 01:00:00.0000.000001", functionPreToString.toString());

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
