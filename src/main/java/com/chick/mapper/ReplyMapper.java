package com.chick.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chick.dto.Criteria;
import com.chick.dto.Reply;
import com.chick.dto.ReplyVO;

public interface ReplyMapper {
	
//	xml에 데이터를 전달할때 = 매개변수로
	public int insert(Reply reply);

	public List<ReplyVO> getList(@Param("cri") Criteria cri,@Param("bno") long bno);
	
}