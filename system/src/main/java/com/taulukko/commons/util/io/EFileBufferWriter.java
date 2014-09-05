/*
 * Created on 04/09/2004
 *
 */
package com.taulukko.commons.util.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.taulukko.commons.util.lang.EString;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EFileBufferWriter 
{

	BufferedWriter m_out;

	public EFileBufferWriter(String sPath) throws IOException
	{

		/*		A  maneira mais eficiente de ler um arquivo de texto e
			   usar
			   FileReader decorado por um BufferedReader. Para gravar, use
			   um BufferedWriter decorando o	FileWriter*/
		m_out = new BufferedWriter(new FileWriter(sPath));

	}

	public void print(String sValue) throws IOException
	{
		//grava linha a linha
		m_out.write(sValue);
	}
	
	public void println(String sValue) throws IOException
	{
		//grava linha a linha
		m_out.write(sValue + EString.FL_NEW_LINE);
	}

	public void close() throws IOException
	{

		//fecha o arquivo
		m_out.close();
	}
}
