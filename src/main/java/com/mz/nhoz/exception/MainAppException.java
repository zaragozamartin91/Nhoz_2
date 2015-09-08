package com.mz.nhoz.exception;


public class MainAppException extends Exception {

	public MainAppException(String msg) {
		super(msg);
	}

	public MainAppException(Exception e1) {
		super(e1);
	}

}
