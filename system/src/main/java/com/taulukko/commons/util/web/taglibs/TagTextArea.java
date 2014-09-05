package com.taulukko.commons.util.web.taglibs;

public class TagTextArea extends TagLibBase
{
	private String m_name;

	private String m_text;

	private String m_rows;

	private String m_cols;

	private boolean m_locked = false;

	private String m_onClick;

	private int m_maxLenth;

	private String id;

	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}

	public String getOnClick()
	{
		return m_onClick;
	}

	public void setOnClick(String click)
	{
		m_onClick = click;
	}

	public boolean getIsLocked()
	{
		return m_locked;
	}

	public void setIsLocked(boolean m_locked)
	{
		this.m_locked = m_locked;
	}

	public TagTextArea(String name, String text, String cols, String rows,
			int maxLenth)
	{
		m_text = text;
		m_name = name;
		m_cols = cols;
		m_rows = rows;
		m_maxLenth = maxLenth;
	}

	public TagTextArea(String name, long text, String cols, String rows,
			int maxLenth)
	{
		m_text = String.valueOf(text);
		m_name = name;
		m_cols = cols;
		m_rows = rows;
		m_maxLenth = maxLenth;
	}

	public String printBeginTag()
	{
		String begin = "<TEXTAREA name=\"" + m_name + "\" cols=\"" + m_cols
				+ "\" rows=\"" + m_rows + "\"";
		if (m_locked)
		{
			begin += " readonly ";
		}
		if (id != null && !id.equals(""))
		{
			begin += " id='" + id + "'";
		}
		if (m_onClick != null && !m_onClick.equals(""))
		{
			begin += " onClick='" + m_onClick + "'";
		}
		begin += "\">\n";
		return begin;

	}

	public String printIntoTag()
	{ 
		return m_text;
	}

	public String printEndTag()
	{
		return "</TEXTAREA>";
	}

	@Override
	public String printBeginValidation()
	{
		String sIntoValidation = "";
		sIntoValidation += "\nfunction validate_" + m_name + "()";
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
		sIntoValidation += "\n           if(element.name==\"" + m_name + "\")";
		sIntoValidation += "\n           {";
		sIntoValidation += "\n               field = element;";
		sIntoValidation += "\n           }";
		sIntoValidation += "\n      }";
		sIntoValidation += "\n   }";
		if (m_maxLenth > 0)
		{
			sIntoValidation += "\n   if(field.value.length > " + m_maxLenth
					+ ")";
			sIntoValidation += "\n   {";
			sIntoValidation += "\n       alert('Tamanho Invalido');";
			sIntoValidation += "\n       return false;";
			sIntoValidation += "\n   }";
		}
		sIntoValidation += "\n return true;";
		return sIntoValidation;
	}

	@Override
	public String printAllTag()
	{
		return super.printAllTag() + super.printAllValidation();
	}
}
