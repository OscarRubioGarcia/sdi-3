package com.sdi.client;

import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.sdi.client.types.UserStatus;

public class Main {
	
	private static final String REST_USER_SERVICE_URL =
			"http://localhost:8280/sdi3-13_Web/rest/UsersServiceRs";
	
	private static final String REST_TASK_SERVICE_URL =
			"http://localhost:8280/sdi3-13_Web/rest/TaskServiceRs";
	
	private static final String REST_CATEGORY_SERVICE_URL =
			"http://localhost:8280/sdi3-13_Web/rest/CategoryServiceRs";
	
	public Authenticator authentic;
	
	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		//Remember to ask credentials here and then create an Authenticator with thoose credentials
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter your login: ");
		String login = reader.next();
		System.out.println("Enter your password: ");
		String password = reader.next();
		
		authentic = new Authenticator(login, password);
		
		List<User> users = restGetUsers(); // A GET operation
		showUsers(users);
		
		/*
		 * String res = getStudentByIdAsJsonString( alumnos.get(0) );
		    System.out.println( res );
			res = getStudentByIdAsXmlString( alumnos.get(0) );
			System.out.println( res );
			Alumno a = getStudentByIdAsObject( alumnos.get(0) );
			printStudent(a);
			a = createNewStudent(); // A PUT operation
			updateStudent( alumnos.get(0) ); // A POST operation
			deleteStudent( alumnos.get(1) ); // A DELETE operation
			System.out.println("\n-- ws REST JAX-RS remote client ended -");
		 */
	}
	
	private String getUserByIdAsXmlString(User user) {
		return ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.path( user.getId().toString() )
			.request()
			.accept( MediaType.APPLICATION_XML )
			.get()
			.readEntity( String.class );
	}
	
	private String getUserByIdAsJsonString(User user) {
		return ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.path( user.getId().toString() )
			.request()
			.accept( MediaType.APPLICATION_JSON )
			.get()
			.readEntity( String.class );
	}
	
	private User getStudentByIdAsObject(User user) {
		return ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.path( user.getId().toString() )
			.request()
			.accept( MediaType.APPLICATION_XML )
			.get()
			.readEntity( User.class );
	}
	
	private List<User> restGetUsers() {
		GenericType<List<User>> listm = new GenericType<List<User>>() {};
		List<User> res = ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.request()
			.get()
			.readEntity( listm );
		
		return res;
	}
	
	private void updateUser(User user) {
		user.setEmail( user.getEmail() + "_REST_updated" );
		ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.request()
			.post( Entity.entity(user, MediaType.APPLICATION_JSON) );
	}
	
	private User createNewUser(String login, String pass, String email, boolean admin) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(pass);
		user.setEmail(email);
		user.setIsAdmin(admin);
		user.setStatus(UserStatus.ENABLED);
		ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.request()
			.put( Entity.entity(user, MediaType.APPLICATION_JSON) );
		
		return user;
	}
	
	private void deleteUser(User user) {
		ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL )
			.path( user.getId().toString() )
			.request()
			.delete();
	}
	
	private void showUsers(List<User> users) {
		printHeader();
		for (int i = 0; i < users.size(); i++) {
			printUser(users.get(i));
		}
	}
	
	private void printHeader() {
		System.out.printf("%s %s %s %s\n",
		"_Login__________",
		"_Pass________",
		"_Email___________________",
		"_IsAdmin_");
	}
	
	private void printUser(User u) {
		System.out.printf("%-20s %-15s %-25s %-8s\n",
			u.getLogin(),
			u.getPassword(),
			u.getEmail(),
			u.getIsAdmin()
			);
	}
	
	

}
