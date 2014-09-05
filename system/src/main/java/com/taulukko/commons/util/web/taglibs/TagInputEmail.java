package com.taulukko.commons.util.web.taglibs;

public class TagInputEmail extends TagInput
{
	private boolean _optional = false;

	public TagInputEmail(String sName, String sText, boolean optional)
	{
		super("text", sName, sText);
		_optional = optional;
	}

	public TagInputEmail(String sName, String sText)
	{
		this(sName, sText, false);
	}

	public TagInputEmail(String sName, long lText, boolean optional)
	{
		super("text", sName, lText);
		_optional = optional;
	}

	public TagInputEmail(String sName, long lText)
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
		sIntoValidation += "\n   var valid = isEmail(field.value) || (field.value==\"\" && "
				+ _optional + ") ;";
		sIntoValidation += "\n   if (!valid)";
		sIntoValidation += "\n   {";
		sIntoValidation += "\n      //no aceita texto, nem numeros flutuantes, apenas inteiro e nulo se for opcional";
		if(this.getFocusDelay()==0)
		{
			sIntoValidation += "\n      field.focus();";
		}
		else
		{
			sIntoValidation += "\n      setTimeout ('field.focus()', " + this.getFocusDelay() + ");";
		}
		sIntoValidation += "\n      return false;";
		sIntoValidation += "\n   }";
		sIntoValidation += "\n   return true;";
		return sIntoValidation;
	}
}
