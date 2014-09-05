/*
 * Created on 01/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.text;

import com.taulukko.commons.util.graphic.EColor;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EParagraph
{	
    String m_sText = "";
	boolean m_bBold = false;
	boolean m_bItalic =false;
	boolean m_bUnderline=false;
	int m_iFontSize=0;
	String m_sFontFamily="";
	EColor m_oBackGround=null;
	EColor m_oForeGround=null;

    public EParagraph(
        String sText,
        boolean bBold,
        boolean bItalic,
        boolean bUnderline,
        int iFontSize,
        String sFontFamily,
        EColor oBackGround,
        EColor oForeGround)
    {
    	//atualiza as propriedades
		m_sText = sText;
		m_bBold = bBold;
		m_bItalic =bItalic;
		m_bUnderline=bUnderline;
		m_iFontSize=iFontSize;
		m_sFontFamily=sFontFamily;
		m_oBackGround=oBackGround;
		m_oForeGround=oForeGround;
    }
    /** Autor: Edson
     * Data: 01/08/2003
     * Depend�ncias:
     * Objetivo: 
     * @return
     */
    public int getFontSize()
    {
        return m_iFontSize;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Depend�ncias:
     * Objetivo: 
     * @return
     */
    public EColor getBackGround()
    {
        return m_oBackGround;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Depend�ncias:
     * Objetivo: 
     * @return
     */
    public EColor getForeGround()
    {
        return m_oForeGround;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @return
     */
    public String getFontFamily()
    {
        return m_sFontFamily;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @return
     */
    public String getText()
    {
        return m_sText;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @param i
     */
    public void setFontSize(int i)
    {
        m_iFontSize = i;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @param color
     */
    public void setBackGround(EColor color)
    {
        m_oBackGround = color;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @param color
     */
    public void setForeGround(EColor color)
    {
        m_oForeGround = color;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @param string
     */
    public void setFontFamily(String string)
    {
        m_sFontFamily = string;
    }

    /** Autor: Edson
     * Data: 01/08/2003
     * Dependencias:
     * Objetivo: 
     * @param string
     */
    public void setText(String string)
    {
        m_sText = string;
    }


}
