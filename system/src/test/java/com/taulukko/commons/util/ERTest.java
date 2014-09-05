package com.taulukko.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class ERTest
{
	/*
	 * ^ : início da linha
	 * $ : fim da linha
	 * ? : 0 ou 1 vez
	 * * : 0 ou mais
	 * + : 1 ou mais
	 * {n} :n vezes
	 * {x,y} : de x a (y+1) vezes
	 * a|b : a ou b
	 * [ab] :  a ou b
	 * [^ab] : negação de a ou b (qualquer coisa menos a ou b)
	 * [x-y] : do (número ou letra) x até o (número ou letra) y, ex: [0-9]
	 * 
	 * */
	@Test
	public void searchRoot()
	{
		Pattern pattern = Pattern.compile("(root)+");

		// acha root
		Matcher m = pattern.matcher("root:x:0:0:root:/root:/bin/bash");

		assertEquals(true, m.find());
		assertEquals("root", m.group());
		assertEquals(0, m.start());
		assertEquals(4, m.end());
		assertEquals(true, m.find());
		assertEquals("root", m.group());
		assertEquals(11, m.start());
		assertEquals(15, m.end());
		assertEquals(true, m.find());
		assertEquals("root", m.group());
		assertEquals(17, m.start());
		assertEquals(21, m.end());
		assertEquals(false, m.find());

		// mas também acha root em
		m = pattern.matcher("operator:x:11:0:operator:/root:/sbin/nologin");
		assertEquals(true, m.find());
		assertEquals("root", m.group());
		assertEquals(26, m.start());
		assertEquals(30, m.end());
		assertEquals(false, m.find());
	}

	@Test
	public void searchRootInBegin()
	{
		Pattern pattern = Pattern.compile("(^root)+");

		// acha root no começo em
		Matcher m = pattern.matcher("root:x:0:0:root:/root:/bin/bash");

		assertEquals(true, m.find());
		assertEquals("root", m.group());
		assertEquals(0, m.start());
		assertEquals(4, m.end());
		assertEquals(false, m.find());

		// e não mais em em
		m = pattern.matcher("operator:x:11:0:operator:/root:/sbin/nologin");
		assertEquals(false, m.find());
	}

	@Test
	public void searchBashInEnd()
	{
		Pattern pattern = Pattern.compile("(bash$)+");

		// acha root no começo em
		Matcher m = pattern.matcher("root:x:0:0:root:/root:/bin/bash");

		assertEquals(true, m.find());
		assertEquals("bash", m.group());
		assertEquals(27, m.start());
		assertEquals(31, m.end());
		assertEquals(false, m.find());

		// e não mais em em
		m = pattern.matcher("mubash:x:11:0:mubash:/home/mubash:/sbin/nologin");
		assertEquals(false, m.find());
	}

	@Test
	public void searchEmptyLine()
	{
		Pattern pattern = Pattern.compile("(^$)+");

		Matcher m = pattern.matcher("");

		assertEquals(true, m.find());
		assertEquals("", m.group());
		assertEquals(0, m.start());
		assertEquals(0, m.end());
		assertEquals(false, m.find());

		m = pattern.matcher("mubash:x:11:0:mubash:/home/mubash:/sbin/nologin");
		assertEquals(false, m.find());
	}

	@Test
	public void searchCarlos()
	{
		Pattern pattern = Pattern.compile("([Cc]arlos)+");

		Matcher m = pattern
				.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(true, m.find());
		assertEquals("carlos", m.group());
		assertEquals(0, m.start());
		assertEquals(6, m.end());
		assertEquals(true, m.find());
		assertEquals("carlos", m.group());
		assertEquals(17, m.start());
		assertEquals(23, m.end());
		assertEquals(true, m.find());
		assertEquals("carlos", m.group());
		assertEquals(30, m.start());
		assertEquals(36, m.end());
		assertEquals(false, m.find());

		m = pattern
				.matcher("acs:x:502:502:Antonio Carlos Silva:/home/acs:/bin/bash");

		assertEquals(true, m.find());
		assertEquals("Carlos", m.group());
		assertEquals(22, m.start());
		assertEquals(28, m.end());
		assertEquals(false, m.find());
	}

	@Test
	public void searchVowel()
	{

		Pattern pattern = Pattern.compile("(^[aeiou])+");

		Matcher m = pattern
				.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(false, m.find());

		m = pattern
				.matcher("acs:x:502:502:Antonio Carlos Silva:/home/acs:/bin/bash");
		assertEquals(true, m.find());

		m = pattern.matcher("adm:x:3:4:adm:/var/adm:/sbin/nologin");
		assertEquals(true, m.find());

		m = pattern.matcher("operator:x:11:0:operator:/root:/sbin/nologin");
		assertEquals(true, m.find());

	}

	@Test
	public void searchConsonantes()
	{

		Pattern [] patterns = { Pattern.compile("(^[bcdfghjklmnpqrstvwxyz])+"),
				Pattern.compile("(^[^aeiou])+") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern
					.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

			assertEquals(true, m.find());

			m = pattern
					.matcher("acs:x:502:502:Antonio Carlos Silva:/home/acs:/bin/bash");
			assertEquals(false, m.find());

			m = pattern.matcher("adm:x:3:4:adm:/var/adm:/sbin/nologin");
			assertEquals(false, m.find());

			m = pattern.matcher("operator:x:11:0:operator:/root:/sbin/nologin");
			assertEquals(false, m.find());
		}
	}

	@Test
	public void searchVowel2Character()
	{

		Pattern pattern = Pattern.compile("(^.[aeiou])+");

		Matcher m = pattern
				.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(true, m.find());

		m = pattern
				.matcher("acs:x:502:502:Antonio Carlos Silva:/home/acs:/bin/bash");
		assertEquals(false, m.find());

		m = pattern.matcher("adm:x:3:4:adm:/var/adm:/sbin/nologin");
		assertEquals(false, m.find());

		m = pattern.matcher("operator:x:11:0:operator:/root:/sbin/nologin");
		assertEquals(false, m.find());

		m = pattern.matcher("root:x:0:0:root:/root:/bin/bash");
		assertEquals(true, m.find());

	}

	@Test
	public void fixedSize()
	{

		Pattern patterns[] = new Pattern[] { Pattern.compile("(^.....$)+"),
				Pattern.compile("(^.{5}$)+") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern
					.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

			assertEquals(false, m.find());

			m = pattern.matcher("aeiou");

			assertEquals(true, m.find());

			m = pattern.matcher("12345");

			assertEquals(true, m.find());

			m = pattern.matcher("1");

			assertEquals(false, m.find());

			m = pattern.matcher("?:><	");

			assertEquals(true, m.find());
		}
	}

	@Test
	public void varSize()
	{

		Pattern pattern = Pattern.compile("(^.{5,9}$)+");

		Matcher m = pattern
				.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(false, m.find());

		m = pattern.matcher("aeiou");

		assertEquals(true, m.find());

		m = pattern.matcher("123457890");

		assertEquals(true, m.find());

		m = pattern.matcher("1234578901");

		assertEquals(false, m.find());

		m = pattern.matcher("1234");

		assertEquals(false, m.find());

		m = pattern.matcher("12345");

		assertEquals(true, m.find());
	}

	@Test
	public void sizeFiveOrMore()
	{

		Pattern pattern = Pattern.compile("(^.{5,}$)+");

		Matcher m = pattern
				.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(true, m.find());

		m = pattern.matcher("aeiou");

		assertEquals(true, m.find());

		m = pattern.matcher("1234578901");

		assertEquals(true, m.find());

		m = pattern.matcher("1234");

		assertEquals(false, m.find());

		m = pattern.matcher("12345");

		assertEquals(true, m.find());
	}

	@Test
	public void vowelAndBash()
	{

		Pattern pattern = Pattern.compile("(^[aeiou].*bash$)+");

		Matcher m = pattern.matcher("acm:x:500:500:acm:/home/acm:/bin/bash");

		assertEquals(true, m.find());

		m = pattern.matcher("aeiou");

		assertEquals(false, m.find());

		m = pattern.matcher("1234578901bash");

		assertEquals(false, m.find());

		m = pattern.matcher("bashaeiou");

		assertEquals(false, m.find());

		m = pattern.matcher("abash");

		assertEquals(true, m.find());
	}

	@Test
	public void or()
	{

		Pattern pattern = Pattern.compile("(acm|carlos)+");

		Matcher m = pattern.matcher("acm:x:500:500:acm:/home/acm:/bin/bash");

		assertEquals(true, m.find());

		m = pattern.matcher("carlos:x:500:500:carlos:/home/carlos:/bin/bash");

		assertEquals(true, m.find());

		m = pattern.matcher("1234578901bash");

		assertEquals(false, m.find());

	}

	@Test
	public void timesZeroOrOne()
	{

		Pattern patterns[] = { Pattern.compile("a?eiou"),
				Pattern.compile("a{0,1}eiou") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern.matcher("aeiou");

			assertEquals(true, m.find());

			m = pattern.matcher("aaeiou");

			assertEquals(true, m.find());

			assertEquals(1, m.start());

			m = pattern.matcher("eiou");

			assertEquals(true, m.find());

			m = pattern.matcher("12345");

			assertEquals(false, m.find());
		}

	}

	@Test
	public void timesZeroOrMore()
	{

		Pattern patterns[] = { Pattern.compile("a*eiou"),
				Pattern.compile("a{0,}eiou") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern.matcher("aeiou");

			assertEquals(true, m.find());

			m = pattern.matcher("aaeiou");

			assertEquals(true, m.find());

			assertEquals(0, m.start());

			m = pattern.matcher("eiou");

			assertEquals(true, m.find());

			m = pattern.matcher("12345");

			assertEquals(false, m.find());
		}

	}

	@Test
	public void timesOneOrMore()
	{

		Pattern patterns[] = { Pattern.compile("a+eiou"),
				Pattern.compile("a{1,}eiou") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern.matcher("aeiou");

			assertEquals(true, m.find());

			m = pattern.matcher("aaeiou");

			assertEquals(true, m.find());

			assertEquals(0, m.start());

			m = pattern.matcher("eiou");

			assertEquals(false, m.find());

			m = pattern.matcher("12345");

			assertEquals(false, m.find());
		}

	}
	
	@Test
	public void interval()
	{

		Pattern patterns[] = { Pattern.compile("[0123456789]"),
				Pattern.compile("[0-9]") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern.matcher("abc9");

			assertEquals(true, m.find());

			m = pattern.matcher("abc");

			assertEquals(false, m.find());

			m = pattern.matcher("0abc");

			assertEquals(true, m.find());

			m = pattern.matcher("ab9cd");

			assertEquals(true, m.find());
		}

	}
	
	@Test
	public void letterInterval()
	{

		Pattern patterns[] = { Pattern.compile("[abcdef]"),
				Pattern.compile("[a-f]") };

		for (Pattern pattern : patterns)
		{
			Matcher m = pattern.matcher("1234a");

			assertEquals(true, m.find());

			m = pattern.matcher("123");

			assertEquals(false, m.find());

			m = pattern.matcher("f123");

			assertEquals(true, m.find());

			m = pattern.matcher("123a45");

			assertEquals(true, m.find());
		}

	}
	

	private static String _er = "<[ ]*body[^>]*>(.*)<[ ]*/[ ]*body[^>]*>";

	@Test
	public void findBody() {
		String content = "qw  < body >teste <div id='subBody'></div> <  /  body>";

		Pattern pattern = Pattern.compile(_er,
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(content);

		Assert.assertTrue(matcher.find());
		Assert.assertEquals("teste <div id='subBody'></div> ", matcher.group(1));
	}
	
	@Test
	public void findBody2() {
		String innerBody = "\n"+
"<div align=\"left\" id=\"centerDiv\" style=\"max-width: 300px\">\n"+
"<H2>Arco da maldição</H2>\n"+
"Ao puxar a corda uma flecha negra de sombras surge, ele</div>\n"+
"<SCRIPT>flagSelected='PT_BR';</SCRIPT><SCRIPT>logado=true;</SCRIPT>";
		
		innerBody = "teste\nteste";
		
		String content = " <BODY>"+ innerBody + "</BODY> ";

		content = content.replace('\n', ' ');
		innerBody  = innerBody.replace('\n', ' ');
		
		Pattern pattern = Pattern.compile(_er,
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(content);

		Assert.assertTrue(matcher.find());
		Assert.assertEquals(innerBody, matcher.group(1));
	}
	
	@Test
	public void findBody3() {
		String content = "qw  < BODY >teste <div id='subBody'></div> <  /  bOdy>";

		Pattern pattern = Pattern.compile(_er,
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(content);

		Assert.assertTrue(matcher.find());
		Assert.assertEquals("teste <div id='subBody'></div> ", matcher.group(1));
	}
}
