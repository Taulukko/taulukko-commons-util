package com.taulukko.commons.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

public class MapKVHandler<K, V> implements ResultSetHandler<Map<K, V>> {

	private Map<K, V> ret = null;
	private String keyFieldName = null;
	private String valueFieldName = null;

	public MapKVHandler(String keyFieldName, String valueFieldName) {
		this.valueFieldName = valueFieldName;
		this.keyFieldName = keyFieldName;
		ret = new HashMap<>();
	}

	@Override
	public Map<K, V> handle(ResultSet rs) throws SQLException {
		while (rs.next()) {
			ret.put( (K) rs.getObject(keyFieldName), (V)rs.getObject(valueFieldName));
		} 
		return ret;
	}

}
