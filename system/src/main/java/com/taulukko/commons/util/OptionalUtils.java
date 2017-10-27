package com.taulukko.commons.util;

import java.util.Optional;

public class OptionalUtils {

	public static <T> T required(Optional<T> value, String msgError) {
		if (!value.isPresent()) {
			throw new RuntimeException(msgError);
		}
		return value.get();
	}

	public static <T> T required(Optional<T> value) {
		return required(value, " Value is required");
	}
}
