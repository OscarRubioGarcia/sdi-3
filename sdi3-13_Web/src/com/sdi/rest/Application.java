package com.sdi.rest;

import java.util.HashSet;
import java.util.Set;

public class Application extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set< Class<?> > res = new HashSet<Class<?>>();
		res.add( UsersServiceRestImpl.class );
		res.add( TaskServiceRestImpl.class );
		res.add( CategoryServiceRestImpl.class );
		return res;
	}
	
}
