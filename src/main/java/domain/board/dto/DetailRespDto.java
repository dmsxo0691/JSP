package domain.board.dto;

import lombok.Data;

@Data
public class DetailRespDto {
	private int id;
	private String title;
	private String content;
	private int readCount;
	private String username;
	private int userId;

	public String getTitle() {
		return title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}