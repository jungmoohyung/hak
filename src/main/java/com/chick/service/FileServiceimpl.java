package com.chick.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.chick.dto.AttachFile;
import com.chick.dto.Thumbnail;
import com.cosmos.dao.FileDao;

import net.coobird.thumbnailator.Thumbnails;

public class FileServiceimpl implements FileService {

	FileDao filedao = new FileDao();
	
	@Override
	public AttachFile fileUpload(FileItem item) throws Exception {
		//첨부파일 : 바이너리파일
		long fileSize = item.getSize();
		AttachFile attachfile = null;
		//System.out.println("업로드 파일 사이즈:" + fileSize);	
		if(fileSize > 0) {
			
			String fileUploadPath = "D:/jmh/upload/";
			String fileName = item.getName();
			// 라이브러리이용
			//System.out.println(FilenameUtils.getExtension(fileName)); 
			//System.out.println(FilenameUtils.getBaseName(fileName));
			
			//서브스티링 이용
			String split_fileName = fileName.substring(0,fileName.lastIndexOf("."));
			String split_extension = fileName.substring(fileName.lastIndexOf(".")+1);
			
			
			//System.out.println(split_fileName);
			//System.out.println(split_extension);
			
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			
			String saveFileName = split_fileName + "_"+ uid + "."+ split_extension;
			
			//System.out.println(fileUploadPath);
			//System.out.println("업로드 파일 이름 : "+ fileName); //운영체제에 따른 파일 경로뽑기 File.separator
			//System.out.println(saveFileName); //저장할 파일 이름
			//업로드 파일 저장
			File file = new File(fileUploadPath + saveFileName);
			item.write(file);
			
			attachfile = new AttachFile();
			
			attachfile.setFileName(fileName);
			attachfile.setFilePath(fileUploadPath);
			attachfile.setSaveFileName(saveFileName);
			attachfile.setFileSize(String.valueOf(fileSize));
			attachfile.setType(item.getContentType());
			
			String fileType = item.getContentType();
			String type = fileType.substring(0,fileType.indexOf("/"));

			if(type.equals("image")) {
				
				attachfile.setThumbnail(setThumbnail(saveFileName,file));
				
			}
				
		}
		
		return attachfile;
	}
	
	
	public Thumbnail setThumbnail(String saveFileName,File file) throws IOException {
		
		//섬네일 파일 저장
		String thumbFileName = "thumb_200x200_" + saveFileName;
		String thumbFilePath = "D:/jmh/upload/thumbnail/";
		File thumbFile = new File(thumbFilePath+thumbFileName);
		
		Thumbnails.of(file).size(200, 200).toFile(thumbFile);
		
		Thumbnail thumbnail = new Thumbnail();
		thumbnail.setFileName(thumbFileName);
		thumbnail.setFilePath(thumbFilePath);
		//파일 사이즈 구하기
		thumbnail.setFileSize(String.valueOf(thumbFile.length()));
		
		return thumbnail;
		
	}
	
	@Override
	public void fileDown(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			//req.setCharacterEncoding("utf8");//html에서 가져올때 한글이 깨짐
			
			String filename = req.getParameter("filename");
			String savefilename = req.getParameter("savefilename");
			String filepath = req.getParameter("filepath");
			
			File file = new File(filepath+savefilename);
			InputStream in = new FileInputStream(file); //인풋으로 인식
			OutputStream os = resp.getOutputStream(); //응답할 서블릿이 가진 아웃풋스트림에게 보내줌
			
			resp.reset(); // 이미 열려있는 출력스트림을 비움
			resp.setHeader("Cache-Control", "no-cache");//파일을 다시받을때 새롭게 받으라는뜻
			//한글시 깨지지않게 utf8로 지정
			resp.addHeader("Content-disposition", "attachment; fileName="+URLEncoder.encode(filename,"UTF-8"));
			byte[] fileByte = new byte[(int)file.length()];//파일 크기를 계산
			
			int readByte = 0;
			while((readByte = in.read(fileByte)) > 0) {
				os.write(fileByte,0,readByte);
				os.flush();
			}
			
			in.close();
			os.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int delete(String no, String savefilename, String filepath, String thumb_filename) {
		int rs = 0;
		rs = filedao.deleteByNo(no);
		//attachfile 레코드 삭제
		
		
		//파일삭제
		File file = new File(filepath+savefilename);
		if (file.exists()) {
			file.delete();
			//썸네일 삭제
			if(thumb_filename != null) {
				File thumbfile = new File(filepath+"thumbnail/"+thumb_filename);
				if(thumbfile.exists()) {
					thumbfile.delete();
				}
			}
			rs=1;
		}
		
		
		return rs;
	}


	@Override
	public AttachFile fileUpload(MultipartFile item){
		//첨부파일 : 바이너리파일
		long fileSize = item.getSize();
		AttachFile attachfile = null;
		//System.out.println("업로드 파일 사이즈:" + fileSize);	
		if(fileSize > 0) {
			
			String fileUploadPath = "D:/jmh/upload/";
			String fileName = item.getOriginalFilename();
			System.out.println(fileName);
			// 라이브러리이용
			//System.out.println(FilenameUtils.getExtension(fileName)); 
			//System.out.println(FilenameUtils.getBaseName(fileName));
			
			//서브스티링 이용
			String split_fileName = fileName.substring(0,fileName.lastIndexOf("."));
			String split_extension = fileName.substring(fileName.lastIndexOf(".")+1);
			
			
			//System.out.println(split_fileName);
			//System.out.println(split_extension);
			
			//중복된 파일을 업로드 하지 않기 위해 UID값 생성
			UUID uid = UUID.randomUUID();
			
			String saveFileName = split_fileName + "_"+ uid + "."+ split_extension;
			
//			System.out.println(fileUploadPath);
//			System.out.println("업로드 파일 이름 : "+ fileName); //운영체제에 따른 파일 경로뽑기 File.separator
//			System.out.println(saveFileName); //저장할 파일 이름
			//업로드 파일 저장
			File file = new File(fileUploadPath + saveFileName);
			
			try {
				item.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				
			}
			
			attachfile = new AttachFile();
			
			attachfile.setFileName(fileName);
			attachfile.setFilePath(fileUploadPath);
			attachfile.setSaveFileName(saveFileName);
			attachfile.setFileSize(String.valueOf(fileSize));
			attachfile.setType(item.getContentType());
			
			String fileType = item.getContentType();
			String type = fileType.substring(0,fileType.indexOf("/"));

			if(type.equals("image")) {
				
				try {
					attachfile.setThumbnail(setThumbnail(saveFileName,file));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
				
		}
		
		return attachfile;
	}

}
