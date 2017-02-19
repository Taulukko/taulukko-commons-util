package com.taulukko.common.groove.features;

import org.junit.Ignore
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(value=JUnit4.class)
public class PropertyTest {

	@Test
	public void propertyGroovyTest() {

		def obj =  new PropertyTestDTO(age:55,name:"Herriker");

		assert obj.age ==55;
		assert obj.name == "Herriker";
		assert obj.output == "setName";


		obj.age=66;
		assert obj.age ==66;
	}

	@Ignore //bad function in Groovy, expected fix in Groovy 3.0
	@Test(expected=Throwable)
	public void privateAccessConstructor() {

		def obj =  new PropertyTestDTO(output:"Herriker");

		assert obj.output == "Herriker";
	}

	@Ignore //bad function in Groovy, expected fix in Groovy 3.0
	@Test(expected=Throwable)
	public void privateAccessProperty() {

		def obj =  new PropertyTestDTO( );

		obj.output = "setName";
		assert obj.output == "setName";
	}

	@Ignore //bad function in Groovy, expected fix in Groovy 3.0
	@Test(expected=Throwable)
	public void privateAccessClosure() {

		def obj =  new PropertyTestDTO().with({output="setName";return it;});
		assert obj.output == "setName";
	}
}
