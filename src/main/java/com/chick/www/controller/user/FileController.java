package com.chick.www.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chick.service.FileService;
import com.chick.service.FileServiceimpl;

@WebServlet("/file/*")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	FileService fileService = new FileServiceimpl();
    public FileController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req,resp);
	}
	

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doAction(req,resp);
	}

	private void doAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String uri = req.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
		switch(cmd) {
		case "fileDown"	:
			fileService.fileDown(req, resp);
			break;
			
		case "fileDel" :
			//파일삭제 레코드삭제
//			System.out.println(req.getParameter("no"));
			
			String no = req.getParameter("no");
			String savefilename = req.getParameter("savefilename");
			String filepath = req.getParameter("filepath");
			String thumb_filename = req.getParameter("thumb_filename");
			
			int rs = fileService.delete(no,savefilename,filepath,thumb_filename);
			System.out.println("파일삭제결과"+rs);
			
			PrintWriter out = resp.getWriter();
			
			out.print(rs);
			
			
			break;
		default :
			System.out.println("테스트중");
		}
		
		
	}
	
	
	
	
	
	void goView(HttpServletRequest req, HttpServletResponse resp, String viewPage) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
		rd.forward(req, resp);
		
	}
	
	
}
