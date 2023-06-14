// 페이지가 로드될 때마다 호출해줄것임
$(document).ready(function(){
	console.log("작동은됨")
	$.ajax("/running/countOfAlarm", {
		contentType : "application/json",
		success : function(data) {
			
			console.log(data.confirmationTotal)
			
			$("#NumberOfAlarm").html(data.confirmationTotal);
			
		}
	})
	
});
