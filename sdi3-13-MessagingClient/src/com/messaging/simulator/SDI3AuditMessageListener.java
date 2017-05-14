package com.messaging.simulator;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
		activationConfig = {
			@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "queue/SDI3AuditQueue")
	})
public class SDI3AuditMessageListener implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		
		try {
			processMessage(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void processMessage(Message msg) throws JMSException {
		if (!(msg instanceof MapMessage)) {
			System.out.println("Message not of expected type");
			return;
		}
		
		//Aqui cojo las tareas 1 a 1 y las pinto
		MapMessage mmsg = (MapMessage) msg;
		
		if(mmsg.getString("command").equals("viewToday")) {
			
			System.out.print(mmsg.getLong("taskId"));
			System.out.print("\t " + mmsg.getString("title"));
			System.out.print("\t " + mmsg.getString("comments"));
			System.out.print("\t " + mmsg.getString("planned"));
			System.out.print("\t " + mmsg.getString("created"));
			System.out.print("\t " + mmsg.getString("finished"));
			System.out.print("\t " + mmsg.getString("categoryId"));
			
			System.out.print("\t " + mmsg.getLong("userId"));
			System.out.println("");
			
		} else if(mmsg.getString("command").equals("viewLate")) {
			
			System.out.print(mmsg.getLong("taskId"));
			System.out.print("\t " + mmsg.getString("title"));
			System.out.print("\t " + mmsg.getString("comments"));
			System.out.print("\t " + mmsg.getString("planned"));
			System.out.print("\t " + mmsg.getString("created"));
			System.out.print("\t " + mmsg.getString("finished"));
			System.out.print("\t " + mmsg.getString("categoryId"));
			System.out.print("\t " + mmsg.getLong("userId"));
			System.out.println("");
			
		} else {
			System.out.println("Unknown task commmand " + mmsg.getString("command"));
		}
		
		
	}

}
