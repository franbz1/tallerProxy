package com.proxy.tallerProxy.api.exception;

public class QuotaExceededException extends RuntimeException {

	public QuotaExceededException(String message) {
		super(message);
	}
}
