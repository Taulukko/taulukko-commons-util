package com.taulukko.commons.util.metrics;

import java.text.NumberFormat;

import org.apache.commons.lang3.time.DurationFormatUtils;

import com.taulukko.commons.util.lang.EDate;
import com.taulukko.commons.util.lang.datetime.ITime;
import com.taulukko.commons.util.lang.datetime.SystemTimeNanoseconds;

public class MeasureTimer {

	/**
	 * Start timer.
	 */
	private long start = 0;
	private ITime time = null;

	/**
	 * Start timer.
	 */
	public MeasureTimer() {
		time = SystemTimeNanoseconds.getInstance();
		reset();
	}

	public MeasureTimer(ITime time) {
		this.time = time;
		reset();
	}

	/**
	 * Returns exact number of nanos since timer was started.
	 * 
	 * @return Number of nanos since timer was started.
	 */
	public long getTime() {
		return time.getTime() - start;
	}

	/**
	 * Restarts the timer.
	 */
	public void reset() {
		start = time.getTime();
	}

	/**
	 * Returns a formatted string showing the elaspsed time suince the instance was
	 * created.
	 * 
	 * @return Formatted time string.
	 */
	public String toString() {

		long duration = getTime();
		
		long nanos =  duration % EDate.TP_MILISECOND_IN_NS;
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumIntegerDigits(6);
		formatter.setGroupingUsed(false);
 		
		return DurationFormatUtils.formatDuration(duration / EDate.TP_MILISECOND_IN_NS, "HH:mm:ss.SSSS.")
				+   formatter.format( nanos);

	}

	/**
	 * Testing this class.
	 * 
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		MeasureTimer timer = new MeasureTimer();
		for (int i = 0; i < 100000000; i++) {
			double b = 998.43678;
			double c = Math.sqrt(b);
		}
		System.out.println(timer);
	}
}
