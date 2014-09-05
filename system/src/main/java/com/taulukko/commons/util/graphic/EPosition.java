/*
 * Created on 25/07/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.graphic;

/**
 * @author Edson
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EPosition implements Comparable
{

    private float m_fX;

    private float m_fY;

    private float m_fZ;

    // ACEITA NO FORMATO (X;Y;Z)
    public EPosition(String sCoordenada)
    {
        // aqui ele pega a string e apos splitar seta os valores, erro causa
        // runtime exception
        String sValues[] = sCoordenada.trim().split(";");
        if (sValues.length < 3)
        {
            // parametros incorretos
            return;
        }
        else
        {
            try
            {
                // seta as informacoes de acordo com a entrada
                m_fX = new Float(sValues[0].substring(1)).floatValue();
                m_fY = new Float(sValues[1]).floatValue();
                m_fZ = new Float(sValues[2].substring(0,
                        sValues[2].length() - 2)).floatValue();
            }
            catch (Exception e)
            {
                // parametros incorretos
            }

        }
    }

    public EPosition(float fX, float fY, float fZ)
    {
        // seta as propriedades padroes
        m_fX = fX;
        m_fY = fY;
        m_fZ = fZ;
    }

    public float getX()
    {
        return m_fX;
    }

    public float getY()
    {
        return m_fY;
    }

    public float getZ()
    {
        return m_fZ;
    }

    public String toString()
    {
        return "(" + m_fX + ";" + m_fY + ";" + m_fZ + ")";
    }

    public Object clone()
    {
        return new EPosition(this.getX(), this.getY(), this.getZ());
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof EPosition))
        {
            // E por si so diferente, mesmo que seja filha
            return false;
        }

        try
        {
            EPosition epos = (EPosition) obj;
            // valida a igualdade
            return epos.getX() == this.getX() && epos.getY() == this.getY()
                    && epos.getZ() == this.getZ();
        }
        catch (Exception e)
        {
            return false;
        }

    }

    /*
     * Objetivo: Como foi substituido o equals, para obedecer o contrato o
     * hashCode tambem foi.
     */
    public int hashCode()
    {
        // captura o hashing do float X
        int iReturn = Float.floatToIntBits(this.getX());
        // do float Y
        iReturn += 37 * iReturn + Float.floatToIntBits(this.getY());
        // e do Z
        iReturn += 37 * iReturn + Float.floatToIntBits(this.getZ());

        // retorna
        return iReturn;
    }

    public int compareTo(Object obj) throws ClassCastException
    {
        // variavel de retorno
        int iRet = 0;

        // verifica se pode ser comparado
        if (!(obj instanceof EPosition))
        {
            throw new ClassCastException();
        }

        // captura o objeto
        EPosition oposition = (EPosition) obj;

        // calcula o retorno dando prioridade para XYZ
        iRet = (int) ((this.getX() - oposition.getX() * 1000) % 1000);
        iRet += (int) ((this.getY() - oposition.getY() * 100) % 100);
        iRet += (int) ((this.getZ() - oposition.getZ() * 10) % 10);

        // retorna o valor
        return iRet;
    }

    /**Calular a distancia entre as coordenadas xy das posicoes*/
    public float diference2D(EPosition b)
    {
        float fDiference = 0;
        EPosition a = this;

        if (a.getX() == b.getX())
        {
            fDiference = Math.abs(a.getY() - b.getY());
        }
        else if (a.getY() == b.getY())
        {
            fDiference = Math.abs(a.getX() - b.getX());
        }
        else
        {
            /**
             * calcula a hippotenusa de: - - - a - | \hipotenusa procurada -
             * c|__b ------------------------
             * 
             * a->c  abs(a.Y -b.Y) b->c  abs(a.x -b.x) ________________
             * hipotenusa =V a->c2 + b->c2 '
             */

            // procurando a->c
            float fAC = Math.abs(a.getY() - b.getY());
            float fCB = Math.abs(a.getX() - b.getX());
            fDiference = Math.abs((float) Math.pow((fAC * fAC) + (fCB * fCB), 0.5));
        }

        return fDiference;
    }
}
