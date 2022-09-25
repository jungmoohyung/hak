package com.cosmos.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chick.common.OracleConn;
import com.chick.dto.Member;
@Repository
public class MemberDaoimp implements MemberDao {
	@Autowired
	private DataSource ds;
	
//	private final Connection conn = OracleConn.getInstance().getConn();
	
	@Override
	public Map<String, String> loginProc(String id, String pw){

		Map<String, String> status = new HashMap<String, String>(); //로그인 실패
		
		PreparedStatement stmt = null;
		Connection conn = null;
		String sql = "select * from member where id = ?";
		try {
		conn = ds.getConnection();
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
			
			resourceClose(conn, stmt);
		}
		return status;
	}
	
	public void resourceClose(Connection conn, PreparedStatement stmt) {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resourceClose(Connection conn,CallableStatement cstmt) {
		try {
			conn.close();
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int insertMember(Member member) {
		
		CallableStatement stmt = null;
		Connection conn = null;
		String id = member.getId();
		String pw = member.getPw();
		String name = member.getName();
		String gender = member.getGender();
		String email = member.getEid() +"@"+ member.getSelDomain();
		String intro = member.getIntro();
		
//		String[] hobby = member.getParameterValues("hobby");

//		String hobby_str = new String();
//
//		for(int i=0; i<hobby.length; i++){
//			 hobby_str += hobby[i];
//			 if(i != hobby.length-1){
//				 hobby_str+=",";
//			 }
//		}

		int rs = 0;
		try {
			conn = ds.getConnection();
			/*
			 * String sql =
			 * String.format("INSERT INTO member(id, pw, name, gender, hobby, email, intro)"
			 * +"values('%s','%s','%s','%s','%s','%s','%s')",
			 * id,pw,name,gender,hobby_str,email,intro);
			 */
			
//			String sql = "call p_insertMember(?,?,?,?,?,?,?,?)";
			String sql = "call p_insert_Member(?,?,?)";
			
			stmt = conn.prepareCall(sql);
			
//			타입 순서에 맞게 오브젝트 배열로 만듬
			StructDescriptor st_desc = StructDescriptor.createDescriptor("OBJ_MEMBER",conn);
			Object[] obj_member = {id,pw,name,gender,email,intro};
			STRUCT member_rec = new STRUCT(st_desc,conn,obj_member);
			
			stmt.setObject(1, member_rec);
//			타입에 맞게 배열로 변경(오라클용)
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("STRING_NT",conn);
			ARRAY hobby_arr = new ARRAY(desc,conn,member.getHobby());
			
			stmt.setArray(2, hobby_arr);
//			stmt = conn.prepareCall(sql);
//			stmt.setString(1, id);
//			stmt.setString(2, pw);
//			stmt.setString(3, name);
//			stmt.setString(4, gender);
			
//			stmt.setString(6, intro);
//			stmt.setString(7, email);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			
			stmt.executeUpdate();
			rs = stmt.getInt(3);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			
			resourceClose(conn, stmt);
		}
		
		return rs;
		
		
	}
	@Override
	public int selectById(String id) {
		
		CallableStatement stmt = null;
		Connection conn = null;
		int rs = 0;
		
		String sql = "call p_idDoubleCheck(?,?)";
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setNString(1,id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			rs = stmt.getInt(2);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			resourceClose(conn, stmt);
		}
		
		return rs;
	}
	
	@Override
	public List<Member> getMember() {
		Connection conn = null;
		CallableStatement stmt = null;
		
		List<Member> member = new ArrayList<>();	
			
		String sql = "call p_get_member(?)";
			try {
				conn = ds.getConnection();
				stmt = conn.prepareCall(sql);
				stmt.registerOutParameter(1, OracleTypes.CURSOR);
				stmt.executeQuery();
				ResultSet rs = (ResultSet)stmt.getObject(1);
				
				while(rs.next()) {
					Member m = new Member();
					m.setId(rs.getString("id"));
					m.setName(rs.getString("name"));
					m.setGender(rs.getString("gender"));
					m.setWdate(rs.getString("wdate"));
					//취미
					
					if(rs.getArray("hobby_nm") != null) { 
						
						//컬렉션 중첩 테이블 데이터 가져오기.
						//ARRAY 랑 Array랑 다름
						ARRAY h_arr = ((OracleResultSet)rs).getARRAY("hobby_nm");
						System.out.println("취미 타입"+ h_arr.getSQLTypeName());
						System.out.println("취미 타입코드:" + h_arr.getBaseType());
						System.out.println("취미 갯수:"+ h_arr.length());
						
						String[] h_val = (String[])h_arr.getArray();
						
						for(String i : h_val) {
							System.out.println(i);
						}
						
						
						m.setHobby_str(Arrays.toString(h_val));
						
					}
					
					member.add(m);
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				resourceClose(conn, stmt);
			}
			
			
		return member;
	}
	
}
