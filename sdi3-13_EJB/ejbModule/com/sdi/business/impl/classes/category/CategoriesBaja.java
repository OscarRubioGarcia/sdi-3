package com.sdi.business.impl.classes.category;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.CategoryDao;

public class CategoriesBaja {

	public int deleteAllByUserId(Long id) throws EntityNotFoundException {
		CategoryDao dao = Factories.persistence.createCategoryDao();
		return dao.deleteAllFromUserId(id);
	}

	
}
