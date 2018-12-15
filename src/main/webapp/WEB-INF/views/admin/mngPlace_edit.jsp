<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<!-- Modal -->
<div class="modal fade" id="placeeditModal" role="dialog">
	<div class="modal-dialog">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>게시물 등록하기</h4>
		</div>
		<div class="modal-body" style="padding:40px 50px;">
			<form class="form-horizontal" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/mngPlace_edit_ok.do">
			<input type="hidden" name="category" value="${category}"/>
			<input type="hidden" name="travel_id" value="${readTravel.id}"/>
					
			<!-- 게시물 제목 -->
			<div class="form-group">
				<label for="subject" class="control-label">제목</label>
				<div>
					<input type="text" class="form-control" name="subject" id="subject" placeholder="제목을 입력하세요." value="${readTravel.subject}">
				</div>
			</div>
			<!--// 게시물 제목 -->
			
			<!-- 여행지 요약정보 -->
			<div class="form-group">
				<label for="address" class="control-label">여행지 주소</label>
				<div>
					<input type="text" class="form-control" name="address" id="address" placeholder="주소를 입력하세요." value="${readTravel.address}">
				</div>
			</div>
			
			<!-- 네이버 맵 -->	
			<div id="naverMap" style="width:40vh; height:25vh;"></div>
			<!-- // 네이버 맵 -->			
			
			<div class="form-group">
				<label for="mapX" class="control-label">여행지 주소</label>
				<div>
					<input type="text" class="form-control" name="mapX" id="mapX" placeholder="X좌표를 입력하세요." value="${readTravel.mapX}">
				</div>
			</div>
			<div class="form-group">
				<label for="mapY" class="control-label">여행지 주소</label>
				<div>
					<input type="text" class="form-control" name="mapY" id="mapY" placeholder="Y 좌표를 입력하세요." value="${readTravel.mapY}">
				</div>
			</div>
			<!--//여행지 요약정보 -->
			
			<!-- 상세 여행지 설명 -->
			<div class="form-group">
				<label for="content" class="control-label">상세여행지 설명</label>
				<div>
					<textarea class="form-control" name="content" id="content" rows="5" placeholder="상세 내용을 입력하세요.">${readTravel.content}</textarea>
				</div>
			</div>
			<!--// 상세 여행지 설명 -->
			
			<!-- 지도좌표 -->
			<div class="form-group">
				<label for="summary" class="control-label">여행지 요약정보</label>
				<div>
					<input type="text" class="form-control" name="summary" id="summary" placeholder="요약 내용을 입력하세요." value="${readTravel.summary}">
				</div>
			</div>
			<!--//지도좌표 -->
			
			<!-- 썸네일 첨부파일 -->
			
			<!-- 썸네일 첨부파일 -->
			<div class="form-group">
				<label for="file" class="control-label">첨부파일</label>
				<input type="file" name="file" id="file" class="form-control" multiple/>
				
				<!-- 첨부파일 리스트가 존재할 경우만 삭제할 항목 선택 가능 -->
				<c:if test="${fileList != null }">
					<c:forEach var="file" items="${fileList}">
						<!-- 이미지를 다운받기 위한 URL 구성 -->
						<c:url value="/download.do" var="downloadUrl">
							<c:param name="file" value="${file.fileDir}/${file.fileName}"/>
						</c:url>
					
					<div class="checkbox">
						<label>
							<input type="checkbox" name="del_file" id="img_del" value="${file.id}"/>
							${file.originName} [ 삭제하기 ]
							<a href="${downloadUrl}">[다운받기]</a>
						</label>
					</div>
					</c:forEach>
				</c:if>
			</div>
			<!--// 썸네일 첨부파일 -->
			<br/>
			<hr/>		
			<div class="form-group text-center" >
				<button type="submit" class="btn btn-danger btn-default">등록완료</button>
				<button type="button" class="btn btn-info btn-default" data-dismiss="modal">취소</button>
			</div>
			
			</form>
		</div>
 		
	</div>
	</div>
</div>