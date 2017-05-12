package com.sdi.rest;

import java.util.List;

import com.sdi.business.CategoriesService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Category;

public class CategoryServiceRestImpl implements CategoryServiceRest{

	CategoriesService service = Factories.services.getCategoryService();
	
	@Override
	public int deleteAllByUserId(Long id) throws BusinessException,
			EntityNotFoundException {
		return service.deleteAllByUserId(id);
	}

	@Override
	public List<Category> getAllCategoriesForUser(Long id)
			throws BusinessException, EntityNotFoundException {
		return service.getAllCategoriesForUser(id);
	}

	@Override
	public void save(Category cat) throws BusinessException,
			EntityNotFoundException, EntityAlreadyExistsException {
		service.save(cat);
		
	}

}
