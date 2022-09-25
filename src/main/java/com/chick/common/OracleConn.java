package com.chick.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class OracleConn {
	
	private static OracleConn Conn1 = new OracleConn();
	private Connection conn;
	
	private OracleConn() {
		oracleConn();
	}
	
	public static OracleConn getInstance() {
		return Conn1;
	}
	
	private void oracleConn(){
		
		Properties pro = new Properties();
		String path = OracleConn.class.getResource("database.properties").getPath();
		//System.out.println(path);
		try {
			
			path = URLDecoder.decode(path,"utf-8");
			pro.load(new FileReader(path));
			
			String driver = pro.getProperty("driver");
			String url = pro.getProperty("url");
			String id = pro.getProperty("id");
			String pw = pro.getProperty("pw");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,id,pw);
			//System.out.println("연결완료");
			
			//System.out.println("드라이버:" + driver +" \n url : " + url +"\n id:"+id+"\n pw:"+pw);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		/*
		 * try { final String url ="jdbc:oracle:thin:@192.168.10.238:1521/xe";
		 * Class.forName("oracle.jdbc.driver.OracleDriver"); conn =
		 * DriverManager.getConnection(url,"jmh","8088");
		 * //System.out.println("오라클 연결완료"); } catch (ClassNotFoundException e) {
		 * e.printStackTrace(); } catch (SQLException e) { e.printStackTrace(); } }
		 * public Connection getConn() { return conn;
		 */
		}
	
	public Connection getConn() { 
		return conn;
	}
	
	
}

