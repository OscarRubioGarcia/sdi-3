package com.messaging.util;

public class Id {
	
	public static int id = 0; 

	public static String next() {
		id++;
		return ""+id;
	}

}
