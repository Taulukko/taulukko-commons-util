package com.taulukko.commons.util.struct;





public interface EIterator<V>

{

	public abstract boolean hasNext();



	public abstract void remove();



	public abstract V next() ;	

}

