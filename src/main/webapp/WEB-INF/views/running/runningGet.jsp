<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

   <my:navBarRunning></my:navBarRunning>
   
   <span id="boardIdText"> ${board.id } </span>
   <br />
   <br />
   <br />
   <br />

   <jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
   <!-- parseDate는 일단 들어오는 형식 대로 받아줘야함   -->
   <fmt:parseDate value="${board.time}" pattern="yyyy-MM-dd'T'HH:mm" var="startDate" />

   <fmt:formatDate value="${now }" pattern="yyyyMMddHHmm" var="nowDate" />
   <fmt:formatDate value="${startDate }" pattern="yyyyMMddHHmm" var="openDate" />

   <!-- 본문  -->

   <div class="container-lg">


		
		<div class="row justify-content-center">
				<div class="row justify-content-center">
					<div class="row">
						
						<div class="col-12 col-md-8 col-lg-6">
							<div class="col-md-3" style="display: flex; justify-content: flex-end; float: right; margin-top: 2px;">
						  		<h3 style="font-weight: bold;">${board.address }</h3>
						  	</div>
							<div id="map" style="width: 100%; height: 600px; border-radius: 8px;"></div>
							<%-- <input id="boardIdText" type="hidden" value="${board.id }" /> --%>
							<input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" /> <input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />
							<input type="hidden" value="${formattedDate }" />
							<!-- 본인 게시물 확인 -->
							<c:set var="isUser" value="false" />
							<c:forEach items="${memberList}" var="memberList">
								<c:if test="${memberList.nickName eq board.writer}">
									<c:set var="isUser" value="true" />
									<c:set var="userName" value="${memberList.nickName}" />
								</c:if>
							</c:forEach>
		
							<c:forEach items="${memberList}" var="memberList">
								<c:set var="memberNickName" value="${memberList.nickName}" />
							</c:forEach>
		
		
							<!-- 본인 신청 확인  -->
							<c:set var="currentUserId" value="${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}" />
							<c:set var="isMember" value="false" />
							<c:forEach items="${members}" var="members">
								<c:if test="${members.memberId eq memberNickName}">
									<c:set var="isMember" value="true" />
								</c:if>
							</c:forEach>
		
							<!-- 대기인원 확인  -->
							<c:set var="isWaiting" value="false" />
							<c:forEach items="${waitingMembers}" var="waitingMembers">
								<c:if test="${waitingMembers.memberId eq memberNickName}">
									<c:set var="isWaiting" value="true" />
								</c:if>
							</c:forEach>
		
							<!-- 거절인원 확인  -->
							<c:set var="isReject" value="false" />
							<c:forEach items="${rejectMembers}" var="rejectMembers">
								<c:if test="${rejectMembers.memberId eq memberNickName}">
									<c:set var="isReject" value="true" />
								</c:if>
							</c:forEach>
		
		
							
							<div class="mt-3">
								<div class="row">
									
									<c:if test="${!isUser}">
										<c:if test="${openDate < nowDate }">
											<div class="col-md-5">
												<button>마감된 러닝</button>
											</div>
										</c:if>
			
										<c:if test="${openDate > nowDate }">
			
											<c:if test="${isReject }">
												<div class="col-md-5">
													<button id="">거절된 러닝입니다.</button>
												</div>
											</c:if>
			
											<c:if test="${not isReject }">
			
			
												<c:if test="${not isWaiting }">
													<!-- 실제로 신청 되기 전에도 취소하기가 보여야함   -->
													<c:choose>
														<c:when test="${isMember}">
															<div class="col-md-5">
																<button class="btn btn-danger" id="joinPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️></button>
															</div>
														</c:when>
														<c:otherwise>
															<c:if test="${board.people > board.currentNum }">
																<div class="col-md-5">
																	<button class="btn btn-primary" id="joinPartyBtn">참여하기🙋‍♂️🙋‍♀️🙋‍♂️🙋‍♀</button>️
																</div>
			           									    </c:if>
			
														</c:otherwise>
													</c:choose>
												</c:if>
			
												<c:if test="${isWaiting }">
													<div class="col-md-3">
														<button class="btn btn-warning">신청대기중👼</button>
													</div>
													<div class="col-md-3">
														<button class="btn btn-danger" id="joinPartyBtn">취소하기🙅‍♀️</button>
													</div>
												</c:if>
			
												<c:if test="${board.people <= board.currentNum }">
													<div class="col-md-5">
														<button class="btn btn-secondary">마감</button>
													</div>
												</c:if>
											</c:if>
										</c:if>
			
										<div class="col-md-3">
											<input type="text" id="currentPeopleHidden" value="지원: ${board.currentNum }명" class="form-control"/>
										</div>
										<div class="col-md-3">
											<input type="text" id="totalPeople" value="총 인원: ${board.people }명" class="form-control"/>
										</div>
											<p id="currentPeople"></p>
										<input type="hidden" id = "currentPeopleHidden" value = "${board.currentNum }"  />
									</c:if>
								</div>
								
								<c:if test="${isUser}">
									<div class="mt-3">
										<div class="row">
											<div class="col-md-5">
												<button type="button" class="btn btn-secondary">내가 쓴 글</button>
											</div>
											<div class="col-md-3">
												<input class="form-control" type="text" id="currentPeopleHidden" value="지원: ${board.currentNum }명" />
											</div>
											<div class="col-md-3">
												<input class="form-control" type="text" id="totalPeople" value="총 인원: ${board.people }명" />
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						
						<div class="col-12 col-md-8 col-lg-6">
							<div class="row">
								<div class="col-md-9">
									<h3 style="font-weight: bold;">러닝 메이트</h3>
								</div>
								<div class="col-md-3">
									<sec:authorize access="#board.writer eq #userName">
										<div style="float: rigth;">
											<a class="btn btn-secondary" href="/running/runningModify/${board.id }">수정</a>
											<button data-bs-toggle="modal" data-bs-target="#deleteConfirmModal" class="btn btn-danger">삭제</button>
										</div>
				
				
				
				
										<div class="d-none">
											<form action="/running/runningRemove" method="post" id="removeForm">
												<input type="text" name="id" value="${board.id }" />
											</form>
										</div>
				
				
										<!-- Modal -->
										<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
														<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
													</div>
													<div class="modal-body">삭제 하시겠습니까?</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
														<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
													</div>
												</div>
											</div>
										</div>
									</sec:authorize>
								</div>
							</div>
							<hr />
							<div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">제목</label>
									</div>
									<div class="col-md-10">
										<input type="text" class="form-control" value="${board.title }" readonly />
									</div>
								</div>
							</div>
							<hr />
							
							<div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">본문</label>
									</div>
									<div class="col-md-10">
										<textarea class="form-control" readonly rows="10">${board.body }</textarea>
									</div>
								</div>
							</div>
							
							<hr />
							
							<div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">작성자</label>
									</div>
									<div class="col-md-10">
										<input id="writerText" type="text" class="form-control" value="${board.writer }" readonly />
									</div>
								</div>
							</div>
							<hr />
							
							<div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">모임시간</label>
									</div>
									<div class="col-md-10">
										<input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
									</div>
								</div>
							</div>
							<hr />
							
							<%-- <div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">작성일시</label>
									</div>
									<div class="col-md-10">
										<input id="insertedText" type="text" readonly class="form-control" value="${board.inserted }" />
									</div>
								</div>
							</div>
							<hr /> --%>
							<input id="insertedText" type="hidden" value="${board.inserted }" />
							
							<div class="mb-3">
								<div class="row">
									<div class="col-md-2">
										<label for="" class="form-label">M A T E</label>
									</div>
									<div class="col-md-4">
										<c:forEach items="${members}" var="member">
											<!-- 보드아이디와 멤버의 보드아이디가 같은 경우 -->
											<!-- 멤버의 아이디와 작성자가 같은 경우는 해주면 안됨  -->
											<c:if test="${board.id eq member.boardId && board.writer ne member.memberId}">
												<div class="mb-1">
													<input type="text" readonly class="form-control" value="${member.memberId}" />
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<hr />
							
							
							
					</div>
				</div>
			</div>
		</div>
	</div>

      <%-- <div class="row justify-content-center">
         <div id="map" style="width: 500px; height: 500x;"></div>
         <div class="col-12 col-md-8 col-lg-6">
            <div class="d-flex">
               <div class="me-auto">
                  <h1>
                     <span id="boardIdText"> ${board.id } </span>
                     번게시물${formattedDate }
                  </h1>
               </div>
            </div>

            <div>
               <div class="mb-3">
                  <label for="" class="form-label">제목</label>
                  <input type="text" class="form-control" value="${board.title }" readonly />
               </div>


               <div class="mb-3">
                  <label for="" class="form-label">본문</label>
                  <textarea class="form-control" readonly rows="10">${board.body }</textarea>
               </div>

               <div class="mb-3">
                  <label for="" class="form-label">작성자</label>
                  <input id="writerText" type="text" class="form-control" value="${board.writer }" readonly />
               </div>

               <div class="mb-3">
                  <label for="" class="form-label">모임시간</label>
                  <input id="timeText" type="text" class="form-control" value="${board.time }" readonly />
               </div>

               <div class="mb-3">
                  <label for="" class="form-label">작성일시</label>
                  <input id="insertedText" type="text" readonly class="form-control" value="${board.inserted }" />
               </div>
               <input id="LatSubmit" type="hidden" name="Lat" value="${board.lat }" />
               <input id="LngSubmit" type="hidden" name="Lng" value="${board.lng }" />

               <!-- 본인 게시물 확인 -->
               <c:set var="isUser" value="false" />
               <c:forEach items="${memberList}" var="memberList">
                  <c:if test="${memberList.nickName eq board.writer}">
                     <c:set var="isUser" value="true" />
                     <c:set var="userName" value="${memberList.nickName}" />
                  </c:if>
               </c:forEach>



               <label for="" class="form-label">신청한 사람 </label>
               <c:forEach items="${members}" var="member">
                  <!-- 보드아이디와 멤버의 보드아이디가 같은 경우 -->
                  <!-- 멤버의 아이디와 작성자가 같은 경우는 해주면 안됨  -->
                  <c:if test="${board.id eq member.boardId && board.writer ne member.memberId}">
                     <div class="mb-3">
                        <input type="text" readonly class="form-control" value="${member.memberId}" />
                     </div>
                  </c:if>
               </c:forEach>


               <!-- 본인 게시물 확인 -->
               <c:set var="isUser" value="false" />
               <c:forEach items="${memberList}" var="memberList">
                  <c:if test="${memberList.nickName eq board.writer}">
                     <c:set var="isUser" value="true" />
                     <c:set var="userName" value="${memberList.nickName}" />
                  </c:if>
               </c:forEach>



               <sec:authorize access="#board.writer eq #userName">
                  <div>
                     <a class="btn btn-secondary" href="/running/runningModify/${board.id }">수정</a>
                     <button data-bs-toggle="modal" data-bs-target="#deleteConfirmModal" class="btn btn-danger">삭제</button>
                  </div>




                  <div class="d-none">
                     <form action="/running/runningRemove" method="post" id="removeForm">
                        <input type="text" name="id" value="${board.id }" />
                     </form>
                  </div>


                  <!-- Modal -->
                  <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                     <div class="modal-dialog">
                        <div class="modal-content">
                           <div class="modal-header">
                              <h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
                              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                           </div>
                           <div class="modal-body">삭제 하시겠습니까?</div>
                           <div class="modal-footer">
                              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                              <button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
                           </div>
                        </div>
                     </div>
                  </div>
               </sec:authorize>


               <c:forEach items="${memberList}" var="memberList">
                     <c:set var="memberNickName" value="${memberList.nickName}" />
                  </c:forEach>


                  <!-- 본인 신청 확인  -->
                  <c:set var="currentUserId" value="${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.name}" />
                  <c:set var="isMember" value="false" />
                  <c:forEach items="${members}" var="members">
                     <c:if test="${members.memberId eq memberNickName}">
                        <c:set var="isMember" value="true" />
                     </c:if>
                  </c:forEach>

                  <!-- 대기인원 확인  -->
                  <c:set var="isWaiting" value="false" />
                  <c:forEach items="${waitingMembers}" var="waitingMembers">
                     <c:if test="${waitingMembers.memberId eq memberNickName}">
                        <c:set var="isWaiting" value="true" />
                     </c:if>
                  </c:forEach>

                  <!-- 거절인원 확인  -->
                  <c:set var="isReject" value="false" />
                  <c:forEach items="${rejectMembers}" var="rejectMembers">
                     <c:if test="${rejectMembers.memberId eq memberNickName}">
                        <c:set var="isReject" value="true" />
                     </c:if>
                  </c:forEach>



                  <div>
                     <c:if test="${!isUser}">
                        <c:if test="${openDate < nowDate }">
                           <button>마감된 러닝</button>
                        </c:if>

                        <c:if test="${openDate > nowDate }">

                           <c:if test="${isReject }">
                              <button id="">거절된 러닝입니다.</button>
                           </c:if>

                           <c:if test="${not isReject }">


                              <c:if test="${not isWaiting }">
                                 <!-- 실제로 신청 되기 전에도 취소하기가 보여야함   -->
                                 <c:choose>
                                    <c:when test="${isMember}">
                                       <button id="joinPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️></button>
                                    </c:when>
                                    <c:otherwise>
                                       <c:if test="${board.people > board.currentNum }">
                                          <button id="joinPartyBtn">참여하기🙋‍♂️🙋‍♀️🙋‍♂️🙋‍♀</button>️
               					       </c:if>
                                    </c:otherwise>
                                 </c:choose>
                              </c:if>

                              <c:if test="${isWaiting }">
                                 <button>신청대기중👼👼👼</button>
                                 <button id="joinPartyBtn">취소하기🙅‍♀️🙅‍♂️🙅‍♀️🙅‍♂️</button>
                              </c:if>

                              <c:if test="${board.people <= board.currentNum }">
                                 <button>마감</button>
                              </c:if>
                           </c:if>
                        </c:if>


                        <input type="text" id="totalPeople" value="${board.people }" />
                        <input type="text" id="currentPeopleHidden" value="${board.currentNum -1 }" />
                        <p id="currentPeople"></p>
                        <input type="text" id = "currentPeopleHidden" value = "${board.currentNum }"  />
                     </c:if>

                     <c:if test="${isUser}">
                        <button>내가 올린 게시물</button>
                        <input type="text" id="totalPeople" value="${board.people }" />
                        <input type="text" id="currentPeopleHidden" value="${board.currentNum -1 }" />
                     </c:if>
                  </div> --%>
                  
                  <!-- Modal -->
					<!-- <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
									<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>
								<div class="modal-body">삭제 하시겠습니까?</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
									<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
								</div>
							</div>
						</div>
					</div> -->





                  <!-- **************************************************  -->

                  <sec:authorize access="isAuthenticated()">
                     <my:chatBtn></my:chatBtn>
                     <script src="/js/groupChat.js"></script>
                     <script src="/js/chat.js" charset="UTF-8"></script>
                  </sec:authorize>
                  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d88d8436c67d406cea914acf60c7b220&libraries=services"></script>
                  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
                  <script src="/js/running/runningGet.js"></script>
                  <script src="/js/navBar.js"></script>
</body>
</html>