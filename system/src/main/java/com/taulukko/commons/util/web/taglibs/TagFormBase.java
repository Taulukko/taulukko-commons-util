package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.web.beans.ValueBean;

public class TagFormBase extends TagLibBase
{
	private String _url;

	private String _text;

	private int _action = -1;

	private String _method;

	private String _name;

	private String _validation;

	private ValueBean[] _parameters;

	private String _buttonID = null;

	private boolean _submitPrintalbe = true;
	
	private boolean _isMultipart=false;

	public String getButtonID()
	{
		return _buttonID;
	}

	/** Insert css/id in submit button* */
	public void setButtonID(String classButton)
	{
		_buttonID = classButton;
	}

	public boolean getSubmitPrintalbe()
	{
		return _submitPrintalbe;
	}

	/** Default is true. Print the submit button.* */
	public void setSubmitPrintalbe(boolean submitPrintalbe)
	{
		_submitPrintalbe = submitPrintalbe;
	}

	public TagFormBase(String url, int action, String name, String method,
			String text, String validation, ValueBean[] parameters)
	{
		_url = url;
		_name = name;
		_text = text;
		_action = action;
		_method = method;
		_validation = validation;
		_parameters = parameters;
	}

	public String getURL()
	{
		return _url;
	}

	public String printBeginTag()
	{
		String beginTag = "<form name=\"" + _name + "\" method=\"" + _method
				+ "\" action=\"" + _url + "\""
				+ ((_isMultipart)?" enctype=\"multipart/form-data\" ":"");
		if (_validation != null)
		{
			beginTag += " onSubmit=\"" + _validation + "\" ";
		}

		beginTag += " >\n";
		beginTag += new TagInput("hidden", "actionSwitch", _action)
				.printAllTag()
				+ "\n";
		return beginTag;

	}

	public String printIntoTag()
	{
		String sIntoTag = "";

		for (int iCont = 0; _parameters != null && iCont < _parameters.length; iCont++)
		{
			ValueBean parameter = _parameters[iCont];

			sIntoTag += new TagInput("hidden", parameter.getName(), parameter
					.getValue()).printAllTag();
		}

		if (_submitPrintalbe)
		{
			// imprime submite
			TagInput submit = new TagInput("submit", "cmdSubmit", _text);
			if (null != _buttonID)
			{
				submit.setId(_buttonID);
			}

			sIntoTag += submit.printAllTag();
		}
		return sIntoTag;
	}

	public String printEndTag()
	{
		return "</form>";
	}

	@Override
	public String printBeginValidation()
	{
		String sValidation = "\n<SCRIPT>\nfunction validate_" + _name
				+ "()\n{\n";
		sValidation += "var form = document." + this._name + ";\n";
		sValidation += "var frm = document." + this._name + ";\n";
		sValidation += "var " + this._name + " = document." + this._name
				+ ";\n";
		return sValidation;
	}

	@Override
	public String printEndValidation()
	{
		// TODO Auto-generated method stub
		return "\n}</SCRIPT>\n";
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public String getMethod()
	{
		return _method;
	}

	public void setMethod(String method)
	{
		_method = method;
	}

	public boolean getIsMultipart()
	{
		return _isMultipart;
	}

	public void setIsMultipart(boolean isMultipart)
	{
		_isMultipart = isMultipart;
	}
}
