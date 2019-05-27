package com.dpc.framework.common.beans;


public enum ErrorCode {
	ERROR("-1", "默认异常");
	private String code;
	private String name;

	ErrorCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
