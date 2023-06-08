listComment();

$("#sendCommentBtn").click(function() {
	const boardId = $("#boardIdText").text().trim();
	const content = $("#commentTextArea").val();
	const data = {boardId, content};
	
	$.ajax("/futsalComment/add", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		complete: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
			
			$("#commentTextArea").val("");
		}
	});
})

$("#updateCommentBtn").click(function() {
	const commentId = $("#commentUpdateIdInput").val();
	const content = $("#commentUpdateTextArea").val();
	const data = {
		id : commentId,
		content : content
	}
	$.ajax("/futsalComment/update", {
		method: "put",
		contentType: "application/json",
		data: JSON.stringify(data),
		complete: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	})
})

$("#deleteCommentModalButton").click(function() {
	const commentId = $(this).attr("data-futsalComment-id");
	$.ajax("/futsalComment/id/" + commentId, {
		method: "delete",
		complete: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	});
});

function listComment() {
	const boardId = $("#boardIdText").text().trim();
	$.ajax("/futsalComment/list?board=" + boardId, {
		method: "get", // 생략 가능
		success: function(comments) {
			// console.log(data);
			$("#commentListContainer").empty();
			for (const futsalComment of comments) {
				const editButtons = `
					<button 
						id="commentDeleteBtn${futsalComment.id}" 
						class="commentDeleteButton"
						data-bs-toggle="modal"
						data-bs-target="#deleteCommentConfirmModal" 
						data-futsalComment-id="${futsalComment.id}"><i class="fa-regular fa-trash-can"></i></button>
						:
						<button
							id="commentUpdateBtn${futsalComment.id}"
							class="commentUpdateButton btn btn-secondary"
							data-bs-toggle="modal" data-bs-target="#commentUpdateModal"
							data-futsalComment-id="${futsalComment.id}"><i class="fa-regular fa-pen-to-square"></i></button>
				`;
				// console.log(comment);
				$("#commentListContainer").append(`
					<li class="list-groupp-item d-flex justify-content-between align-items-start">
						<div class="ms-2 me-auto">
							<div class="fw-bold"> <i class="fa-regular fa-user"></i> ${futsalComment.memberId}</div>
							<div style="white-space: pre-wrap;">${futsalComment.content}</div>
						</div>
						<div>
							<span class="badge bg-primary rounded-pill">${futsalComment.inserted}</span>
							<div class="text-end mt-2">
								${futsalComment.editable ? editButtons : ''}
							</div>
						</div>
					</li>
				`);
			};
			$(".commentUpdateButton").click(function() {
				const id = $(this).attr("data-futsalComment-id");
				$.ajax("/futsalComment/id/" + id, {
					success: function(data) {
						$("#commentUpdateIdInput").val(data.id);
						$("#commentUpdateTextArea").val(data.content);
					}
				})
			});
			
			$(".commentDeleteButton").click(function() {
				const commentId = $(this).attr("data-futsalComment-id");
				$("#deleteCommentModalButton").attr("data-futsalComment-id", commentId);
			});
		}
	});
	
}