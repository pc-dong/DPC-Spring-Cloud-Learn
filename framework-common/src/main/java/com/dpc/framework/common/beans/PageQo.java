package com.dpc.framework.common.beans;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PageQo {
	@ApiParam(value = "页码", defaultValue = "0")
	protected Integer page = 0;

	@ApiParam(value = "每页记录数", defaultValue = "20")
	protected Integer pageSize = 20;

	@ApiParam(value = "是否计算总数", defaultValue = "true")
	protected boolean count = true;
}

