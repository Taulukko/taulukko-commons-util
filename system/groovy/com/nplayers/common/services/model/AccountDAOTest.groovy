package com.nplayers.common.services.model;




import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.nplayers.charadas.services.dto.AccountDTO
import com.nplayers.charadas.services.dto.CatalogDTO
import com.nplayers.charadas.services.dto.DeckDTO
import com.nplayers.charadas.services.model.AccountDAO
import com.nplayers.common.BaseTest

@RunWith(JUnit4.class)
class AccountDAOTest extends BaseTest
{

	private static AccountDAO accountDAO = new AccountDAO();

	
	@Test
	public void listDecksSuccess()
	{
		List<DeckDTO> decks= accountDAO.listDecks("pt-br");


		assert   decks.size()>0 
	}

	
	@Test
	public void listDecksFail()
	{
		List<DeckDTO> decks= accountDAO.listDecks(-1);


		assert   decks.size()==0
	}

	
 
	@Test
	public void getLastCatalogSucess()
	{
		CatalogDTO lastCatalog = accountDAO.getLastCatalog(20150101,"pt-br");


		assertEquals   lastCatalog.getI18n() , "pt-br";
		assertTrue   lastCatalog.getVersion() > 20150101;
	}

	@Test
	public void getLastCatalogFail()
	{
		CatalogDTO lastCatalog = accountDAO.getLastCatalog(30150101,"pt-br");


		assertNull  lastCatalog;

		lastCatalog = accountDAO.getLastCatalog(30150101,"pt-pt");

		assertNull  lastCatalog;
	}


	
	@Test
	public void loadSuccess()
	{
		AccountDTO account = accountDAO.load("gandbranco@gmail.com");
		assert account.id=="gandbranco@gmail.com"
		assert account.email=="gandbranco@gmail.com"
	}
	
	
	@Test
	public void loadDeckSuccess()
	{
		DeckDTO deck = accountDAO.loadDeck("actors.pt-br");
		assert deck.id=="actors.pt-br"
		assert deck.contentId=="actors"
	}
	 

	@Test
	public void loadFail()
	{
		AccountDTO account = accountDAO.load("sergiomoura@gmail.com");
		assert account==null;
	}
}
