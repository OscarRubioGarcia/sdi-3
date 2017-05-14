package com.sdi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.Category;

@Path("/CategoryServiceRs")
public interface CategoryServiceRest {
	
	@DELETE
	@Path("/deleteAllByUserId/{id}")
	public int deleteAllByUserId(@PathParam("id") Long id) throws BusinessException, EntityNotFoundException;

	@GET 
	@Path("/find/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Category> getAllCategoriesForUser(@PathParam("id") Long id) throws BusinessException, EntityNotFoundException;

	@PUT 
	@Path("/save/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void save(Category cat) throws BusinessException, EntityNotFoundException, EntityAlreadyExistsException;
	
}
