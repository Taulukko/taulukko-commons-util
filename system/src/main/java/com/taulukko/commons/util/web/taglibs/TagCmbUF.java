package com.taulukko.commons.util.web.taglibs;

import com.taulukko.commons.util.struct.EStackList;
import com.taulukko.commons.util.web.beans.ValueBean;

public class TagCmbUF extends TagSelect
{
	private static final String [] TP_UF_NAMES = {"Acre","Alagoas","Amapa","Amazonas","Bahia",
			"Ceara","Distrito Federal","Espirito Santo","Goias","Maranhao","Mato Grosso",
			"Mato Grosso do Sul","Minas Gerais","Para","Paraiba","Parana","Pernambuco",
			"Piaui","Rio De Janeiro","Rio Grande de Norte","Rio Grande do Sul","Rondonia",
			"Roraima","Santa Catarina","Sao Paulo","Sergipe","Tocantins"};	

	
	
	public TagCmbUF(String name,String selected, boolean haveOptional)
	{
		
		super(name,getUFs(haveOptional),selected);
	}
	
	public static String getDescription(int index)
	{
		return TP_UF_NAMES[index-1];
	}
	
	private static EStackList<ValueBean> getUFs(boolean haveOptional)
	{
		EStackList<ValueBean> values = new EStackList<ValueBean>();
		
		if(haveOptional)
		{
			ValueBean uf = new ValueBean();
			uf.setName("Selecione");
			uf.setValue("0");
			values.add(uf);
		}
		
		for(int cont = 0 ; cont< TP_UF_NAMES.length;cont++ )
		{
			ValueBean uf = new ValueBean();
			uf.setName(TP_UF_NAMES[cont]);
			uf.setValue(String.valueOf((cont+1)));
			values.add(uf);
		}
		return (EStackList<ValueBean>)values.revert();
	}	
}
