
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
			console.log("등산" + data.confirmationTotal)
			$("#NumberOfAlarm").html("❕");
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



			// 기존 내용 삭제
			$("#climbingHostAlarm").empty();
			$("#climbingMemberAlarm").empty();


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

				$("#climbingHostAlarm").append(`
    <div id = "climbingBoard${boardId}" class="btn btn-outline-dark mb-3" style="width: 500px;">
       🏕 ${title} 에 ${memberId} 님이 신청하셨습니다.
            <button class="agreePartyClimbing btn btn-primary mr-10" data-board-memberId="${memberId}" data-board-userId="${userId}" data-board-boardId="${boardId}" data-board-title="${title}">수락</button>
            <button class="disagreePartyClimbing btn btn-danger" data-board-memberId="${memberId}" data-board-userId="${userId}" data-board-boardId="${boardId}" data-board-title="${title}">거절</button>
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
					message = ` ${userId} 수락되었습니다. &nbsp;&nbsp;
					<button class="btn btn-primary memberConfirmationClimbing deleteAlarmClimbing" data-board-memberId="${memberId}" data-board-userId="${userId}" data-board-boardId="${boardId}" data-board-title="${title}" type="button"  value="${boardId}" style="justify-content: flex-end;">확인</button>`
					$("#climbingMemberAlarm").append(`
<div id = "climbingBoard${boardId}" class="btn btn-outline-primary mb-3" style="width: 500px; display: flex; ">
    <div id="alarmDiv${boardId}" class="d-flex align-items-center" style="padding-right: 10px; padding-left: 10px;">🏕 ${title} 신청이 ${message}
    </div>
</div>
`);
				} else if (participation === 1 && userId == memberId) {
					$("#climbingMemberAlarm").append(`
					<div id = "climbingBoard${boardId}" class="btn btn-outline-primary mb-3" style="width: 500px; display: flex; ">
    <div id="alarmDiv${boardId}" class="d-flex align-items-center" style="padding-right: 10px; padding-left: 10px;">🏕 ' ${title} ' 게시물이 올라갔습니다 &nbsp;&nbsp; 
        <button class = "btn btn-primary memberConfirmationClimbing deleteAlarmClimbing" data-board-memberId = "${memberId}" data-board-userId = "${userId}" data-board-boardId = "${boardId}" data-board-title = "${title}"  type="button" value="${boardId}">확인</button>
        &nbsp;&nbsp;<button class="btn btn-danger justConfirmationClimbing deleteAlarmClimbing" data-board-memberId="${memberId}" data-board-userId="${userId}" data-board-boardId="${boardId}" data-board-title="${title}" type="button"  value="${boardId}" style="justify-content: flex-end;">닫기</button>
    </div>
    </div>
    

`);
				}

				else if (participation === 2) {
					message = ` ${userId} 반려되었습니다. &nbsp;&nbsp;
					<button class="btn btn-secondary memberConfirmationClimbing deleteAlarmClimbing" data-board-memberId="${memberId}" data-board-userId="${userId}" data-board-boardId="${boardId}" data-board-title="${title}" type="button" value="${boardId}" style="justify-content: flex-end;">확인</button>`
					$("#climbingMemberAlarm").append(`
<div id = "climbingBoard${boardId}" class="btn btn-outline-danger mb-3" style="width: 500px; display: flex; ">
    <div id="alarmDiv${boardId}" class="d-flex align-items-center" style="padding-right: 10px; padding-left: 10px;">🏕 ${title} 신청이 ${message}
    </div>
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
			$(document).on("click", ".deleteAlarmClimbing", function() {
				var boardId = $(this).closest('.d-flex').attr('id').replace('alarmDiv', '');
				$("#alarmDiv" + boardId).remove();
				$("#climbingBoard" + boardId).remove();
			});

		}
	});
});




$("#climbingHostAlarm").on("click", ".agreePartyClimbing", function() {
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

$("#climbingHostAlarm").on("click", ".disagreePartyClimbing", function() {
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

$("#climbingMemberAlarm").on("click", ".memberConfirmationClimbing", function() {
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
		}
		,
		complete: function() {
			location.href = "/climbing/id/" + boardId;
		}
	});
});

$("#climbingMemberAlarm").on("click", ".justConfirmationClimbing", function() {
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
		}
		
	});
});