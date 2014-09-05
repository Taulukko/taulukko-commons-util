/*
 * Created on 01/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.text;

import java.util.Enumeration;


/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IEDocument
{
	public void add(EParagraph paragraph);
	public EParagraph remove(int iIndex);
	public Enumeration paragraphs();
	public void newPage();
	public long pageCount();
	
}
