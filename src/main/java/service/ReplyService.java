package service;

import java.util.List;

import domain.reply.Reply;
import domain.reply.ReplyDao;
import domain.reply.dto.SaveReqDto;

public class ReplyService {

	private ReplyDao replyDao;

	public ReplyService() {
		replyDao = new ReplyDao();
	}

	public int 댓글삭제(int id) {
		return replyDao.deleteById(id);
	}

	public List<Reply> 글목록보기(int boardId) {
		return replyDao.findAll(boardId);
	}

	public int 댓글쓰기(SaveReqDto dto) {
		return replyDao.save(dto);
	}

	public Reply 댓글찾기(int id) {
		return replyDao.findById(id);
	}
}
