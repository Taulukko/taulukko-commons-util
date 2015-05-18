package com.taulukko.commons.util.config;

import java.util.Properties;

public interface Reloadable {
	public void reload(ConfigBase configbase, Properties properties);
}
