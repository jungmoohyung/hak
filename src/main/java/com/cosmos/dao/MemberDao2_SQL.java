package com.cosmos.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chick.common.OracleConn;

public class MemberDao2_SQL {
	
	private final Connection conn = OracleConn.getInstance().getConn();
	PreparedStatement stmt = null;
	
	public Map<String, String> loginProc(String id, String pw){

		Map<String, String> status = new HashMap<String, String>(); //로그인 실패
		
		
		
		String sql = "select * from member where id = ?";
		try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1,id);
		ResultSet rs = stmt.executeQuery();
		
			if(rs.next()) {
				if(rs.getString("pw").equals(pw)){
					status.put("msg", "ok") ; //로그인 성공
					status.put("name", rs.getString("name")) ; //로그인 성공
				}else{
					status.put("msg","loginFail"); //패스워드 틀림
				}
			/* 자원반납 */
			}else {
				status.put("msg", "loginFail");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			
			//resourceClose();
		}
		return status;
	}
	private void resourceClose() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int insertMember(HttpServletRequest req) {
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String[] hobby = req.getParameterValues("hobby");
		String email = req.getParameter("eid") +"@"+ req.getParameter("selDomain");
		String intro = req.getParameter("intro");

		String hobby_str = new String();

		for(int i=0; i<hobby.length; i++){
			 hobby_str += hobby[i];
			 if(i != hobby.length-1){
				 hobby_str+=",";
			 }
		}

		int rs = 0;
		CallableStatement stmt ;
		try {
			/*
			 * String sql =
			 * String.format("INSERT INTO member(id, pw, name, gender, hobby, email, intro)"
			 * +"values('%s','%s','%s','%s','%s','%s','%s')",
			 * id,pw,name,gender,hobby_str,email,intro);
			 */
			
			String sql = "call p_insertMember(?,?,?,?,?,?,?,?)";
			
			stmt = conn.prepareCall(sql);
			stmt.setString(1, id);
			stmt.setString(2, pw);
			stmt.setString(3, name);
			stmt.setString(4, gender);
			stmt.setString(5, hobby_str);
			stmt.setString(6, intro);
			stmt.setString(7, email);
			stmt.registerOutParameter(8, OracleTypes.INTEGER);
			stmt.executeUpdate(sql);
			rs = stmt.getInt(8);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	public int selectById(String id) {
		
		CallableStatement stmt = null;
		int rs = 0;
		
		String sql = "call p_idDoubleCheck(?,?)";
		
		try {
			stmt = conn.prepareCall(sql);
			stmt.setNString(1,id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
}
