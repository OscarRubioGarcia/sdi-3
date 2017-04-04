package com.sdi.business.impl.classes.category;

import java.util.List;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Category;
import com.sdi.persistence.CategoryDao;

public class CategoriesFind {

	public List<Category> findAllCategoriesForUser(Long id) throws EntityNotFoundException {
		CategoryDao dao = Factories.persistence.createCategoryDao();
		return dao.findByUserId(id);
	}

}
