package com.sdi.business.impl.classes.users;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;
import com.sdi.persistence.exception.PersistenceException;

public class UsersUpdate {

	public void update(User user) throws EntityNotFoundException {
		UserDao dao = Factories.persistence.createUserDao();
		try {
			dao.update(user);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("User no actualizado " + user, ex);
		}
	}
	
	public void enable(Long id) throws EntityNotFoundException{
		UserDao dao = Factories.persistence.createUserDao();
		try {
			dao.enableUser(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("User no habilitado " + id, ex);
		}
	}
	
	public void disable(Long id) throws EntityNotFoundException{
		UserDao dao = Factories.persistence.createUserDao();
		try {
			dao.disableUser(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("User no deshabilitado " + id, ex);
		}
	}

	public void delete(Long id) throws EntityNotFoundException {
		UserDao dao = Factories.persistence.createUserDao();
		try {
			dao.delete(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("User no eliminado " + id, ex);
		}		
	}
}
