package com.dpc.aau.controller;

import com.dpc.aau.model.User;
import com.dpc.aau.service.UserService;
import com.dpc.framework.common.beans.PageBean;
import com.dpc.framework.common.beans.PageQo;
import com.dpc.framework.common.exception.EntityNotFoudException;
import com.dpc.framework.common.exception.ErrorInfoException;
import com.dpc.framework.common.utils.BeanUtil;
import com.dpc.framework.common.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author dongpeichao
 * @version v1.0
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@GetMapping
	@ApiOperation("根据用户Id获取用户")
	public PageBean<User> get(PageQo param) {
		Page<User> page = userService.selectAll(PageRequest.of(param.getPage(), param.getPageSize()));
		return new PageBean(page.getTotalElements(), page.getContent());
	}

	@GetMapping("/{userId}")
	@ApiOperation("根据用户Id获取用户")
	public User get(@PathVariable("userId") long userId) {
		User user = userService.findOne(userId);

		if (null == user) {
			throw new EntityNotFoudException("用户不存在");
		}

		return user;
	}

	@ApiOperation("新增用户")
	@PostMapping
	public void post(@RequestBody @Validated User user) {
		user.setId(IdUtil.getLongUUID());
		user.setInsertTime(new Date());
		userService.save(user);
	}

	@ApiOperation("修改用户")
	@PutMapping
	public void put(@RequestBody @Validated User user) {
		if (0 == user.getId()) {
			throw new ErrorInfoException("用户Id不合法");
		}

		User old = userService.findOne(user.getId());
		if (null == old) {
			throw new ErrorInfoException("用户不存在");
		}

		BeanUtil.copyNonNullProperties(user, old);
		userService.save(old);

	}

	@DeleteMapping("/{userId}")
	@ApiOperation("根据用户Id获取用户")
	public void delete(@PathVariable("userId") long userId) {
		userService.deleteById(userId);
	}
}
