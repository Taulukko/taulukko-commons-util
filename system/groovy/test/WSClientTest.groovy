

package test

import static org.junit.Assert.*

import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.taulukko.commons.util.io.EFile
import com.taulukko.ws.client.WSClient
import com.taulukko.ws.client.WSClientFactory

//test only to use to test wsclient framework
// @Ignore
@RunWith(JUnit4.class)
class WSClientTest extends GroovyTestCase{ 

	@BeforeClass
	public static void before() {
		WSClientFactory.start( "${EFile.workPath}/src/main/resources/",false)
	}

	@Test
	public void simpleTest() {
		WSClient wsclient =  WSClientFactory.getClient("get");

		String out = wsclient.execGet("");

		assert out.contains  ('"Host": "httpbin.org"');
	}
}
