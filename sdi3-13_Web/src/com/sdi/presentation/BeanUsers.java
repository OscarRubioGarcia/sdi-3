package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.CategoriesService;
import com.sdi.business.TasksService;
import com.sdi.business.UsersService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.types.UserStatus;

@ManagedBean(name = "userController")
@SessionScoped
public class BeanUsers implements Serializable {
	private static final long serialVersionUID = 55555L;

	@ManagedProperty(value = "#{user}")
	private BeanUser user;

	private User userr;
	
	public User getUserr() {
		return userr;
	}

	private String login="", password="";
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private User[] users = null;

	private List<User> usersList = null;
	private List<User> usersFiltrado = null;

	private Date currentDate;


	@PostConstruct
	public void init() {
		System.out.println("BeanTareas - PostConstruct");

		setCurrentDate(new Date());

		user = (BeanUser) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("user"));

		// si no existe lo creamos e inicializamos
		if (user == null) {
			System.out.println("BeanUser - No existía");
			user = new BeanUser();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("user", user);
		}
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanUsers - PreDestroy");
	}

	public User[] getUsers() {
		return (users);
	}

	public void setUser(BeanUser user) {
		this.user = user;
	}
	public void setUserr(User user) {
		//this.userr =  user;
	}
	public BeanUser getUser() {
		return user;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

	public void iniciaUser(ActionEvent event) {
		//FacesContext facesContext = FacesContext.getCurrentInstance();

		//ResourceBundle bundle = facesContext.getApplication()
		//		.getResourceBundle(facesContext, "msgs");
		
		user.setId(null);

		user.iniciaUser(event);

	}

	/**
	 * Muestra todos los usuarios registrados.
	 * SEGURIDAD: Si el usuario actual no es administrador, da error
	 * @return
	 */
	public String listado() {
		if (!((User)FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap().get("LOGGEDIN_USER")).getIsAdmin())
			return "error";
		
		UsersService service;
		try {
			service = Factories.services.getUserService();
			users = (User[]) service.listUsers().toArray(new User[0]);

			setUsersList(service.listUsers());

			return "exito"; // vamos a vista listadoUsers.xhtml

		} catch (Exception e) {
			return "error";
		}

	}

	
	public String preBaja(User user){
		this.userr=user;
		return "exito";
	}
	/**
	 * elimina a un usuario, sus tareas y sus categorías
	 */
	public String baja() {
		UsersService service;
		TasksService taskService;
		CategoriesService categoryService;
		
		try {
			taskService = Factories.services.getTaskService();
			taskService.deleteTareaByUserId(userr.getId());
			
			categoryService = Factories.services.getCategoryService();
			categoryService.deleteAllByUserId(userr.getId());
			
			service = Factories.services.getUserService();
			service.deleteUser(userr.getId());
			
			users = (User[]) service.listUsers().toArray(new User[0]);

			setUsersList(service.listUsers());

			userr = null;
			
			return "exito";

		} catch (Exception e) {
			return "error";
		}

	}

//	public String edit(User user) {
//		UsersService service;
//		try {
//			service = Factories.services.createUserService();
//			service.update(user);
//			return "exito";
//		} catch (Exception e) {
//			return "error";
//		}
//
//	}
	
	public String changeStatus(User userr){
		UsersService service;
		try{
			service = Factories.services.getUserService();
			if (userr.getStatus()==UserStatus.ENABLED)
				service.disableUser(userr.getId());
			else
				service.enableUser(userr.getId());
			
			users = (User[]) service.listUsers().toArray(new User[0]);

			setUsersList(service.listUsers());
			
			return "exito";
		} catch(Exception e){
			return "error";		
		}
	}
	
	
	public List<User> getUsersFiltrado() {
		return usersFiltrado;
	}

	public void setUsersFiltrado(List<User> usersFiltrado) {
		this.usersFiltrado = usersFiltrado;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}

}
