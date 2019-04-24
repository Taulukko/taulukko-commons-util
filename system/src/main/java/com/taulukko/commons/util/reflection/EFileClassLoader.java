/*
 * Created on 03/12/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.reflection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Usuario
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@Deprecated
//USE: EReflectionUtils
public class EFileClassLoader extends ClassLoader
{

    public Class loadClass(String sClassName, String sPath)
        throws ClassNotFoundException
    {
        try
        {

            byte[] data;
            //o endereco da classe que nao pertence ao classloader
            data = loadClassData(sPath);
            //setando a referencia
            Class cls = defineClass(sClassName, data, 0, data.length,null);			
			//resolve
			//resolveClass(cls);
			
            if (cls == null)
            {
				throw new ClassNotFoundException("Data class invalid!");
            }
            //carregando a classe
            return loadClass(sClassName,true);     
        }
        catch (IOException ioe)
        {
            throw new ClassNotFoundException("Path invalid!");
        }
    }

    private byte[] loadClassData(String filename) throws IOException
    {

        // Create a file object relative to directory provided
        File f = new File(filename);

        // Get size of class file
        int size = (int)f.length();

        // Reserve space to read
        byte buff[] = new byte[size];

        // Get stream to read from
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);

        // Read in data
        dis.readFully(buff);

        // close stream
        dis.close();

        // return data
        return buff;
    }

}
