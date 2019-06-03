package com.dpc.aau.service;

import com.dpc.aau.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID extends Serializable> implements IService<T, ID> {

	@Autowired
	private BaseRepository<T, ID> repository;

	@Transactional(readOnly = true)
	@Override
	public List<T> selectAll() {
		return (List<T>) repository.findAll((Sort) null);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<T> selectAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isExist(ID id, String paramName, Object paramValue) {
		Long count = repository.count((root, criteriaQuery, criteriaBuilder) -> {
			Predicate p1 = criteriaBuilder.notEqual(root.get("id"), id);
			Predicate p2 = criteriaBuilder.equal(root.get(paramName), paramValue);
			return criteriaBuilder.and(p1, p2);
		});

		return count > 0;
	}

	@Transactional
	@Override
	public void save(T t) {
		repository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public List<T> selectBy(String paramName, Object paramValue) {
		return repository.findAll((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(paramName)
				, paramValue));
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(ID id) {
		return repository.existsById(id);
	}

	@Override
	public T findOne(ID id) {
		Optional<T> optional = repository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
}
