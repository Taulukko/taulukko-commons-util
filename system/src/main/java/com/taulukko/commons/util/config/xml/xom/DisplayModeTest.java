package com.taulukko.commons.util.config.xml.xom;

import java.awt.Color;
import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.taulukko.commons.util.graphic.EGraphicConfiguration;
import com.taulukko.commons.util.struct.EVector;

public class DisplayModeTest extends JFrame 
{

    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private static int m_iTimes = 0;

    private static GraphicsDevice m_device;
    
    private static DisplayMode m_oldDisplayMode;

    private static int m_iLastError = 0;

    private static String m_sLastError = "";
    
    public static final int TP_800X600 = 1;

    public static final int TP_1024X768 = 2;

    private static BufferedImage m_doubleBuffer;

    public DisplayModeTest(GraphicsDevice device,DisplayMode display)
    {
        super(device.getDefaultConfiguration());        
        DisplayModeTest.m_device = device;
        setTitle("Display Mode Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // configura para FullScreen
        configFullScreen();
        //seta o display
        m_oldDisplayMode = DisplayModeTest.m_device.getDisplayMode();
        DisplayModeTest.m_device.setDisplayMode(display);
        // cria uma imagem bufferizada
        m_doubleBuffer = new BufferedImage(display.getWidth(), display
                .getHeight(), BufferedImage.TYPE_INT_ARGB);
        //repinta o form 
        this.repaint();
        
    }

    private void initComponents()
    {
        Container c = this.getContentPane();
        setContentPane(c);
        c.setLayout(null);
    }

    public void configFullScreen()
    {
        boolean isFullScreen = m_device.isFullScreenSupported();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if ( isFullScreen )
        {
            // Full-screen mode
            m_device.setFullScreenWindow(this);
            validate();
        }
        else
        {
            // Windowed mode
            pack();
            setVisible(true);
        }
    }

    private void setError(int iCode, String sMsg)
    {
        m_iLastError = iCode;
        m_sLastError = sMsg;
        this.setVisible(false);
    }

    private synchronized Graphics2D newGraphics()
    {

        Graphics2D ret = null;
        if ( m_doubleBuffer != null )
        {

            ret = (Graphics2D) m_doubleBuffer.createGraphics();
        }
        return ret;

    }

    public static void sleep(int iMiliseconds)
    {
        try
        {
            Thread.sleep(100);
        }
        catch (Exception e)
        {
            m_iLastError= 6;
            m_sLastError = e.getMessage();
        }
    }
    
    public void paint(Graphics g)
    {
        if(!this.isVisible())
        {
            //finalizou ja a animacao
            return;            
        }
        
        Graphics2D g2 = newGraphics();

        if ( g2 == null )
        {
            DisplayModeTest.sleep(100);
            repaint();
            return;
        }

        // captura o tamanho da tela
        int iWidth = DisplayModeTest.m_device.getDisplayMode().getWidth();
        int iHeigth = DisplayModeTest.m_device.getDisplayMode().getHeight();
        int iXStart = (iWidth / 2) - 150;
        int iYStart = (iHeigth / 2) - 150;
        
        // limpa toda a area para criar o novo desenho
        g2.clearRect(0, 0, iWidth, iHeigth);
        g2.setColor(Color.RED);
        if ( m_iTimes < 10 )
        {
            // pinta por 10 segundos um quadrado
            g2.fillRect(iXStart, iYStart, 300, 300);
            m_iTimes++;
        }
        else if ( m_iTimes < 20 )
        {
            // pinta por 10 segundos um circulo
            g2.fillOval(iXStart, iYStart, 300, 300);
            m_iTimes++;
        }
        else
        {
            this.setVisible(false);
        }

        // pinta o buffer duplo
        g.drawImage(m_doubleBuffer, 0, 0, iWidth, iHeigth, this);

        // pausa o sitema por 1 segundo
        DisplayModeTest.sleep(100);
        
        // repinta o form
        repaint();
    }
   
   
    public static void showTest(int iRes)
    {
        DisplayMode display = EGraphicConfiguration.getDisplayModeActive();

        try
        {            
            if ( iRes == TP_1024X768)
            {
                // pega um display com as mesmas configuracoes, porem 800x600
                display = EGraphicConfiguration.getDisplayMode(1024, 768,                        
                        display.getBitDepth(), display.getRefreshRate());

            }
            else
            {
                //800x600   o padr ao
                // pega um display com as mesmas configuraoees, porem 800x600
                display = EGraphicConfiguration.getDisplayMode(800, 600,
                        display.getBitDepth(), display.getRefreshRate());
            }

        }
        catch (Exception e)
        {
            m_iLastError = 3;
            m_sLastError = e.getMessage();
            return;
        }

        EVector<GraphicsDevice> vector = EGraphicConfiguration
                .getAllGraphicsDevice();
        DisplayModeTest test = new DisplayModeTest(vector.get(0),display);                
    }
    
       
    public static void destroy()
    {
        m_device.setDisplayMode(m_oldDisplayMode);
        // limpa erros e resultados anteriores
        m_iLastError = 0;
        m_sLastError = "";
        m_iTimes=0;
        m_doubleBuffer=null;
        m_oldDisplayMode=null;
    }
}
