package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.business.impl.classes.tasks.TasksAlta;
import com.sdi.business.impl.classes.tasks.TasksBaja;
import com.sdi.business.impl.classes.tasks.TasksBuscar;
import com.sdi.business.impl.classes.tasks.TasksListado;
import com.sdi.business.impl.classes.tasks.TasksUpdate;
import com.sdi.business.integration.*;
import com.sdi.model.Task;

/**
 * Session Bean implementation class EjbTaskService
 */
@Stateless
@WebService(name="TaskService")
public class EjbTaskService implements RemoteTaskService, LocalTaskService {

	Auditor audit = new AuditorEJB();
	
    public EjbTaskService() {
    }
    
    @Override
	public List<Task> getTareasByUserId(Long id) throws Exception {
		return new TasksListado().getTasksByUserId(id);
	}

	@Override
	public List<Task> getTareasByUserIdFinished(Long id) throws Exception {
		return new TasksListado().getTasksByUserIdFinished(id);
	}
	
	@Override
	public List<Task> getAllTasksByUserId(Long id) throws Exception {
		return new TasksListado().getAllTasksByUserId(id);
	}

	@Override
	public List<Task> getTareasByUserIdToday(Long id) throws Exception {
		audit.audit("getTasksByUserIdToday( " + id + " )");
		return new TasksListado().getTasksByUserIdToday(id);
	}

	@Override
	public List<Task> getTareasByUserIdThisWeek(Long id) throws Exception {
		return new TasksListado().getTasksByUserIdWeek(id);
	}
	
	@Override
	public List<Task> getTareasByUserIdInbox(Long id) throws Exception {
		audit.audit("getTasksByUserIdInbox( " + id + " )");
		return new TasksListado().getTasksByUserIdInbox(id);
	}
	
	@Override
	public List<Task> getFinishedTareasByUserIdInbox(Long id) throws Exception {
		return new TasksListado().getFinishedTasksByUserIdInbox(id);
	}

	@Override
	public Task findById(Long id) throws EntityNotFoundException {
		return new TasksBuscar().getTaskById(id);
	}

	@Override
	public void saveTarea(Task tarea) throws EntityAlreadyExistsException, EntityNotFoundException {
		new TasksAlta().save(tarea);
	}

	@Override
	public void updateTarea(Task tarea) throws EntityNotFoundException {
		new TasksUpdate().update(tarea);
	}

	@Override
	public void deleteTarea(Long id) throws EntityNotFoundException {
		new TasksBaja().delete(id);
	}

	@Override
	public List<Task> getAllTasks() throws Exception {
		return new TasksListado().getAllTasks();
	}

	@Override
	public void deleteTareaByUserId(Long id) throws EntityNotFoundException {
		new TasksBaja().deleteByUserId(id);
	}

}
