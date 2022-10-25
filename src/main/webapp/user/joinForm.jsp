<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<%@ include file="../layout/header.jsp"%>
	<%
	request.setCharacterEncoding("utf-8");
	%>
	<div class="container">
		<form action="/JSP/user?cmd=join" method="post"
			onsubmit="return valid()">
			<div class="d-flex justify-content-end">
				<button type="button" class="btn btn-info" onClick="usernameCheck()">중복체크</button>
			</div>
			<div class="form-group">
				<input type="text" name="username" id="username"
					class="form-control" placeholder="Enter Username" required />
			</div>

			<div class="form-group">
				<input type="password" name="password" class="form-control"
					placeholder="Enter Password" required />
			</div>

			<div class="form-group">
				<input type="email" name="email" class="form-control"
					placeholder="Enter Email" required />
			</div>

			<div class="d-flex justify-content-end">
				<button type="button" class="btn btn-info" onClick="goPopup();">주소검색</button>
			</div>
			<div class="form-group">
				<input type="text" name="address" id="address" class="form-control"
					placeholder="Enter Address" required readonly />
			</div>
			<br />
			<button type="submit" class="btn btn-primary">회원가입</button>
		</form>
	</div>

	<script>
		var isChecking = false;
		function valid() {
			if (isChecking == false) {
				alert("아이디 중복체크를 해주세요");
			}
			return isChecking;
		}
		function usernameCheck() {
			// DB에서 확인해서 정상이면 isChecking = true
			var username = $("#username").val();
			$.ajax({
				type : "POST",
				url : "/JSP/user?cmd=usernameCheck",
				data : username,
				contentType : "text/plain; charset=utf-8",
				dataType : "text"
			}).done(function(data) {
				if (data === 'ok') { // 유저네임 있다는 것
					isChecking = false;
					alert('유저네임이 중복되었습니다.')
				} else {
					isChecking = true;
					$("#username").attr("readonly", "readonly");
					alert("해당 유저네임을 사용할 수 있습니다.")
				}
			});
		}

		function goPopup() {
			var pop = window.open("/JSP/user/jusoPopup.jsp", "pop",
					"width=570,height=420, scrollbars=yes, resizable=yes");

		}

		function jusoCallBack(roadFullAddr) {
			var addressEl = document.querySelector("#address");
			addressEl.value = roadFullAddr;
		}
	</script>
</body>

<%@ include file="../layout/footer.jsp"%>
</html>