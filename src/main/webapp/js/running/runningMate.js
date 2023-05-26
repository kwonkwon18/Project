

/*$("#joinPartyBtn").click(function() {
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

			$("#currentPeople").text(data.count);
			
			var currentNumber = $("#currentPeople").text().trim();
			var totalNumber = $("#totalPeople").val().trim();
			
			console.log(currentNumber)
			console.log(totalNumber)
			
			if(currentNumber >= totalNumber){
				$("#joinPartyBtn").attr('disabled', 'disabled');
			}
			
		},
		error: function(jqXHR) {
			$("#currentPeople").text(jqXHR.responseJSON.message);
		}


	})


})*/




