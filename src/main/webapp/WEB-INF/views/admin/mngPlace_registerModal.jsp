<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- Modal -->
<div class="modal fade" id="placeModal" role="dialog">
	<div class="modal-dialog">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>게시물 등록하기</h4>
		</div>
		<div class="modal-body" style="padding:40px 50px;">
			<form class="form-horizontal" role="form" method="post" enctype="multipart/form-data" 
			action="${pageContext.request.contextPath}/admin/travel_write_ok.do">
			<input type="hidden" name="category" value="${category}"/>
			<input type="hidden" name="memberId" value="${memberId}"/>
			<!-- 게시물 제목 -->
			<div class="form-group">
				<label for="subject" class="control-label">제목</label>
				<div>
					<input type="text" class="form-control" name="subject" id="subject" placeholder="제목을 입력하세요.">
				</div>
			</div>
			<!--// 게시물 제목 -->
				
			<!--// 게시물 분류 -->
			
			<!-- 여행지 요약정보 -->
			<div class="form-group">
				<label for="address" class="control-label">여행지 주소</label>
				<div>
					<input type="text" class="form-control" name="address" id="address" placeholder="주소를 입력하세요.">
				</div>
			</div>
			
			<!-- 네이버 맵 -->	
			<div id="naverMap" style="width:40vh; height:25vh;"></div>
			<!-- // 네이버 맵 -->
		
			<div class="form-group">
				<label for="mapX" class="control-label">위도</label>
				<div>
					<input type="text" class="form-control" name="mapX" id="mapX" placeholder="X좌표를 입력하세요.">
				</div>
			</div>
			<div class="form-group">
				<label for="mapY" class="control-label">경도</label>
				<div>
					<input type="text" class="form-control" name="mapY" id="mapY" placeholder="Y 좌표를 입력하세요.">
				</div>
			</div>
			<!--//여행지 요약정보 -->
			
			<!-- 상세 여행지 설명 -->
			<div class="form-group">
				<label for="content" class="control-label">상세여행지 설명</label>
				<div>
					<textarea class="form-control" name="content" id="content" rows="5" placeholder="상세 내용을 입력하세요."></textarea>
				</div>
			</div>
			<!--// 상세 여행지 설명 -->
			
			<!-- 지도좌표 -->
			<div class="form-group">
				<label for="summary" class="control-label">여행지 요약정보</label>
				<div>
					<input type="text" class="form-control" name="summary" id="summary" placeholder="요약 내용을 입력하세요.">
				</div>
			</div>
			<!--//지도좌표 -->
			
			<!-- 썸네일 첨부파일 -->
			<div class="form-group">
				<label for="file" class="control-label">첨부파일</label>
				<input type="file" name="file" id="file" class="form-control" multiple/>
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