package com.sdi.business.integration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.sdi.business.TasksService;
import com.sdi.business.UsersService;
import com.sdi.business.exception.BusinessException;
import com.sdi.business.exception.EntityAlreadyExistsException;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.model.User;

@MessageDriven(
	activationConfig = {
		@ActivationConfigProperty(
			propertyName = "destination",
			propertyValue = "queue/SDI3Queue")
})
public class SDI3Listener implements MessageListener{
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory factory;
	
	@Resource(mappedName = "java:/queue/SDI3AuditQueue")
	private Destination queue;
	
	@Resource(mappedName = "java:/queue/SDI3AdminQueue")
	private Destination queueAdmin;
	
	UsersService uservice = Factories.services.getUserService();
	TasksService tservice = Factories.services.getTaskService();
	
	Connection con;
	Session session;
	MessageProducer sender;

	@Override
	public void onMessage(Message msg) {
		System.out.println("SDI3Listener: Msg received");
		try {
			process(msg);
		} catch (JMSException jex) {
			jex.printStackTrace();
			System.out.println("SDI3Listener: Error in message");
		}
	}
	
	private void process(Message m) throws BusinessException, JMSException {
		
		if (!messageOfExpectedType(m)) { 
			System.out.println("Not of expected type " + m);
			return;
		}
		
		MapMessage msg = (MapMessage) m;
		
		String login = msg.getString("credential-login");
		String password = msg.getString("credential-password");
		
		boolean exists = userExists(login, password);
		
		if(exists) {
			
			String cmd = msg.getString("command");
			
			if ("newTask".equals(cmd)) {
				doNewTask(msg, login, password);
			} else if ("markFinished".equals(cmd)) {
				doMarkFinished(msg);
			} else if ("viewToday".equals(cmd)) {
				doViewToday(msg, login, password);
			} else if ("viewLate".equals(cmd)) {
				doViewLate(msg, login, password);
			}
		} else {
			
			//Send message to the SDI3AdminQueue reporting this
			initializeProducer(queueAdmin);
			
			MapMessage newmes = session.createMapMessage();
			newmes.setString("command", msg.getString("command"));
			newmes.setString("credential-login", login);
			newmes.setString("credential-password", password);
			newmes.setString("date", new Date().toString());
			
			sender.send(newmes);
			
			System.out.println("SDI3Listener : sent message to AdminQueue");
			closeProducer();
			
		}
	}
	
	private void doViewLate(MapMessage msg, String login, String password) {
		
		User u;
		try {
			u = uservice.findLoggable(login, password);
			
			List<Task> tasks = tservice.getTareasByUserIdLate(u.getId());
			
			initializeProducer(queue);
			
			MapMessage m;
			
			//Create a producer to send the reply to SDI3AuditQueue
			for(Task t : tasks) {
				
				m = session.createMapMessage();
				m.setString("command", msg.getString("command"));
				m.setString("credential-login", login);
				m.setString("credential-password", password);
				m.setLong("taskId", t.getId());
				m.setString("title", t.getTitle());
				m.setString("comments", t.getComments());
				
				if(t.getPlanned()!=null)
					m.setString("planned", t.getPlanned().toString());
				
				if(t.getCreated()!=null)
					m.setString("created", t.getCreated().toString());
				
				if(t.getFinished()!=null)
					m.setString("finished", t.getFinished().toString());
				
				if(t.getCategoryId()!=null)
					m.setString("categoryId", ""+t.getCategoryId());
				
				m.setLong("userId", t.getUserId());
				
				sender.send(m);
				
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			System.out.println("SDI3Listener : User Not found in database");
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("SDI3Listener : JMS Error found"); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeProducer();
		}
		
	}

	private void doViewToday(MapMessage msg, String login, String password) {

		User u;
		try {
			u = uservice.findLoggable(login, password);
			
			List<Task> tasks = tservice.getTareasByUserIdTodayOnly(u.getId());
			
			initializeProducer(queue);
			
			MapMessage m;
			
			//Create a producer to send the reply to SDI3AuditQueue
			for(Task t : tasks) {
				
				m = session.createMapMessage();
				m.setString("command", msg.getString("command"));
				m.setString("credential-login", login);
				m.setString("credential-password", password);
				m.setLong("taskId", t.getId());
				m.setString("title", t.getTitle());
				m.setString("comments", t.getComments());
				
				if(t.getPlanned()!=null)
					m.setString("planned", t.getPlanned().toString());
				
				if(t.getCreated()!=null)
					m.setString("created", t.getCreated().toString());
				
				if(t.getFinished()!=null)
					m.setString("finished", t.getFinished().toString());
				
				if(t.getCategoryId()!=null)
					m.setString("categoryId", ""+t.getCategoryId());
				
				m.setLong("userId", t.getUserId());
				
				sender.send(m);
			}
			
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			System.out.println("SDI3Listener : User Not found in database"); 
		} catch (JMSException e) {
				e.printStackTrace();
				System.out.println("SDI3Listener : JMS Error found"); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeProducer();
		}
		
	}
	
	private void initializeProducer(Destination file) throws JMSException {
		
		con = factory.createConnection("sdi", "password");
		session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		sender = session.createProducer(file);
		
		con.start();
	}
	
	private void closeProducer() {
		try {
			sender.close();
			session.close();
			con.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void doMarkFinished(MapMessage msg) throws JMSException {
		
		Long taskId = msg.getLong( "taskId" );
		
		try {
			Task task = tservice.findById(taskId);
			
			if(task!=null) {
				task.setFinished(new Date());
				tservice.updateTarea(task);
			}
			
		} catch (EntityNotFoundException e) {
			System.out.println("SDI3Listener : Entity does not exists, task was no marked");
		}
		
	}

	private void doNewTask(MapMessage msg, String login, String password) throws JMSException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		Task task;
		
		try {
			
			User u = uservice.findLoggable(login, password);
			
			task = new Task( msg.getString( "title" ), msg.getString( "comments" ), 
					new Date(), formatter.parse(msg.getString( "planned" ))
					, null, null, u.getId());
			
			tservice.saveTarea(task);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (EntityAlreadyExistsException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	private boolean userExists(String login, String password) {
		
		//Valid Users
		try {
			User u = uservice.findLoggable(login, password);
			if(u!=null)
				return true;
		} catch (EntityNotFoundException e) {
			System.out.println("SDI3Listener : Credentials not valid " + login + " : " + password);
			
			return false;
		}
		
		System.out.println("SDI3Listener : Credentials not valid " + login + " : " + password);
		
		return false;
	}

	private boolean messageOfExpectedType(Message m) {
		
		if(m instanceof MapMessage)
			return true;
		
		return false;
	}
	
}
