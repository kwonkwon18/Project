$(document).ready(function() {
	// 검색 폼의 submit 이벤트를 처리합니다.
	$('#search').click(function(event) {
		event.preventDefault(); // 기본 동작인 폼 제출을 막습니다.

		// 검색어를 가져옵니다.
		var searchTerm = $('#searchInput').val();
		var i = 0;
		// AJAX 요청을 보냅니다.
		$.ajax({
			url: '/running/search',  // 검색 요청을 처리하는 서버의 엔드포인트 URL을 입력합니다.
			method: 'get',   // 요청 메서드를 선택합니다 (GET, POST 등).
			data: { search: searchTerm },  // 요청 데이터로 검색어를 전달합니다.
			success: function(response) {
				var climbingMateList = response.result;
				$("#mateMapData").remove("");
				$("#mateMapBoxItem").remove("");

				$("#mateMapBox").append(`
					<div id="mateMapBoxItem">
					
					</div>
				`)
				for (const board of climbingMateList) {
					if (i < 3) {
						i = i + 1;
						$("#mateMapBoxItem").append(`
						<div class="col-md-4">
							<div class="card" style="width: 18rem; margin-left: 40px;">
								<div class="card-body">
									<h5 class="card-title">🌄${board.title}</h5>
									<p class="card-text">작성자: ${board.writer}</p>
									<p class="card-text">작성일자: ${board.inserted}</p>
									<div style="text-align: right">
										<a href="/climbing/mateId/${board.id}" class="btn btn-primary">더보기</a>
									</div>
								</div>
							</div>
						</div>
				  	`)
					}
				}
				// 요청이 성공하면 결과를 처리합니다.
				// response 변수에 서버에서 받은 응답이 저장됩니다.
			},
		});
	});
});




//$("#joinPartyBtn").click(function() {
//	const total = $("#totalPeople").text().trim();
//	const boardId = $("#boardIdText").text().trim();
//	const userId = $("#writerText").val().trim();
//	const current = $("#currentPeople").text().trim();
//
//	const data = { boardId, userId };
//
//	$.ajax("/climbing/joinParty", {
//		method: "post",
//		contentType: "application/json",
//		data: JSON.stringify(data),
//
//		success: function(data) {
//
//			$("#currentPeople").text(data.count);
//
//			var currentNumber = $("#currentPeople").text().trim();
//			var totalNumber = $("#totalPeople").val().trim();
//
//			console.log(currentNumber)
//			console.log(totalNumber)
//
//			if (currentNumber >= totalNumber) {
//				$("#joinPartyBtn").attr('disabled', 'disabled');
//			}
//
//		},
//		error: function(jqXHR) {
//			$("#currentPeople").text(jqXHR.responseJSON.message);
//		}
//
//
//	})
//
//
//})
//
$(document).ready(function() {
	var latNum = 37.566736219721896;
	var lngNum = 126.9779137163515;


	var mapContainer = document.getElementById('map');
	var mapOption = {
		center: new kakao.maps.LatLng(latNum, lngNum),
		level: 1
	};

	var map = new kakao.maps.Map(mapContainer, mapOption);

	var markerPosition = new kakao.maps.LatLng(latNum, lngNum);
	var marker = new kakao.maps.Marker({
		position: markerPosition
	});

	marker.setMap(map);


});

$("#all1").click(function() {
	$("#dropdown1").slideUp()
})
$("#search1").click(function() {
	$("#dropdown1").slideDown()
})
