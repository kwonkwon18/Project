<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<style>
.container-lg {
	width: 500px;
	margin: 0 auto; /* Center the container horizontally */
	margin-top: 100px; /* Add top margin for spacing */
}

.btn-success {
	background-color: #00C73C;
	border-color: #00C73C;
}

.btn-success:hover {
	background-color: #00A237;
	border-color: #00A237;
}

.form-label {
	margin-bottom: 0.5rem;
	font-weight: bold;
}

.mb-3 {
	margin-bottom: 1rem;
}

#checkIdBtn, #checkNickNameBtn, #checkEmailBtn {
	margin-top: 0.5rem;
}

/* Custom styles for the bordered container */
.registration-container {
	border: 1px solid #ced4da;
	border-radius: 0.25rem;
	padding: 1rem;
	background-color: #f8f9fa;
	margin-bottom: 2rem;
	width: 600px; /* Adjust the width as desired */
	margin: 0 auto;
}
</style>
</head>
<body>

	<my:navBar></my:navBar>

	<my:alert></my:alert>

	<div class="container-lg">
		<form action="/modify" method="post">
			<div class="row justify-content-center">
				<div class="registration-container">
					<h1>회원 정보</h1>
					<!-- .mb-3*4>label+input -->
					<div class="mb-3">
						<label for="inputId"> 아이디 </label>
						<input id="inputId" class="form-control" type="text" name="userId" value="${member.userId }" readonly" />
					</div>
					<div class="mb-3" style="display: none;">
						<label for="inputPassWord"> 비밀번호 </label>
						<input id="inputPassword" class="form-control" type="text" name="password" value="${member.password }" />
					</div>
					<div class="mb-3">
						<label for="inputName"> 이름 </label>
						<input id="inputName" type="text" class="form-control" name="name" value="${member.name }" />
					</div>
					<div class="mb-3">
						<label for="inputNickName"> 별명 </label>
						<input id="inputNickName" class="form-control" type="text" name="nickName" value="${member.nickName }" />

					</div>
					<div class="mb-3">
						<label for="inputBirth"> 생년월일 </label>
						<input id="inputBirth" class="form-control" type="text" name="birth" value="${member.birth }" readonly />

					</div>
					<div class="mb-3">
						<label for="inputGender"> 성별 </label>
						<br />
						<c:if test="${member.gender eq '여자' }">
							<c:set value="여자" var="gender"></c:set>
						</c:if>

						<c:if test="${member.gender eq '남자' }">
							<c:set value="남자" var="gender"></c:set>
						</c:if>

						<input id="inputGender" type="text" name="gender" value="${gender }" readonly />

					</div>
					<div class="mb-3">
						<label for="inputAddress"> 주소 </label>
						<select id="inputAddress" class="form-control" name="address" id="address">
							<option value="강남구">강남구</option>
							<option value="강동구">강동구</option>
							<option value="강북구">강북구</option>
							<option value="강서구">강서구</option>
							<option value="관악구">관악구</option>
							<option value="광진구">광진구</option>
							<option value="구로구">구로구</option>
							<option value="금천구">금천구</option>
							<option value="노원구">노원구</option>
							<option value="도봉구">도봉구</option>
							<option value="동대문구">동대문구</option>
							<option value="동작구">동작구</option>
							<option value="마포구">마포구</option>
							<option value="서대문구">서대문구</option>
							<option value="서초구">서초구</option>
							<option value="성동구">성동구</option>
							<option value="성북구">성북구</option>
							<option value="송파구">송파구</option>
							<option value="양천구">양천구</option>
							<option value="영등포구">영등포구</option>
							<option value="용산구">용산구</option>
							<option value="은평구">은평구</option>
							<option value="종로구">종로구</option>
							<option value="중구">중구</option>
							<option value="중랑구">중랑구</option>


						</select>
					</div>
					<div class="mb-3">
						<label for="inputPhone"></label>
						핸드폰 번호
						<input id="inputPhone" class="form-control" type="text" name="phone" value="${member.phone }" />

					</div>
					<div class="mb-3">
						<label for="inputEmail"> 이메일 </label>
						<input id="inputEmail" class="form-control" type="text" name="email" value="${member.email }" />

					</div>
					<div class="mb-3">
						<label for="inputIntroduce"> 자기소개 </label>
						<input id="inputIntroduce" class="form-control" type="text" name="introduce" value="${member.introduce }" />

					</div>

					<button class="btn btn-primary">수정</button>
		</form>
	</div>
	</div>
	</div>
	<%-- <div class="d-none">
      <form id="removeForm" action="/remove" method="post">
         <input type="text" name="id" value="${member.id }" />
      </form>
   </div> --%>

	<!-- 탈퇴 확인Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">탈퇴 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="removeForm" action="/remove" method="post">
						<input type="hidden" name="id" value="${member.userId }" />
						<label for="passwordInput1" class="form-label">암호</label>
						<input id="passwordInput1" type="password" name="password" class="form-control" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">아니오</button>
					<button type="submit" form="removeForm" class="btn btn-danger">예</button>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<sec:authorize access="isAuthenticated()">
		<my:chatBtn></my:chatBtn>
		<script src="/js/groupChat.js"></script>
		<script src="/js/chat.js" charset="UTF-8"></script>
	</sec:authorize>


	<%--    <form action="/modify" method="post">
      <div class="mb-3">
         <label for="inputId"> 아이디 </label> <input id="inputId"
            class="form-control" type="text" name="userId"
            value="${member.userId }" readonly" />
      </div>
      <div class="mb-3" style="display: none;">
         <label for="inputPassWord"> 비밀번호 </label> <input id="inputPassword"
            class="form-control" type="text" name="password"
            value="${member.password }" />
      </div>
      <div class="mb-3">
         <label for="inputName"> 이름 </label> <input id="inputName" type="text"
            class="form-control" name="name" value="${member.name }" />

      </div>
      <div class="mb-3">
         <label for="inputNickName"> 별명 </label> <input id="inputNickName"
            class="form-control" type="text" name="nickName"
            value="${member.nickName }" />

      </div>
      <div class="mb-3">
         <label for="inputBirth"> 생년월일 </label> <input id="inputBirth"
            class="form-control" type="text" name="birth"
            value="${member.birth }" readonly />

      </div>
      <div class="mb-3">
         <label for="inputGender"> 성별 </label> <br />
         <c:if test="${member.gender eq '여자' }">
            <c:set value="여자" var="gender"></c:set>
         </c:if>

         <c:if test="${member.gender eq '남자' }">
            <c:set value="남자" var="gender"></c:set>
         </c:if>

         <input id="inputGender" type="text" name="gender" value="${gender }"
            readonly />

      </div>
      <div class="mb-3">
         <label for="inputAddress"> 주소 </label> <select id="inputAddress"
            class="form-control" name="address" id="address">
            <option value="강남구">강남구</option>
            <option value="강동구">강동구</option>
            <option value="강북구">강북구</option>
            <option value="강서구">강서구</option>
            <option value="관악구">관악구</option>
            <option value="광진구">광진구</option>
            <option value="구로구">구로구</option>
            <option value="금천구">금천구</option>
            <option value="노원구">노원구</option>
            <option value="도봉구">도봉구</option>
            <option value="동대문구">동대문구</option>
            <option value="동작구">동작구</option>
            <option value="마포구">마포구</option>
            <option value="서대문구">서대문구</option>
            <option value="서초구">서초구</option>
            <option value="성동구">성동구</option>
            <option value="성북구">성북구</option>
            <option value="송파구">송파구</option>
            <option value="양천구">양천구</option>
            <option value="영등포구">영등포구</option>
            <option value="용산구">용산구</option>
            <option value="은평구">은평구</option>
            <option value="종로구">종로구</option>
            <option value="중구">중구</option>
            <option value="중랑구">중랑구</option>


         </select>
      </div>
      <div class="mb-3">
         <label for="inputPhone"></label> 핸드폰 번호 <input id="inputPhone"
            class="form-control" type="text" name="phone"
            value="${member.phone }" />

      </div>
      <div class="mb-3">
         <label for="inputEmail"> 이메일 </label> <input id="inputEmail"
            class="form-control" type="text" name="email"
            value="${member.email }" />

      </div>
      <div class="mb-3">
         <label for="inputIntroduce"> 자기소개 </label> <input id="inputIntroduce"
            class="form-control" type="text" name="introduce"
            value="${member.introduce }" />

      </div>

      <button class="btn btn-primary">수정</button>
   </form> --%>



	<%--    <sec:authorize access="isAuthenticated()">
      <my:chatBtn></my:chatBtn>
      <script src="/js/groupChat.js"></script>
      <script src="/js/chat.js" charset="UTF-8"></script>
   </sec:authorize> --%>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>


	<script src="/js/navBar.js"></script>

</body>
</html>