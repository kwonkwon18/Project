// 페이지가 로드될 때마다 호출해줄것임
$(document).ready(function() {
	console.log("작동은됨")
	$.ajax("/climbing/countOfAlarm", {
		contentType: "application/json",
		success: function(data) {
			console.log(data.confirmationTotal)

			if (data.confirmationTotal > 0) {
				$("#NumberOfAlarm").css("display", "block");
			}
			$("#NumberOfAlarm").html(data.confirmationTotal);
		}
	})
});


$("#alarmList").click(function() {
	$.ajax("/climbing/alarm", {
		contentType: "application/json",
		success: function(data) {
			// 데이터로 들어갈 것 boardId, userId, memberId
			// data.boardId[0], data.memberId[0] ...
			// boardId 별, participation이 0인 리스트를 줘야함
			var alarmList = data.alarmList;
			var memberAlarmList = data.memberAlarmList;
			
			console.log("111" + alarmList)
			console.log("222" +memberAlarmList)


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
               🏕 ${title} 에 ${memberId} 님이 신청하셨습니다. 
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
        🏕 ${title} 신청이 ${message} == ${boardId}
    </div>
`);
				} else if (participation === 1 && userId == memberId) {
					$("#MemberAlarm").append(`
    <div id="" class="d-flex" style="padding-right: 10px; padding-left: 10px;">
       🏕' ${title} ' 게시물이 올라갔습니다
    </div>
`);
				}

				else if (participation === 2) {
					message = ` ${userId} 반려되었습니다. <button data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" class="memberConfirmation deleteAlarm" value="${boardId}">확인</button>`
					$("#MemberAlarm").append(`
    <div id="alarmDiv${boardId}" class="d-flex" style="padding-right: 10px; padding-left: 10px;">
        🏕 ${title} 신청이 ${message} == ${boardId}
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

	console.log(memberId);
	console.log(userId);
	console.log(boardId);
	console.log(title);

	const data = { boardId, userId, memberId };
	console.log(data);

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

	console.log(memberId);
	console.log(userId);
	console.log(boardId);
	console.log(title);

	const data = { boardId, userId, memberId };
	console.log(data);

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