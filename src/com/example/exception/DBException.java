package com.example.exception;

/**
 * 有关事务处理的自定义的数据库异常
 * @author Administrator
 */

public class DBException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DBException() {
		super();
	}
	public DBException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
}
