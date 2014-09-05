package com.taulukko.commons.util.web.taglibs;

public class TagImageSubmit extends TagImage
{

	private String _form;

	private String _userOnclick;

	private String _realOnClick;

	private void initMyVars(String form, String id)
	{
		_form = form;
		_realOnClick = "onSubClick" + id + "()";
	}

	public TagImageSubmit(String form, String id, String src)
	{
		super(id, src);
		this.initMyVars(form, id);
	}

	public TagImageSubmit(String form, String id, String src, String width,
			String height)
	{
		super(id, src, width, height);
		this.initMyVars(form, id);
	}

	@Override
	public String printEndTag()
	{
		String ret = "<SCRIPT>";
		ret += "\nfunction " + _realOnClick;
		ret += "\n{";
		//chama a rotina que o usuario quer
		ret += "\n   " + _userOnclick + ";";
		ret += "\n document." + _form + ".submit();";
		ret += "\n}";
		ret += "\n</SCRIPT>";
		return ret;
	}

	@Override
	public String printIntoTag()
	{
		_userOnclick = this.getOnClick();
		this.setOnClick(_realOnClick);
		return super.printIntoTag();
	}

}
