var lastChatRoomId;
var repeat;
var groupRepeat;
var lastChatId;

function showGroupList() {
	$("#chatListContainer").remove();
	$("#chatButton").hide();
	$("#chatList").show();
	$("#chatBox").hide();
	$("#chatListSearchBtn").hide();
	$("#groupChatListSearchBtn").show();
	$("#searchRemove").hide();
	$("#groupSearchRemove").show();
	$.ajax("/groupChat/open", {
		contentType: "application/json",
		success: function(data) {
			var titleList = data.titleList;
			var lastMessageList = data.lastMessageList;
			var insertedList = data.insertedList;
			var timeList = data.timeList;
			var chatCount = data.chatCount;
			var chatInsertedList = data.chatInsertedList;
			$("#chatList").append(`
			<div id="chatListContainer"></div>
			`)
			for (var i = 0; i < insertedList.length; i++) {
				if (i === 0) {
					$("#chatListContainer").append(`
						<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button0">
							<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
								<span class="titleSapn">${titleList[0]}</span>
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
				}
				for (var j = i - 1; j >= 0; j--) {
					if (chatInsertedList[i] > $("#chatListContainer").find("input.chatInserted").val()) {
						$(`#button${j}`).before(`
							<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="titleSapn">${titleList[i]}</span>
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
					} else {
						$(`#button${j}`).after(`
							<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="titleSapn">${titleList[i]}</span>
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

$("#chatList").on("click", ".openGroupChatRoomBtn", function() {
	var title = $(this).find(".titleSpan").text();
	document.addEventListener('keyup', groupKeyupHandler);
	$("#chatList").hide();
	$("#chatBox").show();
	$(".chatNameTag").remove();
	$("#sendGroupChatBtn").show();
	$("#sendChatBtn").hide();
	$("#dChat").hide();
	$("#dGroupChat").show();
	$(`#returnBtn`).after(`
		<span style="white-space: nowrap; position: absolute; left: 50%; transform: translateX(-50%);" class="chatNameTag">${title} 채팅방</span>
	`);
	var inserted = $(this).find(".inserted").val();
	$.ajax("/groupChat/room", {
		data: { inserted: inserted },
		contentType: "application/json",
		success: function(data) {
			var chatList = data.chatList;
			var myId = data.myId;
			var lastSenderId;
			lastChatRoomId = data.chatRoomId;
			$("#chatBox").append(`
                <div id="chatContainer"></div> 
            `)
			for (const chat of chatList) {
				if (lastSenderId === chat.senderId) {
					if (chat.senderId === myId) {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
	    	                    </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	        	                    <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
	    	                    </div>
		                    `)
						}
					} else {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
		                            <div>${chat.time}</div>
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
				} else {
					if (chat.senderId === myId) {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">${chat.senderId}</div>
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
	    	                    </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">${chat.senderId}</div>
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	        	                    <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
	    	                    </div>
		                    `)
						}
					} else {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-start" style="padding-left: 10px;">${chat.senderId}</div>
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
		                            <div>${chat.time}</div>
		                        </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-start" style="padding-left: 10px;">${chat.senderId}</div>
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
		                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
		                            <div>${chat.time}</div>
		                        </div>
		                    `)
						}
					}
				}
					lastSenderId = chat.senderId;
			}
			scrollToBottom();
			if (chatList[chatList.length - 1] === undefined) {
				lastChatId = 0;
			} else {
				lastChatId = chatList[chatList.length - 1].id;
			}
			console.log(lastChatId);
			groupRepeat = setInterval(function() {
				currentGroupChatId(lastChatId, lastChatRoomId);
			}, 3000);
		}
	})
})

