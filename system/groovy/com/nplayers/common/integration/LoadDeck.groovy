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
class LoadDeck extends BaseTest {


	private JsonSlurper jsonSlurper = new JsonSlurper();

	@Test
	public void loadDeckNotUpdated() {
		WSClient wsclient =  WSClientFactory.getClient("account");


		def json = wsclient.execGet("loadDeck/gandbranco@gmail.com/harry_potter.pt-br/1");


		def deck = jsonSlurper.parseText(json)

		assert  null!=deck ;
		assert  "harry_potter.pt-br"==deck.id ;
		assert  20170203==deck.version ;
		assert  1==deck.status ;
		assert  null!=deck.content ;
		assert removeSpacesString( deck.content) == removeSpacesString(
		'''[
		{
			"content" : "Grifinória", 
			"hints" : [
"Seu símbolo é um leão",
 "Vermelho e dourado são suas cores"
]
		},
		{
"content" : "Dobby",
"hints" : ["Elfo doméstico"]
},
{
	"content" : "Alohomora",
"hints" : ["Magia que destranca portas"]
}
]''' );
	}

	@Test
	public void signinUpdated() {
		WSClient wsclient =  WSClientFactory.getClient("account");

		def json = wsclient.execGet("loadDeck/gandbranco@gmail.com/actors.pt-br/20020101");


		def deck = jsonSlurper.parseText(json)

		assertNotNull deck;
		assertEquals  1,deck.status ;
	}
}
