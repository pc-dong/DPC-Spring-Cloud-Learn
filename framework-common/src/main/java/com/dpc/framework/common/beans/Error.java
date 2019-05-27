package com.dpc.framework.common.beans;

import lombok.Data;

/**
 * @author dongpeichao
 * @version v1.0
 * @email dongpeichao@boco.com.cn
 * @time 2019年05月23日17:57
 * @modify <BR/>
 * 修改内容：<BR/>
 * 修改人员：<BR/>
 * 修改时间：<BR/>
 */
@Data
public class Error {
	private String code = ErrorCode.ERROR.getCode();
	private String msg;

	public static Error error(String msg) {
		Error response = new Error();
		response.setMsg(msg);
		return response;
	}
}
