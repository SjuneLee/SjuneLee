<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>	

  
  <!-- Modal -->
<div class="modal fade" id="outModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" 
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><b>Logout</b></h4>
            </div>
            <div class="modal-body">
                <p>정말 로그아웃하시겠습니까?</p>
            </div>
            <div class="modal-footer">
            	<form method="post" action="${pageContext.request.contextPath}/member/logout.do">
                <button type="submit" class="btn btn-success btn-block">확인</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#open_modal_btn").click(function(e) {
            // 스크립트를 사용하여 특정 Modal을 강제로 열기
            $("#myModal").modal('show');
            // 아래는 창을 강제로 닫기 처리
            //$("#myModal").modal('hide');
        });
    });
</script>