package com.sdi.business.impl.classes.users;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;

public class UsersBuscar {

	public User find(String name, String pass) throws EntityNotFoundException {
		UserDao dao = Factories.persistence.createUserDao();
		User u = dao.findByLoginAndPassword(name, pass);
		
		return u;
	}
	
	public User findLoggable(String name, String pass) throws EntityNotFoundException{
		UserDao dao = Factories.persistence.createUserDao();
		User u = dao.findLoggableByLoginAndPassword(name, pass);
		
		
		return u;
	}
	
	public User findAdmin(String name,String pass) throws EntityNotFoundException{
		User u = findLoggable(name,pass); 
		return u.getIsAdmin()? u:null;
	}

	public User exists(String login) {
		UserDao dao = Factories.persistence.createUserDao();
		return dao.findByLogin(login);
	}
}

