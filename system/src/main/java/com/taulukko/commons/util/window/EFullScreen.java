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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;

import com.taulukko.commons.util.graphic.EGraphicConfiguration;
import com.taulukko.commons.util.script.IEScriptAction;
import com.taulukko.commons.util.struct.EDataMap;
import com.taulukko.commons.util.struct.EVector;

/*
 * Classe ideal para o uso de jogos, ela nao tem nem um tipo de bordas. E
 * necessario setar a visibilidade para true e chamar o metodo show.
 */

public class EFullScreen extends JFrame 
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    public static final String TP_ON_DRAW_GRAPHICS2D = "graphics2D";

    private GraphicsDevice m_device;

    private DisplayMode m_originalDM;

    private boolean m_bIsFullScreen = false;
    
    private IEScriptAction m_onDraw;
    
    

    /*
     * Construtor usado para multiplos monitores
     * 
     * (deve ser chamado uma vez para cada GraphicsDevice)
     * 
     */
    public EFullScreen(GraphicsDevice device)
    {
        super(device.getDefaultConfiguration());
        init(device);
    }

    /*
     * Construtor usado para um monitor
     */
    public EFullScreen()
    {   
        EVector<GraphicsDevice> vetor = EGraphicConfiguration.getAllGraphicsDevice();        
        if ( vetor.getLength() > 0 )
        {
            this.init(vetor.get(0));
        }
    }

    private void init(GraphicsDevice device)
    {
        this.m_device = device;
        m_originalDM = device.getDisplayMode();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = this.getContentPane();        
        container.setLayout(null);        
        this.setFocusable(true);
        this.requestFocusInWindow();        
        this.begin();
    }

    public void begin()
    {
        // verifica se suporta fullscreen
        m_bIsFullScreen = m_device.isFullScreenSupported();
        setUndecorated(m_bIsFullScreen);
        setResizable(!m_bIsFullScreen);
        if ( m_bIsFullScreen )
        {
            // Full-screen mode
            m_device.setFullScreenWindow(this);
            validate();
        }
        else
        {
            // Redimensiona para ocupar a tela cheia
            pack();
            setVisible(true);
        }
        

    }

    public static final EGraphicConfiguration getGraphicConfiguration()
    {
        return new EGraphicConfiguration();
    }
    
    public final void draw(Graphics g)
    {
        if(m_onDraw!=null)
        {
            EDataMap<String,Graphics2D> dataMap = new EDataMap<String,Graphics2D>();
            dataMap.add(TP_ON_DRAW_GRAPHICS2D,(Graphics2D)g);
            m_onDraw.run(dataMap);
        }
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

  
    public boolean getIsFullScreen()
    {
        return m_bIsFullScreen;
    }
    
    public DisplayMode getOriginalDisplayMode()
    {
        return m_originalDM;
    }
    
    public DisplayMode getDisplayMode()
    {        
        return m_device.getDisplayMode();
    }
    
    public void setDisplayMode(DisplayMode displayMode )
    {
        m_device.setDisplayMode(displayMode);
        setSize(new Dimension(displayMode.getWidth(), displayMode.getHeight()));
        validate();
    }
    
    public IEScriptAction getOnDraw()
    {
        return m_onDraw;
    }
    public void setOnDraw(IEScriptAction sOnDraw)
    {
        m_onDraw = sOnDraw;
    }
    
    public static void main(String[] args)
    {
        EFullScreen test = new EFullScreen();
        try
        {            
            test.setDisplayMode(EGraphicConfiguration.getDisplayMode(1024,768,32,60));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
