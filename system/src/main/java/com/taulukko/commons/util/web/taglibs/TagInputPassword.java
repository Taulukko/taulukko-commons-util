package com.taulukko.commons.util.web.taglibs;

public class TagInputPassword extends TagInput
{
	public TagInputPassword(String sName, String sText)
	{
		super("password", sName, sText);

	}
	
	public TagInputPassword(String sName, String sText, int size)
	{
		super("password", sName, sText,size);

	}
}
