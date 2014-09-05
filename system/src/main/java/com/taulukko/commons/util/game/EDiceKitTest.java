package com.taulukko.commons.util.game;

import com.taulukko.commons.util.test.ETest;

public class EDiceKitTest extends ETest
{
	public EDiceKitTest()
	{
		super("Teste da classe EDiceKit","OK","FAIL"," esperado ", " nao era esperado", "ocorreu");
	}
	
	public void run()
	{
		EDiceKit kit = new EDiceKit(1,100,0);
		
		System.out.println("Todos os resultados abaixo devem ser aleatorios entre 1 e 100");
		for(int cont=1;cont<101;cont++)
		{
			System.out.println("Resultado " + cont + "=" + kit.rool()); 
		}
		
		kit.setSeed(12345);
		
		for(int cont=101;cont<201;cont++)
		{
			System.out.println("Resultado " + cont + "=" + kit.rool()); 
		}
		
		System.out.println("Todos os resultados abaixo devem ser esperados");		
		this.assertEquals(kit.rool(12345),kit.rool(12345));
		this.assertEquals(kit.rool(12346),kit.rool(12346));
		this.assertEquals(kit.rool(12347),kit.rool(12347));
		
	}
	
	public static void main(String... arg)
	{
		EDiceKitTest test = new EDiceKitTest();
		test.run();
	}
}
