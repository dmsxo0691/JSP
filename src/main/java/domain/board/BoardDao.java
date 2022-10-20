package domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DB;
import domain.board.dto.DetailRespDto;
import domain.board.dto.SaveReqDto;

public class BoardDao {

	public int deleteById(int id) {
		String sql = "DELETE FROM board WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int updateReadCount(int id) {
		String sql = "UPDATE board SET readCount = readCount+1 WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public DetailRespDto findById(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select b.id, b.title, b.content, b.readCount, b.userId, u.username ");
		sb.append("from board b inner join user u "); // SQL구문 append 시 반드시 줄마다 한칸씩 띄워주어야 한다
		sb.append("on b.userId = u.id "); // 이하 행과 붙어서 출력되는 것 방지
		sb.append("where b.id = ?");

		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			// Persistence API
			if (rs.next()) { // 커서를 이동하는 함수
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUserId(rs.getInt("b.userId"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int count() {
		String sql = "SELECT count(*), id FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}

	public List<Board> findAll(int page) {
		String sql = "SELECT * FROM  board ORDER BY id DESC LIMIT ?, 4";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 4);
			rs = pstmt.executeQuery();

			// Persistence API
			while (rs.next()) { // 커서를 이동하는 함수
				Board board = Board.builder().id(rs.getInt("id")).title(rs.getString("title"))
						.content(rs.getString("content")).readCount(rs.getInt("readCount")).userId(rs.getInt("userId"))
						.createDate(rs.getTimestamp("createDate")).build();
				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}

		return null;
	}

	public int save(SaveReqDto dto) { // 회원가입
		String sql = "INSERT INTO board(userId, title, content, createDate) VALUES(?,?,?, now())";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}
}
