package com.sdi.business.impl.classes.tasks;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.persistence.TaskDao;

public class TasksBuscar {

	public Task getTaskById(Long id) throws EntityNotFoundException {
		TaskDao dao = Factories.persistence.createTareaDao();
		Task t = dao.findById(id);
		if ( t == null) {
			throw new EntityNotFoundException("No se ha encontrado la tarea");
		}
		
		return t;
	}

}
