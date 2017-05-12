package com.sdi.rest;

import java.util.List;

import com.sdi.business.UsersService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class UsersServiceRestImpl implements UsersServiceRest{

	UsersService service = Factories.services.getUserService();
	
	@Override
	public List<User> listUsers() throws BusinessException {
		return service.listUsers();
	}

	@Override
	public void enableUser(Long id) throws BusinessException,
			EntityNotFoundException {
		service.enableUser(id);
		
	}

	@Override
	public void disableUser(Long id) throws BusinessException,
			EntityNotFoundException {
		service.disableUser(id);
	}

	@Override
	public Long save(User user) throws BusinessException,
			EntityAlreadyExistsException {
		return service.save(user);
	}

	@Override
	public int forceSave(User user) throws BusinessException,
			EntityAlreadyExistsException {
		return service.forceSave(user);
	}

	@Override
	public void update(User user) throws EntityNotFoundException {
		service.update(user);
	}

	@Override
	public User findAdmin(String name, String pass)
			throws EntityNotFoundException {
		return service.findAdmin(name, pass);
	}

	@Override
	public User find(String login, String password)
			throws EntityNotFoundException {
		return service.find(login, password);
	}

	@Override
	public void deleteUser(Long id) throws EntityNotFoundException {
		service.deleteUser(id);
	}

	@Override
	public User findLoggable(String login, String password)
			throws EntityNotFoundException {
		return service.findLoggable(login, password);
	}

	@Override
	public User exists(String login) {
		return service.exists(login);
	}

}
