function searchDetailAddrFromCoords(coords, callback) {
	geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
var mapOption = {
	center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	level: 5 // 지도의 확대 레벨 
};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 핑찍기 
var geocoder = new kakao.maps.services.Geocoder();

var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
	infowindow = new kakao.maps.InfoWindow({ zindex: 10 }); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

var clickedLatLng; // 클릭한 위치의 좌표 값을 저장할 변수	


// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
	clickedLatLng = mouseEvent.latLng;
	searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '다시 찍어주세요 ';

			var content = '<div class="bAddr">' + detailAddr + '</div>';

			// 마커를 클릭한 위치에 표시합니다 
			marker.setPosition(mouseEvent.latLng);
			marker.setMap(map);

			// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
			infowindow.setContent(content);
			infowindow.open(map, marker);

			let address = result[0].road_address.address_name;
			console.log(address.split(' ')[1]);


			console.log("위도 " + clickedLatLng.getLat());
			console.log("경도 " + clickedLatLng.getLng());

			$("#LatSubmit").val(clickedLatLng.getLat());
			$("#LngSubmit").val(clickedLatLng.getLng());
			$("#addressInput").val(address.split(' ')[1]);
		}
	});
});




// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {

	// GeoLocation을 이용해서 접속 위치를 얻어옵니다
	navigator.geolocation.getCurrentPosition(function(position) {

		var lat = position.coords.latitude; // 위도
		var lon = position.coords.longitude; // 경도

		var locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
		var message = '<div style="padding:5px;">현재 위치</div>'; // 인포윈도우에 표시될 내용입니다

		// 마커와 인포윈도우를 표시합니다
		displayCurrentLocation(locPosition, message);

	});

} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

	var locPosition = new kakao.maps.LatLng(33.450701, 126.570667);
	var message = 'geolocation을 사용할수 없어요..';

	displayCurrentLocation(locPosition, message);
}

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

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

function displayMarker(place) {

}

function displayCurrentLocation(locPosition) {
	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		map: map,
		position: locPosition
	});

	// 지도 중심좌표를 접속위치로 변경합니다
	map.setCenter(locPosition);
}


$("#inputValue").on("keyup", function(key) {
	if (key.keyCode == 13) {
		var keyword = document.getElementById("inputValue").value.trim();

		ps.keywordSearch(keyword, placesSearchCB);
	}
});
$(document).ready(function() {
	$("#searchPlace").click(function(event) {
		event.preventDefault();
		
		var keyword = document.getElementById("inputValue").value.trim();
		ps.keywordSearch(keyword, placesSearchCB);
	});	
})

marker.setMap(map);

// 클릭한 위치를 기준으로 장소 검색
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
	var latlng = mouseEvent.latLng;
	marker.setPosition(latlng);
});

$("#addButton").click(function() {
	let lng = $("#LngSubmit").val();


	if (lng === '') {
		event.preventDefault();  // 기본 이벤트 동작 중지
		alert("지도에 마커를 표시해주세요");
	}



})
