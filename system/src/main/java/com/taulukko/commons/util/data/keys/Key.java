package com.taulukko.commons.util.data.keys;

public class Key {
	private int minorVersion = 0;

	private int majorVersion = 0;


	private int clusterId = 0;
	private long threadId = 0;
	private int random = 0;
	private long nanos = 0;

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int versionMajor) {
		this.majorVersion = versionMajor;
	}

	
	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int versionMinor) {
		this.minorVersion = versionMinor;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public long getNanos() {
		return nanos;
	}

	public void setNanos(long nanos) {
		this.nanos = nanos;
	}

}
