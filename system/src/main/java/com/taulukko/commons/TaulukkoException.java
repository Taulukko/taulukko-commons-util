package com.taulukko.commons;

public class TaulukkoException extends Exception {

	public TaulukkoException() {
		super(); 
	}

	public TaulukkoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace); 
	}

	public TaulukkoException(String message, Throwable cause) {
		super(message, cause); 
	}

	public TaulukkoException(String message) {
		super(message); 
	}

	public TaulukkoException(Throwable cause) {
		super(cause); 
	}

}
 