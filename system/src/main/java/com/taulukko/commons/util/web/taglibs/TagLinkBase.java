package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.web.beans.ValueBean;

public class TagLinkBase extends TagLibBase
{
	private String _url;

	private String _class;

	private String _font;

	private String _text;

	public TagLinkBase(String url, String sClass, String sFontColor,
			String sText, ValueBean values[])
	{
		_url = url;
		for (int iCont = 0; iCont < values.length; iCont++)
		{
			_url += "&" + values[iCont].getName() + "="
					+ values[iCont].getValue();
		}
		_class = sClass;
		_font = sFontColor;
		_text = sText;
		this.setHasValidation(false);
	}

	public TagLinkBase(String url, String sClass, String sFontColor,
			String sText)
	{
		this(url, sClass, sFontColor, sText, new ValueBean[] {});
	}

	public TagLinkBase(String url, String sText)
	{
		this(url, null, null, sText, new ValueBean[] {});
	}

	public TagLinkBase(String url, String sText, ValueBean values[])
	{
		this(url, null, null, sText, values);
	}

	public String getURL()
	{
		return _url;
	}

	public String printBeginTag()
	{
		String sBeginTag = "<a href=\"" + _url + "\" ";
		if (_class != null)
		{
			sBeginTag = sBeginTag + "class=\"" + _class + "\"";
		}
		sBeginTag = sBeginTag + ">";
		return sBeginTag;

	}

	public String printIntoTag()
	{
		String sIntoTag = null;
		if (_font != null)
		{
			sIntoTag = "<font color=\"" + _font + "\">" + _text + "</font>";
		}
		else
		{
			sIntoTag = _text;
		}
		return sIntoTag;
	}

	public String printEndTag()
	{
		return "</a>";
	}
}
