package com.cosmos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chick.common.OracleConn;
import com.chick.dto.AttachFile;
import com.chick.dto.Board;
import com.chick.dto.Criteria;
import com.chick.dto.Reply;
import com.chick.dto.Thumbnail;

public class BoardDao2_SQL {

	private final Connection conn = OracleConn.getInstance().getConn();
	
	public List<Board> boardList(Criteria cri) {
		
		String sql = "select * from ("
				+ " SELECT rownum as rn, seqno, title, content, wdate, count, name FROM (select seqno, title, content,";
		sql += " TO_CHAR(b.wdate, 'YY\"년\"-MM\"월\"-DD\"일\" HH24\"시\":MI\"분\":SS\"초\" AM') wdate,";
		sql += " count, (SELECT name FROM member m WHERE m.id = b.id) name FROM board b order by seqno desc)";
		sql += " WHERE rownum <= ?*?) ";
		sql += " where 1=1 ";
		sql += " and rn > (? -1)*?";
	if(cri.getCategory() != null) {
		if(cri.getKey() != null) {
			sql += " and " + cri.getCategory() + " like ('%"+cri.getKey()+"%')";
		}
		
	}
		/* String sql = "select * from board order by seqno desc"; */
		PreparedStatement stmt;
		List<Board> board = new ArrayList<Board>();
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setInt(3, cri.getCurrentPage());
			stmt.setInt(4, cri.getRowPerPage());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Board b = new Board();
				b.setSeqNo(rs.getString("seqno"));
				b.setNo(rs.getString("rn"));
				b.setTitle(rs.getString("title"));
				b.setCount(rs.getString("count"));
				b.setName(rs.getString("name"));
				b.setWdate(rs.getString("wdate"));
				board.add(b);
			
			} 
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return board;
	}

	public Board boardDetail(String seqno) {
		/*
		 * sql = "select title, b.content, b.id id,"; sql
		 * +=" to_char(b.wdate, 'YY\"년\"-MM\"월\"-DD\"일\" HH\"시\":MI\"분\":SS\"초\" AM') wdate,"
		 * ; sql +=" count, (select name from member m where m.id = b.id) name"; sql
		 * +=" from board b where b.seqno = ?"; stmt = conn.prepareStatement(sql);
		 */
		Board b = new Board();
		try {
			String sql = "UPDATE board SET count = count+1 where seqno = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,seqno);
			stmt.executeUpdate();
			
			sql = "select title, b.content, b.id, b.open,";
			sql += " to_char(b.wdate, 'YY\"년\"-MM\"월\"-DD\"일\" HH\"시\":MI\"분\":SS\"초\" AM') wdate,";
			sql += " count, (select name from member m where m.id = b.id) name ";
			sql += " from board b";
			sql += " where b.seqno = ?";
			sql += " union all";
			sql += " select '', r.content, r.id, '',";
			sql += " to_char(r.wdate, 'YY\"년\"-MM\"월\"-DD\"일\" HH\"시\":MI\"분\":SS\"초\" AM'),";
			sql += " 0, (select name from member m where m.id = r.id)";
			sql += " from reply r";
			sql += " where r.board_seqno = ?";
		
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, seqno);
			stmt.setString(2, seqno);
			ResultSet rs = stmt.executeQuery();
			
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
				
				while(rs.next()) {
					Reply r = new Reply();
					r.setComment(rs.getString("content"));
					r.setId(rs.getString("id"));
					r.setWdate(rs.getString("wdate"));
					re.add(r);
				}
				b.setReply(re);
				
				//첨부파일 저장
				sql = "select * from attachfile where board_seqno = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,seqno);
				rs = stmt.executeQuery();
				
				
				
				List<AttachFile> fileList = new ArrayList<AttachFile>();
				
				while (rs.next()) {
					AttachFile att = new AttachFile();
					att.setFileName(rs.getString("filename"));
					att.setSaveFileName(rs.getString("savefilename"));
					att.setFileSize(rs.getString("filesize"));
					att.setType(rs.getString("filetype"));
					att.setFilePath(rs.getString("filepath"));
					att.setAttSeqNo(rs.getString("att_seqno"));
					
					
					if(rs.getString("filetype").contains("image")) {
					
					sql = "SELECT * FROM attachfile_thumb WHERE att_seqno = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,rs.getString("att_seqno"));
					ResultSet rs2 = stmt.executeQuery();
					
					
						while(rs2.next()) {
						Thumbnail th = new Thumbnail();
						
						th.setFileName(rs2.getString("filename"));
						th.setFilePath(rs2.getString("filepath"));
						th.setFileSize(rs2.getString("filesize"));
						th.setThumbSeqNo(rs2.getString("thumb_seqno"));
						att.setThumbnail(th);
						}
					}
					fileList.add(att);
				}
				b.setAttachfile(fileList);
				
				
				
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}



	public String insert(Board board, AttachFile attachfile) {
		
		
		String seqno = null;
		String sql = "insert into board(seqno,title,content,count,id";
		if(board.getOpen() !=null) sql += ",open";
				sql += ") ";
				sql += "values (board_seq.nextval,?,?,?,?";
		if(board.getOpen() !=null) sql += ",?";
				sql += ")";
		
		PreparedStatement stmt;
		try {

			conn.setAutoCommit(false); //자동 커밋 방지
			
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,board.getTitle());
			stmt.setString(2,board.getContent());
			stmt.setString(3,board.getCount());
			stmt.setString(4,board.getId());
			if(board.getOpen() !=null) {
			stmt.setString(5,board.getOpen());
			}
			stmt.executeUpdate();
			
			
			
			sql = "select max(seqno) as seqno from board";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			seqno = rs.getString("seqno");
				//첨부파일
				if (attachfile != null) {
				
					String att_seqno = insertAttachFile(seqno,attachfile);
					String fileType = attachfile.getType();
					
					//섬네일
					if (fileType.substring(0,fileType.indexOf("/")).equals("image")) {
					
						insertThumbNail(attachfile,att_seqno);
					
					}
				}
				
				
			conn.commit();
			conn.setAutoCommit(true);
			stmt.close();
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("롤백처리됨");
			}
			e.printStackTrace();
		}
		return seqno;
	}

	void insertThumbNail(AttachFile attachfile,String att_seqno) {
		
		String sql = "insert into attachfile_thumb(thumb_seqno,filename,filesize,filepath,att_seqno)"
				+ " values (thumb_seqno.nextval,?,?,?,?)";
		PreparedStatement stmt;
				try {
					
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, attachfile.getThumbnail().getFileName());
					stmt.setString(2, attachfile.getThumbnail().getFileSize());
					stmt.setString(3, attachfile.getThumbnail().getFilePath());
					stmt.setString(4, att_seqno);
					stmt.executeQuery();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
		
		
	}
	
	String insertAttachFile(String seqno,AttachFile attachfile) {
		
		//첨부파일저장
		String sql = "insert into attachFile(att_seqno, board_seqno, filename, savefilename, filesize, filetype, FIlEPATH)";
		sql += " values(attachfile_seq.nextval, ?,?,?,?,?,?)";
		PreparedStatement stmt;
		String att_seqno = null;
		try {
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
		}
		
		
		return att_seqno;
	}
	
	
	
	
	
	
	public void update(Board board, AttachFile attachfile) {
		
		//보드업데이트
		String sql = "UPDATE board SET title=?, content=?, open=? WHERE seqno=?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public int getTotalRec() {

		int total=0;
		
		String sql = "select count(*) as total from board where OPEN not in 'N'";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			total = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
