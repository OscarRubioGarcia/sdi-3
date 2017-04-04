package com.sdi.business.impl.classes.category;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Category;
import com.sdi.persistence.CategoryDao;
import com.sdi.persistence.exception.PersistenceException;

public class CategoryAlta {
	
	public void save(Category cat) throws EntityAlreadyExistsException {
		CategoryDao dao = Factories.persistence.createCategoryDao();
		try {
			dao.forceSave(cat);
		}
		catch (PersistenceException ex) {
			throw new EntityAlreadyExistsException("Categoria ya existente " + cat, ex);
		}
	}

}
