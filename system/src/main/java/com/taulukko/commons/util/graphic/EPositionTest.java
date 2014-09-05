/*
 * Created on 27/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.graphic;

import com.taulukko.commons.util.test.ETest;


/**
 * @author Edson
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EPositionTest extends ETest
{

    /**
     * @param sNameTest
     * @param sMsgSuccess
     * @param sMsgFail
     * @param sMsgEspec
     * @param sMsgNoEspec
     * @param sMsgOcor
     */
    public EPositionTest()
    {
        super("EPosition Test", "sucesso", "falha", "Era esperado: ", "Nao era esperado: ",
                "Ocorreu: ");        
    }
    
    public void run()
    {
        super.run();
        
        EPosition a = new EPosition(2,2,0);
        EPosition b = new EPosition(2,4,0);
        //x e igual
        assertEquals(2.0f,a.diference2D(b));
        
        //y e igual
        a = new EPosition(2,2,0);
        b = new EPosition(4,2,0);
        assertEquals(2.f,a.diference2D(b));
        
        //x e y sao diferentes
        a = new EPosition(2,2,0);
        b = new EPosition(5,6,0);
        assertEquals(5.f,a.diference2D(b));
        
    }

    public static void main(String argsv[])
    {
        EPositionTest test = new EPositionTest();
        test.run();
        test.finalize();        
    }
    
    
}
