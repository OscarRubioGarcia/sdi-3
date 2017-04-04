package com.sdi.persistence.impl;


import com.sdi.persistence.impl.TaskDaoJdbcImpl;
import com.sdi.persistence.AlumnosDao;
import com.sdi.persistence.CategoryDao;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.UserDao;

/**
 * Implementaci??????n de la factoria que devuelve implementaci??????n de la capa
 * de persistencia con Jdbc 
 * 
 * @author alb
 *
 */
public class SimplePersistenceFactory implements PersistenceFactory {

	@Override
	public AlumnosDao createAlumnoDao() {
		return new AlumnoJdbcDAO();
	}

	@Override
	public TaskDao createTareaDao() {
		return new TaskDaoJdbcImpl();
	}

	@Override
	public CategoryDao createCategoryDao() {
		return new CategoryDaoJdbcImpl();
	}

	@Override
	public UserDao createUserDao() {
		return new UserDaoJdbcImpl();
	}

}
