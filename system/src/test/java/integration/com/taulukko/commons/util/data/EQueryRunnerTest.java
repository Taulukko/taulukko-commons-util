package integration.com.taulukko.commons.util.data;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.data.Command;
import com.taulukko.commons.util.data.EQueryRunner;
import com.taulukko.commons.util.data.SingleObjectHandler;

public class EQueryRunnerTest {

	private static DataSource datasource = null;

	@BeforeClass
	public static void before() throws TaulukkoException {
		FactoryDBTest factory = new FactoryDBTest();
		datasource = factory.getDataSource();
	}

	@Test
	public void query() throws TaulukkoException {
		int total = contaMacros(6);
		System.out.println("Foram encontradas " + total + " macros.");
	}

	private int contaMacros(int userid) throws TaulukkoException {
		EQueryRunner runner = new EQueryRunner(datasource);
		String sql = "SELECT count(*) as total FROM macros where userid = ? ";
		Command command = new Command(sql,userid);
		SingleObjectHandler<Long> handler = new SingleObjectHandler<>("total");
		Long total = runner.query(command, handler);
		Assert.assertNotNull(total);
		Assert.assertTrue(total.intValue() > 0);
		return total.intValue();
	}

	@Test
	public void update() throws TaulukkoException {
		int totalAntigo = contaMacros(6);

		EQueryRunner runner = new EQueryRunner(datasource);
		String sql = "SELECT cmpId FROM macros where userid=  ? limit 1";
		Command command = new Command(sql,6);
		SingleObjectHandler<Integer> handler = new SingleObjectHandler<>("cmpId");
		Integer cmpid = runner.query(command, handler);

		sql = "UPDATE macros SET userid = ?, cmpId = ? where userid <> ? and cmpId <> ?  limit 1";
		command = new Command(sql, 6,cmpid,6, cmpid);
		runner.update(command);

		int totalNovo = contaMacros(6);
		Assert.assertEquals(totalNovo, totalAntigo + 1);
		System.out.println("Foram encontradas " + totalNovo + " campanhas.");

	}
}
