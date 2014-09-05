package com.taulukko.commons.util.web;

public class UTFInvalidCharacters extends FilterCharacters
{

	public byte[] getInvalidCharacters()
	{
		return new byte[]{-1};
	}

}
