package com.messaging.simulator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

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
		System.out.println("New Operation");
		System.out.println("\tuser: " + mmsg.getString("user"));
		System.out.println("\toper: " + mmsg.getString("operation"));
		System.out.println("\tdate: " + mmsg.getString("date"));
		System.out.println(mmsg.getJMSMessageID());
	}

}
