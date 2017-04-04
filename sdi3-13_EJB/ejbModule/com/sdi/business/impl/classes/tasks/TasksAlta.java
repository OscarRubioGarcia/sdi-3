package com.sdi.business.impl.classes.tasks;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class TasksAlta {

	public void save(Task tarea) throws EntityAlreadyExistsException {
		TaskDao dao = Factories.persistence.createTareaDao();
		try {
			dao.save(tarea);
		}
		catch (PersistenceException | AlreadyPersistedException ex) {
			throw new EntityAlreadyExistsException("tarea ya existe " + tarea, ex);
		}
	}

}
