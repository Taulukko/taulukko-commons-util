/*
 * Created on 18/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EContextControl extends EBase
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Color m_color = null;
    private Font m_font = null;
    private Graphics2D m_graphics = null;
    
    public EContextControl(Graphics2D graphics)
    {
        m_graphics = graphics;
    }
    
    public void begin()
    {
        m_color = m_graphics.getColor();
        m_font = m_graphics.getFont();
    }

    public void rollback()
    {
        m_graphics.setColor(m_color);
        m_graphics.setFont(m_font);
        m_color = null;
        m_font = null;
    }

    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        return new EContextControl(m_graphics);
    }

}
