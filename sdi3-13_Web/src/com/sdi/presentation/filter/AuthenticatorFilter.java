package com.sdi.presentation.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.util.Base64;

import com.sdi.business.UsersService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;



@WebFilter("/rest/*")
public class AuthenticatorFilter implements Filter {

	private String username = "sdi";
	private String password = "password";
	
	private String realm = "rest";
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        //Enumeration<String> headerNames = httpRequest.getHeaderNames();

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if (st.hasMoreTokens()) {
              String basic = st.nextToken();

              if (basic.equalsIgnoreCase("Basic")) {
                try {
                  String credentials = new String(Base64.decode(st.nextToken()), "UTF-8");
                  System.out.print("Credentials: " + credentials);
                  int p = credentials.indexOf(":");
                  if (p != -1) {
                    String _username = credentials.substring(0, p).trim();
                    String _password = credentials.substring(p + 1).trim();

                    //Check User
                    UsersService service = Factories.services.getUserService();
        			User existingUser = service.findLoggable(_username, _password);
        			if (existingUser == null) {
                      unauthorized(httpResponse, "Bad credentials, User not found");
                    } else
                    	System.out.println(" 	//Credentials Correct");
                    
                    chain.doFilter(request, response);
                  } else {
                    unauthorized(httpResponse, "Invalid authentication token");
                  }
                } catch ( Exception e) {
                	System.out.println("Authentification Not Found");
                }
              }
            }
          } else {
            unauthorized(httpResponse);
          }

  
        /*
        if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                        System.out.println("Header: " + httpRequest.getHeader(headerNames.nextElement()));
                        
                }
        }*/

        //doFilter
        //chain.doFilter(httpRequest, response);
		
	}
	
	private void unauthorized(HttpServletResponse response, String message) throws IOException {
		response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
		response.sendError(401, message);
	}

	private void unauthorized(HttpServletResponse response) throws IOException {
		unauthorized(response, "Unauthorized");
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		
	}

}
