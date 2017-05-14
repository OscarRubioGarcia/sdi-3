package com.messaging.simulator;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.messaging.util.Id;
import com.messaging.util.Jndi;

public class Main {
	
	private static final String JMS_CONNECTION_FACTORY =
			"jms/RemoteConnectionFactory";
	
	private static final String SDI3_QUEUE = "jms/queue/SDI3Queue";
	
	private static final String SDI3_AUDIT_QUEUE = "jms/queue/SDI3AuditQueue";
	
	Connection con;
	Session session;
	MessageProducer sender;
	
	Connection conn;
	
	String login;
	String password;

	public static void main(String[] args) {
		try {
			new Main().run();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void run() throws JMSException {
		
		boolean exit = false;
		
		Scanner reader = new Scanner(System.in);  
		System.out.println("Enter your login: ");
		login = reader.next();
		System.out.println("Enter your password: ");
		password = reader.next();
		
		initialize();
		
		while(!exit) {
			
			MapMessage msg;
			
			System.out.println("Input 1 to create a new Task");
			System.out.println("Input 2 to view Today Tasks");
			System.out.println("Input 3 to view Late Tasks");
			System.out.println("Input 4 to mark as Finished a Task");
			System.out.println("Input 5 to exit");
			String input = reader.next();
			
			if(input.equals("1")) {
				
				System.out.println("Input the new Tasks title");
				String title = reader.next();
				
				System.out.println("Input the new Tasks comments");
				String comment = reader.next();
				
				System.out.println("Input the new Tasks planned Date (dd-MMM-yyyy eg: 7-Jun-2013)");
				String planned = reader.next();
				
				msg = createMessageNewTask(title, comment, planned);
				showMessage(msg);
				sender.send(msg);
				
			} else if(input.equals("2")) {
				//Today
				
				//send message then initialize consumer
				initializeConsumer();
				System.out.println("Input any number or letter to go back to main menu");
				reader.next();
				
				conn.close();
				
			} else if(input.equals("3")) {
				//Late
				
				//send message then initialize consumer
				initializeConsumer();
				System.out.println("Input any number or letter to go back to main menu");
				reader.next();
				
				conn.close();
				
			} else if(input.equals("4")) {
				//Mark
				
				System.out.println("Input the Task to be marked id");
				String id = reader.next();
				
				msg = createMessageMarkTask(id);
				showMessage(msg);
				sender.send(msg);
				
			} else if(input.equals("5")) {
				exit = true;
			}
			
			msg = null;
		}
		
		close();
		reader.close();
	}

	private void initializeConsumer() throws JMSException {
		ConnectionFactory factory =
				(ConnectionFactory) Jndi.find( JMS_CONNECTION_FACTORY );
		Destination queue = (Destination) Jndi.find(SDI3_AUDIT_QUEUE );	//Igual cambiar por otra queue
		
		conn = factory.createConnection("sdi", "password");
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener( new SDI3AuditMessageListener() );
		
		conn.start();
		
	}

	private void initialize() throws JMSException {
		ConnectionFactory factory =
				(ConnectionFactory) Jndi.find( JMS_CONNECTION_FACTORY );
		Destination queue = (Destination) Jndi.find( SDI3_QUEUE );
		
		con = factory.createConnection("sdi", "password");
		session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		sender = session.createProducer(queue);
		
		con.start();
	}
	
	private void close() throws JMSException {
		sender.close();
		session.close();
		con.close();
	}
	
	private void showMessage(MapMessage msg) throws JMSException {
		
		System.out.println(msg.getString("credential-login") + " " + msg.getString("credential-password") + " " 
				+ msg.getString("command"));
		
	}
	
	private MapMessage createMessageMarkTask(String id) throws JMSException {
		
		MapMessage msg = session.createMapMessage();
		msg.setString("credential-login", login);
		msg.setString("credential-password", password);
		msg.setString("command", "markFinished");
		msg.setString("taskId", id);
		
		return msg;
	}
	
	private MapMessage createMessageNewTask(String title, String comment, 
			String planned) throws JMSException {
		
		MapMessage msg = session.createMapMessage();
			msg.setString("credential-login", login);
			msg.setString("credential-password", password);
			msg.setString("command", "newTask");
			msg.setString("title", title + " "+ Id.next() + " "
					+ System.currentTimeMillis());
			msg.setString("comments", comment);
			msg.setString("planned", planned);
			
		return msg;
	}
	

}
