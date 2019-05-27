package com.dpc.framework.common.exception;

/**
 * 自定义异常
 *
 * @author dongpeichao
 * @version v1.0
 * @email dongpeichao@boco.com.cn
 * @time 2019年05月23日9:57
 * @modify <BR/>
 * 修改内容：<BR/>
 * 修改人员：<BR/>
 * 修改时间：<BR/>
 */
public class EntityNotFoudException extends RuntimeException{
	public EntityNotFoudException(String message) {
		super(message);
	}

	public EntityNotFoudException() {
		super();
	}
}
