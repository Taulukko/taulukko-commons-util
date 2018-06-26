package com.taulukko.commons.util.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.ScalarHandler;

/*Melhoria do ScalarHandler, aceita default*/
public class SingleObjectHandler<T> extends ScalarHandler<T> {

	T orElse = null;

	public SingleObjectHandler(String column) {
		this(column, null);
	}

	public SingleObjectHandler(Integer columnIndex) {
		this(columnIndex, null);
	}

	public SingleObjectHandler(String column, T orElse) {
		super(column);
		this.orElse = orElse;
	}

	public SingleObjectHandler(Integer columnIndex, T orElse) {
		super(columnIndex);
		this.orElse = orElse;
	}

	@Override
	public T handle(ResultSet result) throws SQLException {
		T ret = super.handle(result);

		if (ret == null && orElse != null) {
			return orElse;
		}

		return ret;

	}
}
