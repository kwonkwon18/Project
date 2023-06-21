$("#alarmList").click(function() {
	$.ajax("/climbing/alarm", {
		contentType: "application/json",
		success: function(data) {
			// 데이터로 들어갈 것 boardId, userId, memberId
			// data.boardId[0], data.memberId[0] ...
			// boardId 별, participation이 0인 리스트를 줘야함
			var alarmList = data.alarmList;
			var memberAlarmList = data.memberAlarmList;


			// 기존 내용 삭제
			$("#HostAlarm").empty();
			$("#MemberAlarm").empty();


			alarmList.forEach(function(item) {
				var boardId = item.boardId; // 보드아이디
				var memberId = item.memberId; // 멤버 닉네임
				var title = item.title; // 제목
				var userId = item.userId; // 유저 닉네임
				// 유저 아이디
				// 멤버 아이디 

				$("#HostAlarm").append(`
            <div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
              ${title} 에 ${memberId} 님이 신청하셨습니다. 
              <button class="agreeParty" data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}" >수락</button>
              <button class="disagreeParty" data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}" >거절</button>   
            </div>
          `);

			});

			// 멤버별 알람
			memberAlarmList.forEach(function(item) {
				var boardId = item.boardId; // 보드아이디
				var memberId = item.memberId; // 멤버 닉네임
				var title = item.title; // 제목
				var userId = item.userId; // 유저 닉네임
				var participation = item.participation; // 참여여부

				// 출력할 메시지 변수 초기화
				var message = "";

				// 참여여부(participation) 값에 따라 메시지 설정
				if (participation === 1) {
					message = `수락되었습니다. <button data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" class="memberConfirmation deleteAlarm" value="${boardId}">확인</button>`
				} else if (participation === 2) {
					message = `반려되었습니다. <button data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" class="memberConfirmation deleteAlarm" value="${boardId}">확인</button>`
				}

				$("#MemberAlarm").append(`
    <div id="alarmDiv${boardId}" class="d-flex" style="padding-right: 10px; padding-left: 10px;">
        *** ${title} 신청이 ${message} == ${boardId}
        
    </div>
`);
			});

			// 삭제 버튼에 대한 클릭 이벤트 처리
			$(document).on("click", ".deleteAlarm", function() {
				var boardId = $(this).closest('.d-flex').attr('id').replace('alarmDiv', '');
				$("#alarmDiv" + boardId).remove();
			});

		}
	});
});





$("#HostAlarm").on("click", ".agreeParty", function() {
	var memberId = $(this).data('board-memberid');
	var userId = $(this).data('board-userid');
	var boardId = $(this).data('board-boardid');
	var title = $(this).data('board-title');

	const data = { boardId, userId, memberId };

	$.ajax("/climbing/agreeParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {
			if (data.join) {
				alert("접수 수락하였습니다.");
			} else {
				alert("접수 수락 실패하였습니다.");
			}
		},
		error: function() {
			alert("접수 오류발생.");
		},
		complete: function() {
			location.reload();
		}
	});
});

$("#HostAlarm").on("click", ".disagreeParty", function() {
	var memberId = $(this).data('board-memberid');
	var userId = $(this).data('board-userid');
	var boardId = $(this).data('board-boardid');
	var title = $(this).data('board-title');

	const data = { boardId, userId, memberId };

	$.ajax("/climbing/disagreeParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {
			if (data.out) {
				alert("접수 반려 하였습니다.");
			} else {
				alert("접수 반려 실패하였습니다.");
			}
		},
		error: function() {
			alert("접수 오류발생.");
		},
		complete: function() {
			location.reload();
		}
	});
});

$("#MemberAlarm").on("click", ".memberConfirmation", function() {
	var memberId = $(this).data('board-memberid');
	var userId = $(this).data('board-userid');
	var boardId = $(this).data('board-boardid');
	var title = $(this).data('board-title');

	const data = { boardId, userId, memberId };

	$.ajax("/climbing/confirmation", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {

		},
		error: function() {
			alert("접수 오류발생.");
		},
		complete: function() {
			location.href = "/climbing/id/" + boardId;
		}
	});
});







