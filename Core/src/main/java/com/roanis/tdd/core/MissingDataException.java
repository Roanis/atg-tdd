package com.roanis.tdd.core;

public class MissingDataException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MissingDataException() {
	}
	
	public MissingDataException(String str){
		super(str);
	}
	
	public MissingDataException(Throwable sourceException){
		super(sourceException);
	}
	
	public MissingDataException(String str, Throwable sourceException){
		super(str, sourceException);
	}

}
