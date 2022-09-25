package com.cosmos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.chick.common.OracleConn;


public class FileDao {
	private final Connection conn = OracleConn.getInstance().getConn();
	
	public int deleteByNo(String no) {
		
		int rs = 0;
		//첨부파일 레코드삭제, 섬네일 레코드삭제
		String sql = "DELETE FROM attachfile_thumb WHERE att_seqno = ?";
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.executeUpdate();
			
			sql = "DELETE FROM attachfile WHERE att_seqno = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
}
