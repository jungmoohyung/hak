<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<%
//db연결
Class.forName("com.mysql.jdbc.Driver");
DriverManager.getConnection("jdbc:mysql://localhost/jmh", "root","8088");
out.println("제대로 연결됨");
%>