package com.taulukko.commons.util;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class LooperUntil<T> {
	private  Consumer<Optional<T>> code;

	private LooperUntil(Consumer<Optional<T>> code) {
		this.code = code;

	}

	public static <T>LooperUntil<T> loop( Consumer<Optional<T>> code ) {
		return new LooperUntil<>( code);
	}

	public void until( BooleanSupplier test,Optional<T> param) {
		code.accept(param);
		while (!test.getAsBoolean()) {
			code.accept(param);
		}
	}
}