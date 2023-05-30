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

				<div>
					

					<div class="d-flex">
						<div class="me-auto">
							<div class="mb-3">
								<h1 id="futsalPartyId">${party.id } 매치 정보</h1>
								<label for="" class="form-label"></label>
								<input type="text" class="form-control" value="${party.title }" readonly />
							</div>
							<div id="map" style="width: 500px; height: 350px;"></div>
						</div>
					</div>
					
					<div class="mb-3">
						<label for="" class="form-label">구장명</label>
						<input type="text" class="form-control" value="${party.stadium }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">본문</label>
						<textarea class="form-control" readonly rows="10" readonly >${party.body }</textarea>
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성자</label>
						<input type="text" class="form-control" value="${party.writer }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">지원인원</label>
						<input id="applyNum" type="Number" readonly class="form-control" value="${party.applyNum }" readonly /> ${party.applyNum }
					</div>
					<div class="mb-3">
						<label for="" class="form-label">모집인원</label>
						<input type="Number" readonly class="form-control" value="${party.memberNum }" readonly />
					</div>
					<div class="mb-3">
						<label for="dateInput" class="form-label">매치 시간</label>
						<input id="dateInput" class="form-control" type="date" name="startDate" value="${party.startDate }" readonly />
						<input id="dateInput" class="form-control" type="Time" name="startTime" value="${party.startTime }" readonly />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">매치유형</label>
						<input type="text" class="form-control" value="${party.futsalGender }" readonly />
					</div>
					<div>
						<input id="LatSubmit" type="hidden" name="Lat" value="${party.lat }" /> <br />
						<input id="LngSubmit" type="hidden" name="Lng" value="${party.lng }" />
					</div>
		
					<div>
						<a id="futsalPartyApply" class="btn btn-primary" href="/futsal/futsalPartyMember">지원하기</a>
						<a class="btn btn-secondary" href="/futsal/modify/${party.id }">수정</a>
						<button id="removeButton" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
					</div>

				</div>
			</div>
		</div>
		
		<div class="d-none">
			<form action="/futsal/remove" method="post" id="removeForm">
				<input type="text" name="id" value="${futsalBoard.id }" />
			</form>
		</div>

			<!-- Modal -->
			<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">삭제 하시겠습니까?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
						</div>
					</div>
				</div>
			</div>
	
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/futsal/futsalParty.js"></script>

	<script>
		String selectedColor = request.getParameter("futsalGender");
	</script>
	
	<!-- **************************************************  -->
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3f405ca1718e37ea86f8585e0ca94ef5
&libraries=services"></script>
		<script>
			var latNum = $("#LatSubmit").val();
			var lngNum = $("#LngSubmit").val();
			console.log("latNum:", latNum);
			console.log("lngNum:", lngNum);

			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			mapOption = {
				center : new kakao.maps.LatLng(latNum, lngNum), // 지도의 중심좌표
				level : 4
			// 지도의 확대 레벨
			};

			var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

			// 마커가 표시될 위치입니다 
			var markerPosition = new kakao.maps.LatLng(latNum, lngNum);

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				position : markerPosition
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);

			// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
			// marker.setMap(null);
		</script>
</body>
</html>