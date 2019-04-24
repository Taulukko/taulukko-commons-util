package com.taulukko.commons.util.lang.datetime;

public class ENanoSecond implements ISliceOfTime {
	private long nanos = 0;

	public ENanoSecond(long nanos) {
		this.nanos = nanos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.commons.util.lang.datetime.ISliceOfTime#getNanosecond()
	 */
	@Override
	public long getNanoseconds() {
		return nanos;
	}

}
