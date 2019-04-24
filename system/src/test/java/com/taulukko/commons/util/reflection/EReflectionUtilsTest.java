package com.taulukko.commons.util.reflection;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.reflection.EReflectionUtils;

public class EReflectionUtilsTest {

	@Test
	public void getClassesFromPackage() throws TaulukkoException {
		String packageName = "com.taulukko.commons.util.config";
		List<Class<?>>  classes = EReflectionUtils.getClassesFromPackage(packageName);

		Assert.assertTrue(
				classes.stream().filter(c -> c.getSimpleName().equals("ConfigBaseTest")).findAny().isPresent());
		Assert.assertTrue(classes.stream().filter(c -> c.getSimpleName().equals("TestConfig")).findAny().isPresent());

	}
	
	@Test
	public void getCaller() throws TaulukkoException {
		String fullDomainName = "com.taulukko.commons.util.reflection.EReflectionUtilsTest.getCaller(EReflectionUtilsTest.java";
		String getCaller =   EReflectionUtils.getCaller().substring(0,fullDomainName.length());
		
		Assert.assertEquals(fullDomainName,getCaller);

	}
	
	@Test
	public void getCallerWithParameters() throws TaulukkoException {
		String fullDomainName = "com.taulukko.commons.util.reflection.EReflectionUtils.getCaller(EReflectionUtils.java";
		String getCaller =   EReflectionUtils.getCaller(1).substring(0,fullDomainName.length());
		
		Assert.assertEquals(fullDomainName,getCaller);

	}
	

}
