package com.dpc.aau.service.impl;

import com.dpc.aau.model.User;
import com.dpc.aau.repository.UserRepository;
import com.dpc.aau.service.BaseService;
import com.dpc.aau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class UserServiceImpl extends BaseService<User, Long> implements UserService {

	@Autowired
	private UserRepository userRepository;
}
