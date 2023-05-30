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
			$("#chatList").append(`
			<div id="chatListContainer"></div>
			`)
			for (var i = 0; i < nickNameList.length; i++) {
				$("#chatListContainer").append(`
					<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openChatRoomBtn">
						<span class="nickNameSpan">${nickNameList[i]}</span>
						님과의 대화방
						<br />
						<span>${lastMessageList[i]}</span>
						<input type="hidden" class="inserted" value="${insertedList[i]}">
					</button>
				`);
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
	$("#chatListContainer").remove("");
	$("#chatContainer").remove("");
	clearInterval(repeat);
})


$("#returnBtn").click(function() {
	$("#chatBox").hide();
	$("#chatList").show();
	$("#chatListContainer").remove("");
	$("#chatContainer").remove("");
	clearInterval(repeat);
	showList();
})

$("#chatList").on("click", ".openChatRoomBtn", function() {
	$("#chatList").hide();
	$("#chatBox").show();
	var nickName = $(this).find(".nickNameSpan").text();
	var inserted = $(this).find(".inserted").val();
	$.ajax("/chat/room", {
		data: { inserted: inserted},
		contentType: "application/json",
		success: function(data) {
			var chatList = data.chatList;
			var myId = data.myId;
			lastChatRoomId = data.chatRoomId;
			$("#chatBox").append(`
                <div id="chatContainer" style="padding-bottom:40px;"></div> 
            `)
			for (const chat of chatList) {
				if (chat.senderId === myId) {
					$("#chatContainer").append(`
                        <div class="d-flex justify-content-end" style="padding-right: 10px;">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div> 
                        </div>
                    `)
				} else {
					$("#chatContainer").append(`
                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div>
                        </div>
                    `)
				}
			}
			lastChatId = chatList[chatList.length - 1].id;
			repeat = setInterval(function() {
				currentChatId(lastChatId, lastChatRoomId, $("#chatContainer"));
			}, 1000);

		}
	})
})

function currentChatId(lastChatId, chatRoomId, chatContainer) {
	$.ajax("/chat/check?chatRoomId=" + chatRoomId + "&lastChatId=" + lastChatId, {
		success: function(chatList) {
			if (chatList.length === 0) {
				return;
			}
			for (const chat of chatList) {
				if (chat.senderId === myId) {
					chatContainer.append(`
                        <div class="d-flex justify-content-end">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div> 
                        </div>
                    `)
				} else {
					chatContainer.append(`
                        <div class="d-flex justify-content-start">
                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div>
                        </div>
                    `)
				}
			}
			lastChatId = chatList[chatList.length - 1].id;
		}
	})
}


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
		}
	})
})

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