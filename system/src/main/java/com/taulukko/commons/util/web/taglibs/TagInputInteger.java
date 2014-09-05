package com.taulukko.commons.util.web.taglibs;

public class TagInputInteger extends TagInput
{
	private boolean m_optional = false;

	public TagInputInteger(String sName, String sText, boolean optional)
	{
		super("text", sName, sText);
		m_optional = optional;
	}

	public TagInputInteger(String sName, String sText)
	{
		this(sName, sText, false);
	}

	public TagInputInteger(String sName, long lText, boolean optional)
	{
		super("text", sName, lText);
		m_optional = optional;
	}

	public TagInputInteger(String sName, long lText)
	{
		this(sName, lText, false);
	}

	@Override
	public String printAllTag()
	{
		return super.printAllTag();
	}

	@Override
	public String printIntoValidation()
	{
		// vai ser feito depois, pois vou pegar o fonte que fiz na simplify
		String sIntoValidation = super.printIntoValidation();
		sIntoValidation += "\n   //do field validation";
		sIntoValidation += "\n   var valid = isInteger(field.value) || (field.value==\"\" && "
				+ m_optional + ") ;";
		sIntoValidation += "\n   if (!valid)";
		sIntoValidation += "\n   {";
		sIntoValidation += "\n      //no aceita texto, nem numeros flutuantes, apenas inteiro e nulo se for opcional";
		sIntoValidation += "\n      field.focus();";
		sIntoValidation += "\n      return false;";
		sIntoValidation += "\n   }";
		sIntoValidation += "\n   return true;";
		return sIntoValidation;
	}
}
