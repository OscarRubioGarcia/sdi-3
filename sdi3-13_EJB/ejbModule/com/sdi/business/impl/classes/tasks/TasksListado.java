package com.sdi.business.impl.classes.tasks;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.persistence.TaskDao;

/**
 * Esta clase pertenece a la capa de persistencia y ejecuta un proceso 
 * de negocio.
 * 
 * Si el problema a resolver fuese m??????s complejo habr?????? otras muchas clases de 
 * este estilo en esta capa. 
 * 
 * Las clases que forman la capa de negocio pueden necesitar acceder a la capa
 * de persistencia para resolver su cometido. Esta oculta los detalles de la 
 * tecnolog??????a de persistencia ofreciendo m??????todos del estilo: crear, borrer, 
 * actualizar y diversas consultas.
 * 
 */
public class TasksListado {

	public List<Task> getTasksByUserId(Long id) throws Exception {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findInboxTasksByUserId(id);
	}

	public List<Task> getTasksByUserIdFinished(Long id) {	
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findFinishedTasksInboxByUserId(id);
	}

	public List<Task> getAllTasksByUserId(Long id) {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findByUserId(id);
	}

	public List<Task> getTasksByUserIdToday(Long id) {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findTodayTasksByUserId(id);
	}

	public List<Task> getTasksByUserIdWeek(Long id) {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findWeekTasksByUserId(id);
	}
	
	public List<Task> getTasksByUserIdInbox(Long id) {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findInboxTasksByUserId(id);
	}
	
	public List<Task> getFinishedTasksByUserIdInbox(Long id) {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findFinishedTasksInboxByUserId(id);
	}

	public List<Task> getAllTasks() {
		TaskDao dao = Factories.persistence.createTareaDao();
		return  dao.findAll();
	}
}
