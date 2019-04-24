package com.taulukko.commons.util.lang.datetime;

public class EMilisecond implements ISliceOfTime {
	private long milis = 0;

	public EMilisecond(long lMilis) {
		milis = lMilis;
	}

	@Override
	public long getNanoseconds() {
		return milis * 1000000;
	}
}
