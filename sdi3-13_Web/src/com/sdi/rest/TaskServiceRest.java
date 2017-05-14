package com.sdi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.Task;

@Path("/TaskServiceRs")
public interface TaskServiceRest {
	
	@GET 
	@Path("/AllTasksByUserId/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getAllTasksByUserId(@PathParam("id") Long id) throws Exception;
	
	@GET 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getAllTasks() throws Exception;
	
	@GET 
	@Path("/TareasByUserId/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getTareasByUserId(@PathParam("id") Long id) throws Exception;
	
	@GET 
	@Path("/TareasByUserIdFinished/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getTareasByUserIdFinished(@PathParam("id") Long id) throws Exception;
	
	@GET 
	@Path("/LateAndPendingTasksById/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Task> getLateAndPendingTasksById(@PathParam("id") Long id) throws Exception;
	
	@GET 
	@Path("/findTaskById/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Task findById(@PathParam("id") Long id) throws EntityNotFoundException;
	
	@PUT 
	@Path("/save/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void saveTarea(Task tarea) throws EntityAlreadyExistsException, EntityNotFoundException;
	
	@POST 
	@Path("/update/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void updateTarea(Task tarea) throws EntityNotFoundException;
	
	@DELETE
	@Path("/deleteTarea/{id}")
	void deleteTarea(@PathParam("id") Long id) throws EntityNotFoundException;
	
	
	@DELETE
	@Path("/deleteTareaByUserId/{id}")
	void deleteTareaByUserId(@PathParam("id") Long id) throws EntityNotFoundException;

}
