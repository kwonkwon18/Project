
		// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
		var infowindow = new kakao.maps.InfoWindow({
			zIndex : 1
		});

		var mapContainer = document.getElementById('map'); // 지도를 표시할 div
		var mapOption = {
			center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			level : 3
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

		});