/*
 * Created on 18/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.window;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EPopup extends EBase
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * A simplified way to see a JPanel or other Container. Pops up a JFrame
     * with specified Container as the content pane.
     */

    public static JFrame openInJFrame(Container content, int width, int height,
            String title, Color bgColor)
    {
        JFrame frame = new JFrame(title);
        frame.setBackground(bgColor);
        content.setBackground(bgColor);
        frame.setSize(width, height);
        frame.setContentPane(content);
        frame.setVisible(true);
        return (frame);
    }

    /** Uses Color.white as the background color. */

    public static JFrame openInJFrame(Container content, int width, int height,
            String title)
    {
        return (openInJFrame(content, width, height, title, Color.white));
    }

    /**
     * Uses Color.white as the background color, and the name of the Container's
     * class as the JFrame title.
     */

    public static JFrame openInJFrame(Container content, int width, int height)
    {
        return (openInJFrame(content, width, height, content.getClass()
                .getName(), Color.white));
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.evon.EBase#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        // TODO Auto-generated method stub
        return null;
    }
}
