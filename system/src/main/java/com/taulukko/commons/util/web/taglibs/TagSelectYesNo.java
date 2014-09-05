package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.struct.EStackList;
import com.taulukko.commons.util.web.beans.ValueBean;

public class TagSelectYesNo extends TagSelect
{

	public TagSelectYesNo(String sName, String selected)
	{
		super(sName, getStaticValues(), selected);
	}

	private static EStackList<ValueBean> getStaticValues()
	{
		EStackList<ValueBean> ret = new EStackList<ValueBean>();
		ValueBean value = new ValueBean();
		value.setName("Selecione");
		value.setValue("-1");
		ret.add(value);
		value = new ValueBean();
		value.setName("Sim");
		value.setValue("1");
		ret.add(value);
		value = new ValueBean();
		value.setName("Nao");
		value.setValue("0");
		ret.add(value);
		return ret;
	}

	public TagSelectYesNo(String sName)
	{
		super(sName, getStaticValues());
	}

}
