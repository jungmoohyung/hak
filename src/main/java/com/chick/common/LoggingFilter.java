package com.chick.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class LoggingFilter extends HttpFilter implements Filter {
   
	PrintWriter writer;
	
	
	
    public LoggingFilter() {
        super();
    }
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		long begin = System.currentTimeMillis();
		
		String path = ((HttpServletRequest)request).getContextPath();
		String uri = ((HttpServletRequest)request).getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		writer.printf("path:%s, uri:%s, cmd:%s \n", path,uri,cmd);//어느경로에 접근햇는가
		
		GregorianCalendar now = new GregorianCalendar();
		writer.printf("접근시간:%TF, %TT %n", now, now);//시간과 초를 기록하기위해
		
		String addr = request.getRemoteAddr();//클라이언트 아이피주소
		
		int port = request.getRemotePort();
		writer.printf("접근주소:%s, 접근포트:%d \n", addr, port);
		
		
		chain.doFilter(request, response);
		long end = System.currentTimeMillis();
		writer.printf("작업시간:%d ms \n", (end-begin));
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
		try {
			GregorianCalendar cal = new GregorianCalendar();
			String filename = cal.get(Calendar.YEAR) + "_" + 
			cal.get((Calendar.MONTH)+1)+"_"+cal.get(Calendar.DATE);//저장될 파일의 이름
			
			writer = new PrintWriter(new FileWriter("d:\\jmh\\log\\"+filename+".log",true),true);
		} catch (IOException e) {
			System.out.println("로그 파일 생성 오류");
		}
	}

}
