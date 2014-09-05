package com.taulukko.commons.util.lang;

public class EByte
{

	public static byte[] intToBytes(int integer)
	{
		byte[] bytes = new byte[4];

		bytes[0] = (byte) (integer & 0x000000ff);
		bytes[1] = (byte) ((integer & 0x0000ff00) >> 8);
		bytes[2] = (byte) ((integer & 0x00ff0000) >> 16);
		bytes[3] = (byte) ((integer & 0xff000000) >> 24);

		return bytes;
	}

	public static int reverseBytesToInt(byte[] bytes)
	{
		int q3 = bytes[0] << 24;
		int q2 = bytes[1] << 16;
		int q1 = bytes[2] << 8;
		int q0 = bytes[3];
		if (q2 < 0)
			q2 += 16777216;
		if (q1 < 0)
			q1 += 65536;
		if (q0 < 0)
			q0 += 256;

		return q3 | q2 | q1 | q0;
	}

	public static int bytesToInt(byte[] bytes)
	{
		int q3 = bytes[3] << 24;
		int q2 = bytes[2] << 16;
		int q1 = bytes[1] << 8;
		int q0 = bytes[0];
		if (q2 < 0)
			q2 += 16777216;
		if (q1 < 0)
			q1 += 65536;
		if (q0 < 0)
			q0 += 256;

		return q3 | q2 | q1 | q0;
	}

	public static int bytesToInt(byte[] bytes, int index)
	{
		int q3 = bytes[index + 3] << 24;
		int q2 = bytes[index + 2] << 16;
		int q1 = bytes[index + 1] << 8;
		int q0 = bytes[index];
		if (q2 < 0)
			q2 += 16777216;
		if (q1 < 0)
			q1 += 65536;
		if (q0 < 0)
			q0 += 256;

		return q3 | q2 | q1 | q0;
	}

	public static int reverseBytesToInt(byte[] bytes, int index)
	{
		int q3 = bytes[index] << 24;
		int q2 = bytes[index + 1] << 16;
		int q1 = bytes[index + 2] << 8;
		int q0 = bytes[index + 3];
		if (q2 < 0)
			q2 += 16777216;
		if (q1 < 0)
			q1 += 65536;
		if (q0 < 0)
			q0 += 256;

		return q3 | q2 | q1 | q0;
	}
}
