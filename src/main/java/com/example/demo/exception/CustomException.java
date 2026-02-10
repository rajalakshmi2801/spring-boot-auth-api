package com.example.demo.exception;

public class CustomException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int code;

    public CustomException(String message) {
        super(message);
        this.code = 200; // or 200 if you want
    }

    public int getCode() {
        return code;
    }
}
