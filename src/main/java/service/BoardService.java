package service;

import java.util.List;

import domain.board.Board;
import domain.board.BoardDao;
import domain.board.dto.SaveReqDto;

public class BoardService {
	private BoardDao boardDao;

	public BoardService() {
		boardDao = new BoardDao();
	}

	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
	}

	public List<Board> 글목록보기() {
		return boardDao.findAll();
	}
}
