package com.sdi.business.impl.classes.users;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;

public class UsersListado {

	public List<User> getAllUsers() {
		UserDao dao = Factories.persistence.createUserDao();
		return  dao.findAll();
	}

	public List<User> getAllUsersAndTasks() {
		UserDao dao = Factories.persistence.createUserDao();
		return dao.findAllAndTasks();
	}
}
