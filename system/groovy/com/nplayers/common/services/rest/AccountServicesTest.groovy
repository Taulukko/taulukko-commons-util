package com.nplayers.common.services.rest;




import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.nplayers.charadas.services.dto.CatalogDTO
import com.nplayers.charadas.services.dto.DeckDTO
import com.nplayers.charadas.services.rest.AccountService
import com.nplayers.common.BaseTest

@RunWith(JUnit4.class)
class AccountServicesTest extends BaseTest {


	AccountService account = new  AccountService();


	@Test
	public void signinNotUpdated() {

		CatalogDTO catalog = account.signin("jeanrespons","pt-br",20150101);

		assertNotNull catalog;
		assert  "pt-br" == catalog.id ;
		assert  20170203 == catalog.version ;
		assert  1 == catalog.status ;
	}

	@Test
	public void signinUpdated() {
		CatalogDTO catalog = account.signin("jeanrespons","pt-br",20300120);

		assertNotNull catalog;
		assertEquals  0,catalog.status ;
	}

	@Test
	public void loadDeckSucess() {
		DeckDTO deck = account.loadDeck("gandbranco@gmail.com","actors.pt-br",1);

		assertNotNull deck;
		assert deck.getContentId() == "actors";
	}

	@Test
	public void loadDeckContentSucess() {
		DeckDTO deck = account.loadDeck("gandbranco@gmail.com","harry_potter.pt-br",1);

		assertNotNull deck;
		assert deck.getId() == "harry_potter.pt-br";

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
	public void loadDeckNotUpdatedSucess() {
		DeckDTO deck = account.loadDeck("gandbranco@gmail.com","harry_potter.pt-br",20201010);

		assert deck!=null;
		assert deck.status==0;
 
	}

	@Test(expected=Throwable)
	public void loadDeckIdNotExist() {
		DeckDTO deck = account.loadDeck("gandbranco@gmail.com","no-exist.pt-br",1);

		assertNull deck;
	}

	@Test(expected=Throwable)
	public void loadDeckUserIdNotExist() {
		DeckDTO deck = account.loadDeck("gandbrancoX@gmail.com","actors.pt-br",1);

		assertNull deck;
	}
}
