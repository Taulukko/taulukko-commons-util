package com.taulukko.commons.util.metrics;

import com.taulukko.commons.TaulukkoException;
import com.taulukko.commons.util.reflection.EReflectionUtils;

public class FunctionPreToString<T>  {

	private T o = null;

	public FunctionPreToString(T o) {
		this.o = o;
	}
	
	public T getPatern()
	{
		return o;
	}

	public String toString() {
		String toString = o.toString();

		try {
			String functionPreName = EReflectionUtils.getCaller(3);

			return functionPreName + " : " + toString;
		} catch (TaulukkoException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
