$(document).ready(function() {
	// 검색 폼의 submit 이벤트를 처리합니다.
	$('#search').click(function(event) {
		event.preventDefault(); // 기본 동작인 폼 제출을 막습니다.

		// 검색어를 가져옵니다.
		var searchTerm = $('#searchInput').val();
		// AJAX 요청을 보냅니다.
		$.ajax({
			url: '/climbing/search',  // 검색 요청을 처리하는 서버의 엔드포인트 URL을 입력합니다.
			method: 'get',   // 요청 메서드를 선택합니다 (GET, POST 등).
			data: { search: searchTerm },  // 요청 데이터로 검색어를 전달합니다.
			success: function(response) {
				var climbingMateList = response.result;
				$("#mateListData").empty();
				$("#mateMapBoxItem").remove("");

				$("#mateMapBox").append(`
					<div id="mateMapBoxItem">
					
					</div>
				`);
				for (const board of climbingMateList) {
					$("#mateMapBoxItem").append(`
						<div class="col-md-4">
							<div class="card" style="width: 18rem; margin-left: 40px;">
								<div class="card-body">
									<h5 class="card-title">🌄${board.title}</h5>
									<p class="card-text">작성자: ${board.writer}</p>
									<p class="card-text">작성일자: ${board.inserted}</p>
									<div style="text-align: right">
										<a href="/climbing/mateId/${board.id}" class="btn btn-primary">더보기</a>
									</div>
								</div>
							</div>
						</div>
					`);
				}
				// 요청이 성공하면 결과를 처리합니다.
				// response 변수에 서버에서 받은 응답이 저장됩니다.
			},
		});
	});
});
