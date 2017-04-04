package com.sdi.business;

public interface ServicesFactory {
	
	TasksService getTaskService();
	
	UsersService getUserService();

	CategoriesService getCategoryService();
}