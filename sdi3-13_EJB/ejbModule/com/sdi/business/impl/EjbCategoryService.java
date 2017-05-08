package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.business.impl.classes.category.CategoriesBaja;
import com.sdi.business.impl.classes.category.CategoriesFind;
import com.sdi.business.impl.classes.category.CategoryAlta;
import com.sdi.model.Category;

/**
 * Session Bean implementation class EjbCategoryService
 */
@Stateless
@WebService(name="CategoryService")
public class EjbCategoryService implements RemoteCategoryService, LocalCategoryService {

    public EjbCategoryService() {
    }
    
    @Override
	public int deleteAllByUserId(Long id) throws BusinessException, EntityNotFoundException {
		return new CategoriesBaja().deleteAllByUserId(id);
	}
	
	@Override
	public List<Category> getAllCategoriesForUser(Long id) throws BusinessException, EntityNotFoundException {
		return new CategoriesFind().findAllCategoriesForUser(id);
	}
	
	@Override
	public void save(Category cat) throws BusinessException, EntityNotFoundException, EntityAlreadyExistsException {
		new CategoryAlta().save(cat);
	}

}