function currentGroupChatId(lastChatIdParam, chatRoomId) {
	$.ajax("/groupChat/check?chatRoomId=" + chatRoomId + "&lastChatId=" + lastChatIdParam, {
		success: function(chatList1) {
			const chatList = chatList1.chatList;
			console.log(chatList);
			var lastSenderId = chatList1.lastSenderId;
			if (chatList.length === 0) {
				return;
			}
			for (const chat of chatList) {
				console.log(lastSenderId);
				console.log(chat.senderId);
				if (lastSenderId === chat.senderId) {
					if (chat.senderId === chatList1.myUserId) {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
	    	                    </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	        	                    <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
	    	                    </div>
		                    `)
						}
					} else {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
		                            <div>${chat.time}</div>
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
				} else {
					if (chat.senderId === chatList1.myUserId) {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">${chat.senderId}</div>
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
	    	                    </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">${chat.senderId}</div>
	                	        <div class="d-flex justify-content-end" style="padding-right: 10px;">
	            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
	        	                    <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
	    	                    </div>
		                    `)
						}
					} else {
						if (chat.fileName !== null) {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-start" style="padding-left: 10px;">${chat.senderId}</div>
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
	          						<div>
										<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
									</div>
		                            <div>${chat.time}</div>
		                        </div>
		                    `)
						} else {
							$("#chatContainer").append(`
	                	        <div class="d-flex justify-content-start" style="padding-left: 10px;">${chat.senderId}</div>
		                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
		                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
		                            <div>${chat.time}</div>
		                        </div>
		                    `)
						}
					}
				}
			lastSenderId = chat.senderId;
			}
			lastChatId = chatList[chatList.length - 1].id;
			scrollToBottom();
		}
	})
}

$("#sendGroupChatBtn").click(function() {
	const message = $("#chatTextArea").val();
	const chatRoomId = lastChatRoomId;

	// 파일 정보를 FormData에 추가
	const formData = new FormData();
	formData.append("message", message);
	formData.append("chatRoomId", chatRoomId);

	// 파일 선택된 경우 FormData에 파일 추가
	const files = $("#fileInputBtn")[0].files;
	if (files.length > 0) {
		for (let i = 0; i < files.length; i++) {
			formData.append("files", files[i]);
		}
	}
	$.ajax("/groupChat/add", {
		method: "POST",
		data: formData,
		processData: false,
		contentType: false,
		complete: function() {
			$("#chatTextArea").val("");
			scrollToBottom();
		}
	})
})

$("#deleteGroupChatRoomModalButton").click(function() {
	$("#chatContainer").remove("");
	clearInterval(groupRepeat);
	$.ajax("/groupChat/deleteRoom/" + lastChatRoomId, {
		success: showList()
	})
})

$("#groupChatListSearchBtn").click(function() {
	var search = $("#chatListSearchText").val();
	$.ajax("/groupChat/findRoom?search=" + search, {
		success: function(data) {
			var titleList = data.titleList;
			var lastMessageList = data.lastMessageList;
			var insertedList = data.insertedList;
			var timeList = data.timeList;
			var chatCount = data.chatCount;
			var chatInsertedList = data.chatInsertedList;
			$("#chatListContainer").remove("");
			$("#chatList").append(`
			<div id="chatListContainer"></div>
			`)
			for (var i = 0; i < insertedList.length; i++) {
				if (i === 0) {
					$("#chatListContainer").append(`
						<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button0">
							<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
								<span class="titleSapn">${titleList[0]}</span>
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
				}
				for (var j = i - 1; j >= 0; j--) {
					if (chatInsertedList[i] > $("#chatListContainer").find("input.chatInserted").val()) {
						$(`#button${j}`).before(`
							<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="titleSapn">${titleList[i]}</span>
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
					} else {
						$(`#button${j}`).after(`
							<button type="button" style="width: 100%; height: 60px; margin-bottom: 5px;" class="openGroupChatRoomBtn" id="button${i}">
								<div class="d-flex" style="padding-right: 10px; padding-left: 10px;">
									<span class="titleSapn">${titleList[i]}</span>
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
	})
})

$("#groupSearchRemove").click(function() {
	$("#chatListSearchText").val("");
	showGroupList();
})

function groupKeyupHandler(event) {
	if (event.key === 'Enter') {
		document.getElementById('sendGroupChatBtn').click();
	}
};

$("#groupChatRoomListBtn").click(function() {
	showGroupList();
	$(this).addClass("active");
	$("#personalChatRoomListBtn").removeClass("active");
})