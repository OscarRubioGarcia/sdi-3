package com.sdi.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.sdi.business.UsersService;
import com.sdi.model.User;

public class Main {
	
	private static final String USER_SERVICE_JNDI_KEY =
			"sdi3-13/"
			+ "sdi3-13_EJB/"
			+ "EjbUserService!"
			+ "com.sdi.business.impl.RemoteUserService";

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void run() throws Exception {
		Context ctx = new InitialContext();
		UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		List<User> users = service.listUsers();
		printHeader();
		for (User a : users) {
			printLine(a);
		}
	}

	private void printLine(User a) {
		
		System.out.println(a.getId() + " - " + a.getEmail() + " - " + a.getLogin() + " - " + a.getPassword() + " - " + a.getStatus());
		
	}

	private void printHeader() {
		
		System.out.println("Listado Usuarios");
		
	}

}
