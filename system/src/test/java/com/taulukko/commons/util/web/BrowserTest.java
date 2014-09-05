package com.taulukko.commons.util.web;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.taulukko.commons.util.web.Browser;

public class BrowserTest
{
	@Test
	public void chrome9()
	{
		Browser browser = new Browser("mozilla/5.0 (windows; u; windows nt 6.1; en-us) applewebkit/534.13 (khtml, like gecko) chrome/9.0.597.98 safari/534.13");
		assertEquals("Chrome",browser.getName());
		assertEquals(9.059798,browser.getVersion(),0.1);
	}
	
	@Test
	public void safari5()
	{
		Browser browser = new Browser("mozilla/5.0 (windows; u; windows nt 6.1; pt-br) applewebkit/533.19.4 (khtml, like gecko) version/5.0.3 safari/533.19.4");
		assertEquals("Safari",browser.getName());
		assertEquals(5.03,browser.getVersion(),0.1);
		browser = new Browser("mozilla/5.0 (windows; u; windows nt 6.1; pt-br) applewebkit/533.19.4 (khtml, like gecko) safari/533.19.4 version/5.0.3");
		assertEquals("Safari",browser.getName());
		assertEquals(5.03,browser.getVersion(),0.1);
	}
	@Test
	public void opera11()
	{
		Browser browser = new Browser("Opera/9.80 (Windows NT 6.0; U; en) Presto/2.8.99 Version/11.10");
		assertEquals("Opera",browser.getName());
		assertEquals(11.1,browser.getVersion(),0.1);
	}

	@Test
	public void opera9()
	{
		Browser browser = new Browser("Opera/9.63 (X11; Linux x86_64; U; ru) Presto/2.1.1");
		assertEquals("Opera",browser.getName());
		assertEquals(9.63,browser.getVersion(),0.1);
	}

	@Test
	public void opera4()
	{
		Browser browser = new Browser("Opera/4.02 (Windows 98; U) [en]");
		assertEquals("Opera",browser.getName());
		assertEquals(4.02,browser.getVersion(),0.1);
	}


	@Test
	public void firefox4b()
	{
		Browser browser = new Browser("mozilla/5.0 (windows nt 6.1; wow64; rv:2.0b10) gecko/20100101 firefox/4.0b10");
		assertEquals("Firefox",browser.getName());
		assertEquals(4,browser.getVersion(),0.1);
	} 
	

	@Test
	public void firefox3()
	{
		Browser browser = new Browser("mozilla/5.0 (windows; u; windows nt 6.1; pt-br; rv:1.9.2.13) gecko/20101203 firefox/3.6.13");
		assertEquals("Firefox",browser.getName());
		assertEquals(3.613,browser.getVersion(),0.1);
	} 

	@Test
	public void msie9b()
	{
		Browser browser = new Browser("mozilla/5.0 (compatible; msie 9.0; windows nt 6.1; win64; x64; trident/5.0)");
		assertEquals("Internet Explorer",browser.getName());
		assertEquals(9,browser.getVersion(),0.1);
	} 
}
