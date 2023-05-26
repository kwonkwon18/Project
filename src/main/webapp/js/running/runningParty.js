

$("#joinPartyBtn").click(function() {

	const total = $("#totalPeople").text().trim();
	const boardId = $("#boardIdText").text().trim();
	const userId = $("#writerText").val().trim();
	const current = $("#currentPeople").text().trim();

	const data = { boardId, userId };

	$.ajax("/running/joinParty", {
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function(data) {
            if (data.join) {
                alert("신청되었습니다.");
                $("#currentPeople").text(data.count);

                var currentNumber = parseInt($("#currentPeople").text().trim());
                var totalNumber = parseInt($("#totalPeople").text().trim());

                if (currentNumber >= totalNumber) {
                    $("#joinPartyBtn").attr('disabled', 'disabled');
                }
            } else {
                alert(data.message); // "신청 불가능합니다." 메시지 표시
            }
            location.reload();
        },

        error: function(jqXHR) {
            alert("신청실패");
            $("#currentPeople").text(jqXHR.responseJSON.message);
        }
    });
});

$("#rejectPartyBtn").click(function() {
	
	alert("취소되었습니다. ");
	
	const total = $("#totalPeople").text().trim();
	const boardId = $("#boardIdText").text().trim();
	const userId = $("#writerText").val().trim();
	const current = $("#currentPeople").text().trim();

	const data = { boardId, userId };

	$.ajax("/running/rejectParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		success: function(data) {

			$("#currentPeople").text(data.count);
			
			var currentNumber = $("#currentPeople").text().trim();
			var totalNumber = $("#totalPeople").val().trim();
			
			console.log(currentNumber)
			console.log(totalNumber)
			

			location.reload();
		},
		error: function(jqXHR) {
			$("#currentPeople").text(jqXHR.responseJSON.message);
		}
		


	})


})



