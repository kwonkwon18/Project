$("#futsalPartyApply").click(function() {
	// 게시물 번호 request body에 추가
	const futsalPartyId = $("#futsalPartyId").text().trim();
	const futsalApplyMember = $("#applyNum").text().trim();
	
	const data = {futsalPartyId, futsalApplyMember};
	
	$.ajax("futsal/futsalPartyMember", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		
		success: function(data) {
			if(data.futsalParty) {
				// 지원버튼 회색으로
				$("#futsalPartyApply").html(`<a id="futsalPartyApply" class="btn btn-secondary" href="/futsal/futsalPartyMember">지원완료</a>`)
			}
			$("#applyNum").text(data.count);
		}
	})
})