package com.sdi.presentation;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import com.sdi.business.UsersService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

@ManagedBean(name = "logController")
@SessionScoped
public class BeanLog implements Serializable {
	private static final long serialVersionUID = 55555L;

	private String login="", password="", password2="", email="", message="nada";

	private User currentUser;
	
	public BeanLog() {
		System.out.println("BeanLogin - No existia");
	}
	
	/**
	 * establece el usuario en el mapa de sesión. Invalida la sesión anterior
	 * en el caso de que no se haya hecho ya.
	 * @return
	 */
	public String log(){
		UsersService service;
		try{
			service = Factories.services.getUserService();
			User localUser = service.findLoggable(login, password);
			if (localUser == null)
				return "error";
			
			setCurrentUser(localUser);
			
			// save user in session
			FacesContext.getCurrentInstance()
					.getExternalContext()
					.getSessionMap()
					.put("LOGGEDIN_USER", currentUser);
			
			return "exito";
		}catch(Exception e){
			return "error";
		}
	}

	/**
	 * cierra la sesión actual (invalidate)
	 */
	public String huir(){
		//Map<String, Object> session = FacesContext.getCurrentInstance()
			//	.getExternalContext().getSessionMap();
		//session.remove("LOGGEDIN_USER");
		
		//currentUser=null;
		
		FacesContext.getCurrentInstance()
		.getExternalContext().invalidateSession();
		
		return "exito";
	}
	
	/**
	 * registra un nuevo usuario.
	 * @return
	 */
	public String salva() {
		UsersService service;
		try {
			service = Factories.services.getUserService();

			if ("".equals(login) || "".equals(email) || "".equals(password) ||
					"".equals(password2))
				return "error";
			
			// contraseñas diferentes
			if (!password.equals(password2)){
				setMessage("errorContrasenaDiferente");
				return "error";
			}
				
			// ya existe
			if (service.exists(login) != null){
				setMessage("errorRegistroUsuarioExistente");
				return "error";
			}
			
			service.save(new User(login,email,password));
			
			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			
			if (session.containsKey("LOGGEDIN_USER"))
				session.remove("LOGGEDIN_USER");
			
			log();

			this.password=password2="";

			return "exito";
		} catch (Exception e) {
			return "error";
		}

	}
	
	/**
	 * Reinicia la base de datos
	 * SEGURIDAD: comprueba que el usuario sea administrador (no se muestra
	 * la opción en la página)
	 * @return
	 */
	public String borrarBase(){
		try{
			BaseDeDatos.borraTodo();
			BaseDeDatos.iniciaUsuarios();
			BaseDeDatos.iniciaCategorias();
			BaseDeDatos.iniciaTareas();
			
			return "exito";
		} catch(Exception e){
			return "error";
		}
	}
	
	
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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
