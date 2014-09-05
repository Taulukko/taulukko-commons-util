package com.taulukko.commons.util.web.taglibs;

public class TagInput extends TagLibBase
{
	private String _type;

	private String _name;

	private String _text;

	private boolean _readOnly = false;

	private int _size = -1;

	private String _class = null;

	private String _id = null;

	private boolean _locked = false;

	private String _onClick;
	

	private String _onKeypress;

	private int _maxLength = -1;

	private int _focusDelay = 0;

	public int getFocusDelay()
	{
		return _focusDelay;
	}

	public void setFocusDelay(int focusDelay)
	{
		_focusDelay = focusDelay;
	}

	public int getMaxLength()
	{
		return _maxLength;
	}

	public void setMaxLength(int max)
	{
		_maxLength = max;
	}

	public String getOnClick()
	{
		return _onClick;
	}

	public void setOnClick(String click)
	{
		_onClick = click;
	}

	public boolean getIsLocked()
	{
		return _locked;
	}

	public void setIsLocked(boolean m_locked)
	{
		this._locked = m_locked;
	}

	public TagInput(String sType, String sName, String sText, int size)
	{
		this(sType, sName, sText);
		_size = size;
	}

	public TagInput(String sType, String sName, long lText, int size)
	{
		this(sType, sName, lText);
		_size = size;
	}

	public TagInput(String sType, String sName, String sText)
	{
		_text = sText;
		_name = sName;
		_type = sType;
	}

	public TagInput(String sType, String sName, long lText)
	{
		_text = String.valueOf(lText);
		_name = sName;
		_type = sType;
	}

	public String printBeginTag()
	{
		return "";

	}

	public String printIntoTag()
	{
		String sIntoTag = "<input type=\"" + _type + "\" name=\"" + _name
				+ "\" value=\"" + _text + "\"";
		if (_size != -1)
		{
			sIntoTag += " size=" + _size + " ";
		}
		if (_locked)
		{
			sIntoTag += " readonly ";
		}

		if (_readOnly)
		{
			sIntoTag += " readonly ";
		}

		if (_class != null)
		{
			sIntoTag += " class='" + _class + "' ";
		}

		if (_id != null)
		{
			sIntoTag += " id='" + _id + "' ";
		}

		if (_maxLength != -1)
		{
			sIntoTag += " maxlength=" + _maxLength + " ";
		}

		if (_onClick != null && !_onClick.equals(""))
		{
			sIntoTag += " onClick=\"" + _onClick + "\" ";
		}
		
		if (_onKeypress != null && !_onKeypress.equals(""))
		{
			sIntoTag += " onkeypress=\"" + _onKeypress + "\" ";
		}
		
		sIntoTag += "\\>\n";
		return sIntoTag;
	}

	public String printEndTag()
	{
		return "";
	}

	@Override
	public String printBeginValidation()
	{
		String sIntoValidation = "";
		sIntoValidation += "\nfunction validate_" + _name + "()";
		sIntoValidation += "\n{";
		return super.printBeginValidation() + sIntoValidation;
	}

	@Override
	public String printEndValidation()
	{
		String sIntoValidation = "";
		sIntoValidation += "\n}";
		return sIntoValidation + super.printEndValidation();
	}

	@Override
	public String printIntoValidation()
	{
		// vai ser feito depois, pois vou pegar o fonte que fiz na simplify
		String sIntoValidation = "";
		sIntoValidation += "\n   var field;";
		sIntoValidation += "\n   for(contF = 0 ; contF < document.forms.length; contF++)";
		sIntoValidation += "\n   {";
		sIntoValidation += "\n      var form = document.forms[contF];";
		sIntoValidation += "\n      for(contE = 0; field== undefined && contE < form.elements.length ; contE++)";
		sIntoValidation += "\n      {";
		sIntoValidation += "\n           var element = form.elements[contE];";
		sIntoValidation += "\n           if(element.name==\"" + _name + "\")";
		sIntoValidation += "\n           {";
		sIntoValidation += "\n               field = element;";
		sIntoValidation += "\n           }";
		sIntoValidation += "\n      }";
		sIntoValidation += "\n   }";
		return sIntoValidation;
	}

	public String getInputClass()
	{
		return _class;
	}

	public void setInputClass(String Inputclass)
	{
		this._class = Inputclass;
	}

	public String getID()
	{
		return _id;
	}

	public void setID(String id)
	{
		this._id = id;
	}

	public String getId()
	{
		return _id;
	}

	public void setId(String id)
	{
		this._id = id;
	}

	public int getSize()
	{
		return _size;
	}

	public void setSize(int size)
	{
		_size = size;
	}

	public boolean getReadOnly()
	{
		return _readOnly;
	}

	public void setReadOnly(boolean readOnly)
	{
		_readOnly = readOnly;
	}

	public String getOnKeypress()
	{
		return _onKeypress;
	}

	public void setOnKeypress(String onKeypress)
	{
		_onKeypress = onKeypress;
	}
}
