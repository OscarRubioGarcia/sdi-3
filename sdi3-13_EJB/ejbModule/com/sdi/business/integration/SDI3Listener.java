package com.sdi.business.integration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

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
	
	UsersService uservice = Factories.services.getUserService();
	TasksService tservice = Factories.services.getTaskService();

	@Override
	public void onMessage(Message msg) {
		System.out.println("SDI3Listener: Msg received");
		try {
			process(msg);
		} catch (JMSException jex) {
			// here we should log the exception
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
				//doViewToday(msg);
			} else if ("viewLate".equals(cmd)) {
				//doViewLate(msg);
			}
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
