package com.taulukko.commons.util.web.taglibs;

public class TagInputDate extends TagLibBase
{
	private String _name;

	private static final String TP_MONTHS[] =
	{ "Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho",
			"Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };

	private static final int TP_YEAR_COUNT = 100;

	private static final int TP_YEAR_START = 1910;

	private int _day = 0;

	private int _month = 0;

	private int _year = 0;

	private boolean _haveOptional;

	public TagInputDate(String name, boolean haveOptional)
	{
		_haveOptional = haveOptional;
		_name = name;
	}

	public TagInputDate(String name, boolean haveOptional, int year, int month,
			int day)
	{
		_year = year;
		_month = month;
		_day = day;
		_name = name;
	}

	public String printBeginTag()
	{
		return "";

	}

	public String printIntoTag()
	{
		// ADICIONA COMBO DE DIA
		String sIntoTag = " <SELECT name = \"cmbDay" + _name + "\">";
		sIntoTag = sIntoTag + "<OPTION value=\"0\">Selecione</OPTION>";

		for (int cont = 1; cont <= 31; cont++)
		{
			String selected = "";
			if (_day == cont)
			{
				selected = " selected ";
			}
			sIntoTag = sIntoTag + "<OPTION " + selected + " value=\"" + cont
					+ "\">" + cont + "</OPTION>";
		}
		sIntoTag = sIntoTag + "</SELECT>\n";

		// ADICIONA COMBO DE MES
		sIntoTag = sIntoTag + " <SELECT name = \"cmbMonth" + _name + "\">";
		sIntoTag = sIntoTag + "<OPTION value=\"0\">Selecione</OPTION>";
		for (int cont = 1; cont <= TP_MONTHS.length; cont++)
		{
			String selected = "";
			if (_month == cont)
			{
				selected = " selected ";
			}
			sIntoTag = sIntoTag + "<OPTION " + selected + " value=\"" + cont
					+ "\">" + TP_MONTHS[cont - 1] + "</OPTION>";
		}
		sIntoTag = sIntoTag + "</SELECT>\n";

		// ADICIONA COMBO DE ANO
		sIntoTag = sIntoTag + " <SELECT name = \"cmbYear" + _name + "\">";
		sIntoTag = sIntoTag + "<OPTION value=\"0\">Selecione</OPTION>";
		for (int cont = TP_YEAR_START; cont <= TP_YEAR_START + TP_YEAR_COUNT; cont++)
		{
			String selected = "";
			if (_year == cont)
			{
				selected = " selected ";
			}
			sIntoTag = sIntoTag + "<OPTION " + selected + " value=\"" + cont
					+ "\">" + cont + "</OPTION>";
		}
		sIntoTag = sIntoTag + "</SELECT>\n";

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
		// vai ser feito depois, pois vou pegar o fonte que fiz na simplify
		String sIntoValidation = "";
		sIntoValidation += "\n  var fieldD;";
		sIntoValidation += "\n  var fieldM;";
		sIntoValidation += "\n  var fieldY;";
		sIntoValidation += "\n   for(contF = 0 ; contF < document.forms.length; contF++)";
		sIntoValidation += "\n   {";
		sIntoValidation += "\n      var form = document.forms[contF];";
		sIntoValidation += "\n      for(contE = 0; contE < form.elements.length ; contE++)";
		sIntoValidation += "\n      {";
		sIntoValidation += "\n           var element = form.elements[contE];";
		sIntoValidation += "\n           if(element.name==\"cmbDay" + _name
				+ "\")";
		sIntoValidation += "\n           {";
		sIntoValidation += "\n               fieldD = element;";
		sIntoValidation += "\n           }";
		sIntoValidation += "\n           if(element.name==\"cmbMonth" + _name
				+ "\")";
		sIntoValidation += "\n           {";
		sIntoValidation += "\n               fieldM = element;";
		sIntoValidation += "\n           }";
		sIntoValidation += "\n           if(element.name==\"cmbYear" + _name
				+ "\")";
		sIntoValidation += "\n           {";
		sIntoValidation += "\n               fieldY = element;";
		sIntoValidation += "\n           }";
		sIntoValidation += "\n      }";
		sIntoValidation += "\n   }";
		sIntoValidation += "\n   return isDate(fieldD,fieldY.value,fieldM.value,fieldD.value,"
				+ ((_haveOptional) ? 1 : 0) + ");";
		return sIntoValidation;
	}
}
