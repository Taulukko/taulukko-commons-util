package com.taulukko.commons.util.lang;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.util.lang.EString;

public class EStringTest
{
	@Test
	public void elipse()
	{
		Assert.assertEquals("tes", new EString("teste").elipsis(3).toString());
		Assert.assertEquals("teste de texto", new EString("teste de texto").elipsis(15).toString());
		Assert.assertEquals("teste de texto", new EString("teste de texto").elipsis(14).toString());
		Assert.assertEquals("teste de t...", new EString("teste de texto").elipsis(13).toString());

	}
}
