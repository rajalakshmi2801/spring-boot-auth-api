package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    public ApiResponse() {
		// TODO Auto-generated constructor stub
	}
	private int code;
    private String message;
    private T data;
}
