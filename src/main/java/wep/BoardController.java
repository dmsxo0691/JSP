package wep;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import domain.board.Board;
import domain.board.dto.CommonRespDto;
import domain.board.dto.DetailRespDto;
import domain.board.dto.SaveReqDto;
import domain.board.dto.UpdateReqDto;
import domain.reply.Reply;
import domain.user.User;
import service.BoardService;
import service.ReplyService;
import util.Script;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		BoardService boardService = new BoardService();
		ReplyService replyService = new ReplyService();
		HttpSession session = request.getSession();
		if (cmd.equals("saveForm")) {
			User principal = (User) session.getAttribute("principal");
			if (principal != null) {
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
				dis.forward(request, response);
			} else {
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
			}
		} else if (cmd.equals("save")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.?????????(dto);
			if (result == 1) { // ??????
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "???????????????");
			}
		} else if (cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page"));
			List<Board> boards = boardService.???????????????(page);
			request.setAttribute("boards", boards);

			int boardCount = boardService.?????????();
			int lastPage = (boardCount - 1) / 4; // 2/4 = 0, 3/4 = 0, 4/4 = 1, 9/4 = 2 ( 0page, 1page, 2page)
			double currentPosition = (double) page / (lastPage) * 100;

			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);

			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.???????????????(id);
			List<Reply> replys = replyService.???????????????(id);
			if (dto == null) {
				Script.back(response, "??????????????? ?????????????????????");
			} else {
				request.setAttribute("dto", dto);
				request.setAttribute("replys", replys);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}

		} else if (cmd.equals("delete")) {

			// 1. ?????? ?????? json ???????????? ?????? ??????????????? ??????
			int id = Integer.parseInt(request.getParameter("id"));

			// 2. DB?????? id????????? ??? ??????
			int result = boardService.?????????(id);

			// 3. ????????? json ???????????? ??????
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("??????");

			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			System.out.println("respData : " + respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
		} else if (cmd.equals("updateForm")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.???????????????(id);
			request.setAttribute("dto", dto);
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);
		} else if (cmd.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);

			int result = boardService.?????????(dto);

			if (result == 1) {
				response.sendRedirect("/JSP/board?cmd=detail&id=" + id);
			} else {
				Script.back(response, "??? ????????? ?????????????????????.");
			}
		} else if (cmd.equals("search")) {
			String keyword = request.getParameter("keyword");
			int page = Integer.parseInt(request.getParameter("page"));

			List<Board> boards = boardService.?????????(keyword, page);
			request.setAttribute("boards", boards);

			int boardCount = boardService.?????????(keyword);
			int lastPage = (boardCount - 1) / 4; // 2/4 = 0, 3/4 = 0, 4/4 = 1, 9/4 = 2 ( 0page, 1page, 2page)
			double currentPosition = (double) page / (lastPage) * 100;

			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
		}
	}
}