var lastChatRoomId;

$("#chatButton").click(function() {
	$("#chatButton").hide();
	$("#chatList").show();
	$.ajax("/chat/open", {
		contentType: "application/json",
		success: function(data) {
			var nickNameList = data.nickNameList;
			var lastMessageList = data.lastMessageList;
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
					</button>
				`);
			}
		}
	});
});

$(".chatClose").click(function() {
	$("#chatButton").show();
	$("#chatList").hide();
	$("#chatBox").hide();
	$("#chatListContainer").remove("");
	$("#chatContainer").remove("");
})


$("#returnBtn").click(function() {
	$("#chatBox").hide();
	$("#chatList").show();
	$("#chatContainer").remove("");
})

$("#chatList").on("click", ".openChatRoomBtn", function() {
	$("#chatList").hide();
	$("#chatBox").show();
	var nickName = $(this).find(".nickNameSpan").text();
	$.ajax("/chat/room", {
		data: { yourNickName: nickName },
		contentType: "application/json",
		success: function(data) {
			var chatList = data.chatList;
			var myId = data.myId;
			$("#chatBox").append(`
				<div id="chatContainer"></div> 
			`)
			for (const chat of chatList) {
				lastChatRoomId = chat.chatRoomId;
				if (chat.senderId === myId) {
					$("#chatContainer").append(`
						<div class="d-flex justify-content-end">
							<div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div> 
						</div>
					`)
				} else {
					$("#chatContainer").append(`
						<div class="d-flex justify-content-start">
							<div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px;">${chat.message}</div>
						</div>
					`)
				}
			}
		}
	})
})

$("#sendChatBtn").click(function() {
	const message = $("#chatTextArea").val();
	const chatRoomId = lastChatRoomId;
	const data = { message, chatRoomId};
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

})