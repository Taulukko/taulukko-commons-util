package com.taulukko.commons.util.lang.datetime;

public class SystemTimeMiliseconds implements ITime {

	private static SystemTimeMiliseconds instance = null;

	private SystemTimeMiliseconds() {
	}

	public static SystemTimeMiliseconds getInstance() {
		if (instance == null) {
			instance = new SystemTimeMiliseconds();
		}
		return instance;
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis() * 1000000;
	}

}
