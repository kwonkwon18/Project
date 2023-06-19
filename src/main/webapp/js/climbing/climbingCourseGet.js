// bootstrap 참고하여 toast 를 설정해줘야함 
const toast = new bootstrap.Toast(document.querySelector("#liveToast"));

// 라이크 아이콘을 클릭했을 때
$("#likeIcon").click(function() {

	const boardId = $("#boardIdText").text().trim();
	console.log(boardId);

	const data1 = { boardId };
	console.log(data1);
	
	
	$.ajax("/climbing/climbingCourseLike", {
		method : "post",
		contentType : "application/json",
		data : JSON.stringify(data1),
		
		success : function(data){
			if(data.like){
				$("#likeIcon").html(`<i class="fa-solid fa-heart"></i>`);
			} else{
				$("#likeIcon").html(`<i class="fa-regular fa-heart"></i>`);
			}
			
			// 좋아요 수 업데이트 < == 이건 조건과 무관하게 진행
			$("#likeNumber").text(data.count);
		},
		
		error : function(jqXHR){
			
			
			// $("body").prepend(jqXHR.responseJSON.message);
			
			// 토스트 안에 해당 메시지를 담고 
			$(".toast-body").text(jqXHR.responseJSON.message + "실패");
			// 토스트를 띄워준다. 
			toast.show();
		}
		
	});
});














