var lastChatRoomId;
var repeat;
var lastChatId;

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
}
$("#chatButton").click(function() {
	$("#chatButton").hide();
	$("#chatList").show();
	showList();
});

$(".chatClose").click(function() {
	$("#chatButton").show();
	$("#chatList").hide();
	$("#chatBox").hide();
	$("#chatListContainer").remove();
	$("#chatContainer").remove();
	clearInterval(repeat);
})


$("#returnBtn").click(function() {
	$("#chatBox").hide();
	$("#chatList").show();
	$("#chatListContainer").remove();
	$("#chatContainer").remove();
	clearInterval(repeat);
	showList();
})

$("#chatList").on("click", ".openChatRoomBtn", function() {
	var nickName = $(this).find(".nickNameSpan").text();
	$("#chatList").hide();
	$("#chatBox").show();
	$(".chatNameTag").remove();
	$(`#returnBtn`).after(`
		<span style="white-space: nowrap; position: absolute; left: 50%; transform: translateX(-50%);" class="chatNameTag">${nickName}님과의 채팅방</span>
	`);
	var inserted = $(this).find(".inserted").val();
	$.ajax("/chat/room", {
		data: { inserted: inserted },
		contentType: "application/json",
		success: function(data) {
			var chatList = data.chatList;
			var myId = data.myId;
			lastChatRoomId = data.chatRoomId;
			$("#chatBox").append(`
                <div id="chatContainer"></div> 
            `)
			for (const chat of chatList) {
				if (chat.senderId === myId) {
					$("#chatContainer").append(`
                        <div class="d-flex justify-content-end" style="padding-right: 10px;">
                            <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
                        </div>
                    `)
				} else {
					$("#chatContainer").append(`
                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
                            <div>${chat.time}</div>
                        </div>
                    `)
				}
			}
			scrollToBottom();
			if(chatList[chatList.length - 1] === undefined) {
				lastChatId = 0;				
			} else {
				lastChatId = chatList[chatList.length - 1].id;
			}
			console.log(lastChatId);
			repeat = setInterval(function() {
				currentChatId(lastChatId, lastChatRoomId, $("#chatContainer"));
			}, 3000);
		}
	})
})

function currentChatId(lastChatIdParam, chatRoomId, chatContainer) {
	$.ajax("/chat/check?chatRoomId=" + chatRoomId + "&lastChatId=" + lastChatIdParam, {
		success: function(chatList1) {
			const chatList = chatList1.chatList;
			console.log(chatList.myUserId);
			console.log(chatList.length);
			console.log(chatList);
			if (chatList.length === 0) {
				return;
			}
			for (const chat of chatList) {
				if (chat.senderId === chatList1.myUserId) {
					chatContainer.append(`
                        <div class="d-flex justify-content-end" style="padding-right: 10px;">
                            <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
                        </div>
                    `)
				} else {
					chatContainer.append(`
                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
                            <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
                        </div>
                    `)
				}
			}
			lastChatId = chatList[chatList.length - 1].id;
			scrollToBottom();
		}
	})
}

$("#fileInputBtn").on("change", function() {
	var files = $(this)[0].files;
	$("#dropupBtn").click();
	if(files.length > 1) {
		$("#chatTextArea").val(files[0].name + "....");		
	} else {
		$("#chatTextArea").val(files[0].name);		
	}
})

$("#sendChatBtn").click(function() {
	const message = $("#chatTextArea").val();
	const chatRoomId = lastChatRoomId;
	const data = { message, chatRoomId };
	$.ajax("/chat/add", {
		contentType: "application/json",
		method: "post",
		data: JSON.stringify(data),
		complete: function() {
			$("#chatTextArea").val("");
			scrollToBottom();
		}
	})
})

document.addEventListener('keyup', function(event) {
  if (event.key === 'Enter') {
    document.getElementById('sendChatBtn').click();
  }
});

$("#deleteChatRoomModalButton").click(function() {
	$("#chatBox").hide();
	$("#chatList").show();
	$("#chatListContainer").remove("");
	$("#chatContainer").remove("");
	clearInterval(repeat);
	$.ajax("/chat/deleteRoom/" + lastChatRoomId, {
		success: showList()
	})
})

//$(".chatRoomCheckBtn").click(function() {
//	var yourNickName = $(this).val();
//	$.ajax("/chat/roomCheck", {
//		contentType: "application/json",
//		data: { yourNickName : yourNickName },
//		success: function(data) {
//			console.log(data.check);
//			if(data.check) {
//				$("#chatListContainer").remove("");
//				$("#chatContainer").remove("");
//				$("#chatButton").hide();
//				$("#chatList").hide();
//				$("#chatBox").show();
//				$.ajax("/chat/roomOpen", {
//					data: { yourNickName : yourNickName },
//					contentType: "application/json",
//					success: function(data) {
//						var chatList = data.chatList;
//						var myId = data.myId;
//						lastChatRoomId = data.chatRoomId;
//						$("#chatBox").append(`
//			                <div id="chatContainer" style="padding-bottom:40px;"></div> 
//			            `)
//						for (const chat of chatList) {
//							if (chat.senderId === myId) {
//								$("#chatContainer").append(`
//			                        <div class="d-flex justify-content-end" style="padding-right: 10px;">
//			                            <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
//			                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
//			                        </div>
//			                    `)
//							} else {
//								$("#chatContainer").append(`
//			                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
//			                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
//			                            <div>${chat.time}</div>
//			                        </div>
//			                    `)
//							}
//						}
//						lastChatId = chatList[chatList.length - 1].id;
//						repeat = setInterval(function() {
//							currentChatId(lastChatId, lastChatRoomId, $("#chatContainer"));
//						}, 3000);
//			
//					}
//				})
//			} else {
//				$("#createChatRoomCheckBtn").click();
//			}
//		} 
//	})
//})

$("#createChatRoomBtn").click(function() {
	var modalBodyText = $(".chatRoomModalBody").text();
	var yourNickName = modalBodyText.substring(0, modalBodyText.indexOf("님과의"));
	console.log(yourNickName);
	$.ajax("/chat/roomCreate", {
		method: "POST",
		contentType: "application/json",
		data: { yourNickName: yourNickName },
		success: function() {
			$(".btn-close").click();
			$("#chatListContainer").remove("");
			$("#chatContainer").remove("");
			$("#chatButton").hide();
			$("#chatList").show();
			$("#chatBox").hide();
			showList();
		}
	})
})

function scrollToBottom() {
  var chatBox = document.getElementById("chatBox");
  chatBox.scrollTop = chatBox.scrollHeight;
}