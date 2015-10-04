package com.taulukko.commons.util.i18n;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class I18nTest {

	@Test
	public void testCommum() throws IOException {

		I18n i18n = new I18nBuilder("not found").build("index.properties",
				"common.properties");
		String text = i18n.getText("sem-relacao");
		Assert.assertEquals("not found", text);

		text = i18n.getText("ERRO1", "teste1", "teste2");
		Assert.assertEquals("ERRO DE EXEMPLO teste1 teste2", text);
	}

	@Test
	public void testIndex() throws IOException {

		I18n i18n = new I18nBuilder("not found").build("index.properties",
				"common.properties");
		String text = i18n.getText("sem-relacao");
		Assert.assertEquals("not found", text);

		text = i18n.getText("MSG1", "teste1", "teste2");
		Assert.assertEquals("TESTE DE EXEMPLO teste1 teste2", text);
	}

	@Test
	public void testStreamToString() {
		Assert.assertNotNull("Test file missing",
				getClass().getResource("/index.properties"));

	}
	 
	@Test
	public void testeJS() throws IOException {
		I18n i18n = new I18nBuilder("not found").build("index.properties",
				"common.properties");
		String text = i18n.getJSMap("mapName");
		Assert.assertEquals("var mapName = new Map();\n\n"
				+ " mapName.set(\"TESTEJS\","
				+ "\"Esse é um teste de \\\"javascript\\\" %0\");\n"
				+ " mapName.set(\"ERRO1\",\"ERRO DE EXEMPLO %0 %1\");\n"
				+ " mapName.set(\"MSG1\",\"TESTE DE EXEMPLO %0 %1\");\n"
				+ " mapName.set(\"MSG2\",\"TESTE DE EXEMPLO %0 %1\");", text);

	}
	
	@Test
	public void testeJSCommon() throws IOException {
		I18nBuilder i18nBuilder = new I18nBuilder("not found");
		String text = i18nBuilder.getCommonJS().asString();
		Assert.assertTrue(text.contains("function i18n (ctx,dft)"));

	}

}
