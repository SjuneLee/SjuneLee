<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

	<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="padding:35px 50px;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4><span class="glyphicon glyphicon-lock"></span><b>Login</b></h4>
        </div>
        <div class="modal-body" style="padding:40px 50px;">
          <form name="login" id="login" role="form" method="post" action="${pageContext.request.contextPath}/member/login_ok.do">
            <div class="form-group">
              <label for="user_id1"><span class="glyphicon glyphicon-user"></span>UserID</label>
              <input type="text" class="form-control" id="user_id1" name="user_id1" placeholder="아이디를 입력하세요." maxlength="20">
            </div>
            <div class="form-group">
              <label for="user_pw1"><span class="glyphicon glyphicon-eye-open"></span>Password</label>
              <input type="password" class="form-control" id="user_pw1" name="user_pw1" placeholder="비밀번호를 입력하세요." maxlength="20">
            </div>
            <div class="checkbox">
              <label><input type="checkbox" value="" checked>아이디 저장하기</label>
            </div>
              <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span>Login</button>
          </form>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
          <p>아이디를 잃어버리셨나요? <a href="${pageContext.request.contextPath}/member/find_idpw.do">아이디 찾기</a></p>
          <p>비밀번호를 잃어버리셨나요? <a href="${pageContext.request.contextPath}/member/find_idpw.do">비밀번호 찾기</a></p>
        </div>
      </div>
      
    </div>
  </div>