<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>시월의 공간</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script src="https://kit.fontawesome.com/e06bccf31a.js"
	crossorigin="anonymous"></script>
	
<script src="http://code.jquery.com/jquery-3.6.0.js"></script>
    <script
      defer
      src="https://use.fontawesome.com/releases/v5.15.2/js/all.js"
      integrity="sha384-vuFJ2JiSdUpXLKGK+tDteQZBqNlMwAjhZ3TvPaDfN9QmbPb7Q8qUpbSNapQev3YF"
      crossorigin="anonymous"
    ></script>
    <script>
      let weatherIcon = {
        "01": "fas fa-sun",
        "02": "fas fa-cloud-sun",
        "03": "fas fa-cloud",
        "04": "fas fa-cloud-meatball",
        "09": "fas fa-cloud-sun-rain",
        10: "fas fa-cloud-showers-heavy",
        11: "fas fa-poo-storm",
        13: "fas fa-snwflake",
        50: "fas fa-smog",
      };
      var apiURI =
        "https://api.openweathermap.org/data/2.5/weather?id=1835848&appid=7135cfc6a8989754bea0259d02428674&units=metric";
      $.ajax({
        url: apiURI,
        dataType: "json",
        type: "GET",
        success: function (resp) {
          var $Icon = resp.weather[0].icon.substr(0, 2);
          var $Temp = Math.floor(resp.main.temp) + "º";
          var $city = resp.name;
          $(".currIcon").append(' <i class="' + weatherIcon[$Icon] + ' "></i>');
          $(".currTemp").prepend($Temp);
          $(".city").append($city);
        },
      });
    </script>
    <style>
      .weather {
        width: 300px;
        height: 150px;
        text-align: center;
        background-color: #fff;
        color: cornflowerblue;
      }
      .weather > div {
        float: left;
        height: 75px;
        line-height: 75px;
        width: 50%;
      }
      .weather > div.currIcon,
      .weather > div.currTemp {
        font-size: 50px;
      }
      .weather > div.city {
        width: 100%;
        font-size: 23px;
      }
    </style>

</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<div class="navbar_logo">
			<i class="fa-solid fa-kiwi-bird"
				style="font-size: 25px; color: #d49466"></i> <a class="navbar-brand"
				href="<%=request.getContextPath()%>/index.jsp">시월의 공간</a>
		</div>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<c:choose>
			<c:when test="${sessionScope.principal != null}">
				<div class="collapse navbar-collapse" id="collapsibleNavbar">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/board?cmd=saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/user?cmd=updateForm">회원정보</a>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/user?cmd=logout">로그아웃</a></li>
					</ul>
				</div>
			</c:when>
			<c:otherwise>
				<div class="collapse navbar-collapse" id="collapsibleNavbar">
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/user?cmd=joinForm">회원가입</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/user?cmd=loginForm">로그인</a></li>
					</ul>
				</div>
			</c:otherwise>
		</c:choose>

	</nav>
	<br>
	
	<div class="weather container">
      <div class="currIcon"></div>
      <div class="currTemp"></div>
      <div class="city"></div>
    </div>