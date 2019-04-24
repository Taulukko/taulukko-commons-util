package com.taulukko.commons.util;

import java.util.ArrayList;
import java.util.List;
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
	
	/**If is not present, return empty list.*/
	public static <T> List<T> compatibilize(Optional<List<T>> value) {
		if(value.isPresent())
		{
			return value.get();
		}
		else
		{
			return new ArrayList<>();
		}
	}
	
}
