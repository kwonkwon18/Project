var lastChatRoomId;
var repeat;
var lastChatId;
var currentIndex = 0;
var searchResults = $('.highlight');

function showList() {
	$("#chatListContainer").remove();
	$("#chatButton").hide();
	$("#chatList").show();
	$("#chatBox").hide();
	$("#chatListSearchBtn").show();
	$("#groupChatListSearchBtn").hide();
	$("#searchRemove").show();
	$("#groupChatRoomListBtn").removeClass("active");
	$("#personalChatRoomListBtn").addClass("active");
	$("#groupSearchRemove").hide();
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
			for (var i = 0; i < nickNameList.length; i++) {
				if (i === 0) {
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
				}
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
					} else {
						$(`#button${j}`).after(`
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
					}
				}
			}
		}
	});
}

function scrollToBottom() {
	var chatBox = document.getElementById("chatBox");
	chatBox.scrollTop = chatBox.scrollHeight;
}

$("#chatButton").click(function() {
	$("#chatButton").hide();
	showList();
});

$(".chatClose").click(function() {
	document.removeEventListener('keyup', keyupHandler);
	$("#chatButton").show();
	$("#chatList").hide();
	$("#chatBox").hide();
	$("#chatListContainer").remove();
	$("#chatContainer").remove();
	clearInterval(repeat);
	clearInterval(groupRepeat);
})


$("#returnBtn").click(function() {
	document.removeEventListener('keyup', keyupHandler);
	$("#chatContainer").remove();
	clearInterval(groupRepeat);
	clearInterval(repeat);
	showList();
})

$("#chatList").on("click", ".openChatRoomBtn", function() {
	var nickName = $(this).find(".nickNameSpan").text();
	document.addEventListener('keyup', keyupHandler);
	$("#chatList").hide();
	$("#chatBox").show();
	$(".chatNameTag").remove();
	$("#sendGroupChatBtn").hide();
	$("#sendChatBtn").show();
	$("#dChat").show();
	$("#dGroupChat").hide();
	$("#chatMemberListBtn").hide();
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
					if (chat.fileName !== null) {
						$("#chatContainer").append(`
                	        <div class="d-flex justify-content-end" style="padding-right: 10px;" id="${chat.id}">
            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
          						<div>
									<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
								</div>
    	                    </div>
	                    `)
					} else {
						$("#chatContainer").append(`
                	        <div class="d-flex justify-content-end" style="padding-right: 10px;" id="${chat.id}">
            	                <div style="font-size: 12px; margin-top: auto; margin-right: 2px;">${chat.time}</div>
        	                    <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div> 
    	                    </div>
	                    `)
					}
				} else {
					if (chat.fileName !== null) {
						$("#chatContainer").append(`
	                        <div class="d-flex justify-content-start" style="padding-left: 10px;" id="${chat.id}">
          						<div>
									<img class="img-fluid img-thumbnail" src="${chat.imgUrl}" height="200" width="200" />
								</div>
	                            <div style="font-size: 12px; margin-top: auto; margin-left: 2px;">${chat.time}</div>
	                        </div>
	                    `)
					} else {
						$("#chatContainer").append(`
	                        <div class="d-flex justify-content-start" style="padding-left: 10px;" id="${chat.id}">
	                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
	                            <div style="font-size: 12px; margin-top: auto; margin-left: 2px;">${chat.time}</div>
	                        </div>
	                    `)
					}
				}
			}
			scrollToBottom();
			if (chatList[chatList.length - 1] === undefined) {
				lastChatId = 0;
			} else {
				lastChatId = chatList[chatList.length - 1].id;
			}
			console.log(lastChatId);
			repeat = setInterval(function() {
				currentChatId(lastChatId, lastChatRoomId);
			}, 3000);
		}
	})
})

function currentChatId(lastChatIdParam, chatRoomId) {
	$.ajax("/chat/check?chatRoomId=" + chatRoomId + "&lastChatId=" + lastChatIdParam, {
		success: function(chatList1) {
			const chatList = chatList1.chatList;
			if (chatList.length === 0) {
				return;
			}
			for (const chat of chatList) {
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
	                            <div style="font-size: 12px; margin-top: auto; margin-left: 2px;">${chat.time}</div>
	                        </div>
	                    `)
					} else {
						$("#chatContainer").append(`
	                        <div class="d-flex justify-content-start" style="padding-left: 10px;">
	                            <div style=" padding: 5px; background-color: #f0f0f0; border-radius: 15px; margin-bottom: 5px; word-break: break-all; max-width: 200px;">${chat.message}</div>
	                            <div style="font-size: 12px; margin-top: auto; margin-left: 2px;">${chat.time}</div>
	                        </div>
	                    `)
					}
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
	if (files.length > 1) {
		$("#chatTextArea").val(files[0].name + "....");
	} else {
		$("#chatTextArea").val(files[0].name);
	}
})

