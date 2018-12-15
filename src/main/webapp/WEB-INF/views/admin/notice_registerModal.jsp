<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
	
<!-- Modal -->
<div class="modal fade" id="noticeModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>공지사항 등록하기</h4>
		</div>
		<div class="modal-body" style="padding:40px 50px;">
			<form class="form-vertical" method="post" enctype="multipart/form-data"
				action="${pageContext.request.contextPath}/admin/admin_notice_write_ok.do">
			<!-- 카테고리:공지사항 -->
			<input type="hidden" id="category" name="category" value="notice"/>
			<!-- 글 제목 -->
			<div class="form-group">
				<label for="subject" class="control-label">글 제목</label>
				<div>
					<input type="text" class="form-control" id="subject" name="subject" placeholder="제목을 입력하세요.">
				</div>
			</div>
			<!--// 글 제목 -->
					
			<!-- 내용 -->
			<div class="form-group">
				<label for="content" class="control-label">내용</label>
				<div>
					<textarea id="content" name="content" class="form-control" rows="5" placeholder="내용을 입력하세요."></textarea>
				</div>
			</div>
			<!--// 내용 -->
			
			<!-- 첨부파일 -->
			<div class="form-group">
				<label for="file" class="control-label">첨부파일</label>
				<input type="file" name="file" id="file" class="form-control" multiple/>
			</div>
			<div class="form-group">
				<button type="reset" class="btn btn-primary">초기화</button>
			</div>
			<!--// 첨부파일 -->
			
			<div class="text-center">
				<button type="submit" class="btn btn-danger btn-default" id="notice_reg_done">등록완료</button>
				<button class="btn btn-info btn-default" data-dismiss="modal" id="notice_reg_cancel">취소</button>
			</div>
			</form>
		</div>
		
		</div>
	</div>
</div>