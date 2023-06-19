$("#alarmList").click(function() {
	$.ajax("/running/alarm", {
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

				console.log(boardId)
				console.log(memberId)
				console.log(title)
				console.log(userId)

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
				if (participation === 1 && userId != memberId) {
					message = ` ${userId} 수락되었습니다. <button data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" class="memberConfirmation deleteAlarm" value="${boardId}">확인</button>`
					$("#MemberAlarm").append(`
    <div id="alarmDiv${boardId}" class="d-flex" style="padding-right: 10px; padding-left: 10px;">
        *** ${title} 신청이 ${message} == ${boardId}
    </div>
`);
				} else if (participation === 2) {
					message = ` ${userId} 반려되었습니다. <button data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" class="memberConfirmation deleteAlarm" value="${boardId}">확인</button>`
					$("#MemberAlarm").append(`
    <div id="alarmDiv${boardId}" class="d-flex" style="padding-right: 10px; padding-left: 10px;">
        *** ${title} 신청이 ${message} == ${boardId}
    </div>
`);
				} else if (participation === 0) {

				}

				console.log("&&" + boardId);
				console.log("&&" + memberId);
				console.log("&&" + title);
				console.log("&&" + userId);
				console.log("&&" + participation);


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

	console.log(memberId);
	console.log(userId);
	console.log(boardId);
	console.log(title);

	const data = { boardId, userId, memberId };
	console.log(data);

	$.ajax("/running/agreeParty", {
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

	console.log(memberId);
	console.log(userId);
	console.log(boardId);
	console.log(title);

	const data = { boardId, userId, memberId };
	console.log(data);

	$.ajax("/running/disagreeParty", {
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

	console.log(memberId);
	console.log(userId);
	console.log(boardId);
	console.log(title);

	const data = { boardId, userId, memberId };
	console.log(data);

	$.ajax("/running/confirmation", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(data) {

		},
		error: function() {
			alert("접수 오류발생.");
		},
		complete: function() {
			location.href = "/running/id/" + boardId;
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