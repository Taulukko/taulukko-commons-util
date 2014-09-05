package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.struct.EIterator;
import com.taulukko.commons.util.struct.EStackList;
import com.taulukko.commons.util.web.beans.ValueBean;

public class TagSelect extends TagLibBase
{
	private String _name;

	private EStackList<ValueBean> _values;

	private String _selected;
	
	private String id = null;


	public TagSelect(String sName, EStackList<ValueBean> values, String selected)
	{
		_name = sName;
		_values = values;
		_selected = selected;
	}

	public TagSelect(String sName, EStackList<ValueBean> values)
	{
		_name = sName;
		_values = values;
		_selected = "";
	}
	
	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}


	public String printBeginTag()
	{
		String beginTag = "<SELECT NAME=\"" + _name + "\"";

		if (this.getOnClick() != null)
		{
			beginTag += " onclick=\"" + this.getOnClick() + "\" ";
		}
		
		if (this.getOnChange() != null)
		{
			beginTag += " onChange=\"" + this.getOnChange() + "\" ";
		}
		
		
		if (this.getOnBlur() != null)
		{
			beginTag += " onBlur=\"" + this.getOnBlur() + "\" ";
		}
		
		
		if (this.getOnFocus() != null)
		{
			beginTag += " onFocus=\"" + this.getOnFocus() + "\" ";
		}
		
		if (this.id != null)
		{
			beginTag += " id=\"" + this.id + "\" ";
		}
		
		beginTag += ">";
		return beginTag;
	}

	public String printIntoTag()
	{
		String intoTag = "";
		EIterator<ValueBean> lista = _values.getItens();
		while (lista.hasNext())
		{
			ValueBean valueBean = lista.next();
			String selected = "";
			if (_selected.toUpperCase().equals(
					valueBean.getValue().toUpperCase()))
			{
				selected = " selected ";
			}

			intoTag += "\n<OPTION " + selected + " VALUE=\""
					+ valueBean.getValue() + "\">" + valueBean.getName();
		}
		return intoTag;
	}

	public String printEndTag()
	{
		String endTag = "</SELECT>";
		return endTag;
	}

	public EStackList<ValueBean> getValues()
	{
		return _values;
	}

	public void setValues(EStackList<ValueBean> values)
	{
		_values = values;
	}
}
