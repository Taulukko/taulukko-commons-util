package integration.com.taulukko.commons.util.data;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.commons.util.data.Command;
import com.taulukko.commons.util.data.EQueryRunner;
import com.taulukko.commons.util.data.SingleObjectHandler;

public class EQueryRunnerTest
{

	private static DataSource datasource = null;

	@BeforeClass
	public static void before() throws SQLException
	{
		FactoryDBTest factory = new FactoryDBTest();
		datasource = factory.getDataSource();
	}

	@Test
	public void query() throws SQLException
	{
		int total = contaCmps(6);
		System.out.println("Foram encontradas " + total + " campanhas.");
	}

	private int contaCmps(int gmid) throws SQLException
	{
		EQueryRunner runner = new EQueryRunner(datasource);
		String sql = "SELECT count(*) as total FROM cmps WHERE gmid = ?";
		Command command = new Command(sql, gmid);
		SingleObjectHandler<Long> handler = new SingleObjectHandler<>("total");
		Long total = runner.query(command, handler);
		Assert.assertNotNull(total);
		Assert.assertTrue(total.intValue() > 0);
		return total.intValue();
	}

	@Test
	public void update() throws SQLException
	{
		int totalAntigo = contaCmps(6);

		EQueryRunner runner = new EQueryRunner(datasource);
		String sql = "SELECT id FROM cmps WHERE gmid <> ? limit 1";
		Command command = new Command(sql, 6);
		SingleObjectHandler<Integer> handler = new SingleObjectHandler<>("id");
		Integer cmpid = runner.query(command, handler);

		sql = "UPDATE cmps SET gmid = ? WHERE id = ?";
		command = new Command(sql, 6, cmpid);
		runner.update(command);

		int totalNovo = contaCmps(6);
		Assert.assertEquals(totalNovo, totalAntigo + 1);
		System.out.println("Foram encontradas " + totalNovo + " campanhas.");

	}
}
