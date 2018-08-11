package com.taulukko.commons.util.config;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.io.EFile;

@RunWith(value = JUnit4.class)
public class ConfigBaseTest {

	private static TestConfig config = null;

	@BeforeClass
	public static void init() throws TaulukkoException {

		ConfigBuilder<TestConfig> configBuilder = new ConfigBuilder<TestConfig>() {

			public TestConfig createNewConfig() {

				return new TestConfig((a, b) -> {
				});
			}
		};

		try {
			TestConfig.startDefault(TestConfig.class, configBuilder, "config-base-test",
					EFile.getWorkPath() + "/src/test/resources");
		} catch (Exception e) {
			throw new TaulukkoException(e);
		}

		config = TestConfig.<TestConfig>getInstance(TestConfig.class);

		config = ConfigBase.getInstance(TestConfig.class);

		config = TestConfig.<TestConfig>getInstance(TestConfig.class);
	}

	@Test
	public void propertyBasicConfig() {
		Assert.assertEquals("01-08-2008", config.getServerCreated());

		Assert.assertFalse(config.getExtended().containsKey("serverCreated"));
		Assert.assertNull(config.get("serverCreated"));
	}

	@Test
	public void propertyExtendedConfig() {
		Assert.assertTrue(0 < config.getExtended().size());

		Assert.assertEquals("33", config.get("property3"));
		Assert.assertEquals("33", config.getExtended().get("property3"));
	}

	static class TestConfig extends ConfigBase<TestConfig> {

		public TestConfig(Reloadable<TestConfig> reloadable) {
			super(reloadable, TestConfig.class);
		}
	}
}
