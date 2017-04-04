package com.sdi.persistence;

import java.util.List;

import com.sdi.model.User;
import com.sdi.persistence.exception.AlreadyPersistedException;


public interface UserDao{

	User findById(Long id);
	User findByLogin(String login);
	User findByLoginAndPassword(String login, String password);
	void enableUser(Long id);
	void disableUser(Long id);
	int delete(Long id);
	Long save(User u) throws AlreadyPersistedException;
	List<User> findAll();
	int update(User u);
	User findLoggableByLoginAndPassword(String login, String password);
	int forceSave(User dto);
}
