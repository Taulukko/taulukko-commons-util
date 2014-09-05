package com.taulukko.commons.util.web.taglibs;

public class TagActionImage extends TagImage {

	private String _form;
	private int _action=0;

	private void initMyVars(String form, String id, int action)
	{
		_action=action;
		_form=form;
		this.setOnClick("onClick"+id+"()");
	}
	
	public TagActionImage(String form, String id, String src, int action) {
		super(id,src);
		this.initMyVars(form,id,action);
	}
	
	public TagActionImage(String form, String id, String src, String width, String height,int action) {
		super(id,src,width,height);
		this.initMyVars(form,id,action);
	}
	
	@Override
	public String printEndTag() {
		String ret = "<SCRIPT>";
		ret += "\nfunction onClick"+this.getId()+"()";
		ret += "\n{";
		ret += "\n   document." + _form + ".actionSwitch="+_action+";";
		ret += "\n   document." + _form + ".submit();";
		ret += "\n}";
		ret += "\n</SCRIPT>";
		return ret;
	}

}
