package com.sdi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import com.sdi.ws.BusinessException_Exception;
import com.sdi.ws.EjbUserServiceService;
import com.sdi.ws.EntityNotFoundException_Exception;


import com.sdi.ws.User;
import com.sdi.ws.UserService;
import com.sdi.ws.UserStatus;

public class Main {

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
		System.out.println("Bienvenido a GTD Task Manager \nSomos Óscar y "
				+ "Christian, tus hosts\nPor favor, elige una de las opciones "
				+ "siguientes o escribe exit para salir o volver al menú:\n"
				+ "---------------");
		String opcion="";
		
		while (true){
			System.out.println("");
			//currentMenu.showOptions();
			System.out.println("1: Listar usuarios del sistema (y tareas)\n"
							+ "2: Deshabilitar un usuario\n"
							+ "3: Eliminar un usuario (y tareas)\n"
							+ "4: Habilitar un usuario");
 
			opcion = console.readLine();
			if("1".equals(opcion))
				printAll();
			else if("2".equals(opcion))
				disableUser();
			else if("3".equals(opcion))
				deleteUser();
			else if("4".equals(opcion))
				enableUser();
			else if("exit".equals(opcion))
				break;
			else
				System.out.println("Esa opción no es válida");
		}
		
		System.out.println("Hasta la próxima!");
		
		
	}
	
	/**
	 * Muestra los usuarios habilitados y da la opción de deshabilitarlos. Estos
	 * usuarios no podrán loguearse.
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws BusinessException_Exception
	 * @throws EntityNotFoundException_Exception
	 */
	private void disableUser() throws IOException, NumberFormatException, 
				BusinessException_Exception, EntityNotFoundException_Exception{
		printEnabledUsers(true);
		UserService service = new EjbUserServiceService().getUserServicePort();
		String opcion = "";
		
		while(true)
			try{
				System.out.println("Introduce el id del usuario que desees "
									+ "deshabilitar");
				opcion = console.readLine();
		
				if ("exit".equals(opcion)) return;
		
				service.disableUser((long) Integer.parseInt(opcion));
				
				return;
			}catch(NumberFormatException nfe){
				System.out.println("Type a number, please");
			}
	}
	
	/**
	 * Muestra los usuarios deshabilitados y da la opción de habilitarlos. Estos
	 * usuarios podrán loguearse.
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws BusinessException_Exception
	 * @throws EntityNotFoundException_Exception
	 */
	private void enableUser() throws IOException, NumberFormatException, 
				BusinessException_Exception, EntityNotFoundException_Exception{
		printEnabledUsers(false);
		UserService service = new EjbUserServiceService().getUserServicePort();
		String opcion = "";
		
		while(true)
			try{
				System.out.println("Introduce el id del usuario que desees "
									+ "habilitar");
				opcion = console.readLine();
		
				if ("exit".equals(opcion)) return;
		
				service.enableUser((long) Integer.parseInt(opcion));
				
				return;
			}catch(NumberFormatException nfe){
				System.out.println("Type a number, please");
			}
	}
	/**
	 * Elimina de la base de datos todo rastro del ususario (datos personales,
	 * tareas y categorías).
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws EntityNotFoundException_Exception
	 */
	private void deleteUser() throws IOException, NumberFormatException, 
											EntityNotFoundException_Exception{
		printUsers();
		UserService service = new EjbUserServiceService().getUserServicePort();
		String opcion = "";
		System.out.println("Introduce el id del usuario que "
							+ "desees eliminar (también eliminará sus "
							+ "tareas y categorías, ¡cuidado!)");
		opcion = console.readLine();
		
		if ("exit".equals(opcion))	return;
		
		service.deleteUser((long) Integer.parseInt(opcion));
	}
	/**
	 * Muestra en una lista a todos los usuarios con el número de tareas de 
	 * diversos tipos (completadas, completadas con retraso, planificadas y sin
	 * planificar)
	 * @throws BusinessException_Exception 
	 */
	private void printAll() throws BusinessException_Exception{
		UserService service = new EjbUserServiceService().getUserServicePort();
		Collection<User> users;
		users = service.allUsersInfoAndTasks();
		StringBuilder userBuilder = new StringBuilder();
		
		for (User user : users){
			System.out.println(user.getLogin() + " - " + user.getEmail()
					+ " - " + user.getStatus() + " - " + user.isIsAdmin()
					+ "\n\t--" + user.getTareas().get(0) + " tareas completadas"
					+ "\n\t--" + user.getTareas().get(1) + " de ellas con retraso"
					+ "\n\t--" + user.getTareas().get(2) + " planificadas"
					+ "\n\t--" + user.getTareas().get(3) + " sin planificar");
			System.out.println(userBuilder.toString());

			System.out.println();
		}
	}
	
	/**
	 * true enabled, false disabled
	 * @param status
	 */
	private void printEnabledUsers(boolean enabled){
		Collection<User> users;
		try {
			UserService service = new EjbUserServiceService().getUserServicePort();
			users = service.listUsers();
		} catch (BusinessException_Exception e) {
			System.out.println("Se ha producido un error de conexión con la bbdd");
			return;
		}
		
		System.out.println("Listado Usuarios");
		if (enabled){
			for (User a : users){
				if (UserStatus.ENABLED==a.getStatus())
					printLine(a);
			}}
		else {
			for (User a : users){
				if (UserStatus.DISABLED==a.getStatus())
					printLine(a);
			}}
			
	}
	private void printUsers(){
		Collection<User> users;
		try {
			UserService service = new EjbUserServiceService().getUserServicePort();
			users = service.listUsers();
		} catch (BusinessException_Exception e) {
			System.out.println("Se ha producido un error de conexión con la bbdd");
			return;
		}
		
		System.out.println("Listado Usuarios");
		for (User a : users) {
			printLine(a);
		}
	}
	private void printLine(User a) {
		System.out.println(a.getId() + " - " + a.getEmail() + " - " 
							+ a.getLogin() + " - " + a.getPassword() 
							+ " - " + a.getStatus());
	}
}
