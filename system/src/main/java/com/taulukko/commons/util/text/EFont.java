/*
 * Created on 29/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.text;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EFont extends EBase
{
    private float m_fSize;
    private Font m_font;
    public static final float TP_DEFAULT_SIZE = 12;

    public EFont(String sPath) throws Exception
    {        
        this(new File(sPath));
    }    
    private EFont(File file) throws Exception
    {
        this(new FileInputStream(file));
             
    }
    
    private EFont(FileInputStream fis) throws Exception
    {   
        this(Font.createFont(Font.TRUETYPE_FONT, fis));
             
    }
    
    private EFont(Font font)
    {        
        m_font = font;        
    }

    public float getSize()
    {
        return m_fSize;        
    }    
    
    public void setSize(float fSize)
    {
        m_fSize = fSize;        
    }
    
    public Font getJavaFont()
    {
        return m_font.deriveFont(m_fSize);
    }
        
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {        
        return new EFont(m_font);
    }

}
