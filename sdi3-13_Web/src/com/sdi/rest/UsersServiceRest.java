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

import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.User;

@Path("/UsersServiceRs")
public interface UsersServiceRest {
	
	@GET 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<User> listUsers() throws BusinessException;
	
	@GET
	@Path("/enable/{id}")
	public void enableUser(@PathParam("id") Long id) throws BusinessException, EntityNotFoundException;
	
	@GET
	@Path("/disable/{id}")
	public void disableUser(@PathParam("id") Long id) throws BusinessException, EntityNotFoundException;
	
	@PUT 
	@Path("/save/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Long save(User user) throws BusinessException, EntityAlreadyExistsException;
	
	@PUT 
	@Path("/forcesave/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public int forceSave(User user) throws BusinessException, EntityAlreadyExistsException;
	
	@POST 
	@Path("/update/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void update(User user) throws EntityNotFoundException;
	
	@GET 
	@Path("/findAdmin/{id}/{pass}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User findAdmin(@PathParam("id") String name, @PathParam("pass") String pass) throws EntityNotFoundException;
	
	@GET 
	@Path("/find/{id}/{pass}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User find(@PathParam("id") String login, @PathParam("pass") String password) throws EntityNotFoundException;
	
	@DELETE
	@Path("/deleteUser/{id}")
	public void deleteUser(@PathParam("id") Long id) throws EntityNotFoundException;
	
	@GET 
	@Path("/findloggable/{id}/{pass}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	User findLoggable(@PathParam("id") String login, @PathParam("pass") String password) throws EntityNotFoundException;
	
	@GET 
	@Path("/exists/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public User exists(@PathParam("id") String login);
	
}
