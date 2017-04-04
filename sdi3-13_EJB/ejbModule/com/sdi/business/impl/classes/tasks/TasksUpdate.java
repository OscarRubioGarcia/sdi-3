package com.sdi.business.impl.classes.tasks;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.exception.PersistenceException;

public class TasksUpdate {

	public void update(Task tarea) throws EntityNotFoundException {
		TaskDao dao = Factories.persistence.createTareaDao();
		try {
			dao.update(tarea);
		} catch (PersistenceException ex) {
			throw new EntityNotFoundException("Task no actualizada " + tarea, ex);		
		}
	}

}
