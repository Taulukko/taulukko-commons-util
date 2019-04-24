package com.taulukko.commons.util.config;

public interface ConfigBuilder<T extends ConfigBase> {
	public T createNewConfig();
}
