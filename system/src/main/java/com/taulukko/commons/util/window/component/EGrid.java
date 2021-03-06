package com.taulukko.commons.util.window.component;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.taulukko.commons.util.struct.EVector;

/*
 * Created on 27/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EGrid extends JPanel
{

	private JPanel m_tablePanel;
	private JTable m_table;
	private JScrollPane m_scroll;
	private boolean m_bEditable = true;

	public EGrid(
		EColumn columns[],
		String sTitle,
		boolean bTitleVisible,
		int iWidth,
		int iHeigth)
	{

		//titulos das colunas
		EVector titles = new EVector(columns.length);
		m_tablePanel = new JPanel();
		m_table = new JTable();
		m_scroll = new JScrollPane();
		m_scroll.setViewportView(m_table);

		//configura o layout do painel da tabela
		this.setLayout(null);
		m_tablePanel.setLayout(new GridLayout(1, 0));

		for (int iCont = 0; iCont < columns.length; iCont++)
		{
			//cria titulos das colunas
			titles.set(iCont, columns[iCont].getTitle());
		}

		m_tablePanel.setBorder(new TitledBorder((bTitleVisible) ? sTitle : ""));
		m_table.setModel(new DefaultTableModel(new Object[][] {
		}, titles.toArray()));

		for (int iCont = 0; iCont < columns.length; iCont++)
		{
			m_table.getColumnModel().getColumn(iCont).setPreferredWidth(
				columns[iCont].getWidth());
			m_table.getColumnModel().getColumn(iCont).setResizable(
				columns[iCont].getIsResizable());
		}

		m_table.getTableHeader().setReorderingAllowed(false);
		m_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		m_table.setCellSelectionEnabled(true);

		m_tablePanel.add(m_scroll);

		this.add(m_tablePanel);
		m_tablePanel.setBounds(10, 10, iWidth - 10, iHeigth - 10);
		m_table.setColumnSelectionAllowed(false);
	}

	public void addRow(Object[] row)
	{
		DefaultTableModel dtm = (DefaultTableModel) m_table.getModel();
		dtm.addRow(row);
	}

	public void removeRows(int iRows[])
	{
		DefaultTableModel dtm = (DefaultTableModel) m_table.getModel();

		for (int i = (iRows.length - 1); i >= 0; --i)
			dtm.removeRow(iRows[i]);
	}

	public void removeSelectedRows()
	{
		int[] l = m_table.getSelectedRows();
		DefaultTableModel dtm = (DefaultTableModel) m_table.getModel();

		for (int i = (l.length - 1); i >= 0; --i)
			dtm.removeRow(l[i]);
	}

	public int[] getSelectedRowIndexes()
	{
		return m_table.getSelectedRows();
	}

	public int[] getSelectedColumnIndexes()
	{
		return m_table.getSelectedColumns();
	}

	public void setCell(int ix, int iy, Object value)
	{
		m_table.getModel().setValueAt(value, iy, ix);
	}

	public Object getCell(int ix, int iy)
	{
		return m_table.getModel().getValueAt(iy, ix);
	}

	public void addMouseListener(MouseListener listner)
	{
		m_table.addMouseListener(listner);
	}
	public void addKeyListener(KeyListener listner)
	{
		m_table.addKeyListener(listner);
	}

}
