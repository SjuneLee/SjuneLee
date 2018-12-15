<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
	
	<!-- Modal -->
  <div class="modal fade" id="submitModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="padding:35px 50px;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4>출발일 선택</h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form name="plan_info" id="plan_info" role="form" method="get" action="${pageContext.request.contextPath}/content/insertTravelPlan.do">
          	<input type="hidden" id="start_date" name="start_date" />
          	<input type="hidden" id="last_date" name="last_date" />
            <div class="form-group">
              <label for="plan_name">여행제목</label>
              <input type="text" class="form-control" id="plan_name" name="plan_name" placeholder="제목을 입력하세요." maxlength="20">
            </div>
            <div class="form-group">
              <label for="plan_date">여행기간</label>
              <input type="text" class="form-control" id="plan_date" name="plan_date"  readonly/>
            </div>
              <input type="hidden" class="form-control" id="plan_period" name="plan_period"  readonly/>
              <button type="submit" class="btn btn-success btn-block">완료</button>
          </form>
        </div>
        <div class="modal-footer">
        </div>
      </div>
      
    </div>
  </div>