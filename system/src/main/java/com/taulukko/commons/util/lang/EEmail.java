package com.taulukko.commons.util.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.taulukko.commons.TaulukkoException;

public class EEmail {

	private String email = null;

	public EEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return email;
	}

 
    
	public boolean isValid() throws TaulukkoException {
		return EmailValidator.getInstance().isValid(email);
	}

	public String getUsername() throws TaulukkoException {

		String[] parts = StringUtils.split(email, "@");

		if (parts.length != 2) {
			throw new TaulukkoException("Format email invalid!");
		}

		return parts[0];

	}

	public String getDomain() throws TaulukkoException {

		String[] parts = StringUtils.split(email, "@");

		if (parts.length != 2) {
			throw new TaulukkoException("Format email invalid!");
		}

		return parts[1];

	}

	public EEmail normalize() throws TaulukkoException {
		EEmail ret = new EEmail(this.email.trim());

		if (ret.email.endsWith("gmail.com")) {
			String username = ret.getUsername();
			String domain = ret.getDomain();
			username = StringUtils.remove(username, ".");
			username = username.toLowerCase();
			return new EEmail(username + "@" + domain);
		}
		return ret;
	}

}