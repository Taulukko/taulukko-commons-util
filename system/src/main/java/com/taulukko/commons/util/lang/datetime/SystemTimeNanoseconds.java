package com.taulukko.commons.util.lang.datetime;

public class SystemTimeNanoseconds implements ITime {

	private static SystemTimeNanoseconds instance = null;

	private SystemTimeNanoseconds() {
	}

	public static SystemTimeNanoseconds getInstance() {
		if (instance == null) {
			instance = new SystemTimeNanoseconds();
		}
		return instance;
	}

	@Override
	public long getTime() {
		return System.nanoTime();
	}

}