/*
$("#").click(function() {
	$("#chatButton").hide();
	$("#chatList").show();
	showList();
});


function showList() {
	$.ajax("/chat/open", {
		contentType: "application/json",
		success: function(data) {
			var nickNameList = data.nickNameList;
			var lastMessageList = data.lastMessageList;
			var insertedList = data.insertedList;
			var timeList = data.timeList;
			var chatCount = data.chatCount;
			var chatInsertedList = data.chatInsertedList;
			$("#chatList").append(`
			<div id="chatListContainer"></div>
			`)
			$("#chatListContainer").append(`
					<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openChatRoomBtn" id="button0">
						<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
							<span class="nickNameSpan">${nickNameList[0]}</span>
							<span>님과의 대화방</span>
							<span class="ms-auto">${timeList[0]}</span>
						</div>
						<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
							<span>${lastMessageList[0]}</span>
							<span style="margin-left: auto;"class="badge text-bg-secondary">${chatCount[0]}</span>
						</div>
						<input type="hidden" class="inserted" value="${insertedList[0]}">
						<input type="hidden" class="chatInserted" value="${chatInsertedList[0]}">
					</button>
				`);
			for (var i = 1; i < nickNameList.length; i++) {
				for (var j = i - 1; j >= 0; j--) {
					if (chatInsertedList[i] > $("#chatListContainer").find("input.chatInserted").val()) {
						$(`#button${j}`).before(`
							<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="nickNameSpan">${nickNameList[i]}</span>
									<span>님과의 대화방</span>
									<span class="ms-auto">${timeList[i]}</span>
								</div>
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span>${lastMessageList[i]}</span>
									<span style="margin-left: auto;"class="badge text-bg-secondary">${chatCount[i]}</span>
								</div>
								<input type="hidden" class="inserted" value="${insertedList[i]}">
								<input type="hidden" class="chatInserted" value="${chatInsertedList[i]}">
							</button>
						`);
						break;
					} else if (chatInsertedList[i] < chatInsertedList[j]) {
						$(`#button${j}`).after(`
							<button type="button	" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="nickNameSpan">${nickNameList[i]}</span>
									<span>님과의 대화방</span>
									<span class="ms-auto">${timeList[i]}</span>
								</div>
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span>${lastMessageList[i]}</span>
									<span style="margin-left: auto;"class="badge text-bg-secondary">${chatCount[i]}</span>
								</div>
								<input type="hidden" class="inserted" value="${insertedList[i]}">
								<input type="hidden" class="chatInserted" value="${chatInsertedList[i]}">
							</button>
						`);
						break;
					}
				}
			}
		}
	});
}*/

$("#currentListBtn").click(function() {
	$("#myPostListBtn").removeClass("active");
	$("#yourPostListBtn").removeClass("active");
	$(this).addClass("active");
	$("#myInfoContainer").remove();
	$.ajax("/climbing/myPageJs", {
		success: function(data) {
			var totalMyData = data.totalMyData;
			var myNickName = data.MyNickName;
			$("#myInfo").after(`
				<div class="container-lg" style="max-width: 1200px;" id="myInfoContainer">
					<div class="row row-cols-1 row-cols-md-3 g-4" id="myInfoRow">
					</div>
				</div>
			`)
			for (var i = 0; i < 6; i++) {
				var idNumber = i + 1;
				if (myNickName === totalMyData[i].writer) {
					var post = "내가 올린 게시물";
				} else {
					var post = "내가 신청한 게시물";
				}
				$("#myInfoRow").append(`
					<div class="col">
						<div class="card text-white bg-success mb-3" style="max-width: 21rem; margin-left: 20px;">
							<h3 style="margin-left: 15px;">${post}</h3>
								<div class="card-body">
								<h5 class="card-title">
									<span id="boardIdText${idNumber}">"${totalMyData[i].id}"</span>
									번게시물
								</h5>
								<div id="map${idNumber}" class="map-container" style="width: 300px; height: 300px;"></div>
								<div>
									<div class="mb-3">
										<label for="" class="form-label">제목</label>
										<input type="text" class="form-control" value="${totalMyData[i].title}" readonly />
									</div>
									<div class="mb-3">
										<label for="" class="form-label">작성자</label>
										<input id="writerData${idNumber}" type="text" class="form-control" value="${totalMyData[i].writer}" readonly />
									</div>
									<div class="mb-3">
										<label for="" class="form-label">모임시간</label>
										<input id="timeText" type="text" class="form-control" value="${totalMyData[i].time}" readonly />
									</div>
									<button type="button" onclick="location.href='/climbing/id/${totalMyData[i].id}'">내 게시물 상세 보기</button>
									<input class="LatSubmit${idNumber}" type="hidden" name="Lat" value="${totalMyData[i].lat}" />
									<input class="LngSubmit${idNumber}" type="hidden" name="Lng" value="${totalMyData[i].lng}" />
								</div>
							</div>
						</div>
					</div>
				`)
				var latNum = totalMyData[i].lat;
				var lngNum = totalMyData[i].lng;
				var mapContainer = document.getElementById(`map${idNumber}`);
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
			}
		}
	})
})

