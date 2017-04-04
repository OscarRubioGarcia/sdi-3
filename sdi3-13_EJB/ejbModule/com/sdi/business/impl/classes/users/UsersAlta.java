package com.sdi.business.impl.classes.users;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class UsersAlta {

	public Long save(User user) throws EntityAlreadyExistsException {
		UserDao dao = Factories.persistence.createUserDao();
		try {
			return dao.save(user);
		}
		catch (PersistenceException | AlreadyPersistedException ex) {
			throw new PersistenceException("Usuario ya existe " + user, ex);
		}
	}

	public int forceSave(User user) throws EntityAlreadyExistsException {
		UserDao dao = Factories.persistence.createUserDao();
		try {
			return dao.forceSave(user);
		}
		catch (PersistenceException ex) {
			throw new PersistenceException("Usuario ya existe " + user, ex);
		}
	}
}

