package com.sdi.rest;

import java.util.List;

import com.sdi.business.TasksService;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;

public class TaskServiceRestImpl implements TaskServiceRest{

	TasksService service = Factories.services.getTaskService();
	
	@Override
	public List<Task> getAllTasksByUserId(Long id) throws Exception {
		
		return service.getAllTasksByUserId(id);
	}

	@Override
	public List<Task> getAllTasks() throws Exception {
		
		return service.getAllTasks();
	}

	@Override
	public List<Task> getTareasByUserId(Long id) throws Exception {
		
		return service.getTareasByUserId(id);
	}

	@Override
	public List<Task> getTareasByUserIdFinished(Long id) throws Exception {
		
		return service.getTareasByUserIdFinished(id);
	}

	@Override
	public Task findById(Long id) throws EntityNotFoundException {
		
		return service.findById(id);
	}

	@Override
	public void saveTarea(Task tarea) throws EntityAlreadyExistsException,
			EntityNotFoundException {
		service.saveTarea(tarea);
		
	}

	@Override
	public void updateTarea(Task tarea) throws EntityNotFoundException {
		service.updateTarea(tarea);
		
	}

	@Override
	public void deleteTarea(Long id) throws EntityNotFoundException {
		service.deleteTarea(id);
		
	}

	@Override
	public void deleteTareaByUserId(Long id) throws EntityNotFoundException {
		service.deleteTareaByUserId(id);
		
	}

}
