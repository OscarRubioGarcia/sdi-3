package com.sdi.business.impl.classes.tasks;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.persistence.TaskDao;
import com.sdi.persistence.exception.PersistenceException;

public class TasksBaja {

	public void delete(Long id) throws EntityNotFoundException {
		TaskDao dao = Factories.persistence.createTareaDao();
		try {
			dao.delete(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("Tarea no eliminada " + id, ex);
		}
	}

	public void deleteByUserId(Long id) throws EntityNotFoundException {
		TaskDao dao = Factories.persistence.createTareaDao();
		try {
			dao.deleteAllFromUserId(id);
		}
		catch (PersistenceException ex) {
			throw new EntityNotFoundException("Tarea no eliminada " + id, ex);
		}
	}
}
