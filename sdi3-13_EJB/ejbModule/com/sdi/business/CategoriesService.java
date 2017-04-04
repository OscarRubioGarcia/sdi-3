package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.Category;

public interface CategoriesService {

	public int deleteAllByUserId(Long id) throws BusinessException, EntityNotFoundException;

	List<Category> getAllCategoriesForUser(Long id) throws BusinessException,
			EntityNotFoundException;

	void save(Category cat) throws BusinessException, EntityNotFoundException, EntityAlreadyExistsException;
}
