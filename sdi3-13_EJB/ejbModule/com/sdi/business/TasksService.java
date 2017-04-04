package com.sdi.business;

import java.util.List;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.Task;

/**
 * Este es el interfaz que ofrecer�� cualquier implementaci��n de la clase fachada.
 * 
 * Al separar la implementaci��n de la fachada de su interfaz se permite cambiar 
 * las implementaciones reales de la fachada. Esto es muy ��til cuando se necesita 
 * a��adir funcionalidad extra como acceso remoto, web services,
 * control de acceso, etc. Al hacerlo de esta forma esos cambios solo 
 * afectan a las factorias y no al contenido de las capas. Las factor��as, en
 * un desarrollo profesional, se configuran declarativamente (properties, xml, etc)
 * 
 * @author alb
 *
 */
public interface TasksService {

	List<Task> getAllTasksByUserId(Long id) throws Exception;
	List<Task> getAllTasks() throws Exception;
	List<Task> getTareasByUserId(Long id) throws Exception;
	List<Task> getTareasByUserIdFinished(Long id) throws Exception;
	List<Task> getTareasByUserIdToday(Long id) throws Exception;
	List<Task> getTareasByUserIdThisWeek(Long id) throws Exception;
	
	Task findById(Long id) throws EntityNotFoundException;
	void saveTarea(Task tarea) throws EntityAlreadyExistsException, EntityNotFoundException;
	void updateTarea(Task tarea) throws EntityNotFoundException;
	void deleteTarea(Long id) throws EntityNotFoundException;
	List<Task> getFinishedTareasByUserIdInbox(Long id) throws Exception;
	List<Task> getTareasByUserIdInbox(Long id) throws Exception;
	void deleteTareaByUserId(Long id) throws EntityNotFoundException;

}