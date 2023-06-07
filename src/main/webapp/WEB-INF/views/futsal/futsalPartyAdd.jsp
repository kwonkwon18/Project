<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<div class="container-lg">
	
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>매치 생성</h1>
	
			<div id="map" style="width: 80%; height: 350px;"></div>
			<div id="clickLatlng"></div>
			모일 장소를 찍어주세요 !
			<br />
			<input id="inputValue" type="text" value="하늘공원" />
			<button id="searchPlace">검색</button>
		

				<form method="post" enctype="multipart/form-data">
					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input id="titleInput" class="form-control" type="text" name="title" value="${party.title }" />
					</div>
					<div class="mb-3">
						<label for="titleInput" class="form-label">구장명</label>
						<input id="titleInput" class="form-control" type="text" name="stadium" value="${party.stadium }" />
					</div>
					<div class="mb-3">
						<label for="titleInput" class="form-label">모집인원</label>
						<input id="titleInput" class="form-control" type="Number" name="memberNum" value="${party.memberNum }" />
					</div>
					
					<input id="wirterInput" type="hidden" name="writer" value="${party.writer }" />
					
					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea rows="10" id="bodyTextarea" class="form-control" name="body">${party.body }</textarea>
					</div>
					<div>
						<label for="futsalGenderInput" class="form-label">매치유형</label>
						<select name="futsalGender" id="">
						<option value="남성">남성</option>
						<option value="여성">여성</option>
						<option value="혼성">혼성</option>
						</select>
						
						<%-- <input id="futsalGenderInput" class="form-control" type="text" name="futsalGender" value="${party.futsalGender }" /> --%>
					</div>
					<div class="mb-3">
						<label for="dateInput" class="form-label">매치 시간</label>
						<input id="dateInput" class="form-control" type="date" name="startDate" value="${party.startDate }" />
						<input id="dateInput" class="form-control" type="Time" name="startTime" value="${party.startTime }" />
					</div>
					<input id="LatSubmit" type="hidden" name="Lat" value="" />
					<input id="LngSubmit" type="hidden" name="Lng" value="" />
					<div class="mb-3">
						<input class="btn btn-primary" type="submit" value="등록" />
					</div>
				</form>
			</div>
		</div>
	</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
	<script>
		// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
		var infowindow = new kakao.maps.InfoWindow({
			zIndex : 1
		});

		var mapContainer = document.getElementById('map'); // 지도를 표시할 div
		var mapOption = {
			center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			level : 4
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
			//var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
			//message += '경도는 ' + latlng.getLng() + ' 입니다';

			document.getElementById('LatSubmit').value = latlng.getLat();
			document.getElementById('LngSubmit').value = latlng.getLng();

			//var resultDiv = document.getElementById('clickLatlng');
			//resultDiv.innerHTML = message;

		});
	</script>
</body>
</html>