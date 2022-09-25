package com.chick.common;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener
public class Loginimpl implements HttpSessionBindingListener {

	private String id;
	private String name;
	public static int total_user = 0;
	
	
    public Loginimpl() {
    	
    }

    public Loginimpl(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static int getTotal_user() {
		return total_user;
	}

	public void valueBound(HttpSessionBindingEvent event)  { 
		//System.out.println("로그인");
		++total_user;
		//System.out.println("현재접속자수 = "+ total_user);
	}

    public void valueUnbound(HttpSessionBindingEvent event)  { 
		//System.out.println("로그아웃");
    	--total_user;
		//System.out.println("현재접속자수 = "+ total_user);

    }
	
}
