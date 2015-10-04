package com.taulukko.commons.util.i18n;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class I18n {

	private Map<String, String> map = null;
	private String notFoundText = null;

	protected I18n(String notFoundText, Map<String, String> map)
			throws IOException {
		this.map = map;
		this.notFoundText = notFoundText;
	}
	
	
	
	public String getJSMap(String mapName)
	{
		String ret = "var "+ mapName + " = new Map();\n";
		Set<String> keys =  map.keySet();
		for(String key:keys)
		{
			String value = map.get(key);
			CharSequence find = "\"";
			CharSequence replace =  "\\\"";
			
			key = key.replace(find,replace);
			value = value.replace(find,replace);
			ret+= String.format( "\n "+ mapName + ".set(\"%s\",\"%s\");",key,value);
		}
		return ret;
	}

	public String getText(String key, String... parameters) {
		if (!map.containsKey(key)) {
			return notFoundText;
		}
		return fill(map.get(key), parameters);
	}

	private String fill(String text, String[] parameters) {
		for (int index = 0; index < parameters.length; index++) {
			CharSequence target = "%" + String.valueOf(index);
			CharSequence replacement = parameters[index];
			text = text.replace(target, replacement);
		}
		return text;
	}

}
