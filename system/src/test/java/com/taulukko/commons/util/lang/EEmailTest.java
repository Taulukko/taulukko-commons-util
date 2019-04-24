package com.taulukko.commons.util.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.taulukko.commons.TaulukkoException;

public class EEmailTest
{

	@Test
	public void validateEmail() throws TaulukkoException {
		 
		 
		
		assertFalse(new EEmail("gand@branco@terra.com.br").isValid());
		assertFalse(new EEmail("gand.branco.gmail.com.br").isValid());
		assertTrue(new EEmail("#gandbranco@gmail.com").isValid());
		assertFalse(new EEmail("gandbranco@gmailcom").isValid());
		assertTrue(new EEmail("Gandbra_nco@gmail.com").isValid()); 
	}

	@Test
	public void getUsernameFromEmail() throws TaulukkoException {

		assertEquals("gand.branco",new EEmail("gand.branco@terra.com.br").getUsername());
		assertEquals("gand.branco", new EEmail("gand.branco@gmail.com.br").getUsername());
		assertEquals("gandbranco", new EEmail("gandbranco@gmail.com").getUsername());
		assertEquals("Gandbranco",new EEmail("Gandbranco@gmail.com").getUsername());
	}

	@Test
	public void getDomainFromEmail() throws TaulukkoException {

		assertEquals("terra.com.br", new EEmail("gand.branco@terra.com.br").getDomain());
		assertEquals("gmail.com.br", new EEmail("gand.branco@gmail.com.br").getDomain());
		assertEquals("gmail.com", new EEmail("gandbranco@gmail.com").getDomain());
		assertEquals("gmail.com", new EEmail("Gandbranco@gmail.com").getDomain());
	}

	@Test
	public void normalizeEmail() throws TaulukkoException {

		assertEquals("gand.branco@terra.com.br",  new EEmail("gand.branco@terra.com.br").normalize().toString());
		assertEquals("gand.branco@gmail.com.br", new EEmail("gand.branco@gmail.com.br").normalize().toString());
		assertEquals("gandbranco@gmail.com",  new EEmail("gand.branco@gmail.com").normalize().toString());
		assertEquals("Gand.branco@terra.com.br",  new EEmail("Gand.branco@terra.com.br").normalize().toString());
		assertEquals("Gand.branco@gmail.com.br",  new EEmail("Gand.branco@gmail.com.br").normalize().toString());
		assertEquals("gandbranco@gmail.com",  new EEmail("Gand.branco@gmail.com").normalize().toString());
		assertEquals("Gand.branco@terra.com.br", new EEmail(" Gand.branco@terra.com.br	").normalize().toString());
		assertEquals("Gand.branco@gmail.com.br",  new EEmail("	Gand.branco@gmail.com.br ").normalize().toString());
		assertEquals("gandbranco@gmail.com", new EEmail("  Gand.branco@gmail.com  ").normalize().toString());
	}
}
