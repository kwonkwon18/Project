listComment();

$("#sendCommentBtn").click(function() {
	const boardId = $("#boardIdText").text().trim();
	const content = $("#commentTextArea").val();
	const data = { boardId, content };
	$("#commentTextArea").val("");

	$.ajax("/climbingComment/add", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();

		}
	});
})

$("#updateCommentBtn").click(function() {
	const commentId = $("#commentUpdateIdInput").val();
	const content = $("#commentUpdateTextArea").val();
	const data = {
		id: commentId,
		content: content
	}
	$.ajax("/climbingComment/update", {
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
	const commentId = $(this).attr("data-climbingComment-id");
	$.ajax("/climbingComment/id/" + commentId, {
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
	$.ajax("/climbingComment/list?board=" + boardId, {
		method: "get", // 생략 가능
		success: function(comments) {
			// console.log(data);
			$("#commentListContainer").empty();
			for (const climbingComment of comments) {
				const editButtons = `
					<button 
						id="commentDeleteBtn${climbingComment.id}" 
						class="commentDeleteButton"
						data-bs-toggle="modal"
						data-bs-target="#deleteCommentConfirmModal" 
						data-climbingComment-id="${climbingComment.id}"><i class="fa-regular fa-trash-can"></i></button>
						:
						<button
							id="commentUpdateBtn${climbingComment.id}"
							class="commentUpdateButton btn btn-secondary"
							data-bs-toggle="modal" data-bs-target="#commentUpdateModal"
							data-climbingComment-id="${climbingComment.id}"><i class="fa-regular fa-pen-to-square"></i></button>
				`;
				// console.log(comment);
				$("#commentListContainer").append(`
					<li class="list-groupp-item d-flex justify-content-between align-items-start">
						<div class="ms-2 me-auto">
							<div class="fw-bold"> <i class="fa-regular fa-user"></i> ${climbingComment.memberId}</div>
							<div style="white-space: pre-wrap;">${climbingComment.content}</div>
						</div>
						<div>
							<span class="badge bg-primary rounded-pill">${climbingComment.inserted}</span>
							<div class="text-end mt-2">
								${climbingComment.editable ? editButtons : ''}
							</div>
						</div>
					</li>
				`);
			};
			$(".commentUpdateButton").click(function() {
				const id = $(this).attr("data-climbingComment-id");
				$.ajax("/climbingComment/id/" + id, {
					success: function(data) {
						$("#commentUpdateIdInput").val(data.id);
						$("#commentUpdateTextArea").val(data.content);
					}
				})
			});

			$(".commentDeleteButton").click(function() {
				const commentId = $(this).attr("data-climbingComment-id");
				$("#deleteCommentModalButton").attr("data-climbingComment-id", commentId);
			});
		}
	});

}

$("#deleteCommentModalButton").click(function() {
	const commentId = $(this).attr("data-comment-id");
	$.ajax("/comment/id/" + commentId, {
		method: "delete",
		complete: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	});
});

document.addEventListener("keyup", function(event) {
	if (event.key === "Enter") {
		document.querySelector("#sendCommentBtn").click();
	}
});