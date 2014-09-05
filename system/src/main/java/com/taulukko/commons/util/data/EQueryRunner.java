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

public class EQueryRunner extends QueryRunner
{
	public EQueryRunner(DataSource datasource)
	{
		super(datasource);
	}

	public EQueryRunner(boolean pmdKnownBroken)
	{
		super(pmdKnownBroken);
	}

	public EQueryRunner(DataSource ds, boolean pmdKnownBroken)
	{
		super(ds, pmdKnownBroken);
	}

	public <T> T query(Command command, ResultSetHandler<T> rsh)
			throws SQLException
	{
		return this.query(command.getSql(), rsh, command.getParameters());
	}

	public int update(Command command) throws SQLException
	{
		return this.update(command.getSql(), command.getParameters());
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> insert(Command command) throws SQLException
	{
		if (!command.getSql().trim().toUpperCase().startsWith("INSERT"))
		{
			throw new SQLException("Only insert commands are accept.");
		}
		Connection con = this.getDataSource().getConnection();

		PreparedStatement ps = con.prepareStatement(command.getSql(),Statement.RETURN_GENERATED_KEYS);

		List<T> keys = new ArrayList<T>();

		try
		{

			Object parameters[] = command.getParameters();
			for (int index = 0; index < parameters.length; index++)
			{
				ps.setObject(index+1, parameters[index]);
			}
			ps.execute();

			ResultSet generatedKeysRs = ps.getGeneratedKeys();

			while (generatedKeysRs.next())
			{
				keys.add((T) generatedKeysRs.getObject(1));

			}

		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				ps.close();
			}
			catch (SQLException e)
			{
				throw e;
			}
			finally
			{
				DbUtils.closeQuietly(con);
			}
		}

		return keys;
	}
}
