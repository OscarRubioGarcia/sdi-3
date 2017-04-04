package com.sdi.business.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sdi.business.CategoriesService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TasksService;
import com.sdi.business.UsersService;

public class LocalEjbServicesLocator implements ServicesFactory {
	
	private static final String TASKS_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-13/"
			+ "sdi3-13_EJB/"
			+ "EjbTaskService!"
			+ "com.sdi.business.impl.LocalTaskService";
	
	private static final String CATEGORYS_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-13/"
			+ "sdi3-13_EJB/"
			+ "EjbCategoryService!"
			+ "com.sdi.business.impl.LocalCategoryService";
	
	private static final String USERS_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-13/"
			+ "sdi3-13_EJB/"
			+ "EjbUserService!"
			+ "com.sdi.business.impl.LocalUserService";
	
	@Override
	public TasksService getTaskService() {
		try {
			Context ctx = new InitialContext();
			return (TasksService) ctx.lookup(TASKS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public UsersService getUserService() {
		try {
			Context ctx = new InitialContext();
			return (UsersService) ctx.lookup(USERS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public CategoriesService getCategoryService() {
		try {
			Context ctx = new InitialContext();
			return (CategoriesService) ctx.lookup(CATEGORYS_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}



}
