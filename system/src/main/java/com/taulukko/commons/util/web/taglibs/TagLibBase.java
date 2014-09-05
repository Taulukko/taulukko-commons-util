package com.taulukko.commons.util.web.taglibs;

public abstract class TagLibBase
{
	private String _classCSS = "";

	private boolean _hasValidation = true;

	private String _onClick;

	private String _onChange;

	private String _onBlur;

	private String _onFocus;

	public String getOnChange()
	{
		return _onChange;
	}

	public void setOnChange(String onChange)
	{
		_onChange = onChange;
	}

	public String getOnClick()
	{
		return _onClick;
	}

	public void setOnClick(String click)
	{
		_onClick = click;
	}

	public abstract String printBeginTag();

	public abstract String printIntoTag();

	public abstract String printEndTag();

	public String printBeginValidation()
	{
		return "<SCRIPT>";
	}

	public String printIntoValidation()
	{
		return "";
	}

	public String printEndValidation()
	{
		return "</SCRIPT>";
	}

	public String printAllValidation()
	{
		return printBeginValidation() + printIntoValidation()
				+ printEndValidation();
	}

	public String printAllTag()
	{
		if (_hasValidation)
		{
			return printBeginTag() + printIntoTag() + printEndTag()
					+ printAllValidation();
		}

		return printBeginTag() + printIntoTag() + printEndTag();

	}

	public String toString()
	{
		return printAllTag();
	}

	public boolean getHasValidation()
	{
		return _hasValidation;
	}

	public void setHasValidation(boolean hasValidation)
	{
		_hasValidation = hasValidation;
	}

	public String getOnBlur()
	{
		return _onBlur;
	}

	public void setOnBlur(String onBlur)
	{
		_onBlur = onBlur;
	}

	public String getOnFocus()
	{
		return _onFocus;
	}

	public void setOnFocus(String onFocus)
	{
		_onFocus = onFocus;
	}

	public String getClassCSS()
	{
		return _classCSS;
	}

	public void setClassCSS(String classCSS)
	{
		_classCSS = classCSS;
	}
}
