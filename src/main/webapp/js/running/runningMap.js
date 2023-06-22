let mapInfo = []; // 전역변수로 사용하고 싶음 

// 중심 좌표를 먼저 나오는 녀석으로 해줌
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
	mapOption = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level: 5 // 지도의 확대 레벨
	};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

$(document).ready(function() {
	// 검색 폼의 submit 이벤트를 처리합니다.

	mapInfo = []

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
				let runningMateList = response.result;
				console.log(runningMateList);
				$("#mateMapData").remove("");
				$("#mateMapBoxItem").remove("");

				console.log(runningMateList.title);

				$("#mateMapBox").append(`
          <div id="mateMapBoxItem">

          </div>
        `);

				for (let i = 0; i < 3; i++) {
					const runningMate = runningMateList[i];
					if (runningMate == null) {
						break;
					}

					mapInfo.push({
						content: runningMate.title,
						lat: runningMate.lat,
						lng: runningMate.lng
					});



					$("#mateMapBoxItem").append(`
            <div class="col-md-4">
              <div class="card" style="width: 18rem; margin-left: 40px;">
                <div class="card-body">
                  <h5 class="card-title">🙋‍♂️🙋‍♂️${runningMate.title}</h5>
                  <p class="card-text">작성자: ${runningMate.writer}</p>
                  <p class="card-text">작성일자: ${runningMate.inserted}</p>
                  <p class="card-text">모임장소: ${runningMate.address}</p>
                  <p class="card-text">모임시간: ${runningMate.time}</p>
                  <div style="text-align: right">
                    <a href="/running/id/${runningMate.id}" class="btn btn-primary">더보기</a>
                  </div>
                </div>
              </div>
            </div>
        
          `);
				}
				console.log("길이" + mapInfo.length)


			},
			complete: function() {
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
					mapOption = {
						center: new kakao.maps.LatLng(mapInfo[0].lat, mapInfo[0].lng), // 지도의 중심좌표
						level: 5 // 지도의 확대 레벨
					};

				var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

				console.log(3284023490 + mapInfo)
				console.log(123123 + mapInfo[0].content)
				console.log(123123 + mapInfo[0].lat)
				console.log(123123 + mapInfo[0].lng)

				var positions = [];

				console.log()
				for (var i = 0; i < mapInfo.length; i++) {
					var position = {
						content: mapInfo[i].content,
						latlng: new kakao.maps.LatLng(mapInfo[i].lat, mapInfo[i].lng)
					};
					positions.push(position);
				}

				for (var i = 0; i < positions.length; i++) {
					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						map: map, // 마커를 표시할 지도
						position: positions[i].latlng // 마커의 위치
					});

					// 마커에 표시할 인포윈도우를 생성합니다
					var infowindow = new kakao.maps.InfoWindow({
						content: positions[i].content // 인포윈도우에 표시할 내용
					});

					// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
					// 이벤트 리스너로는 클로저를 만들어 등록합니다
					// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
					kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
					kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
				}

				// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
				function makeOverListener(map, marker, infowindow) {
					return function() {
						infowindow.open(map, marker);
					};
				}

				// 인포윈도우를 닫는 클로저를 만드는 함수입니다
				function makeOutListener(infowindow) {
					return function() {
						infowindow.close();
					};
				}

				mapInfo = [];
			}


		});
	});
});

console.log("***" + mapInfo)

$("#all1").click(function() {
	$("#dropdown1").slideUp()
})
$("#search1").click(function() {
	$("#dropdown1").slideDown()
})
