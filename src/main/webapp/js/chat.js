$("#chatButton").click(function() {
	$("#chatButton").hide();
	$("#chatList").show();
	$.ajax("/chat/open", {
		success: function(data) {
			var nickNameList = data.nickNameList;
			var lastMessageList = data.lastMessageList;
			for (var i = 0; i < nickNameList.length; i++) {
				$("#chatList").append(`
					<button type="button" style="width: 100%; height: 60px; position: absolute; margin-bottom: 5px;" class="openChatRoomBtn">
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
})


$("#returnBtn").click(function() {
	$("#chatBox").hide();
	$("#chatList").show();
})

$(".openChatRoomBtn").click(function() {
	$.ajax("/chat/room", {
		success: function(data) {
			$("#chatList").hide();
			$("#chatBox").show();
		}
	})
	$(this).find(".nickNameSpan").text();
})