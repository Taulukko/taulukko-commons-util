package com.taulukko.commons.util.lang;

import com.taulukko.commons.util.test.ETest;

public class TesteByte extends ETest {
	
		public TesteByte()
		{
			super("Teste Conversor de Bytes"," OK! ", " Fail!" ,"Era esperado","Nao era esperado","Ocorreu");					
		}
		
		public static void main(String argsv[])
		{
			TesteByte test = new TesteByte();
			test.run();
		}
		
		public void run()
		{
			
			for(int inteiro = -1000000;inteiro < 1000000;inteiro+=10000)
			{
				byte [] bytes = EByte.intToBytes(inteiro);
				this.assertEquals(inteiro,EByte.bytesToInt(bytes));
			}
		}
}
