package com.dpc.aau.service;

import com.dpc.aau.model.User;

/**
 * @author dongpeichao
 * @version v1.0
 * @email dongpeichao@boco.com.cn
 * @time 2019年05月27日11:25
 * @modify <BR/>
 * 修改内容：<BR/>
 * 修改人员：<BR/>
 * 修改时间：<BR/>
 */
public interface UserService extends IService<User, Long> {
	@Override
	default void deleteById(Long aLong) {

	}
}
