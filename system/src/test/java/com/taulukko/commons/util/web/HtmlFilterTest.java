package com.taulukko.commons.util.web;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.taulukko.commons.util.web.HtmlFilter;

public class HtmlFilterTest
{

	@Test
	public void simple()
	{
		String test = "teste1<Script>teste2</Script>teste3";
		assertEquals("teste1&lt;Script&gt;teste2&lt;/Script&gt;teste3",
				HtmlFilter.filter(test));
		test = "teste1< Script > teste2 </ Script >teste3";
		assertEquals("teste1&lt; Script &gt; teste2 &lt;/ Script &gt;teste3",
				HtmlFilter.filter(test));
	}

	@Test
	public void ignoreInput()
	{
		String test = "teste1<Script>teste2</Script><INPUT>teste4</INPUT>teste3" +
				"<TEXTAREA>teste4</TEXTAREA>teste5";
		assertEquals(
				"teste1&lt;Script&gt;teste2&lt;/Script&gt;" +
				"<INPUT>teste4</INPUT>teste3" +
				"&lt;TEXTAREA&gt;teste4&lt;/TEXTAREA&gt;teste5",
				HtmlFilter.filter(test, new String[] { "input" }));
	}

	@Test
	public void ignoreTextArea()
	{
		String test = "teste1<Script>teste2</Script><INPUT>teste2</INPUT>" +
				"<TEXTAREA>teste4</TEXTAREA>teste3";
		assertEquals(
				"teste1&lt;Script&gt;teste2&lt;/Script&gt;&lt;INPUT&gt;teste2&lt;/INPUT&gt;" +
				"<TEXTAREA>teste4</TEXTAREA>teste3",
				HtmlFilter.filter(test, new String[] { "textArea" }));
	}
	

	@Test
	public void ignoreTags()
	{
		assertEquals(
				"<p>teste</p>",
				HtmlFilter.filter("<p>teste</p>", HtmlFilter.TP_HTML_TAGS));
		assertEquals(
				"&lt;script&gt;teste&lt;/script&gt;",
				HtmlFilter.filter("<script>teste</script>", HtmlFilter.TP_HTML_TAGS));
		
		assertEquals(
				"<  p>teste</p>",
				HtmlFilter.filter("<  p>teste</p>", HtmlFilter.TP_HTML_TAGS));
		
		assertEquals(
				"<  p>teste&lt;//p&gt;",
				HtmlFilter.filter("<  p>teste<//p>", HtmlFilter.TP_HTML_TAGS));
	}
	
	
	@Test
	public void ignoreMore()
	{
		String test = "teste1<Script>teste2</Script><INPUT id='teste'>teste2</INPUT>" +
				"<TEXTAREA>teste4</TEXTAREA>teste3";
		assertEquals(
				"teste1&lt;Script&gt;teste2&lt;/Script&gt;<INPUT id='teste'>teste2</INPUT>" +
				"<TEXTAREA>teste4</TEXTAREA>teste3",
				HtmlFilter.filter(test, new String[] { "textArea","input" }));
	}
	
	@Test
	public void em()
	{
		String test = "teste1<em>teste2</em>teste3";
		assertEquals("teste1<em>teste2</em>teste3",
				HtmlFilter.filter(test,HtmlFilter.TP_HTML_TAGS));
		test = "teste1< EM >teste2</EM>teste3";
		assertEquals("teste1< EM >teste2</EM>teste3",
				HtmlFilter.filter(test,HtmlFilter.TP_HTML_TAGS));
	}
}
