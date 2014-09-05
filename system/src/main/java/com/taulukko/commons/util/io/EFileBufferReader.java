/*
 * Created on 04/09/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taulukko.commons.util.EBase;
import com.taulukko.commons.util.exception.EException;
import com.taulukko.commons.util.lang.EString;

/**
 * @author Edson
 * 
 */
public class EFileBufferReader extends EBase
{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private BufferedReader m_in;
    private String m_sPath="";

    public EFileBufferReader(String sPath) throws IOException
    {

        /*
         * A maneira mais eficiente de ler um arquivo de texto e usar FileReader
         * decorado por um BufferedReader. Para gravar, use um BufferedWriter
         * decorando o FileWriter
         */
        m_in = new BufferedReader(new FileReader(sPath));
        m_sPath =sPath;

    }

    
    public BufferedReader getBufferedReader()
    {
        return m_in;
    }
    
    public String readLine() throws IOException
    {
        // le linha a linha
        return m_in.readLine();
    }

    public int read() throws IOException
    {
        // le caracter a caracter
        return m_in.read();
    }

    public void close() throws IOException
    {
        // fecha o arquivo
        m_in.close();
    }

    // retorna uma string contendo todo o arquivo
    public String toString()
    {
        try
        {
            String sRet = "";
            String sLine = "";

            sLine = this.readLine();

            while (sLine != null)
            {
                // adiciona linha a linha
                sRet = sRet + EString.FL_NEW_LINE + sLine;
                sLine = this.readLine();
            }
            // retorna a linha
            return sRet;
        }
        catch (Exception e)
        {
            EException ee = new EException(e.getMessage(),1);
            ee.setStackTrace(e.getStackTrace());
            this.setLastException(ee);
            return null;
        }
    }

    /* (non-Javadoc)
     * @see br.com.evon.EBase#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {       
        try
        {
            return new EFileBufferReader(m_sPath);
        }
        catch(Exception e)
        {
            CloneNotSupportedException cln = new CloneNotSupportedException();
            cln.setStackTrace(e.getStackTrace());
            throw cln;
        }
    }
}
