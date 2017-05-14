package com.sdi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sdi.business.UsersService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.User;
import com.sdi.model.types.UserStatus;

public class Main {

	private static final String USER_SERVICE_JNDI_KEY = "sdi3-13/"
			+ "sdi3-13_EJB/" + "EjbUserService!"
			+ "com.sdi.business.impl.RemoteUserService";
	private Context ctx; 
	private UsersService service;
	
	protected static BufferedReader console = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		ctx = new InitialContext();
		service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		System.out.println("Bienvenido a GTD Task Manager \nSomos Óscar y "
				+ "Christian, tus hosts\nPor favor, elige una de las opciones "
				+ "siguientes o escribe exit para salir o volver al menú:\n"
				+ "---------------");
		String opcion = "";

		while (true) {
			System.out.println("");
			// currentMenu.showOptions();
			System.out.println("1: Listar usuarios del sistema (y tareas)\n"
					+ "2: Deshabilitar un usuario\n"
					+ "3: Eliminar un usuario (y tareas)\n"
					+ "4: Habilitar un usuario");

			opcion = console.readLine();
			if ("1".equals(opcion))
				printAll();
			else if ("2".equals(opcion))
				disableUser();
			else if ("3".equals(opcion))
				deleteUser();
			else if ("4".equals(opcion))
				enableUser();
			else if ("exit".equals(opcion))
				break;
			else
				System.out.println("Esa opción no es válida");
		}

		System.out.println("Hasta la próxima!");

	}

	/**
	 * Muestra los usuarios habilitados y da la opción de deshabilitarlos. Estos
	 * usuarios no podrán loguearse.
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws NamingException 
	 * @throws EntityNotFoundException 
	 * @throws BusinessException 
	 * @throws BusinessException_Exception
	 * @throws EntityNotFoundException_Exception
	 */
	private void disableUser() throws IOException, NumberFormatException, 
				NamingException, BusinessException, EntityNotFoundException {
		printEnabledUsers(true);
//		UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		String opcion = "";

		while (true)
			try {
				System.out.println("Introduce el id del usuario que desees "
						+ "deshabilitar");
				opcion = console.readLine();

				if ("exit".equals(opcion))
					return;

				service.disableUser((long) Integer.parseInt(opcion));

				return;
			} catch (NumberFormatException nfe) {
				System.out.println("Type a number, please");
			}
	}

	/**
	 * Muestra los usuarios deshabilitados y da la opción de habilitarlos. Estos
	 * usuarios podrán loguearse.
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws NamingException 
	 * @throws EntityNotFoundException 
	 * @throws BusinessException 
	 * @throws BusinessException_Exception
	 * @throws EntityNotFoundException_Exception
	 */
	private void enableUser() throws IOException, NumberFormatException, 
				NamingException, BusinessException, EntityNotFoundException {
		printEnabledUsers(false);

//		UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		String opcion = "";

		while (true)
			try {
				System.out.println("Introduce el id del usuario que desees "
						+ "habilitar");
				opcion = console.readLine();

				if ("exit".equals(opcion))
					return;

				service.enableUser((long) Integer.parseInt(opcion));

				return;
			} catch (NumberFormatException nfe) {
				System.out.println("Type a number, please");
			}
	}

	/**
	 * Elimina de la base de datos todo rastro del ususario (datos personales,
	 * tareas y categorías).
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws EntityNotFoundException 
	 * @throws NamingException 
	 * @throws EntityNotFoundException_Exception
	 */
	private void deleteUser() throws IOException, NumberFormatException, 
									EntityNotFoundException, NamingException{
		printUsers();
//		UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		String opcion = "";
		System.out.println("Introduce el id del usuario que "
				+ "desees eliminar (también eliminará sus "
				+ "tareas y categorías, ¡cuidado!)");
		opcion = console.readLine();

		if ("exit".equals(opcion))
			return;

		service.deleteUser((long) Integer.parseInt(opcion));
	}

	/**
	 * Muestra en una lista a todos los usuarios con el número de tareas de
	 * diversos tipos (completadas, completadas con retraso, planificadas y sin
	 * planificar)
	 * @throws NamingException 
	 * 
	 * @throws BusinessException_Exception
	 */
	private void printAll() throws NamingException {
//		UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		Collection<User> users;
		users = service.allUsersInfoAndTasks();
		StringBuilder userBuilder = new StringBuilder();

		for (User user : users) {
			System.out.println(user.getLogin() + " - " + user.getEmail()
					+ " - " + user.getStatus() + " - " + user.getIsAdmin()
					+ "\n\t--" + user.getTareas()[0]
					+ " tareas completadas" + "\n\t--"
					+ user.getTareas()[1] + " de ellas con retraso"
					+ "\n\t--" + user.getTareas()[2] + " planificadas"
					+ "\n\t--" + user.getTareas()[3] + " sin planificar");
			System.out.println(userBuilder.toString());

			System.out.println();
		}
	}

	/**
	 * true enabled, false disabled
	 * 
	 * @param status
	 */
	private void printEnabledUsers(boolean enabled) {
		Collection<User> users;
		try {
//			UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
			users = service.listUsers();
		} catch (Exception e) {
			System.out
					.println("Se ha producido un error de conexión con la bbdd");
			return;
		}

		System.out.println("Listado Usuarios");
		if (enabled) {
			for (User a : users) {
				if (UserStatus.ENABLED == a.getStatus())
					printLine(a);
			}
		} else {
			for (User a : users) {
				if (UserStatus.DISABLED == a.getStatus())
					printLine(a);
			}
		}

	}

	private void printUsers() {
		Collection<User> users;
		try {
//			UsersService service = (UsersService) ctx.lookup(USER_SERVICE_JNDI_KEY);
			users = service.listUsers();
		} catch (Exception e) {
			System.out
					.println("Se ha producido un error de conexión con la bbdd");
			return;
		}

		System.out.println("Listado Usuarios");
		for (User a : users) {
			printLine(a);
		}
	}

	private void printLine(User a) {
		System.out.println(a.getId() + " - " + a.getEmail() + " - "
				+ a.getLogin() + " - " + a.getPassword() + " - "
				+ a.getStatus());
	}
}
