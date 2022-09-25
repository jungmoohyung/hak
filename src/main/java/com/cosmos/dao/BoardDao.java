package com.cosmos.dao;

import java.util.List;
import java.util.Map;

import com.chick.dto.AttachFile;
import com.chick.dto.Board;
import com.chick.dto.Criteria;

public interface BoardDao {
	public List<Board> boardList(Criteria cri);
	
	public Board boardDetail(String seqno);
	
	public String insert(Board board, AttachFile attachfile);
	
	public void insertThumbNail(AttachFile attachfile,String att_seqno);
	
	public String insertAttachFile(String seqno,AttachFile attachfile);
	
	public void update(Board board, AttachFile attachfile);
	
	public int getTotalRec(Criteria cri);
	
	public Map<String, String> deleteByNo(String seqno);

	public String insertBoard(Board board, AttachFile attachFile);
	
}
