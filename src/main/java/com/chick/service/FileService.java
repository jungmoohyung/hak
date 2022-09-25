package com.chick.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.chick.dto.AttachFile;
import com.chick.dto.Thumbnail;


public interface FileService {
	//서블릿기반
	public AttachFile fileUpload(FileItem item) throws Exception;
	//스프링기반
	public AttachFile fileUpload(MultipartFile item);
	
	public Thumbnail setThumbnail(String saveFileName,File file) throws IOException;
	
	public void fileDown(HttpServletRequest req, HttpServletResponse resp);

	public int delete(String no, String savefilename, String filepath, String thumb_filename);
	
	
}