$("#sendChatBtn").click(function() {
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
	$.ajax("/chat/add", {
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

function keyupHandler(event) {
	if (event.key === 'Enter') {
		document.getElementById('sendChatBtn').click();
	}
};

$("#deleteChatRoomModalButton").click(function() {
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
	$.ajax("/chat/roomCreate", {
		method: "POST",
		contentType: "application/json",
		data: { yourNickName: yourNickName },
		success: function() {
			$(".btn-close").click();
			$("#chatContainer").remove("");
			showList();
		}
	})
})

$("#chatListSearchBtn").click(function() {
	var search = $("#chatListSearchText").val();
	$.ajax("/chat/findRoom?search=" + search, {
		success: function(data) {
			var nickNameList = data.nickNameList;
			var lastMessageList = data.lastMessageList;
			var insertedList = data.insertedList;
			var timeList = data.timeList;
			var chatCount = data.chatCount;
			var chatInsertedList = data.chatInsertedList;
			$("#chatListContainer").remove("");
			$("#chatList").append(`
			<div id="chatListContainer"></div>
			`)
			for (var i = 0; i < nickNameList.length; i++) {
				if (i === 0) {
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
				}
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
					} else {
						$(`#button${j}`).after(`
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
					}
				}
			}
		}
	})
})

$("#searchRemove").click(function() {
	$("#chatListSearchText").val("");
	showList();
})

$("#chatSearchOpenBtn").click(function() {
	$("#chatSearchBox").css("display", "flex");
	$("#chatSearchBtn").show();
	$("#nextBtn").hide();
})

$("#chatSearchRemove").click(function() {
	$("#chatSearch").val("");
	$("#chatSearchBox").css("display", "none");
})

$("#chatSearch").keyup(function() {
	$("#chatSearchBtn").show();
	$("#nextBtn").hide();
})

$("#chatSearchBtn").click(function() {
	$("#chatSearchBtn").hide();
	$("#nextBtn").show();
	var search = $("#chatSearch").val();
	var chatRoomId = lastChatRoomId;
	var count = 1;
	if($("#chatSendBtn").is(":hidden")) {
		$.ajax("/groupChat/search?search=" + search + "&chatRoomId=" + chatRoomId, {
			success: function(data) {
				var idList = data.chatList;
				var element = idList[0];
				if(idList.length <= 0) {
        			alert('검색어를 찾을 수 없습니다.');
				} else {
					document.getElementById(element).scrollIntoView(true);
					$("#nextBtn").click(function() {
						element = idList[count];
						document.getElementById(element).scrollIntoView(true);
						if(count === idList.length - 1) {
							count = 0;
						} else {
							count = count + 1;
						}
					})
				}
			}
		})		
	} else {
		$.ajax("/chat/search?search=" + search + "&chatRoomId=" + chatRoomId, {
			success: function(data) {
				var idList = data.chatList;
				var element = idList[0];
				if(idList.length <= 0) {
        			alert('검색어를 찾을 수 없습니다.');
				} else {
					document.getElementById(element).scrollIntoView(true);
					$("#nextBtn").click(function() {
						element = idList[count];
						document.getElementById(element).scrollIntoView(true);
						if(count === idList.length - 1) {
							count = 0;
						} else {
							count = count + 1;
						}
					})
				}
			}
		})
	}
});


$("#personalChatRoomListBtn").click(function() {
	showList();
	$(this).addClass("active");
	$("#groupChatRoomListBtn").removeClass("active");
});