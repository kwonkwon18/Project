<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>같이 달릴 사람 !</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<div id="map" style="width: 100%; height: 350px;"></div>


	모일 장소를 찍어주세요 !
	<br />
	<input id="inputValue" type="text" placeholder="예) 하늘공원" />
	<br />
	<button id="searchPlace">검색</button>

	<div id="clickLatlng"></div>

	<div class="container-lg">

		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>게시물 작성</h1>
				<form method="post">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input id="titleInput" class="form-control" type="text" name="title" value="${runningBoard.title }" required />
					</div>

					<%-- 					<div class="mb-3">
						<label for="wirterInput" class="form-label">글쓴이</label>
						<input id="wirterInput" class="form-control" type="text" name="writer" value="${runningBoard.writer }" />
					</div> --%>

					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea rows="10" id="bodyTextarea" class="form-control" name="body" required>${runningBoard.body }</textarea>
					</div>

					<div class="mb-3">
						<label for="" class="form-label">인원수</label>
						<input id="peopleInput" type="number" required class="form-control" name="people" value="${runningBoard.people }" placeholder="숫자를 입력해주세요 ex) 1" />
					</div>
					
					<div class="mb-3">
						<label for="dateInput" class="form-label">모이는 시간</label>
						<input  required id = "dateInput" name = "time" type="datetime-local" />
					</div>

					<input id="LatSubmit" type="hidden" name="Lat" value="37.56676114114433" />
					<input id="LngSubmit" type="hidden" name="Lng" value="126.97869755300808" />
					<input id="addButton" disabled class="btn btn-primary" type="submit" value="등록" />
			</div>
			</form>
		</div>
	</div>
	</div>

	<!-- ******************************************************************  -->

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
	<script>
		var checkLat = false;
		var checkLng = false;
		var checktitleInput = false;

		var checkbodyTextarea = false;
		var checkpeopleInput = false;

		function enableSubmit() {
			if (checkLat && checkLng && checktitleInput && checkbodyTextarea && checkpeopleInput) {
				$("#addButton").removeAttr("disabled");
			} else {
				$("#addButton").attr("disabled", "");
			}
		}

		$("#titleInput").keyup(function() {

			checktitleInput = true;

			enableSubmit();
		});


		$("#writerInput").keyup(function() {
			checkwirterInput = true;
			enableSubmit();
		});

		$("#bodyTextarea").keyup(function() {
			checkbodyTextarea = true;
			enableSubmit();
		});

 
		
		$("#peopleInput").keyup(function() {
			checkpeopleInput = true;
			enableSubmit();
		});
		
		$("#peopleInput").click(function() {
			checkpeopleInput = true;
			enableSubmit();
		});
		

		// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
		var infowindow = new kakao.maps.InfoWindow({
			zIndex : 1
		});

		var mapContainer = document.getElementById('map'); // 지도를 표시할 div
		var mapOption = {
			center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			level : 1
		// 지도의 확대 레벨
		};

		// 지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);

		// 장소 검색 객체를 생성합니다
		var ps = new kakao.maps.services.Places();

		// 키워드로 장소를 검색합니다
		var keyword = document.getElementById("inputValue").value.trim();
		ps.keywordSearch(keyword, placesSearchCB);

		// 키워드 검색 완료 시 호출되는 콜백함수 입니다
		function placesSearchCB(data, status, pagination) {
			if (status === kakao.maps.services.Status.OK) {

				// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
				// LatLngBounds 객체에 좌표를 추가합니다
				var bounds = new kakao.maps.LatLngBounds();

				for (var i = 0; i < data.length; i++) {
					displayMarker(data[i]);
					bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
				}

				// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
				map.setBounds(bounds);
			}
		}

		// 지도에 마커를 표시하는 함수입니다
		function displayMarker(place) {

		}

		// 검색 버튼 클릭 이벤트를 등록합니다
		$("#searchPlace").click(function() {
			var keyword = document.getElementById("inputValue").value.trim();
			ps.keywordSearch(keyword, placesSearchCB);
		});

		var markerPosition = new kakao.maps.LatLng(37.566826, 126.9786567);

		var marker = new kakao.maps.Marker({
			position : markerPosition
		});

		marker.setMap(map);

		// 클릭한 위치를 기준으로 장소 검색
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
			var latlng = mouseEvent.latLng;
			marker.setPosition(latlng);

			// 이거는 저장해서 값으로 사용해야함 
			var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
			message += '경도는 ' + latlng.getLng() + ' 입니다';

			document.getElementById('LatSubmit').value = latlng.getLat();
			document.getElementById('LngSubmit').value = latlng.getLng();
			checkLat = true;
			checkLng = true;

			var resultDiv = document.getElementById('clickLatlng');
			resultDiv.innerHTML = message;

			enableSubmit();

		});
	</script>



</body>
</html>