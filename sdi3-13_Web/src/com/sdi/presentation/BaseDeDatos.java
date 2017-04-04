package com.sdi.presentation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sdi.business.CategoriesService;
import com.sdi.business.TasksService;
import com.sdi.business.UsersService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Category;
import com.sdi.model.Task;
import com.sdi.model.User;

public class BaseDeDatos {

	/**
	 * borra toda la base de datos menos lo relativo al administrador admin1
	 */
	public static void borraTodo(){
		BeanUsers buser = new BeanUsers();
		buser.listado();
		List<User> usuarios = buser.getUsersList();
		
		for (User user : usuarios)
			if (!"admin1".equals(user.getLogin())){
				buser.preBaja(user);
				buser.baja();}
	}
	
	/**
	 * crea en la base 3 usuarios genéricos 
	 */
	public static String iniciaUsuarios(){
		UsersService service;
		try{
			service = Factories.services.getUserService();
			
			service.forceSave(new User("user1", "user1@user.com", "user1").setId((long) 10));
			service.forceSave(new User("user2", "user2@user.com", "user2").setId((long) 11));
			service.forceSave(new User("user3", "user3@user.com", "user3").setId((long) 12));

			return "exito";
		} catch(Exception e){
			return "error";
		}
	}
	
	/**
	 * crea en la base tareas genéricas para los 3 usuarios genéricos 
	 */
	public static String iniciaTareas(){
		TasksService service;
		try{
			service = Factories.services.getTaskService();
			
			//User 10
			//Tareas Inbox del dia
			service.saveTarea(new Task("Tarea 1", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 2", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 3", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 4", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 5", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 6", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 7", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 8", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 9", " ", new Date(), new Date(), null, null, (long)10));
			service.saveTarea(new Task("Tarea 10", " ", new Date(), new Date(), null, null, (long)10));
			
			//User 11
			//Tareas Inbox del dia
			service.saveTarea(new Task("Tarea 1", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 2", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 3", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 4", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 5", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 6", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 7", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 8", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 9", " ", new Date(), new Date(), null, null, (long)11));
			service.saveTarea(new Task("Tarea 10", " ", new Date(), new Date(), null, null, (long)11));
			
			//User 12
			//Tareas Inbox del dia
			service.saveTarea(new Task("Tarea 1", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 2", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 3", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 4", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 5", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 6", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 7", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 8", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 9", " ", new Date(), new Date(), null, null, (long)12));
			service.saveTarea(new Task("Tarea 10", " ", new Date(), new Date(), null, null, (long)12));
			
			//User 10
			service.saveTarea(new Task("Tarea 11", " ", new Date(), yesterday(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 12", " ", new Date(), yesterday(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 13", " ", new Date(), yesterday(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 14", " ", new Date(), yesterday(), null, (long) 2, (long)10));
			service.saveTarea(new Task("Tarea 15", " ", new Date(), yesterday(), null, (long) 2, (long)10));
			service.saveTarea(new Task("Tarea 16", " ", new Date(), yesterday(), null, (long) 2, (long)10));
			service.saveTarea(new Task("Tarea 17", " ", new Date(), yesterday(), null, (long) 3, (long)10));
			service.saveTarea(new Task("Tarea 18", " ", new Date(), yesterday(), null, (long) 3, (long)10));
			service.saveTarea(new Task("Tarea 19", " ", new Date(), yesterday(), null, (long) 3, (long)10));
			service.saveTarea(new Task("Tarea 20", " ", new Date(), yesterday(), null, (long) 3, (long)10));
			
			//User 10
			service.saveTarea(new Task("Tarea 21", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 22", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 23", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 24", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 25", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 26", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 27", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 28", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 29", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			service.saveTarea(new Task("Tarea 30", " ", new Date(), thisWeek(), null, (long) 1, (long)10));
			
			//User 11
			service.saveTarea(new Task("Tarea 11", " ", new Date(), yesterday(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 12", " ", new Date(), yesterday(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 13", " ", new Date(), yesterday(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 14", " ", new Date(), yesterday(), null, (long) 5, (long)11));
			service.saveTarea(new Task("Tarea 15", " ", new Date(), yesterday(), null, (long) 5, (long)11));
			service.saveTarea(new Task("Tarea 16", " ", new Date(), yesterday(), null, (long) 5, (long)11));
			service.saveTarea(new Task("Tarea 17", " ", new Date(), yesterday(), null, (long) 6, (long)11));
			service.saveTarea(new Task("Tarea 18", " ", new Date(), yesterday(), null, (long) 6, (long)11));
			service.saveTarea(new Task("Tarea 19", " ", new Date(), yesterday(), null, (long) 6, (long)11));
			service.saveTarea(new Task("Tarea 20", " ", new Date(), yesterday(), null, (long) 6, (long)11));
			
			//User 11
			service.saveTarea(new Task("Tarea 21", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 22", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 23", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 24", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 25", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 26", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 27", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 28", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 29", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			service.saveTarea(new Task("Tarea 30", " ", new Date(), thisWeek(), null, (long) 4, (long)11));
			
			//User 12
			service.saveTarea(new Task("Tarea 11", " ", new Date(), yesterday(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 12", " ", new Date(), yesterday(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 13", " ", new Date(), yesterday(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 14", " ", new Date(), yesterday(), null, (long) 8, (long)12));
			service.saveTarea(new Task("Tarea 15", " ", new Date(), yesterday(), null, (long) 8, (long)12));
			service.saveTarea(new Task("Tarea 16", " ", new Date(), yesterday(), null, (long) 8, (long)12));
			service.saveTarea(new Task("Tarea 17", " ", new Date(), yesterday(), null, (long) 9, (long)12));
			service.saveTarea(new Task("Tarea 18", " ", new Date(), yesterday(), null, (long) 9, (long)12));
			service.saveTarea(new Task("Tarea 19", " ", new Date(), yesterday(), null, (long) 9, (long)12));
			service.saveTarea(new Task("Tarea 20", " ", new Date(), yesterday(), null, (long) 9, (long)12));
			
			//User 12
			service.saveTarea(new Task("Tarea 21", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 22", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 23", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 24", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 25", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 26", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 27", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 28", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 29", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			service.saveTarea(new Task("Tarea 30", " ", new Date(), thisWeek(), null, (long) 7, (long)12));
			
			return "exito";
		} catch(Exception e){
			return "error";
		}
	}
	
	private static Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}
	
	private static Date thisWeek() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, +3);
	    return cal.getTime();
	}
	
	/**
	 * crea en la base categorías genéricas para los 3 usuarios genéricos
	 */
	public static String iniciaCategorias(){
		CategoriesService service;
		try{
			service = Factories.services.getCategoryService();
			
			//User 10
			service.save(new Category((long)1, "categoria1", (long) 10));
			service.save(new Category((long)2, "categoria2", (long) 10));
			service.save(new Category((long)3, "categoria3", (long) 10));
			
			//User 11
			service.save(new Category((long)4, "categoria1", (long) 11));
			service.save(new Category((long)5, "categoria2", (long) 11));
			service.save(new Category((long)6, "categoria3", (long) 11));
			
			//User 12
			service.save(new Category((long)7, "categoria1", (long) 12));
			service.save(new Category((long)8, "categoria2", (long) 12));
			service.save(new Category((long)9, "categoria3", (long) 12));
			
			return "exito";
		} catch(Exception e){
			return "error";
		}
	}
}
