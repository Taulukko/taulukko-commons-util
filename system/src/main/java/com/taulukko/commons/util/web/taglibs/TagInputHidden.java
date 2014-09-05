package com.taulukko.commons.util.web.taglibs;

public class TagInputHidden extends TagInput
{
	public TagInputHidden(String sName, String sText)
	{
		super("hidden", sName, sText);

	}
	
	public TagInputHidden(String sName, String sText, int size)
	{
		super("hidden", sName, sText,size);

	}
}
