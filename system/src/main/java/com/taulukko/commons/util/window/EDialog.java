package com.taulukko.commons.util.window;

import javax.swing.JDialog;
import javax.swing.JPanel;

/*
 /*
 * A diferenca deste form para window , e que este nao aceita menus e e modal.
 * e necessario setar a visibilidade para true e chamar o metodo show.
 * */

public class EDialog extends JDialog
{
	private JPanel m_panel;

	public EDialog(String sTitle, int iWhidth, int iHeight, EWindow owner)
	{
		super(owner);
		this.setTitle(sTitle);
		this.setSize(iWhidth, iHeight);
		this.setResizable(false);
		m_panel = new JPanel(false);
	}

	public EDialog(String sTitle, int iWhidth, int iHeight, EDialog owner)
	{
		super(owner);
		this.setTitle(sTitle);
		this.setSize(iWhidth, iHeight);
		this.setResizable(false);
		m_panel = new JPanel(false);
	}

	/**
	 * @return
	 */
	public JPanel getPanel()
	{
		return m_panel;
	}

	/**
	 * @param sPanel
	 */
	public void setPanel(JPanel sPanel)
	{
		m_panel = sPanel;
	}

	public void centeralized()
	{
		this.setLocation(
			(this.getToolkit().getScreenSize().width / 2)
				- (this.getWidth() / 2),
			(this.getToolkit().getScreenSize().height / 2)
				- (this.getHeight() / 2));
	}

}
