package com.taulukko.commons.util.window;

/*
 * @(#)Cube.java 1.0 04/09/03
 *
  * templates as examples.
 *
 */

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EWindow extends JFrame 
{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private JPanel m_panel;

	public EWindow(String sTitle, int iWhidth, int iHeight)
	{
		this.setTitle(sTitle);
		this.setSize(iWhidth, iHeight);		
		m_panel = new JPanel(null );
	}

    public EWindow(String sTitle, int iWhidth, int iHeight,boolean bIgnoredRepaint)
    {
        this(sTitle,iWhidth,iHeight);
        
        if(bIgnoredRepaint)
        {
            //o usuario deseja controlar sozinho a renderizacao do form
            this.setUndecorated(true);
            this.setIgnoreRepaint(true);
        }
        
    }

    
	public void draw(Graphics g)
	{		
	}

	public void clear()
	{

		repaint();
	}

	public void refresh()
	{
		repaint();
	}

	public void paint(Graphics g)
	{
		this.draw(g);
		if(m_panel!=null)
		{
			//se existir painel		
			m_panel.repaint();
		}
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
        this.getContentPane().add(sPanel);
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
