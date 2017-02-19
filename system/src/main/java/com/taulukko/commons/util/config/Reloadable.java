package com.taulukko.commons.util.config;

import java.util.Map;

public interface Reloadable {
	public void reload(ConfigBase configbase, Map<String,String> properties);
}
