package com.sdi.client;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sdi.client.types.UserStatus;

/**
 * An implementation of the DTO pattern
 * 
 * @author alb
 */
@XmlRootElement(name = "user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String login;
	private String email;
	private String password;
	private Boolean isAdmin = false;
	private UserStatus status = UserStatus.ENABLED;
	
	private int[] tareas = new int[4];
	
	public User(){
	}
	
	public User(String nombreUsuario, String email, String password) {
		this.login = nombreUsuario;
		this.email = email;
		this.password = password;
	}

	public User setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}
	
	@XmlElement
	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	@XmlElement
	public String getLogin() {
		return login;
	}

	public User setLogin(String login) {
		this.login = login;
		return this;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	@XmlElement
	/**
	 * completadas, completadas con retraso, planificadas y no planificadas
	 * @return
	 */
	public int[] getTareas() {
		return tareas;
	}

	public User setTareas(int completadas, int completadasRetraso, int planificadas, int noPlanificadas) {
		this.tareas[0] = completadas;
		this.tareas[1] = completadasRetraso;
		this.tareas[2] = planificadas;
		this.tareas[3] = noPlanificadas;
		return this;
	}
	
	@XmlElement
	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	@XmlElement
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id 
				+ ", login=" + login 
				+ ", email=" + email 
				+ ", password=" + password 
				+ ", isAdmin=" + isAdmin 
				+ ", tareas= " + tareas.toString() + "]";
	}

	@XmlElement
	public UserStatus getStatus() {
		return status;
	}

	public User setStatus(UserStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tareas == null) ? 0 : tareas.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (status != other.status)
			return false;
		if (tareas != other.tareas)
			return false;
		return true;
	}	
	
}
