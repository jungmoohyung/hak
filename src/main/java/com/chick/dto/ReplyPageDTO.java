package com.chick.dto;

import java.util.List;

public class ReplyPageDTO {
	
	private int total;
	private List<ReplyVO> reply;
	
	
	public ReplyPageDTO(int total, List<ReplyVO> reply) {
		this.total = total;
		this.reply = reply;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public List<ReplyVO> getReply() {
		return reply;
	}


	public void setReply(List<ReplyVO> reply) {
		this.reply = reply;
	}
}
