package com.taulukko.commons.util.web.taglibs;

public class TagImage extends TagLibBase
{
	public String _id = null;

	public String _src = null;

	public String _width = null;

	public String _heigth = null;

	public String _onClick = null;

	public TagImage(String id, String src)
	{
		_id = id;
		_src = src;
	}

	public TagImage(String id, String src, String width, String height)
	{
		this(id, src);
		_width = width;
		_heigth = height;
	}

	@Override
	public String printBeginTag()
	{
		return "";
	}

	@Override
	public String printIntoTag()
	{
		String ret = "<img src=\"" + _src + "\"";

		if (_width != null)
		{
			ret += " width=\"" + _width + "\" ";
		}

		if (_heigth != null)
		{
			ret += " heigth=\"" + _heigth + "\" ";
		}

		if (_onClick != null)
		{
			ret += " onClick=\"" + _onClick + "\" ";
		}

		if (this.getClassCSS() != null)
		{
			ret += " class=\"" + this.getClassCSS() + "\" ";
		}

		ret += "/>";
		return ret;
	}

	@Override
	public String printEndTag()
	{
		return "";
	}

	public String getOnClick()
	{
		return _onClick;
	}

	public void setOnClick(String click)
	{
		_onClick = click;
	}

	public String getId()
	{
		return _id;
	}

	public void setId(String _id)
	{
		this._id = _id;
	}

}
