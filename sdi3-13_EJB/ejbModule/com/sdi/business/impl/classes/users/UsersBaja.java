package com.sdi.business.impl.classes.users;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.PersistenceException;

public class UsersBaja {

	public void delete(Long id) throws EntityNotFoundException {
		UserDao dao = Factories.persistence.createUserDao();
		try {
			dao.delete(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("Usuario no eliminado " + id, ex);
		}
	}
}

