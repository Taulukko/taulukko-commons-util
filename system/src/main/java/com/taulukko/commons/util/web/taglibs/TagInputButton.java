package com.taulukko.commons.util.web.taglibs;

public class TagInputButton extends TagInput
{
	public TagInputButton(String sName, String sText)
	{
		super("button", sName, sText);
		this.setHasValidation(false);
	}
	
	public TagInputButton(String sName, String sText,int size)
	{
		super("button", sName, sText,size);
		this.setHasValidation(false);
	}
}
