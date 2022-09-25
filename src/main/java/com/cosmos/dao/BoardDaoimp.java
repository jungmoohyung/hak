package com.cosmos.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chick.dto.AttachFile;
import com.chick.dto.Board;
import com.chick.dto.Criteria;
import com.chick.dto.Reply;
import com.chick.dto.Thumbnail;

@Repository
public class BoardDaoimp implements BoardDao{

//	private final Connection conn = OracleConn.getInstance().getConn();
	@Autowired
	private DataSource ds;
	
	public List<Board> boardList(Criteria cri) {
		Connection conn = null;
		CallableStatement stmt = null;
		List<Board> board = new ArrayList<Board>();
		String search_title = null;
		String search_name = null;
		
		if (cri.getCategory() != null && cri.getCategory().equals("title")) {
			search_title = cri.getKey();
		}
		if (cri.getCategory() != null && cri.getCategory().equals("name")) {
			search_name = cri.getKey();
		}
//		System.out.println("토탈의"+search_title);
//		System.out.println("토탈의 네임"+search_name);
		
		try {
			conn = ds.getConnection();
			String sql = "call p_getboardlist(?,?,?,?,?)";
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setString(3, search_name);
			stmt.setString(4, search_title);
			
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			
			stmt.executeQuery();

			ResultSet rs = (ResultSet)stmt.getObject(5);
			
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getString("rn"));
				b.setSeqNo(rs.getString("seqno"));
				b.setTitle(rs.getString("title"));
				b.setCount(rs.getString("count"));
				b.setName(rs.getString("name"));
				b.setWdate(rs.getString("wdate"));
				board.add(b);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		return board;
	}

