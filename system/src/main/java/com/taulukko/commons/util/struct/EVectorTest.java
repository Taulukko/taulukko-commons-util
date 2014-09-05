package com.taulukko.commons.util.struct;

import java.util.Iterator;
import java.util.Enumeration;

import com.taulukko.commons.util.test.ETest;

public class EVectorTest extends ETest
{
    public static void main(String argsv[])
    {
        EVectorTest et = new EVectorTest();

        et.testar();
    }

    public EVectorTest()
    {
        super(
            "Vector Teste",
            "Sucesso no Teste!",
            "Falha no Teste!",
            "Era esperado",
            "Nao era esperado",
            "e ocorreu");
    }

    public void testar()
    {
        //cabe�alho do teste
        System.out.println("INICIANDO TESTE DA CLASSE VBVECTOR...");

        //declarando o Range do teste		
        int iRangeTest = 5;

        System.out.println("Instanciando a classe...");
        EVector v = new EVector(iRangeTest);

        System.out.println("Testando a propriedade set...");

        for (int iCont = (-1 * iRangeTest); iCont <= iRangeTest * 2; iCont++)
        {
            try
            {
                System.out.println(
                    "Atribuindo uma String no �ndex " + iCont + "...");
                v.set(iCont, "Teste " + iCont);
            }
            catch (Exception err)
            {
                System.out.println(
                    "Erro ao tentar acessar o �ndex " + iCont + "...");
            }
        }

        //testando a funcao length()
        System.out.println("Testando a funcao length()");
        assertEquals(new Integer(v.getLength()), new Integer(5));

        System.out.println("Testando a propriedade get...");

        //testando o m�todo get		
        for (int iCont = 0; iCont < iRangeTest; iCont++)
        {
            assertEquals(v.get(iCont), "Teste " + iCont);
        }

        //testando a funcao elements
        System.out.println("Testando a funcao elements...");
        Enumeration varEnum = v.getElements();
        int iContE = 0;
        while (varEnum.hasMoreElements())
        {
            String s = (String)varEnum.nextElement();
            assertEquals(s, "Teste " + iContE++);
        }

        //testando a funcao itens
        System.out.println("Testando a funcao itens...");
        Iterator varEnumI = v.getItens();
        int iContI = 0;
        while (varEnumI.hasNext())
        {
            String s = (String)varEnumI.next();
            assertEquals(s, "Teste " + iContI++);
            varEnumI.remove();
        }

        //testando o redimensionamento
        v.redimPreserve(iRangeTest * 2);
        System.out.println("Testando a funcao redimPreserve...");

        for (int iCont = iRangeTest; iCont < iRangeTest * 2; iCont++)
        {
            try
            {
                System.out.println(
                    "Atribuindo uma String no �ndex " + iCont + "...");
                v.set(iCont, "Teste " + iCont);
            }
            catch (Exception err)
            {
                System.out.println(
                    "Erro ao tentar acessar o �ndex " + iCont + "...");
            }
        }

        //testando o redimensionamento
        for (int iCont = 0; iCont < iRangeTest * 2; iCont++)
        {
            assertEquals(v.get(iCont), "Teste " + iCont);
        }

        //testando o redimensionamento sem preserve		
        v.redim(iRangeTest);
        System.out.println("Testando a funcao redim...");

        for (int iCont = iRangeTest; iCont < iRangeTest * 2; iCont++)
        {
            try
            {
                System.out.println(
                    "Atribuindo uma String no index " + iCont + "...");
                v.set(iCont, "Teste " + iCont);
            }
            catch (Exception err)
            {
                System.out.println(
                    "Erro ao tentar acessar o index " + iCont + "...");
            }
        }

        //testando o redimensionamento
        for (int iCont = 0; iCont < iRangeTest; iCont++)
        {
            assertEquals(v.get(iCont).toString(), "Teste " + iCont);
        }

    }
}