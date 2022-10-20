package service;

import java.util.List;

import domain.board.Board;
import domain.board.BoardDao;
import domain.board.dto.DetailRespDto;
import domain.board.dto.SaveReqDto;

public class BoardService {
	private BoardDao boardDao;

	public BoardService() {
		boardDao = new BoardDao();
	}

	public int 글삭제(int id) {
		return boardDao.deleteById(id);
	}

	public DetailRespDto 글상세보기(int id) {
		// 조회수 업데이트
		int result = boardDao.updateReadCount(id);
		if (result == 1) {
			return boardDao.findById(id);
		} else {
			return null;
		}
	}

	public int 글개수() {
		return boardDao.count();
	}

	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
	}

	public List<Board> 글목록보기(int page) {
		return boardDao.findAll(page);
	}
}
