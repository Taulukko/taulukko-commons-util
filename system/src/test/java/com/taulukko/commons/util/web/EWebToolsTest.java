package com.taulukko.commons.util.web;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.util.web.util.EWebTools;

public class EWebToolsTest
{
	@Test
	public void parseQueryStringToMapTest()
	{
		String queryString = "http:\\\\wwww.taulukko.com.br\\index.jsp?teste1=111&teste2=abc";
		Map<String, String> map = EWebTools.parseQueryStringToMap(queryString);
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey("teste1"));
		Assert.assertTrue(map.containsKey("teste2"));
		Assert.assertEquals("111", map.get("teste1"));
		Assert.assertEquals("abc", map.get("teste2"));
		
		queryString = "teste1=111&teste2=abc";
		map = EWebTools.parseQueryStringToMap(queryString);
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey("teste1"));
		Assert.assertTrue(map.containsKey("teste2"));
		Assert.assertEquals("111", map.get("teste1"));
		Assert.assertEquals("abc", map.get("teste2"));
	}
}
