var latNum = $("#LatSubmit").val();
var lngNum = $("#LngSubmit").val();
console.log("latNum:", latNum);
console.log("lngNum:", lngNum);

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center: new kakao.maps.LatLng(latNum, lngNum), // 지도의 중심좌표
		level: 3
		// 지도의 확대 레벨
	};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 마커가 표시될 위치입니다 
var markerPosition = new kakao.maps.LatLng(latNum, lngNum);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
	position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);


$("#joinPartyBtn").click(function() {

	const total = $("#totalPeople").text().trim();
	const boardId = $("#boardIdText").text().trim();
	const userId = $("#writerText").val().trim();
	const current = $("#currentPeople").text().trim();

	const data = { boardId, userId };

	$.ajax("/futsal/joinParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		success: function(data) {
			if (data.join) {
				alert("신청되었습니다.");
				$("#currentPeople").text(data.count);

				var currentNumber = parseInt($("#currentPeople").text().trim());
				var totalNumber = parseInt($("#totalPeople").text().trim());

				if (currentNumber >= totalNumber) {
					$("#joinPartyBtn").attr('disabled', 'disabled');
				}
			} else {
				alert("취소되었습니다" ); // "신청 불가능합니다." 메시지 표시
			}
			location.reload();
		},

		error: function(jqXHR) {
			alert("신청실패");
			$("#currentPeople").text(jqXHR.responseJSON.message);
		}
	});
});

$("#rejectPartyBtn").click(function() {

	alert("취소되었습니다. ");

	const total = $("#totalPeople").text().trim();
	const boardId = $("#boardIdText").text().trim();
	const userId = $("#writerText").val().trim();
	const current = $("#currentPeople").text().trim();

	const data = { boardId, userId };

	$.ajax("/futsal/rejectParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		success: function(data) {

			$("#currentPeople").text(data.count);

			var currentNumber = $("#currentPeople").text().trim();
			var totalNumber = $("#totalPeople").val().trim();

			console.log(currentNumber)
			console.log(totalNumber)


			location.reload();
		},
		error: function(jqXHR) {
			$("#currentPeople").text(jqXHR.responseJSON.message);
		}
	})
})




