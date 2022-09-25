package com.chick.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chick.common.Loginimpl;
import com.chick.dto.AttachFile;
import com.chick.dto.Board;
import com.chick.dto.Criteria;
import com.cosmos.dao.BoardDao;
import com.cosmos.dao.BoardDaoimp;

import net.coobird.thumbnailator.Thumbnails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class BoardServiceImp implements BoardService {
	@Autowired
	BoardDao boardDao;
	
	private static final String CHARSET = "utf-8";

	@Override
	public List<Board> list(Criteria cri) {
		return boardDao.boardList(cri);
	}
	
	@Override
	public Board searchBoard(String seqno) {
		return boardDao.boardDetail(seqno);
	}
	
	@Override
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		factory.setDefaultCharset("CHARSET");//상수로 선언하는게 좋다.
		//factory form의 데이터를 가져와서 저장 utf8로 저장하는게 좋음
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		Board board = new Board();
		AttachFile attachfile = null;
		FileService fileService = new FileServiceimpl();
		
		try {
			List<FileItem> items = upload.parseRequest(req);
			//멀티파트 확인법
			for(FileItem item : items) {
				if (item.isFormField()) {//2진데이터인지 텍스트인지 구별해줌
					
					board = getFormParameter(item,board);
					
				}else {
					
					attachfile = fileService.fileUpload(item);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession sess = req.getSession();
		board.setId(((Loginimpl) sess.getAttribute("loginUser")).getId());
		return boardDao.insert(board, attachfile);
		
	}

	
	@Override
	public String update(HttpServletRequest req, HttpServletResponse resp) {

		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		factory.setDefaultCharset("CHARSET");//상수로 선언하는게 좋다.
		//factory form의 데이터를 가져와서 저장 utf8로 저장하는게 좋음
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		Board board = new Board();
		AttachFile attachfile = null;
		FileService fileService = new FileServiceimpl();
		
		try {
			List<FileItem> items = upload.parseRequest(req);
			//멀티파트 확인법
			for(FileItem item : items) {
				if (item.isFormField()) {//2진데이터인지 텍스트인지 구별해줌
					
					board = getFormParameter(item,board);
					
				}else {
					attachfile = fileService.fileUpload(item);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession sess = req.getSession();
		board.setId(((Loginimpl)sess.getAttribute("loginUser")).getId());
		
		boardDao.update(board, attachfile);
		
		
		String seqno = (String)req.getParameter("seqno");
		
		
		return board.getSeqNo();
	}
	@Override
	public Board getFormParameter(FileItem item,Board board) {
		
		//<input> : 태그값
		//System.out.printf("필드이름 : %s, 필드값: %s\n", item.getFieldName(), item.getString());
		String get = item.getString();
		//System.out.println(get);
		if (item.getFieldName().equals("title")) {
			board.setTitle(get);
		}else if (item.getFieldName().equals("open")) {
			board.setOpen(get);
		}else if (item.getFieldName().equals("content")) {
			board.setContent(get);
		}else if (item.getFieldName().equals("count")) {
			board.setCount(get);
		}else if (item.getFieldName().equals("seqno")) {
			board.setSeqNo(get);
		}
		
		
		return board;
		
	}
	
	
	@Override
	public int getTotalRec(Criteria cri) {
		
		return boardDao.getTotalRec(cri);
		
	}
	
	
	@Override
	public void delete(String seqno) {
		Map<String, String> map = boardDao.deleteByNo(seqno);
		
		String savefilename =  map.get("savefilename");
		String filepath =  map.get("filepath");
		String thumb_filename =  map.get("thumb_filename");
		String thumb_filepath =  map.get("thumb_filepath");
		
		
		if(savefilename != null) {
			//파일삭제
			File file = new File(filepath+savefilename);
			if (file.exists()) {
				file.delete();
				//썸네일 삭제
				if(thumb_filename != null) {
					File thumbfile = new File(thumb_filepath+thumb_filename);
					if(thumbfile.exists()) {
						thumbfile.delete();
					}
				}
			}
		}
	}

	@Override
	public String insert(Board board, MultipartFile files) {
		FileService fileService = new FileServiceimpl();
		return boardDao.insert(board,fileService.fileUpload(files));
	}
}