	public Board boardDetail(String seqno) {
		Connection conn = null;
		CallableStatement stmt=null;
		/*
		 * sql = "select title, b.content, b.id id,"; sql
		 * +=" to_char(b.wdate, 'YY\"년\"-MM\"월\"-DD\"일\" HH\"시\":MI\"분\":SS\"초\" AM') wdate,"
		 * ; sql +=" count, (select name from member m where m.id = b.id) name"; sql
		 * +=" from board b where b.seqno = ?"; stmt = conn.prepareStatement(sql);
		 */
		Board b = new Board();
		try {
			conn = ds.getConnection();
			String sql = "call p_board_detail(?,?,?,?)";
			stmt = conn.prepareCall(sql);
			stmt.setInt(1,Integer.parseInt(seqno));
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			
			stmt.executeQuery();
			ResultSet rs = (ResultSet)stmt.getObject(2);
			
			if (rs.next()) {
				b.setContent(rs.getString("content"));
				b.setCount(rs.getString("count"));
				b.setId(rs.getString("id"));
				b.setName(rs.getString("name"));
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setOpen(rs.getString("open"));
				b.setSeqNo(seqno);
				
				List<Reply> re = new ArrayList<Reply>();
				
				rs = (ResultSet)stmt.getObject(3);
				
				while(rs.next()) {
					Reply r = new Reply();
					r.setComment(rs.getString("content"));
					r.setId(rs.getString("id"));
					r.setWdate(rs.getString("wdate"));
					re.add(r);
				}
				b.setReply(re);

				List<AttachFile> fileList = new ArrayList<AttachFile>();
				rs = (ResultSet)stmt.getObject(4);
				
				while (rs.next()) {
					AttachFile att = new AttachFile();
					att.setFileName(rs.getString("filename"));
					att.setSaveFileName(rs.getString("savefilename"));
					att.setFileSize(rs.getString("filesize"));
					att.setType(rs.getString("filetype"));
					att.setFilePath(rs.getString("filepath"));
					att.setAttSeqNo(rs.getString("att_seqno"));
					

					Thumbnail th = new Thumbnail();
					
					th.setFileName(rs.getString("thumb_name"));
					th.setFilePath(rs.getString("thumb_path"));
					th.setFileSize(rs.getString("thumb_size"));
//					th.setThumbSeqNo(rs.getString("thumb_seqno"));
					att.setThumbnail(th);
					
					fileList.add(att);
					}
				
				b.setAttachfile(fileList);
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		return b;
	}



	public String insert(Board board, AttachFile attachfile) {
		
		Connection conn = null;
		String seqno = null;
		
		CallableStatement stmt =null;
		try {
			conn = ds.getConnection();
			String sql = "call p_insert_board(?,?,?)";
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD",conn);
			Object[] obj_board = {board.getTitle(),board.getContent(),board.getOpen(),board.getId()};
			STRUCT board_rec = new STRUCT(st_board,conn,obj_board);
			
			stmt.setObject(1, board_rec);
			
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("ATTACH_NT",conn);
			ARRAY attach_arr = null;
			
			if(attachfile != null) {
//				여기서 attach가 여러개일경우
				
				StructDescriptor st_thumb = StructDescriptor.createDescriptor("OBJ_ATTACH_THUMB",conn);
				STRUCT thumb_rec=null;
				Object[] obj_thumb= null;
				if(attachfile.getThumbnail() != null) {
					
					obj_thumb = new Object[]{attachfile.getThumbnail().getFileName(),
						attachfile.getThumbnail().getFileSize(),
						attachfile.getThumbnail().getFilePath()};
				}
				
				thumb_rec = new STRUCT(st_thumb,conn,obj_thumb);
			
				
				STRUCT[] attach_rec = new STRUCT[1];
				StructDescriptor st_attach = StructDescriptor.createDescriptor("OBJ_ATTACH",conn);

				Object[] obj_attach = { attachfile.getFileName(), attachfile.getSaveFileName(),
										attachfile.getFileSize(), attachfile.getType(),
										attachfile.getFilePath(),thumb_rec};
				attach_rec[0] = new STRUCT(st_attach,conn,obj_attach);
//				여기까지 반복
				
				attach_arr = new ARRAY(desc,conn,attach_rec);
			}else {
				attach_arr = new ARRAY(desc,conn, null);
			}
			stmt.setArray(2, attach_arr);
			stmt.registerOutParameter(3, OracleType.VARCHAR2);
			stmt.executeQuery();
			seqno = stmt.getString(3);
				
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("롤백처리됨");
			}
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		
		return seqno;
	}

	public void insertThumbNail(AttachFile attachfile,String att_seqno) {
		Connection conn = null;
		String sql = "insert into attachfile_thumb(thumb_seqno,filename,filesize,filepath,att_seqno)"
				+ " values (thumb_seqno.nextval,?,?,?,?)";
		PreparedStatement stmt = null;
				try {
					conn = ds.getConnection();
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, attachfile.getThumbnail().getFileName());
					stmt.setString(2, attachfile.getThumbnail().getFileSize());
					stmt.setString(3, attachfile.getThumbnail().getFilePath());
					stmt.setString(4, att_seqno);
					stmt.executeQuery();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					resourceClose(conn,stmt);
				}
		
		
		
	}
	
	public String insertAttachFile(String seqno,AttachFile attachfile) {
		Connection conn = null;
		
		//첨부파일저장
		String sql = "insert into attachFile(att_seqno, board_seqno, filename, savefilename, filesize, filetype, FIlEPATH)";
		sql += " values(attachfile_seq.nextval, ?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		String att_seqno = null;
		try {
			conn = ds.getConnection();
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, seqno);
		stmt.setString(2, attachfile.getFileName());
		stmt.setString(3, attachfile.getSaveFileName());
		stmt.setString(4, attachfile.getFileSize());
		stmt.setString(5, attachfile.getType());
		stmt.setString(6, attachfile.getFilePath());
		stmt.executeQuery();
		
		
		sql = "select max(att_seqno) from attachfile";
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		att_seqno = rs.getString(1);
		
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		
		
		return att_seqno;
	}
	
	
	
	
	
	
	public void update(Board board, AttachFile attachfile) {
		Connection conn = null;
		
		//보드업데이트
		String sql = "call p_updateBoard(?,?,?,?)";
		CallableStatement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setString(1,board.getTitle());
			stmt.setString(2,board.getContent());
			stmt.setString(3,board.getOpen());
			stmt.setString(4,board.getSeqNo());
			
			stmt.executeQuery();
			
			//첨부파일
			if (attachfile != null) {
			
				String att_seqno = insertAttachFile(board.getSeqNo(),attachfile);
				String fileType = attachfile.getType();
				
				//섬네일
				if (fileType.substring(0,fileType.indexOf("/")).equals("image")) {
				
					insertThumbNail(attachfile,att_seqno);
				
				}
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		
		
	}

	public int getTotalRec(Criteria cri) {
		
		Connection conn = null;
		CallableStatement stmt =null;
		
		String search_title = null;
		String search_name =null;
		int total=0;
		if (cri.getCategory() != null && cri.getCategory().equals("title")) {
			search_title = cri.getKey();
		}
		if (cri.getCategory() != null && cri.getCategory().equals("name")) {
			search_name = cri.getKey();
		}
//		System.out.println("토탈의"+search_title);
//		System.out.println("토탈의 네임"+search_name);
		String sql = "call p_getboardtotal(?,?,?)";
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			stmt.setString(1, search_name);
			stmt.setString(2, search_title);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeQuery();
			total = stmt.getInt(3);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		return total;
	}

	public Map<String, String> deleteByNo(String seqno) {
		Connection conn = null;
		CallableStatement stmt=null;
		Map<String, String> map = new HashMap<>();
		String sql = "call p_deleteBoard(?,?,?,?,?)";
		try {
			conn = ds.getConnection();
			stmt = conn.prepareCall(sql);
			
			stmt.setString(1, seqno);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.registerOutParameter(5, OracleTypes.VARCHAR);
			stmt.executeQuery();
			
			map.put("savefilename", stmt.getString(2));
			map.put("filepath", stmt.getString(3));
			map.put("thumb_filename", stmt.getString(4));
			map.put("thumb_filepath", stmt.getString(5));

			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		
		return map;
		
	}
	
	public void resourceClose(Connection conn, PreparedStatement stmt) {
		try {
			if(stmt != null || conn != null) {
			stmt.close();
			conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resourceClose(Connection conn,CallableStatement cstmt) {
		try {
			if(cstmt != null || conn != null) {
				cstmt.close();
				conn.close();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String insertBoard(Board board,AttachFile attachFile) {

		Connection conn = null;
		String seqno = null;
		
		CallableStatement stmt =null;
		try {
			conn = ds.getConnection();
			String sql = "call p_insert_board(?,?)";
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD",conn);
			Object[] obj_board = {board.getTitle(),board.getContent(),board.getOpen(),board.getId()};
			STRUCT board_rec = new STRUCT(st_board,conn,obj_board);
			
			stmt.setObject(1, board_rec);
			
			stmt.registerOutParameter(2, OracleType.VARCHAR2);
			stmt.executeQuery();
			seqno = stmt.getString(2);
				
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("롤백처리됨");
			}
			e.printStackTrace();
		}finally {
			resourceClose(conn,stmt);
		}
		
		return seqno;
	}
}
