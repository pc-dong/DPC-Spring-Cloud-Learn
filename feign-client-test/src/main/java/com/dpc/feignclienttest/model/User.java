package com.dpc.feignclienttest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @author dongpeichao
 */
public class User {
	@ApiModelProperty("主键")
	private long id;
	@ApiModelProperty("用户名")
	private String username;
	@ApiModelProperty("真实姓名")
	private String name;
	@ApiModelProperty("插入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertTime;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("部门")
	private String department;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id &&
				Objects.equals(username, user.username) &&
				Objects.equals(name, user.name) &&
				Objects.equals(insertTime, user.insertTime) &&
				Objects.equals(password, user.password) &&
				Objects.equals(department, user.department);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, username, name, insertTime, password, department);
	}
}
