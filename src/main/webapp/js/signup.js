// 이메일 중복확인 버튼이 클릭되면
$("#checkEmailBtn").click(function() {
	const email = $("#inputEmail").val();
	$.ajax("checkEmail/" + email, {
		success: function(data) {
			
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
			}
		}
	});
});


// 별명 중복확인 버튼이 클릭되면
$("#checkNickNameBtn").click(function() {
	const NickName = $("#inputNickName").val();
	
	$.ajax("/checkNickName/" + NickName, {
		success: function(data) {
			`{"available": true}`
			
			if (data.available) {
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
			} else {
				$("#availableNickNameMessage").addClass("d-none");
				$("#notAvailableNickNameMessage").removeClass("d-none");
			}
		}
	});
});




// id 중복확인 버튼이 클릭되면
$("#checkIdBtn").click(function() {
	const userid = $("#inputId").val();	
	// 입력한 ID와 ajax 요청 보내서
	$.ajax("IDCheck/" + userid, {
		success: function(data) {
			// `{"available": true}` 
			
			if (data.available) {
				// 사용가능하다는 메세지 출력
				$("#availableIdMessage").removeClass("d-none");
				$("#notAvailableIdMessage").addClass("d-none");
			} else {
				// 사용가능하지 않다는 메세지 출력
				$("#avai lableIdMessage").addClass("d-none");
				$("#notAvailableIdMessage").removeClass("d-none");
			}
		}
	})
});


// 패스워드, 패스워드체크 인풋에 키업 이벤트 발생하면
$("#inputPassword, #inputPasswordCheck").keyup(function() {
	// 패스워드에 입력한 값
	const pw1 = $("#inputPassword").val();
	// 패스워드확인에 입력한 값이
	const pw2 = $("#inputPasswordCheck").val();

	if (pw1 === pw2) {
		// 같으면
		// submit 버튼 활성화
		$("#signupSubmit").removeClass("disabled");
		// 패스워드가 같다는 메세지 출력
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");

	} else {
		// 그렇지 않으면
		// submit 버튼 비활성화
		$("#signupSubmit").addClass("disabled");
		// 패스워드가 다르다는 메세지 출력
		$("#passwordFailText").removeClass("d-none");
		$("#passwordSuccessText").addClass("d-none");

	}


})
