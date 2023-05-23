

$("#joinPartyBtn").click(function() {
	const total = $("#totalPeople").val().trim();
	const boardId = $("#boardIdText").text().trim();
	const userId = $("#writerText").val().trim();
	const current = $("#currentPeople").text().trim();

	const data = { boardId, userId };

	$.ajax("/running/joinParty", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		success: function(data) {
			if (data.join && total > current) {
				$("#currentPeople").text(data.count);
			} else {
				$("#currentPeople").text(data.count);
			}

		},
		error: function(jqXHR) {
			$("#currentPeople").text(jqXHR.responseJSON.message);
		}


	})


})



