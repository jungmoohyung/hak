package com.chick.www.controller.user;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chick.common.Loginimpl;
import com.chick.dto.Board;
import com.chick.dto.Criteria;
import com.chick.dto.Page;
import com.chick.service.BoardService;
import com.chick.service.BoardServiceImp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
//	@GetMapping("/list")
//	@PostMapping("/list")
	@RequestMapping(value="list", method= {RequestMethod.POST,RequestMethod.GET})
	public String list(Criteria cri,Model model) {
		
		if(cri.getCurrentPage() == 0) cri.setCurrentPage(1);
		if(cri.getRowPerPage() == 0) cri.setRowPerPage(3);
		//게시판 리스트
		List<Board> board = boardService.list(cri);
		
		model.addAttribute("pageMaker", new Page(boardService.getTotalRec(cri),cri));
		model.addAttribute("board", board);
		return "/board/boardList";
		
	}
	@GetMapping("detail")
	public String detail(@ModelAttribute("no") String seqno, Model model,RedirectAttributes rttr) {
	
		model.addAttribute("board", boardService.searchBoard(seqno));
		
		return "/board/Detail";
	}
	
	@GetMapping("modify")
	public void regform() {
	}
	
	@PostMapping("register")
	public String register(Board board,MultipartFile filename,HttpSession sess,
							RedirectAttributes rttr) {
		
		board.setId(((Loginimpl)sess.getAttribute("loginUser")).getId());
		
		rttr.addFlashAttribute("no", boardService.insert(board, filename));
		
		return "redirect:/board/detail?no";
	}
//		}else if (cmd.equals("boardDetail.bo")){
//			
//			String seqno = req.getParameter("no");
//			if(seqno == null) {
//				seqno = (String)req.getAttribute("seqno");
//			}
//			Board board = boardService.searchBoard(seqno);
//			String page = req.getParameter("page");
//			
//			req.setAttribute("board", board);
//			
//			if(page != null) {
//				goView(req,resp,"/board/modify.jsp");
//				
//			}else {
//				goView(req,resp,"/board/Detail.jsp");
//				
//			}
//			
//		}else if (cmd.equals("boardwrite.bo")) {
//			goView(req,resp,"/board/BoardWrite.jsp");
//			
//		}else if (cmd.equals("boardReg.bo")) {
//			req.setAttribute("seqno", boardService.insertBoard(req, resp));
//			goView(req,resp,"boardDetail.bo");
//			
//		}else if (cmd.equals("modify.bo")) {
//			
//			req.setAttribute("seqno", boardService.update(req,resp));
//			
//			goView(req,resp,"boardDetail.bo");
//			
//		}else if (cmd.equals("boardDelete.bo")) {
//			String seqno = req.getParameter("no");
//			boardService.delete(seqno);
//			goView(req,resp,"boardList.bo");
//		}
//	}
//	
//	
//	
//	
//	
//	void goView(HttpServletRequest req, HttpServletResponse resp, String viewPage) throws ServletException, IOException {
//		RequestDispatcher rd = req.getRequestDispatcher(viewPage);
//		rd.forward(req, resp);
//		
//	}

}
