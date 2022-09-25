package com.chick.service;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.chick.dto.Board;
import com.chick.dto.Criteria;

public interface BoardService {
	
	public List<Board> list(Criteria cri);
	
	public Board searchBoard(String seqno);
	
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp);
	
	public String insert(Board board, MultipartFile files);
	
	public String update(HttpServletRequest req, HttpServletResponse resp);
	
	void delete(String seqno);

	Board getFormParameter(FileItem item, Board board);

	int getTotalRec(Criteria cri);
}

