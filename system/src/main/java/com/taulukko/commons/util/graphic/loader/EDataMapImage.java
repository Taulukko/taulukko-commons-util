/*
 * Created on 18/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.graphic.loader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.struct.EDataMap;

/**
 * @author Edson
 * 
 * Classe controladora de imagens
 */
public class EDataMapImage extends EBase
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private static BufferedImage m_image = null;

    private static Graphics2D m_graphics2D = null;

    private static EDataMapImage m_loader = null;

    private static EDataMap<String, Image> m_dataMap = new EDataMap<String, Image>();

    private static int m_iCheckPoint = 0;

    // poara forcar a criacao dos objetos estaticos
    static
    {
        EDataMapImage.getInstance();
    }

    // Construtor privado, usado na primeira vez que eh usado o ELoader
    private EDataMapImage()
    {
        // cria uma imagem bufferizada e um Graphics2D
        this(new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB));
    }

    // Construtor privado, usado na primeira vez que eh usado o ELoader
    private EDataMapImage(BufferedImage image)
    {
        // cria uma imagem bufferizada e um Graphics2D
        this(image, image.createGraphics());
    }

    // construtor usado para criar um ELoader com parametros pre definidos
    private EDataMapImage(BufferedImage image,
            Graphics2D graphics)
    {
        // cria uma imagem bufferizada
        m_image = image;
        m_graphics2D = graphics;
    }

    /**
     * Objetivo: Carregar uma imagem, salva-la num datamap. E retornar true se
     * carregar sem erros.
     */

    public static void set(Image image, String sKey)
    {
        if ( null == image )
        {
            m_dataMap.remove(sKey);
        }
        else
        {
            if ( m_dataMap.get(sKey) == null )
            {
                // adiciona na base a imagem se ela ainda n�o existir
                m_dataMap.add(sKey, image);
                // tenta carregar pela primeira vez
                test(sKey);
            }
        }
    }

    // testa se uma imagem esta carregada
    public static boolean test(Image image)
    {
        if ( image == null )
        {
            return false;
        }

        // se ele conseguir desenhar ele retorna true
        return m_graphics2D.drawImage(image, 10, 10, 10,
                10, null);
    }

    // testa se uma imagem esta carregada
    public static boolean test(String sKey)
    {
        return test(m_dataMap.get(sKey));
    }

    // retorna o total de imagens carregadas
    public static int getLength()
    {
        return m_dataMap.size();
    }

    public static void checkOut()
    {
        m_iCheckPoint = getLength();
    }

    public static boolean getIsChecked()
    {
        return m_iCheckPoint != 0;
    }

    public static void checkIn()
    {
        m_iCheckPoint = 0;
    }

    // testa se todas as imagens estao carregadas
    public static int testAll()
    {
        // variavel de calculo de retorno
        int iContOk = 0;

        Iterator iterator = m_dataMap.keySet().iterator();
        String sKey = null;

        while (iterator.hasNext())
        {
            // enquanto houver possibilidade do teste ser bem sucedido
            sKey = iterator.next().toString();
            if ( test(sKey) )
            {
                // adiciona 1 aos elementos ok
                iContOk++;
            }
        }
        if ( getIsChecked() )
        {
            // calcula a diferen�a de itens carregados entre a ultima marca e
            // agora
            int iNewCreated = getLength() - m_iCheckPoint;
            int iNewOK = iContOk - m_iCheckPoint;

            // retorna a porcentagem carregada dos novos itens
            int iPorcent = (int) (((double) iNewOK) / ((double) iNewCreated) * 100);
            return iPorcent;
        }
        else
        {
            //retorna o total absoluto de imagens carregadas
            return iContOk;
        }
    }

    // retorna uma imagem carregada anteriroemente
    public static Image get(String sKey)
    {
        return EDataMapImage.getDataMap().get(sKey);
    }

    public static EDataMapImage getInstance()
    {
        if ( m_loader == null )
        {
            m_loader = new EDataMapImage();
        }
        return m_loader;
    }

    public static Image loadImage(String sPath)
    {
        Image image = get(sPath);
        if ( null == image )
        {
            // imagem n�o encontrada na base
            image = Toolkit.getDefaultToolkit().getImage(
                    sPath);
            // salva na base
            set(image, sPath);
        }
        // retorna a imagem
        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.evon.EBase#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        return new EDataMapImage(m_image, m_graphics2D);
    }

    protected static EDataMap<String, Image> getDataMap()
    {
        return m_dataMap;
    }
}
