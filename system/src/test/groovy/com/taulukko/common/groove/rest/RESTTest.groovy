

package com.taulukko.common.groove.rest

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class RESTTest extends GroovyTestCase{


	@Test
	public void simpleTest() {


		String s = new URL("http://httpbin.org/get")
				.getText(connectTimeout: 5000,
				readTimeout: 10000,
				useCaches: true,
				allowUserInteraction: false,
				requestProperties: ['Connection': 'close']);

		assert  s.contains('"Host": "httpbin.org"');
	}

	@Test
	public void simpleTestPost() {


		String s = new URL("http://httpbin.org/post")
				.getText(connectTimeout: 5000,
				method: "POST",
				readTimeout: 10000,
				useCaches: true,
				allowUserInteraction: false,
				requestProperties: ['Connection': 'close']);

		assert  s.contains('"Host": "httpbin.org"');
	}
}
