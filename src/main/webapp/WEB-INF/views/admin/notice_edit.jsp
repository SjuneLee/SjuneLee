<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style>
/** body 영역이 메뉴에 가려지는 부분 확보하기 */
body {
	padding-top: 50px;
}
</style>
</head>
<body>
<div class="container">
	<div class="page-header">
		<h1>공지사항 수정</h1>
	</div>

	<form class="form-horizontal" method="post" enctype="multipart/form-data"
	action="${pageContext.request.contextPath}/admin/notice_edit_ok.do">
	
		<!--  수정 대상에 대한 상태유지 -->
		<input type="hidden" name="noticeId" id="noticeId" value="${noticeEdit.id}"/>
		<!-- 제목 -->
		<div class="form-group">
			<label for="subject" class="control-label" >글 제목</label>
				<input type="text" class="form-control" id="subject" name="subject"
					placeholder="공지사항 제목을 입력하세요." value="${noticeEdit.subject}">
		</div>
		<!-- 내용 -->
		<div class="form-group">
			<label class="control-label" for="content">내용</label>
			<textarea name="content" id="content" class="ckeditor">${noticeEdit.content}</textarea>
		</div>
		<!-- 파일업로드 -->
		<div class="form-group">
			<label class="control-label" for="file">첨부파일</label>
			<div>
			<input class="form-control" id="file" name="file" type="file" multiple />
			
			<!--  첨부파일 리스트가 존재할 경우만 삭제할 항목 선택 가능 -->
			<c:if test="${fileList != null}">
				<c:forEach var="file" items="${fileList}">
					<!-- 이미지를 다운받기 위한 URL 구성 -->
					<c:url value="/download.do" var="downloadUrl">
						<c:param name="file" value="${file.fileDir}/${file.fileName}"/>
					</c:url>
					<div class="checkbox">
						<label>
							<input type="checkbox" name="del_file" id="img_del" value="${file.id}" />
							${file.originName} 삭제하기
							<a href="${downloadUrl}">[다운받기]</a>
						</label>
					</div>
				</c:forEach>
			</c:if>
			</div>
		</div>
		<!--//파일 업로드 -->
		
		<!-- 버튼들 -->
		<div class="form-group"> 
			<div class="text-center">
				<button class="btn btn-primary" type="submit">수정완료</button>
				<a href="${pageContext.request.contextPath}/admin/admin_notice.do">
					<button class="btn btn-danger" type="button">취소</button>
				</a>
			</div>
		</div>
	</form>
</div>
</body>
</html>