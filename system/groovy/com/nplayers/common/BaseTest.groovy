package com.nplayers.common

import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.taulukko.commons.util.io.EFile
import com.taulukko.ws.client.WSClientFactory

@RunWith(JUnit4.class)
class BaseTest  extends GroovyTestCase
{
	private static boolean loaded = false;

	@BeforeClass
	public static void before()
	{
		synchronized (loaded)
		{
			if(!loaded)
			{
				//WSClientFactory.start("${EFile.workPath}/src/main/resources/")//versao nova do wsclient
				WSClientFactory.start("${EFile.workPath.replace('.','')}src/main/resources".replace('\\','/'),false);
				loaded =true;
			}
		}
	}

	String removeSpacesString(String value)
	{
		return value.replace('\n','').replace(' ','') ;
	}
	
	@Test
	void defaultTest() {
		assert loaded
	}
}