$("#myPostListBtn").click(function() {
	$("#currentListBtn").removeClass("active");
	$("#yourPostListBtn").removeClass("active");
	$(this).addClass("active");
	$("#myInfoContainer").remove();
	$.ajax("/climbing/myPageJs", {
		success: function(data) {
			var totalMyData = data.totalMyData;
			var myNickName = data.MyNickName;
			$("#myInfo").after(`
				<div class="container-lg" style="max-width: 1200px;" id="myInfoContainer">
					<div class="row row-cols-1 row-cols-md-3 g-4" id="myInfoRow">
					</div>
				</div>
			`)
			for (var i = 0; i < totalMyData.length; i++) {
				if (myNickName === totalMyData[i].writer) {
					var idNumber = i + 1;
					$("#myInfoRow").append(`
						<div class="col">
							<div class="card text-white bg-primary mb-3" style="max-width: 21rem; margin-left: 20px;">
									<div class="card-body">
									<h5 class="card-title">
										<span id="boardIdText${idNumber}">"${totalMyData[i].id}"</span>
										번게시물
									</h5>
									<div id="map${idNumber}" class="map-container" style="width: 300px; height: 300px;"></div>
									<div>
										<div class="mb-3">
											<label for="" class="form-label">제목</label>
											<input type="text" class="form-control" value="${totalMyData[i].title}" readonly />
										</div>
										<div class="mb-3">
											<label for="" class="form-label">작성자</label>
											<input id="writerData${idNumber}" type="text" class="form-control" value="${totalMyData[i].writer}" readonly />
										</div>
										<div class="mb-3">
											<label for="" class="form-label">모임시간</label>
											<input id="timeText" type="text" class="form-control" value="${totalMyData[i].time}" readonly />
										</div>
										<button type="button" onclick="location.href='/climbing/id/${totalMyData[i].id}'">내 게시물 상세 보기</button>
										<input class="LatSubmit${idNumber}" type="hidden" name="Lat" value="${totalMyData[i].lat}" />
										<input class="LngSubmit${idNumber}" type="hidden" name="Lng" value="${totalMyData[i].lng}" />
									</div>
								</div>
							</div>
						</div>
					`)
					var latNum = totalMyData[i].lat;
					var lngNum = totalMyData[i].lng;
					var mapContainer = document.getElementById(`map${idNumber}`);
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
				}
			}
		}
	})
})

$("#yourPostListBtn").click(function() {
	$("#myPostListBtn").removeClass("active");
	$("#currentListBtn").removeClass("active");
	$(this).addClass("active");
	$("#myInfoContainer").remove();
	$.ajax("/climbing/myPageJs", {
		success: function(data) {
			var totalMyData = data.totalMyData;
			var myNickName = data.MyNickName;
			$("#myInfo").after(`
				<div class="container-lg" style="max-width: 1200px;" id="myInfoContainer">
					<div class="row row-cols-1 row-cols-md-3 g-4" id="myInfoRow">
					</div>
				</div>
			`)
			for (var i = 0; i < totalMyData.length; i++) {
				if (myNickName !== totalMyData[i].writer) {
					var idNumber = i + 1;
					$("#myInfoRow").append(`
						<div class="col">
							<div class="card text-white bg-primary mb-3" style="max-width: 21rem; margin-left: 20px;">
									<div class="card-body">
									<h5 class="card-title">
										<span id="boardIdText${idNumber}">"${totalMyData[i].id}"</span>
										번게시물
									</h5>
									<div id="map${idNumber}" class="map-container" style="width: 300px; height: 300px;"></div>
									<div>
										<div class="mb-3">
											<label for="" class="form-label">제목</label>
											<input type="text" class="form-control" value="${totalMyData[i].title}" readonly />
										</div>
										<div class="mb-3">
											<label for="" class="form-label">작성자</label>
											<input id="writerData${idNumber}" type="text" class="form-control" value="${totalMyData[i].writer}" readonly />
										</div>
										<div class="mb-3">
											<label for="" class="form-label">모임시간</label>
											<input id="timeText" type="text" class="form-control" value="${totalMyData[i].time}" readonly />
										</div>
										<button type="button" onclick="location.href='/climbing/id/${totalMyData[i].id}'">내 게시물 상세 보기</button>
										<input class="LatSubmit${idNumber}" type="hidden" name="Lat" value="${totalMyData[i].lat}" />
										<input class="LngSubmit${idNumber}" type="hidden" name="Lng" value="${totalMyData[i].lng}" />
									</div>
								</div>
							</div>
						</div>
					`)
					var latNum = totalMyData[i].lat;
					var lngNum = totalMyData[i].lng;
					var mapContainer = document.getElementById(`map${idNumber}`);
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
				}
			}
		}
	})
})

$("#currentListBtn").click();