/*
 * Created on 28/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.window.component.grid;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.taulukko.commons.util.struct.ESet;

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ETableModel extends DefaultTableModel
{
	private ESet m_editableCells = new ESet();
	private boolean m_bEditable = false;
	
	

	/**
	 * 
	 */
	public ETableModel()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ETableModel(int arg0, int arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ETableModel(Object[] arg0, int arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ETableModel(Vector arg0, int arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ETableModel(Object[][] arg0, Object[] arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ETableModel(Vector arg0, Vector arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public boolean isCellEditable(int iX, int iY)
	{
		//se for editavel a tabela e a celula for editavel		
		return m_bEditable  && (m_editableCells
		.get(String.valueOf(iX) + "-" + String.valueOf(iY) )!= null);
	}
	
	public void setCellEditable(int iX, int iY,boolean bEditable)
	{
		String sKey=String.valueOf(iX) + "-" + String.valueOf(iY);
		if(bEditable)
		{
			//ADICIONA COMO EDITAVEL
			m_editableCells.add( sKey ); 
		}
		else
		{
			//REMOVE DA SITUAcaO DE EDITAVEL
			m_editableCells.remove( sKey );
		}
	}

	/**
	 * @return
	 */
	public boolean getIsEditable()
	{
		return m_bEditable;
	}

	/**
	 * @param sB
	 */
	public void setIsEditable(boolean sB)
	{
		m_bEditable = sB;
	}

}
