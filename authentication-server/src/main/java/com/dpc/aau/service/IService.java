package com.dpc.aau.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface IService<T, ID extends Serializable> {

	/**
	 * 查询所有记录
	 *
	 * @return List
	 */
	List<T> selectAll();

	/**
	 * 分页查询所有记录
	 *
	 * @return Page
	 */
	Page<T> selectAll(Pageable pageable);

	/**
	 * 判断记录是否已存在
	 *
	 * @param id         id
	 * @param paramName  属性名
	 * @param paramValue 属性值
	 * @return boolean
	 */
	boolean isExist(ID id, String paramName, Object paramValue);

	/**
	 * 保存或修改记录
	 *
	 * @param t 记录
	 */
	void save(T t);

	/**
	 * 根据属性查询记录
	 *
	 * @param paramName  属性名
	 * @param paramValue 属性值
	 * @return List
	 */
	List<T> selectBy(String paramName, Object paramValue);

	/**
	 * 记录是否存在
	 *
	 * @param id key
	 */
	boolean exists(ID id);

	/**
	 * 根据Id查询
	 *
	 * @param id id
	 * @return T
	 */
	T findOne(ID id);

	/**
	 * 根据Id删除
	 *
	 * @param id id
	 */
	void deleteById(ID id);
}
