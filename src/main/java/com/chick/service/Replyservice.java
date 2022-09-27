package com.chick.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chick.dto.Criteria;
import com.chick.dto.Reply;
import com.chick.dto.ReplyPageDTO;
import com.chick.dto.ReplyVO;

public interface Replyservice {

	public int register(Reply reply);

	public List<ReplyVO> getList(Criteria cri, long bno);
	
	public ReplyPageDTO getListPage(Criteria cri, long bno);

	public ReplyVO get(Long rno);

	public int modify(ReplyVO vo);

	public int remove(Long rno);
	
}