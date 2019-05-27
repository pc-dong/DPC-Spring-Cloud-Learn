package com.dpc.framework.common.beans;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
	private long total;

	private List<T> data;

	public PageBean(){

	}

	public PageBean(long total, List<T> content) {
		this.total = total;
		this.data = content;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
