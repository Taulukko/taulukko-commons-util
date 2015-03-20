package integration.com.taulukko.commons.util.data;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
 



import com.taulukko.commons.util.data.EConectorMySQL;
import com.taulukko.commons.util.data.IEConector;
import com.taulukko.commons.util.data.IFactory;
import com.taulukko.commons.util.lang.EDate;

public class FactoryDBTest implements IFactory {
	private static DataSource dataSource = null;

	public FactoryDBTest() {
		if (dataSource != null ) {
			return;
		}
		 
		try {

			BasicDataSource ds = new BasicDataSource();
			String driver = "com.mysql.jdbc.Driver";

			String url = null;
			String username = "root";
			String password = "PWS";

			url = "jdbc:mysql://localhost:3309/rpg?autoReconnect=true";

 
			ds.setDriverClassName(driver);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);
			ds.setTestOnBorrow(true);
			ds.setTestWhileIdle(true);
			ds.setValidationQuery("SELECT 1");
			ds.setMaxActive(5);
			ds.setMaxIdle(5);
			ds.setMaxWait(10000);
			ds.setMaxIdle((int) (EDate.TP_HOUR_IN_MS * 24));

			dataSource = ds;

		} catch (Exception e) {
			System.out.println("open connection exception" + e);
		}

		
	}

	@Override
	public IEConector getConnector() throws SQLException {
		IEConector con = new EConectorMySQL("root", "PWS", "localhost:3309", "rpg");
		con.open();
		return con;
	}

	@Override
	public DataSource getDataSource() throws SQLException {
		return dataSource;
	}
}
