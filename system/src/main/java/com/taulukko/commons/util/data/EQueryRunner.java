package com.taulukko.commons.util.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.taulukko.commons.TaulukkoException;

public class EQueryRunner extends QueryRunner {
	public EQueryRunner(DataSource datasource) {
		super(datasource);
	}

	public EQueryRunner(boolean pmdKnownBroken) {
		super(pmdKnownBroken);
	}

	public EQueryRunner(DataSource ds, boolean pmdKnownBroken) {
		super(ds, pmdKnownBroken);
	}

	public <T> T query(Command command, ResultSetHandler<T> rsh) throws TaulukkoException {
		try {
			return this.query(command.getSql(), rsh, command.getParameters());
		} catch (SQLException e) {
			throw new TaulukkoException(e);
		}
	}

	public int update(Command command) throws TaulukkoException {
		try {
			return this.update(command.getSql(), command.getParameters());
		} catch (SQLException e) {
			throw new TaulukkoException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> insert(Command command) throws TaulukkoException {
		if (!command.getSql().trim().toUpperCase().startsWith("INSERT")) {
			throw new TaulukkoException("Only insert commands are accept.");
		}
		Connection con = null;
		PreparedStatement ps = null;
		List<T> keys = new ArrayList<>();
		try {
			con = this.getDataSource().getConnection();

			ps = con.prepareStatement(command.getSql(), Statement.RETURN_GENERATED_KEYS);

			Object parameters[] = command.getParameters();
			for (int index = 0; index < parameters.length; index++) {
				ps.setObject(index + 1, parameters[index]);
			}
			ps.execute();

			ResultSet generatedKeysRs = ps.getGeneratedKeys();

			while (generatedKeysRs.next()) {
				keys.add((T) generatedKeysRs.getObject(1));

			}

		} catch (SQLException e) {
			throw new TaulukkoException(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new TaulukkoException(e);
			} finally {
				DbUtils.closeQuietly(con);
			}
		}

		return keys;
	}
}
