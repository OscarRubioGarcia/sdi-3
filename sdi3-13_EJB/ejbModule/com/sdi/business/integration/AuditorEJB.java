package com.sdi.business.integration;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class AuditorEJB implements Auditor{

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory factory;
	
	@Resource(mappedName = "java:/queue/SDI3AuditQueue")
	private Destination queue;
	
	@Resource 
	private SessionContext ctx;
	
	@Override
	public void audit(String operation) {
		audit(ctx.getCallerPrincipal(), operation);
	}
	
	public void audit(Principal principal, String operation) {
		
		Map<String, String> msg = new HashMap<String, String>();
		
		msg.put("user", principal.getName() );
		msg.put("operation", operation );
		msg.put("date", new Date().toString() );
		
		sendMapMessage( msg );
		
		System.out.println("Msg sent with " + msg);
	}

	private void sendMapMessage(Map<String, String> msgMap) {
		
		Connection con = null;
		
		try {
			con = factory.createConnection("sdi", "password");
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session.createProducer(queue);
			
			MapMessage msg = createJmsMapMessage(msgMap, session);
			sender.send( msg );
		} catch (JMSException jex) {
			jex.printStackTrace();
		}
		finally {
			close(con);
		}
	}

	private MapMessage createJmsMapMessage(Map<String, String> msgMap,
			Session session) throws JMSException {
		
		MapMessage msg = session.createMapMessage();
		
		msg.setString("user", msgMap.get("user"));
		msg.setString("operation", msgMap.get("operation"));
		msg.setString("date", msgMap.get("date"));
		//Add here the rest
		
		return msg;
	}

	private void close(Connection con) {
		try {
			con.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
