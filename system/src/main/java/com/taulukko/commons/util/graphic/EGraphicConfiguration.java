/*
 * Created on 24/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.graphic;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.struct.EVector;
import com.taulukko.commons.util.window.EDisplayModeNotSuportted;

/**
 * @author Edson
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EGraphicConfiguration extends EBase
{

    public EGraphicConfiguration()
    {

    }

    public static DisplayMode getDisplayMode(int iWidth, int iHeigth,
            int iBitDepth, int iRefreshRate) throws EDisplayModeNotSuportted
    {
        EVector<DisplayMode> vetor = EGraphicConfiguration.getAllDisplayMode();
        for (int iCont = 0; iCont < vetor.getLength(); iCont++)
        {
            if (vetor.get(iCont).getHeight() == iHeigth
                    && vetor.get(iCont).getWidth() == iWidth
                    && vetor.get(iCont).getBitDepth() == iBitDepth
                    && vetor.get(iCont).getRefreshRate() == iRefreshRate)
            {
                return vetor.get(iCont);
            }
        }

        throw new EDisplayModeNotSuportted();
    }

    public static EVector<GraphicsDevice> getAllGraphicsDevice()
    {
        GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        return new EVector<GraphicsDevice>(devices);
    }

    public static EVector<DisplayMode> getAllDisplayMode()
    {
        GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        DisplayMode[] displays = device.getDisplayModes();
        return new EVector<DisplayMode>(displays);
    }

    public static DisplayMode getDisplayModeActive()
    {
        GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        return device.getDisplayMode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        return new EGraphicConfiguration();
    }

}
