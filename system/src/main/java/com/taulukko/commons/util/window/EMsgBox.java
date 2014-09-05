/*
 * Created on 27/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.window;

import javax.swing.JOptionPane;

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EMsgBox
{
	public static final int TP_RETURN_OK = 0;
	public static final int TP_RETURN_NO = 1;
	public static final int TP_OK = 1;
	public static final int TP_YES_NO = 2;
	public static int msgBox(String sMessege, String sTitle, int iType)
	{
		String[] opcoes=null;
		int iOption = 0;
		int iMessege = 0;

		//configura a caixa
		if (iType == TP_YES_NO)
		{
			opcoes = new String[] { "Sim","Nao" };
			iOption = JOptionPane.YES_NO_OPTION ;
		}
		else if (iType >= TP_OK)
		{
			opcoes = new String[] { "OK" };
			iOption = JOptionPane.OK_OPTION;
		}
		else
		{
			return 0;
		}

		iMessege = JOptionPane.ERROR_MESSAGE;

		return JOptionPane.showOptionDialog(
			null,
			sMessege,
			sTitle,
			iOption,
			iMessege,
			null,
			opcoes,
			opcoes[0]);
	}
}
