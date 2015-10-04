package com.taulukko.commons.util.i18n;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.taulukko.commons.util.io.EResource;
import com.taulukko.commons.util.io.EResourceGet;

public class I18nBuilder {
	private String common;
	private Map<String, Map<String, String>> dictionary = new HashMap<>();
	private Map<String, String> commonMap = null;
	private String notFoundText = null;
	private EResourceGet commonJS = new EResource(
			"/com/taulukko/commons/util/i18n/i18n.js").get();

	public I18nBuilder(String notFoundText) throws IOException {
		this(notFoundText, null);
	}

	public I18nBuilder(String notFoundText, String common) throws IOException {
		this.common = common;
		this.notFoundText = notFoundText;

		boolean commonExist = this.common != null;

		if (commonExist) {
			this.common = this.common.replace('\\', '/');

			if (this.common.startsWith("/")) {
				this.common = this.common
						.substring(1, this.common.length() - 1);
			}

			loadCommonContext();
		}

	}

	private void loadCommonContext() throws IOException {
		commonMap = loadMap(common);
	}

	private HashMap<String, String> loadMap(String filePath) throws IOException {
		if (!filePath.startsWith("/")) {
			filePath = "/" + filePath;
		}

		String content = new EResource(filePath).get().asString();

		String lines[] = content.split("\n");
		HashMap<String, String> map = new HashMap<String, String>();

		for (String line : lines) {
			String parts[] = line.split("=");
			String key = parts[0];
			String text = parts[1];
			if (parts.length > 2) {
				for (int index = 2; index < parts.length; index++) {
					text += "=" + parts[index];
				}
			}
			map.put(key, text);
		}
		return map;
	}

	public I18n build(String context) throws IOException {
		prepare(context);
		Map<String, String> map = dictionary.get(context);
		return new I18n(notFoundText, map);
	}

	public I18n build(String context, String parentContext) throws IOException {
		prepare(context, parentContext);
		Map<String, String> map = dictionary.get(context);
		return new I18n(notFoundText, map);
	}

	public boolean prepare(String context) throws IOException {
		if (dictionary.containsKey(context)) {
			return false;
		}
		HashMap<String, String> map = loadMap(context);
		if (commonMap != null) {
			map.putAll(commonMap);
		}
		dictionary.put(context, map);
		return true;
	}

	public boolean prepare(String context, String parentContext)
			throws IOException {
		Map<String, String> parentMap = null;

		boolean contextLoaded = prepare(context);

		if (contextLoaded) {
			prepare(parentContext);
			parentMap = dictionary.get(parentContext);
			Map<String, String> map = dictionary.get(context);
			map.putAll(parentMap);
		}
		return contextLoaded;

	}

	public EResourceGet getCommonJS() {
		return commonJS;
	}

}
