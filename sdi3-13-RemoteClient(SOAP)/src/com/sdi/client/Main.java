package com.sdi.client;

import java.util.Collection;

import com.sdi.ws.EjbUserServiceService;
import com.sdi.ws.UserService;
import com.sdi.ws.User;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void run() throws Exception {
		UserService service = new EjbUserServiceService().getUserServicePort();
		Collection<User> users = service.listUsers();
		
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
