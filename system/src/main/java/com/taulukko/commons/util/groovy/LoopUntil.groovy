package com.taulukko.commons.util.groovy;

class LooperUntil {
	private Closure code

	static LooperUntil loop( Closure code ) {
		new LooperUntil(code:code)
	}

	void until( Closure test ) {
		code()
		while (!test()) {
			code()
		}
	}
}