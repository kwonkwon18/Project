<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="border: 1fx solid #ccc; padding: 10px; overflow-y: auto; float: left; height: 90vh; width: 300px; display: none; background-color: blue; border-radius: 3%; margin-bottom: 15px; margin-left: 15px;" id="chatBox" class="position-fixed bottom-0 start-0">
	<button type="button" id="returnBtn" style="background-color: white; position: absolute; top: 0; left: 0; border-color: white; height: 40px; width: 40px;">
		<i class="fa-solid fa-arrow-left-long"></i>
	</button>
	<span style="position: absolute; left: 50%; transform: translateX(-50%);">나의 채팅방</span>
	<button type="button" class="chatClose" style="background-color: white; position: absolute; top: 0; right: 0; border-color: white; height: 40px; width: 40px;">
		<i class="fa-solid fa-x"></i>
	</button>
	<br />
	<br />
	<div style="display: inline-block; padding: 5px; background-color: #f0f0f0; border-radius: 15px; float: right;">ㅇ야양ㅇ</div>
	<br />
	<br />
	<div style="display: inline-block; padding: 5px; background-color: #f0f0f0; border-radius: 15px;">?</div>
	<br />
	<br />
</div>


<div style="border: 1fx solid #ccc; padding: 10px; overflow-y: auto; float: left; height: 90vh; width: 300px; display: none; background-color: blue; border-radius: 3%; margin-bottom: 15px; margin-left: 15px;" id="chatList" class="position-fixed bottom-0 start-0">
	<span style="position: absolute; left: 50%; transform: translateX(-50%);">채팅방 리스트</span>
	<button type="button" class="chatClose" style="background-color: white; position: absolute; top: 0; right: 0; border-color: white; height: 40px; width: 40px;">
		<i class="fa-solid fa-x"></i>
	</button>
	<br />
	<br />
</div>

<button type="button" id="chatButton" class="btn btn-lg btn-primary position-fixed bottom-0 start-0" style="border-radius: 50%; margin-bottom: 15px; margin-left: 15px;">
	<i class="fa-regular fa-comments"></i>
</button>