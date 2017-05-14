package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.User;

public interface UsersService {

	public List<User> listUsers() throws BusinessException;
	public void enableUser(Long id) throws BusinessException, EntityNotFoundException;
	public void disableUser(Long id) throws BusinessException, EntityNotFoundException;
	public Long save(User user) throws BusinessException, EntityAlreadyExistsException;
	public int forceSave(User user) throws BusinessException, EntityAlreadyExistsException;
	
	public List<User> allUsersInfoAndTasks();
	
	public void update(User user) throws EntityNotFoundException;
	public User findAdmin(String name, String pass) throws EntityNotFoundException;
	public User find(String login, String password) throws EntityNotFoundException;
	public void deleteUser(Long id) throws EntityNotFoundException;
	User findLoggable(String login, String password)
			throws EntityNotFoundException;
	public User exists(String login);

}
