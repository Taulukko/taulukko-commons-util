package com.nplayers.common.integration;




import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.nplayers.common.BaseTest
import com.taulukko.ws.client.WSClient
import com.taulukko.ws.client.WSClientFactory

import groovy.json.JsonSlurper

@RunWith(JUnit4.class)
class SigninTest extends BaseTest
{


	private JsonSlurper jsonSlurper = new JsonSlurper();

	@Test
	public void signinNotUpdated()
	{
		WSClient wsclient =  WSClientFactory.getClient("account");

		//get
		def json = wsclient.execGet("signin/jeanrespons/pt-br/1");


		def catalog = jsonSlurper.parseText(json)

		assertNotNull catalog;
		assertEquals  "pt-br",catalog.id ;
		assertEquals  20170203,catalog.version ;
		assertEquals  1,catalog.status ;

	}
	
	@Test
	public void signinUpdated()
	{
		WSClient wsclient =  WSClientFactory.getClient("account");

		//get
		def json = wsclient.execGet("signin/jeanrespons/pt-br/20300120");


		def catalog = jsonSlurper.parseText(json)

		assertNotNull catalog;
		assertEquals  0,catalog.status ;

	}
}
