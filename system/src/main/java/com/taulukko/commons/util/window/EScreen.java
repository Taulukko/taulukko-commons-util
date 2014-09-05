package com.taulukko.commons.util.window;

/*
 * @(#)Cube.java 1.0 04/09/03
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_2\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */

import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JWindow;

/*
 * Classe ideal para o uso de jogos, ela nao tem nem um tipo de bordas.
 * E necessario setar a visibilidade para true e chamar o metodo show.
 * */

public class EScreen extends JWindow implements WindowListener
{ 
 
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public EScreen (int iWhidth , int iHeight)
	{ 
        super();
       
		this.setSize(iWhidth,iHeight);	
		this.addWindowListener(this);
		this.enableEvents(WindowEvent.WINDOW_CLOSING);		
	}
	
	public void init()
	{
	}

	public void draw(Graphics g){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0)
	{
		this.setVisible(false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0)
	{		

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
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
