package com.chick.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chick.dto.Criteria;
import com.chick.dto.Reply;
import com.chick.dto.ReplyVO;
import com.chick.mapper.ReplyMapper;


@Service
public class ReplyServiceImp implements Replyservice {

	private static final Logger log = LoggerFactory.getLogger(Replyservice.class);
	
	@Autowired
	private ReplyMapper mapper;
	
	
	@Override
	public int register(Reply reply) {
		log.info("reply register service called.." + reply);
		return mapper.insert(reply);
	}


	@Override
	public List<ReplyVO> getList(Criteria cri, long bno) {
		return mapper.getList(cri,bno);
	}

}
