<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

<div class="d-flex">
  <div class="row">
    <c:forEach items="${runningBoards}" var="board" varStatus="status">
      <div class="col-md-4">
        <div class="card" style="width: 18rem;">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <h5 class="card-title">
              <div class="me-auto">
                <h1>
                  <span id="boardIdText">${board.id}</span>
                  번게시물
                </h1>
              </div>
            </h5>
            <div>
              <div id="map${status.index + 1}" class="map-container" style="width: 300px; height: 300px;"></div>
              <div class="mb-3">
                <label for="" class="form-label">제목</label>
                <input type="text" class="form-control" value="${board.title}" readonly />
              </div>
              <!-- 본문 내용 -->
              <label for="" class="form-label">같이 달린 사람 🏃‍♀️🏃‍♂️🏃‍♀️🏃‍♂️ <c:forEach items="${members}" var="member">
                <c:if test="${board.id eq member.boardId}">
                  <div class="mb-3">
                    <input type="text" readonly class="form-control" value="${member.memberId}" />
                  </div>
                </c:if>
              </c:forEach>
              <div class="mb-3">
                <label for="" class="form-label">작성일시</label>
                <input type="text" readonly class="form-control" value="${board.inserted}" />
              </div>
              <input class="LatSubmit${status.index + 1}" type="hidden" name="Lat" value="${board.lat}" />
              <input class="LngSubmit${status.index + 1}" type="hidden" name="Lng" value="${board.lng}" />
              <a href="/running/id/${board.id}" class="btn btn-primary">Go somewhere</a>
            </div>
          </div>
        </div>
      </div>
      <c:set var="latNum" value="${board.lat}" />
      <c:set var="lngNum" value="${board.lng}" />
      
      <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
      <script>
        var latNum = ${latNum};
        var lngNum = ${lngNum};

        
        var mapContainer = document.getElementById('map${status.index + 1}');
        var mapOption = {
          center: new kakao.maps.LatLng(latNum, lngNum),
          level: 3
        };

        var map = new kakao.maps.Map(mapContainer, mapOption);

        var markerPosition = new kakao.maps.LatLng(latNum, lngNum);
        var marker = new kakao.maps.Marker({
          position: markerPosition
        });

        marker.setMap(map);
      </script>
    </c:forEach>
  </div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>
