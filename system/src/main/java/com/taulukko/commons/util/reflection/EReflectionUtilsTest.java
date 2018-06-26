package com.taulukko.commons.util.reflection;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.TaulukkoException;

public class EReflectionUtilsTest {

	@Test
	public void getClassesFromPackage() throws TaulukkoException {
		String packageName = "com.taulukko.commons.util.config";
		List<Class> classes = EReflectionUtils.getClassesFromPackage(packageName);

		Assert.assertTrue(
				classes.stream().filter(c -> c.getSimpleName().equals("ConfigBaseTest")).findAny().isPresent());
		Assert.assertTrue(classes.stream().filter(c -> c.getSimpleName().equals("TestConfig")).findAny().isPresent());

	}

}
