package com.utils;

public class ServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerException(String exception) {
		super(exception);
	}

	public ServerException(String exception, Throwable throwable) {
		super(exception, throwable);
	}

}
