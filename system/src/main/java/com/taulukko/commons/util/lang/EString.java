/*
 * Created on 25/07/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.lang;

/**
 * @author Edson
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EString implements Cloneable
{

	private static final String TP_ALPHA_NUMERIC = "abcdefgklmnopqrstuvxyzwy0123456789ABCDEFGHIJKLMNOPQRSTUVXZYWàáãäâèéêẽëíìĩîïóòõôöúùũûüÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜ";

	public static final String EMPTY = "";

	public static final String FL_NEW_LINE = "\r\n";

	private String _string = "";

	private boolean _regExActive = false;

	public EString()
	{
		_string = "";
	}

	public EString(String sValor)
	{
		_string = sValor;
	}


	public static int convertToInt(byte t) {
		return (t < 0) ? 256 + t : t;
	}

	public static String getBytes(String value) {
		byte bytes[] = value.getBytes();

		String s = "{chars:[";

		for (int index = 0; index < bytes.length; index++) {
			if (index > 0) {
				s += ",";
			}
			if (index % 2 == 0) {
				s += "{unicodeDecimal:";
				char c = value.charAt(index / 2);
				s += (int) (c);
				s += ",unicodeHex:"
						+ Integer.toHexString((int) (c)).toUpperCase();
				s += ",rawInt:";
			}
			byte charByte = bytes[index];
			int charInt = convertToInt(charByte);
			String hexValue = Integer.toHexString(charInt);
			s += charInt + ",rawHex:" + hexValue.toUpperCase() + ",rawBinary:"
					+ Integer.toBinaryString(charInt);
			if (index % 2 == 1) {
				s += "}";
			}
		}
		s += "]}";
		return s;
	}
	/*
	 * Ativa e desativa sistema de expressão regular, por default é desligado
	 */
	public boolean getRegExActive()
	{
		return _regExActive;
	}

	public void setRegExActive(boolean regExActive)
	{
		_regExActive = regExActive;
	}

	public EString elipsis(int max)
	{
		if (max<=3)
		{
			return new EString(_string).substring(0,max);
		}

		
		if (_string.length()<=max)
		{
			return new EString(_string);
		}
		
		int elipsisIndex = max-3;
		elipsisIndex = ( elipsisIndex<=0)?0:elipsisIndex;
		
		StringBuffer sb = new StringBuffer();
		sb.append(_string.substring(0,max-3));
		sb.append("...");
		EString ret = new EString(sb.toString());
		return ret;
		
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Gerar uma cpia da classe <br>
	 * String usando o delimitador ;
	 * 
	 * @param b
	 * @return
	 */
	public Object clone()
	{
		return new EString(this.toString());
	}
	
	public static EString of(String value)
	{
		return new EString(value);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: EString
	 * join(String[],String,int,int) Objetivo: Concatenar um array de Strings
	 * para uma nica <br>
	 * String usando o delimitador ;
	 * 
	 * @param b
	 * @return
	 */
	public static EString join(String sValores[]) 
	{
		return join(sValores, "", 0, sValores.length); 
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: EString
	 * join(String[],String,int,int) Objetivo: Concatenar um array de Strings
	 * para uma nica <BR>
	 * String usando o delimitador recebido
	 * 
	 * @param b
	 * @return
	 */
	public static EString join(String sValores[], String sDelimitador)
	{
		return join(sValores, sDelimitador, 0, sValores.length);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Concatenar um array de Strings
	 * para uma nica <BR>
	 * String usando o delimitador recebido a partir do <br>
	 * ndex inicial e at o ndex final
	 * 
	 * @param b
	 * @return
	 */
	public static EString join(String sValores[], String sDelimitador,
			int iInicial, int iFinal)
	{
		String sRet = "";

		for (int iCont = iInicial; iCont < sValores.length && iCont <= iFinal; iCont++)
		{
			sRet += ((iCont == iInicial) ? "" : sDelimitador) + sValores[iCont];
		}

		return new EString(sRet);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param index
	 * @return
	 */
	public char charAt(int index)
	{
		return _string.charAt(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		return _string.equals(obj.toString());
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param anotherString
	 * @return
	 */
	public boolean equalsIgnoreCase(String anotherString)
	{
		return _string.equalsIgnoreCase(anotherString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return _string.hashCode();
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: indexOf(int,int) Objetivo:
	 * Achar o primeiro index do caracter
	 * 
	 * @param ch
	 * @return
	 */
	public int indexOf(int iCh)
	{
		return indexOf(iCh, 0);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Achar o primeiro index do
	 * caracter
	 * 
	 * @param ch
	 * @return
	 */
	public int indexOf(int ch, int start)
	{
		return _string.indexOf(ch, start);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: indexOf(EString,int)
	 * Objetivo: Achar o primeiro index do caracter
	 * 
	 * @param value
	 * @return
	 */
	public int indexOf(EString value)
	{
		return indexOf(value, 0);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Achar o primeiro index do
	 * caracter
	 * 
	 * @param value
	 * @param start
	 * @return
	 */
	public int indexOf(EString value, int start)
	{

		for (int contE = start; contE < this.getLength(); contE++, start++)
		{
			if (this.charAt(contE) == value.charAt(0))
			{
				// 1-achado equivalencia no primeiro item
				int contI = 0;
				for (contI = 0; (contE + contI) < this.getLength()
						&& contI < value.getLength(); contI++)
				{
					if (value.charAt(contI) != this.charAt(contE + contI))
					{
						// 1-achado equivalencia no primeiro item
						// 2-achado discordncia no primeiro item
						// seta como no achado e sai do for interno
						break;
					}
				}

				if (contI == value.getLength())
				{
					// 1-achado equivalencia no primeiro item
					// 2-A primeira ocorrencia foi achada
					return contE;
				}
			}
		}

		// retorna -1 se ele no encontrou nada
		return -1;
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: lastIndexOf(int,int)
	 * Objetivo:
	 * 
	 * @param ch
	 * @return
	 */
	public int lastIndexOf(int ch)
	{
		return lastIndexOf(ch, this.getLength() - 1);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param ch
	 * @return
	 */
	public int lastIndexOf(int ch, int start)
	{
		return _string.lastIndexOf(ch, start);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: lastIndexOf(EString, int)
	 * Objetivo: Pegar a primeira ocorrencia de <br>
	 * sValor iniciando do fim para o comeo
	 * 
	 * @param ch
	 * @return
	 */
	public int lastIndexOf(EString value)
	{
		return lastIndexOf(value, this.getLength() - 1);
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Dependencias: lastIndexOf(EString, int)
	 * Objetivo: Pegar a primeira ocorrencia de <br>
	 * sValor iniciando de iStart para o comeo
	 * 
	 * @param ch
	 * @return
	 */
	public int lastIndexOf(EString value, int start)
	{
		int ret = start;
		for (int contE = start; contE >= 0; contE--, ret--)
		{
			if (this.charAt(contE) == value.charAt(0))
			{
				// 1-achado equivalencia no primeiro item
				int contI = 0;
				for (contI = 0; (contI + contE) < this.getLength()
						&& contI < value.getLength(); contI++)
				{
					if (value.charAt(contI) != this.charAt(contE + contI))
					{
						// 1-achado equivalencia no primeiro item
						// 2-achado discordncia no primeiro item
						// seta como no achado e sai do for interno
						break;
					}
				}

				if (contI == value.getLength())
				{
					// saiu do for por ter achado a palavra
					return contE;
				}

			}
		}

		// retorna
		return ret;

	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @return
	 */
	public int getLength()
	{
		return _string.length();
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Concatenar duas Strings
	 * 
	 * @param StringA
	 * @return
	 */
	public EString concat(EString string)
	{
		return new EString(this.toString() + string.toString());
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param oldChar
	 * @param newChar
	 * @return
	 */
	public EString replace(char oldChar, char newChar)
	{
		return new EString(_string.replaceAll(getRegExConfig() + oldChar,
				new String(new char[] { newChar })));
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param ldString
	 * @param newString
	 * @return
	 */

	public EString replace(String find, String replace)
	{
		return this.replace(new EString(find), new EString(replace));
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param oldChar
	 * @param newChar
	 * @return
	 * 
	 *         OBSERVAÇÕES: Não utilizar regexp para substituições sem regexp, o
	 *         exemplo abaixo:
	 * 
	 *         //http://download.oracle.com/javase/tutorial/essential/regex/
	 *         String sfind = "\\Q" + find.toString() + "\\E"; String sreplace
	 *         =replace.toString(); return new EString(
	 *         this.toString().replaceAll(sfind,sreplace));
	 * 
	 *         poderia ser enganosamente ser aceito, embora não funcionaria para
	 *         barra, por sempre ter um caracter de escape (fora os caracteres
	 *         normais) o regexp não é aconselhavel de ser usado.
	 */

	public EString replace(EString find, EString replace)
	{
		if (_regExActive)
		{
			_string = _string.replaceAll(find.toString(), replace.toString());
		}
		else if (!find.equals(replace))
		{
			int index = _string.indexOf(find.toString());

			while (index >= 0)
			{
				this.replaceFirst(find, replace, index);
				index = _string.indexOf(find.toString(),
						index + replace.getLength());
			}
		}
		return this;
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Substituir a primeira ocorrencia
	 * de uma String por outra. Aceita exp regular se o objeto EString estiver
	 * configurado para tal
	 * 
	 * @param find
	 * @param replacement
	 * @return
	 */
	public EString replaceFirst(EString sFind, EString sReplace)
	{
		return this.replaceFirst(sFind, sReplace, 0);

	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo: Substituir a primeira ocorrencia
	 * de uma String por outra. Não aceita exp regular
	 * 
	 * @param find
	 * @param replacement
	 * @return
	 */
	public EString replaceFirst(EString sFind, EString sReplace, int index)
	{
		int iFirstIndex = indexOf(sFind, index);

		if (iFirstIndex >= 0 && !sFind.equals(sReplace))
		{

			String ret = left(iFirstIndex).toString();
			ret = ret.concat(sReplace.toString());
			ret = ret.concat(substring(iFirstIndex + sFind.getLength())
					.toString());
			_string = ret;
		}

		return this;

	}

	public static String getRex(String value)
	{
		String ret = value.replaceAll("\\[", "\\\\[");
		ret = value.replaceAll("\\]", "\\\\]");
		ret = value.replaceAll("\\.", "\\\\.");
		return ret;
	}

	private String getRegExConfig()
	{
		return (_regExActive) ? "" : "\\Q";
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param regex
	 * @return
	 */
	public String[] split(String sSeparador)
	{
		if (_regExActive)
		{
			return _string.split(sSeparador);
		}
		else
		{
			return _string.split("\\Q" + sSeparador + "\\E");
		}

	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param regex
	 * @param limit
	 * @return
	 */
	public String[] split(String regex, int limit)
	{
		return _string.split(getRegExConfig() + regex, limit);
	}

	/**
	 * Autor: Edson Data: 27/07/2003 Objetivo: Retornar iSize elementos a
	 * esquerda da String
	 * 
	 * @param beginIndex
	 * @return
	 */
	public EString left(int iSize)
	{
		if (iSize < 0)
		{
			iSize = 0;
		}
		else if (iSize > this.getLength())
		{
			iSize = this.getLength();
		}

		return substring(0, iSize);
	}

	/**
	 * Autor: Edson Data: 27/07/2003 Objetivo: Retornar iSize elementos a
	 * direita da String
	 * 
	 * @param beginIndex
	 * @return
	 */
	public EString right(int iSize)
	{
		if (iSize < 0)
		{
			iSize = 0;
		}
		else if (iSize > this.getLength())
		{
			iSize = this.getLength();
		}
		return substring(_string.length() - iSize, _string.length());
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param beginIndex
	 * @return
	 */
	public EString substring(int beginIndex)
	{
		return new EString(_string.substring(beginIndex));
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public EString substring(int beginIndex, int endIndex)
	{
		return new EString(_string.substring(beginIndex, endIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _string.toString();
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:
	 * 
	 * @return
	 */
	public EString trim()
	{
		return new EString(_string.trim());
	}

	/**
	 * Autor: Edson Data: 25/07/2003 Objetivo:Retorna uma string n vezes
	 * repetida
	 * 
	 * @return
	 */
	public EString repeat(int iQuantidade)
	{
		EString sRet = new EString();
		for (int iCont = 0; iCont < iQuantidade; iCont++)
		{
			sRet = sRet.concat(this);
		}
		// retorna o valor repetido
		return sRet;
	}

	// ---------------------------------------------------------------------------
	/**
	 * Convert a String into an int, giving 0 as default value.
	 * 
	 * @param s
	 *            a String to be converted
	 * @return int value represented by the argument, or 0 if the argument isn't
	 *         a valid number.
	 */
	public int toInt()
	{
		// or try: return Integer.parseInt(s);
		try
		{
			// return (Integer.valueOf(s)).intValue();
			return Integer.parseInt(this.toString());
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}

	// ---------------------------------------------------------------------------
	/**
	 * Convert a String into an int, giving 0 as default value.
	 * 
	 * @param s
	 *            a String to be converted
	 * @return int value represented by the argument, or 0 if the argument isn't
	 *         a valid number.
	 */
	public long toLong()
	{
		// or try: return Integer.parseInt(s);
		try
		{
			// return (Integer.valueOf(s)).intValue();
			return Long.parseLong(this.toString());
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}

	// ---------------------------------------------------------------------------
	/**
	 * Convert a byte into an hexadecimal number. The output is always
	 * lowercase, two digit long in the range <code>00-ff</code>.
	 * <p>
	 * <b>Example:</b> <code>12</code> gives <code>0c</code>, <code>18</code>
	 * gives <code>12</code>, <code>255</code> gives <code>ff</code>, ...
	 * 
	 * @param n
	 *            a byte value to be converted.
	 * @return a two digit hexadecimal number.
	 */
	public static final EString toHexString(byte n)
	{
		// note: & 0xff is used to reset high bits
		if ((n >= 0) && (n <= 15))
			return new EString("0" + Integer.toHexString(n & 0xff));

		return new EString(Integer.toHexString(n & 0xff));
	}

	// ---------------------------------------------------------------------------
	/**
	 * Convert a number represented by the given byte array into a hexadecimal
	 * number String.
	 * <p>
	 * <b>Example:</b> <code>{ 12, 22, 16 }</code> gives <code>0c1610</code>.
	 * That means <code>12</code> is <code>0c</code>, <code>22</code> is
	 * <code>16</code>, <code>16</code> is <code>10</code> or, if you prefer all
	 * together,<br>
	 * <code>12 * 256^2 + 22 * 256^1 + 16 * 256 = 792080</code> gives
	 * <code>c1610</code>.
	 * 
	 * @param n
	 *            the byte array to be converted.
	 * @return a hexadecimal String with an even number of digits.
	 */
	public static final EString toHexString(byte[] n)
	{
		StringBuffer hex = new StringBuffer(2 * n.length);
		for (int i = 0; i < n.length; i++)
		{
			hex.append(toHexString(n[i]));
		}
		return new EString(hex.toString());
	}

	public EString pingRight(String separator, int frequency)
	{
		if (this.getLength() < frequency + 1)
		{
			return (EString) this.clone();
		}
		return new EString(this.left(this.getLength() - frequency)
				.pingRight(separator, frequency).toString()
				+ separator + this.right(frequency).toString());
	}

	public EString pingRight(EString separator, int frequency)
	{
		return this.pingRight(separator.toString(), frequency);
	}

	public EString trimNULL()
	{
		byte value[] = this.toString().getBytes();
		for (int cont = 0; cont < value.length; cont++)
		{
			if (value[cont] == 0)
			{
				return new EString(new String(value, 0, cont));
			}
		}
		return (EString) this.clone();
	}

	public EString clearSigns()
	{
		EString value = (EString) (this.clone());
		value = value.replace(new EString("ó"), new EString("o"));
		value = value.replace(new EString("õ"), new EString("o"));
		value = value.replace(new EString("ç"), new EString("c"));
		value = value.replace(new EString("ê"), new EString("e"));
		value = value.replace(new EString("é"), new EString("e"));
		value = value.replace(new EString("í"), new EString("i"));
		value = value.replace(new EString("ã"), new EString("a"));
		value = value.replace(new EString("á"), new EString("a"));
		value = value.replace(new EString("à"), new EString("a"));
		value = value.replace(new EString("À"), new EString("A"));
		value = value.replace(new EString("Ó"), new EString("O"));
		value = value.replace(new EString("Ô"), new EString("O"));
		value = value.replace(new EString("Ç"), new EString("C"));
		value = value.replace(new EString("Ê"), new EString("E"));
		value = value.replace(new EString("É"), new EString("E"));
		value = value.replace(new EString("Í"), new EString("I"));
		value = value.replace(new EString("Ã"), new EString("A"));
		value = value.replace(new EString("Á"), new EString("A"));
		value = value.replace(new EString("À"), new EString("A"));
		value = value.replace(new EString("ú"), new EString("u"));
		value = value.replace(new EString("ũ"), new EString("u"));
		value = value.replace(new EString("ù"), new EString("u"));
		value = value.replace(new EString("Ú"), new EString("U"));
		value = value.replace(new EString("Û"), new EString("U"));
		value = value.replace(new EString("Ù"), new EString("U"));
		value = value.replace(new EString("Û"), new EString("U"));
		return value;
	}

	public boolean isAlphaNumeric()
	{
		return isAlphaNumeric(new Object[] {});
	}

	public boolean isAlphaNumeric(Object excepts[])
	{

		for (int cont = 0; cont < _string.length(); cont++)
		{
			String charNow = _string.substring(cont, cont + 1);
			if (TP_ALPHA_NUMERIC.indexOf(charNow) == -1)
			{
				boolean isExcept = false;
				for (int contException = 0; contException < excepts.length; contException++)
				{
					isExcept = isExcept
							|| excepts[contException].toString()
									.equals(charNow);
				}
				if (!isExcept)
				{
					return false;
				}
			}
		}
		return true;
	}
}