<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	button.active {font-weight: bold; text-decoration: underline;}
</style>

<div style="position: relative; display: none; border: 1fx solid #ccc; overflow-y: auto; float: left; height: 85vh; width: 300px; background-color: blue; border-radius: 3%; margin-bottom: 50px; margin-left: 15px; z-index: 1;" id="chatBox" class="position-fixed bottom-0 start-0">
	<div style="padding-top: 10px; position: sticky; top: 0px; background-color: red; height: 40px; width: 100%;">
		<button type="button" id="returnBtn" style="background-color: white; position: absolute; top: 0; left: 0; border-color: white; height: 40px; width: 40px;">
			<i class="fa-solid fa-arrow-left-long"></i>
		</button>
		<button type="button" class="chatClose" style="background-color: white; position: absolute; top: 0; right: 0; border-color: white; height: 40px; width: 40px;">
			<i class="fa-solid fa-x"></i>
		</button>
	</div>

	<div class="input-group" style="position: fixed; bottom: 10px; width: 300px;">
		<div class="btn-group dropup">
			<button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" id="dropupBtn">
				<i class="fa-solid fa-bars"></i>
			</button>
			<div class="dropdown-menu">
				<input type="file" multiple name="files" accept="image/*" id="fileInputBtn" />
				<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteChatRoomConfirmModal">방 나가기</button>
			</div>
		</div>
		<input type="text" style="height: 40px;" placeholder="채팅을 입력해주세요" id="chatTextArea" class="form-control" />
		<button id="sendChatBtn" class="btn btn-outline-danger">
			<i class="fa-regular fa-paper-plane"></i>
		</button>
	</div>
</div>


<div style="border: 1fx solid #ccc; padding: 10px; overflow-y: auto; float: left; height: 90vh; width: 300px; display: none; background-color: blue; border-radius: 3%; margin-bottom: 15px; margin-left: 15px; z-index: 1;" id="chatList" class="position-fixed bottom-0 start-0">
	<span style="position: absolute; left: 50%; transform: translateX(-50%);">채팅방 리스트</span>
	<button type="button" class="chatClose" style="background-color: white; position: absolute; top: 0; right: 0; border-color: white; height: 40px; width: 40px;">
		<i class="fa-solid fa-x"></i>
	</button>
	<br />
	<br />
	<div class="mb-3 container text-center">
		<div class="row">
			<button class="col active" type="button" id="personalChatRoomListBtn">1:1 채팅방</button>
			<button class="col" type="button" id="groupChatRoomListBtn">그룹 채팅방</button>
		</div>
	</div>
	<div class="mb-3" style="display: flex;">
		<input type="text" style="width: 200px; border: 0px;" id="chatListSearchText" />
		<button type="button" id="searchRemove" style="background-color: white; border-color: white; border: 0px;">
			<i class="fa-solid fa-x"></i>
		</button>
		<button type="button" style="margin-left: auto; border: 0px;" id="chatListSearchBtn">검색</button>
	</div>

</div>

<div class="modal fade" id="createChatRoom" tabindex="-1" aria-labelledby="chatRoomModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header" id="chatRoomModalBefore">
				<h1 class="modal-title fs-5" id="chatRoomModalLabel">대화방 생성</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-footer">
				<button type="button" id="createChatRoomBtn" class="btn btn-primary">예</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니요</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteChatRoomConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5">방 나가기</h1>
				<button class="btn-close" type="button" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">방에서 나가시겠습니까?</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-bs-dismiss="modal">닫기</button>
				<button id="deleteChatRoomModalButton" data-bs-dismiss="modal" type="button" class="btn btn-danger">방 나가기</button>
			</div>
		</div>
	</div>
</div>

<button type="button" id="chatButton" class="btn btn-lg btn-primary position-fixed bottom-0 start-0" style="border-radius: 50%; margin-bottom: 15px; margin-left: 15px; z-index: 1;">
	<i class="fa-regular fa-comments"></i>
</button>

