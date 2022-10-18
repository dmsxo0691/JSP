<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 2번 필터를 회피하기 위해 sendRedirect 코드를 아래와 같이 변경
RequestDispatcher dis = request.getRequestDispatcher("board?cmd=list");
dis.forward(request, response); //톰캣이 생성하는 request와 response 재사용, 다시 접근x -> 내부 이동
%>