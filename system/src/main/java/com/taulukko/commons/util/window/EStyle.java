/*
 * Created on 18/05/2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.window;

import javax.swing.UIManager;

import com.taulukko.commons.util.EBase;

/**
 * @author Edson
 *
 *  * Window - Preferences - Java - Code Style - Code Templates
 */
public class EStyle extends EBase
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /** Tell system to use native look and feel, as in previous
     *  releases. Metal (Java) LAF is the default otherwise.
     */

    public static void setNativeLookAndFeel() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch(Exception e) {
        System.out.println("Error setting native LAF: " + e);
      }
    }
    
   
    /* (non-Javadoc)
     * @see br.com.evon.EBase#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        // TODO Auto-generated method stub
        return null;
    }

   
}
