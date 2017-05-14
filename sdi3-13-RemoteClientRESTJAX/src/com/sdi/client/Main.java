package com.sdi.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		
		boolean exit = false;
		
		//Remember to ask credentials here and then create an Authenticator with thoose credentials
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		
		System.out.println("Enter your login: ");
		String login = reader.next();
		System.out.println("Enter your password: ");
		String password = reader.next();
		
		authentic = new Authenticator(login, password);
		
		while(!exit) {
			
			System.out.println("Input 1 to View Category List");
			System.out.println("Input 2 to view Pending and Late Tasks related to a Category");
			System.out.println("Input 3 to create a new Task");
			System.out.println("Input 4 to mark as Finished a Task");
			System.out.println("Input 5 to exit");
			String input = reader.next();
			
			User currentUser = login(login, password);
			
			if(input.equals("1")) {
				
				System.out.println("List of Categories");
				
				List<Category> cats = restGetCategories(currentUser.getId()); // A GET operation
				showCategories(cats);
				
			} else if(input.equals("2")) {
				//View Late and Pending Tasks for category id
				System.out.println("Input the Category Id to view the Late and Pending Tasks");
				String id = reader.next();
				
				System.out.println("List of Late or Peding Tasks");
				List<Task> tasks = restGetLateAndPendingTasks(id);
				showTasks(tasks);
				
			} else if(input.equals("3")) {
				
				System.out.println("Input the new Tasks title");
				String title = reader.next();
				
				System.out.println("Input the new Tasks comments");
				String comment = reader.next();
				
				System.out.println("Input the new Tasks planned Date (dd-MMM-yyyy eg: 7-Jun-2013)");
				String planned = reader.next();
				
				restPostNewTask(title, comment, planned, currentUser.getId());
				
			} else if(input.equals("4")) {
				
				System.out.println("Input the Task to be marked id");
				String id = reader.next();
				
				restPostUpdateTask(id);
				
			} else if(input.equals("5")) {
				exit = true;
			}
		}
		
		reader.close();
	}
	
	private List<Task> restGetLateAndPendingTasks(String id) {
		
		GenericType<List<Task>> listm = new GenericType<List<Task>>() {};
		List<Task> res = ClientBuilder.newClient()
			.register( authentic )
			.target( REST_TASK_SERVICE_URL + "/LateAndPendingTasksById/"+id )
			.request()
			.get()
			.readEntity( listm );
		
		return res;
	}

	private void restPostUpdateTask(String id) {
		
		//Get Task 
		GenericType<Task> listm = new GenericType<Task>() {};
		Task t = ClientBuilder.newClient()
			.register( authentic )
			.target( REST_TASK_SERVICE_URL + "/findTaskById/"+id )
			.request()
			.get()
			.readEntity( listm );
		
		//Update Task
		t.setFinished(new Date());
		
		ClientBuilder.newClient()
			.register( authentic )
			.target( REST_TASK_SERVICE_URL + "/update/")
			.request()
			.post( Entity.entity(t, MediaType.APPLICATION_JSON) );
		
		System.out.println("RESTCLIENT : Task succesfully marked as finished");
	}

	private void restPostNewTask(String title, String comment, String planned, Long userId) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		
		Task task = new Task();
		
		try {
			
		task.setUserId(userId);
		task.setComments(comment);
		task.setPlanned(formatter.parse(planned));
		task.setTitle(title);
		
		ClientBuilder.newClient()
			.register( authentic )
			.target( REST_TASK_SERVICE_URL+"/save/" )
			.request()
			.put( Entity.entity(task, MediaType.APPLICATION_JSON) );
		
		System.out.println("RESTCLIENT : Task created " + task.getTitle());
		
		} catch (ParseException e) {
			System.out.println("RESTCLIENT : Task was not created due to date error");
		}
	}

	private List<Category> restGetCategories(Long id) {
		GenericType<List<Category>> listm = new GenericType<List<Category>>() {};
		List<Category> res = ClientBuilder.newClient()
			.register( authentic )
			.target( REST_CATEGORY_SERVICE_URL + "/find/"+id )
			.request()
			.get()
			.readEntity( listm );
		
		return res;
	}

	private User login(String login, String password) {
		GenericType<User> listm = new GenericType<User>() {};
		User res = ClientBuilder.newClient()
			.register( authentic )
			.target( REST_USER_SERVICE_URL + "/find/"+login+"/"+password )
			.request()
			.get()
			.readEntity( listm );
		
		return res;
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
	
	private void showTasks(List<Task> tasks) {
		printTaskHeader();
		for (Task t : tasks) {
			printTask(t);
		}
	}

	private void printTaskHeader() {
		System.out.printf("%s %s %s %s %s %s %s %s \n",
				"_Id__________",
				"_Title________",
				"_Comment___________________",
				"_Planned________",
				"_Created________",
				"_Finished________",
				"_CategoryId________",
				"_UserId________"
				);
	}
	
	private void printTask(Task t) {
		System.out.printf("%-20s %-15s %-25s \n",
				t.getId(),
				t.getTitle(),
				t.getComments(),
				t.getPlanned(),
				t.getCreated(),
				t.getFinished(),
				t.getCategoryId(),
				t.getUserId()
				);
	}

	private void showCategories(List<Category> categories) {
		printCategoryHeader();
		for (Category c : categories) {
			printCategory(c);
		}
	}
	
	private void printCategoryHeader() {
		System.out.printf("%s %s %s \n",
		"_Id__________",
		"_Category_Name________",
		"_User_Id___________________");
	}
	
	private void printCategory(Category c) {
		System.out.printf("%-20s %-15s %-25s \n",
			c.getId(),
			c.getName(),
			c.getUserId()
			);
	}
	
	

}
