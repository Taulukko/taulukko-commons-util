package com.taulukko.commons.util.config;

import java.util.Map;

@FunctionalInterface
public interface Reloadable<T extends ConfigBase<T>> {
	public void reload(ConfigBase<T> configbase, Map<String, String> properties);
}
