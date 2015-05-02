package com.taulukko.commons.util.crypt;


import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

 
 
public class Base64Test {
	@Test
	public void removeSymbols() throws IOException
	{  
		String encode = Base64.encode("Alayão!çãéôõ".getBytes(Charset.forName("UTF8")),0);
		Assert.assertEquals("QWxhecOjbyHDp8Ojw6nDtMO1",encode);
		String decode = new String(Base64.decode( encode),Charset.forName("UTF8"));
		Assert.assertEquals("Alayão!çãéôõ", decode);
	}
	
	@Test(expected=IOException.class)
	public void terminator() throws IOException
	{
		String test = "eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTEyNDU0ODk4MjQ0MTU0Mzg2MTMxIiwiYXpwIjoiOTY3MTg1NTI1OTkwLTRkZHR0ZXZ1OW5lNGtpcmMxamJpOTNyNGV0ZDZjcmFjLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJnYW5kYnJhbmNvQGdtYWlsLmNvbSIsImF0X2hhc2giOiJrUEpxVjhDSlp6QmwySTAtRkY2Y0J3IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF1ZCI6Ijk2NzE4NTUyNTk5MC00ZGR0dGV2dTluZTRraXJjMWpiaTkzcjRldGQ2Y3JhYy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImlhdCI6MTQzMDU5MjA0OSwiZXhwIjoxNDMwNTk1NjQ5fQ";
		String decript = new String(Base64.decode(test));
		String expected= "{\"iss\":\"accounts.google.com\",\"sub\":\"112454898244154386131\",\"azp\":\"967185525990-4ddttevu9ne4kirc1jbi93r4etd6crac.apps.googleusercontent.com\",\"email\":\"gandbranco@gmail.com\",\"at_hash\":\"kPJqV8CJZzBl2I0-FF6cBw\",\"email_verified\":true,\"aud\":\"967185525990-4ddttevu9ne4kirc1jbi93r4etd6crac.apps.googleusercontent.com\",\"iat\":1430592049,\"exp\":1430595649}";
		Assert.assertEquals(expected, decript);
	}
	
	
	@Test
	public void terminatorMissing() throws IOException
	{
		String test = "eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwic3ViIjoiMTEyNDU0ODk4MjQ0MTU0Mzg2MTMxIiwiYXpwIjoiOTY3MTg1NTI1OTkwLTRkZHR0ZXZ1OW5lNGtpcmMxamJpOTNyNGV0ZDZjcmFjLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJnYW5kYnJhbmNvQGdtYWlsLmNvbSIsImF0X2hhc2giOiJrUEpxVjhDSlp6QmwySTAtRkY2Y0J3IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF1ZCI6Ijk2NzE4NTUyNTk5MC00ZGR0dGV2dTluZTRraXJjMWpiaTkzcjRldGQ2Y3JhYy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImlhdCI6MTQzMDU5MjA0OSwiZXhwIjoxNDMwNTk1NjQ5fQ";
		String decript = new String(Base64.decode(test,true));
		String expected= "{\"iss\":\"accounts.google.com\",\"sub\":\"112454898244154386131\",\"azp\":\"967185525990-4ddttevu9ne4kirc1jbi93r4etd6crac.apps.googleusercontent.com\",\"email\":\"gandbranco@gmail.com\",\"at_hash\":\"kPJqV8CJZzBl2I0-FF6cBw\",\"email_verified\":true,\"aud\":\"967185525990-4ddttevu9ne4kirc1jbi93r4etd6crac.apps.googleusercontent.com\",\"iat\":1430592049,\"exp\":1430595649}";
		Assert.assertEquals(expected, decript);
	}
}
