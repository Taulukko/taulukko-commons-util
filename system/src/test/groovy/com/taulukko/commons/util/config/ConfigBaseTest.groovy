package com.taulukko.commons.util.config;

import java.util.Map

import org.junit.BeforeClass
import org.junit.Test; 
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.taulukko.commons.util.io.EFile

@RunWith(value = JUnit4.class)
class ConfigBaseTest {

	static TestConfig config = null;

	@BeforeClass
	static void init() {

		Reloadable reloadable = new Reloadable(){
					public void reload(ConfigBase configbase, Map<String,String> properties) {
					}
				};

		ConfigBuilder configBuilder = new ConfigBuilder<TestConfig>(){

					public TestConfig createNewConfig() {

						return new TestConfig( reloadable);
					}
				};

		TestConfig.startDefault(TestConfig.class,configBuilder,
				"config-base-test", "${EFile.workPath}/src/test/resources");

		config = TestConfig.<TestConfig>getInstance(TestConfig.class);


		config = ConfigBase.getInstance(TestConfig.class);
		
		config = TestConfig.<TestConfig>getInstance(TestConfig.class);
	}

	@Test
	void loaded() {
		assert config.get
	}

	
	@Test
	void propertyBasicConfig() {
		assert "01-08-2008" == config.getServerCreated();
		def x = config.getExtended();
		assert false == config.getExtended().containsKey("serverCreated");
		assert null  == config["serverCreated"];
	}

	@Test
	void propertyExtendedConfig() {
		assert 0 < config.getExtended().size();
		 
		assert "33" == config.property3;
		
		assert "33" == config["property3"];
		assert "33" == config.getExtended().get("property3");
	}


	static class TestConfig extends ConfigBase<TestConfig> {

		public TestConfig( Reloadable reloadable) {
			super(reloadable,TestConfig.class);
		}
	}
}
