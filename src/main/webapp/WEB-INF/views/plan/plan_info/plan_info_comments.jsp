<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp" %>
<!-- 별점 기능 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/star-rating.min.css" media="all">
<script src="${pageContext.request.contextPath}/assets/js/star-rating.min.js" type="text/javascript"></script>
<!--//별점 기능 -->
<style type="text/css">
img {
	height: 70px; weight: 90px; max-width:100%;
}
#tabmenu {background-color: #2c3e50;}
.head-page {
	margin-top: 7%;
	margin-bottom: 3%;
	height: 20%;
	border-bottom: 0.5px solid #E6E6E6;
}
#contentInfo {
	padding-top: 13.5%;
}
#header_subject, #rating, #address, #likes ,#period {
color: white;
text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
div.media-body{display: inline-block; width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */ white-space: normal;}
</style>
</head>

<body  data-spy="scroll" data-target=".scrollspy">
<%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>	
<div class="container">
	<!-- head page -->
		<div class="row head-page">
			<c:choose>
			<c:when test="${planInfo[0].fileDir != null}">
				<c:url var="backGroundUrl" value="/download.do">
					<c:param name="file" value="${planInfo[0].fileDir}/${planInfo[0].fileName}" />
					<c:param name="orgin" value="${planInfo[0].fileOriginName}" />
				</c:url>
				<div class="col-md-12 col-sm-12 col-xs-12" id="contentInfo"
					style= "background: url('${backGroundUrl}') no-repeat 100% 100%/100% 100%;">
					<h2 id = "period"></h2>
					<h1 id = "header_subject">
						${planInfo[0].planSubject}
					</h1>
					<h2 id = "likes_condition" class ="text-warning">&nbsp;</h2>
					<p id ="likes">
						&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span><span id ="like_sum">${planInfo[0].planLikeSum}</span>
					</p>
					<p id = "rating">
						평점:${ratingAvg} / ${commentCount}개의 평가
					</p>
					<form class="form-horizontal" method="post">
						<input type="hidden" name="travelPlanId" value="${travelPlan.id}">
						<button type="submit"   name="likes_change" id="likes_change" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-heart"></span> 좋아요 </button><br><br>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-12 col-sm-12 col-xs-12" id="contentInfo"
					style= "background: url('${pageContext.request.contextPath}/assets/img/no_image.jpg') no-repeat 100% 100%/100% 100%;">
					<h2 id = "period"></h2>
					<h1 id = "header_subject">
						${planInfo[0].planSubject}
					</h1>
					<h2 id = "likes_condition" class ="text-warning">&nbsp;</h2>
					<p id ="likes">
						&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span><span id ="like_sum">${planInfo[0].planLikeSum}</span>
					</p>
					<p id = "rating">
						평점:${ratingAvg} / ${commentCount}개의 평가
					</p>
					<form class="form-horizontal" method="post">
						<input type="hidden" name="travelPlanId" value="${travelPlan.id}">
						<button type="submit"   name="likes_change" id="likes_change" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-heart"></span> 좋아요 </button><br><br>
					</form>
				</div>
			</c:otherwise>
			</c:choose>
		</div>
		<!--// head page -->

	<!-- tab menu -->
	<ul class="nav nav-tabs nav-tabs-responsive list-inline" id="tabmenu">
		<li><a href="${pageContext.request.contextPath}/plan/plan_info/schedule.do?travel_plan_id=${planInfo[0].planId}">여행지 정보</a></li>
		<li><a href="${pageContext.request.contextPath}/plan/plan_info/map.do?travel_plan_id=${planInfo[0].planId}" target="_blank">지도</a></li>
		<li class="active"><a href="${pageContext.request.contextPath}/plan/plan_info/comments.do?travel_plan_id=${planInfo[0].planId}">댓글</a></li>
		<!-- <li class="pull-right"><a href="#">좋아요<span class="glyphicon glyphicon-thumbs-up"></span></a></li> -->
	</ul>
	<!-- // tab menu -->
	
	<!-- 댓글 폼 시작 -->
	<form class="form-horizontal" role="form" method="post" id="comment_form"
		action="${pageContext.request.contextPath}/plan/plan_info/comment_insert.do">
	<!-- 글 번호 상태 유지 -->
	<%-- <input type="hidden" value="${readPlan.category}" name="category_comment"/> --%>
	<input type="hidden" name="travel_plan_id" value="${planInfo[0].planId}" />
	<!-- 별점 -->
	<p>이 장소가 어떠셨나요?</p>
	<input id="input-21b" value="1" type="text" class="rating" name="rating"
		data-min=0 data-max=5 data-step=1 data-size="sm" required title="">
	<!--// 별점 끝 -->
	<div class="form-group">
		<label for="comment" class="col-md-1 col-sm-2 col-xs-2 control-label">			
				<span class="glyphicon glyphicon-user" style="font-size: 50px;"></span>			
		</label>
		<div class="col-md-9 col-sm-8 col-xs-10">
			<textarea rows="3" name="content" class="form-control"
				placeholder="댓글을 입력하세요."></textarea>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-12 text-center">
			<button type="submit" class="btn btn-primary btn-lg">등록</button>
		</div>
	</div>
	</form>
	<!--// 댓글 폼 끝 -->
	
	<!-- 댓글 목록 -->
	<ul class="media-list" id="comment_list">
	
	</ul><!-- end 댓글 목록 -->
	<!-- 덧글 삭제시 사용될 Modal -->
		<div class="modal fade" id="comment_delete_modal">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">

				</div>
			</div>
		</div>
		
		<!-- 덧글 수정시 사용될 Modal -->
		<div class="modal fade" id="comment_edit_modal">
			<div class="modal-dialog">
				<div class="modal-content">

				</div>
			</div>
		</div>
</div>

<footer>
<!-- 페이지 하단 -->
<%@ include file="/WEB-INF/inc/footer.jsp" %>
<!-- // 페이지 하단-->
</footer>
<!-- 덧글 항목에 대한 템플릿 참조 -->
<script id="tmpl_comment_item" type="text/x-handlebars-template">
    <li class="media" style='border-top: 1px dotted #ccc; padding-top: 15px'
		id="comment_{{id}}">
        <div class="media-body" style='display: block;'>
            <h4 class="media-heading clearfix">
                <!-- 작성자,작성일시 -->
                <div class='pull-left'>
					<img src="${pageContext.request.contextPath}/download.do?file={{profileImg}}" class="img-thumbnail" width="40" height="40">
                    {{writerId}}
                    <small>
                        <a href='mailto:{{email}}'>
                            <i class='glyphicon glyphicon-envelope'></i></a>
                            / 평점: {{rating}} / {{regDate}}&nbsp;&nbsp;
						<!-- 수정,삭제 버튼 -->
						<a href='${pageContext.request.contextPath}/plan/plan_info/plan_comment_edit.do?comment_id={{id}}&travelPlanId={{travelPlanId}}' 
							data-toggle="modal" data-target="#comment_edit_modal" class='btn btn-warning btn-xs'>
                        	<i class='glyphicon glyphicon-edit'></i>
                    	</a>
                    	<a href='${pageContext.request.contextPath}/plan/plan_info/plan_comment_delete.do?comment_id={{id}}&travelPlanId={{travelPlanId}}' 
							data-toggle="modal" data-target="#comment_delete_modal" class='btn btn-danger btn-xs'>
                       	 	<i class='glyphicon glyphicon-remove'></i>
                    	</a>
                    </small>
                </div>               
            </h4>
            <!-- 내용 -->
            <p>{{{content}}}</p>
        </div>
    </li>
</script>
<!--// 덧글 리스트 끝-->
						
<!-- 좋아요기능 관련 스크립트 -->
<script type="text/javascript">
	$(function() {
		// 좋아요 버튼 클릭시 이벤트
		$("#likes_change").click(function(e) {
			e.preventDefault();
			var travel_plan_id_val =  '<c:out value="${planInfo[0].planId}"/>';
			console.log("travel_plan_id_val="+travel_plan_id_val);
			var loginInfo = '<c:out value="${loginInfo}"/>';
			$.ajax({
				"url" : "${pageContext.request.contextPath}/plan/plan_info/likes.do",
				"type": "post",
				"dataType": "json",
				"data": { travel_plan_id: travel_plan_id_val, login_info: loginInfo },
				"success" : function(req) {
					if(loginInfo == 0){//test
						alert("로그인 하셔야 합니다.");
						return false;	
					}
					
					if (req.result == 0) {
						
						$("#likes_condition").empty();
						$("#likes_condition").append('좋아한 일정에 없는 곳입니다.');
					} else {
						$("#likes_condition").empty();
						$("#likes_condition").append('좋아한 일정에 저장되었습니다.');
					}
				}
			})
		})
	})
</script>
<!--//좋아요기능 관련 스크립트 끝-->	

<!-- 덧글기능 관련 스크립트 -->
<script type="text/javascript">
	$(function(){
		'<c:out value="${planInfo[0].planId}"/>'
		/** 페이지가 열리면서 동작하도록 이벤트 정의 없이 Ajax요청 */
		$.get("${pageContext.request.contextPath}/plan/plan_info/comment_list.do?travel_plan_id=${planInfo[0].planId}", {
			travel_plan_id: "${planInfo[0].planId}"
		}, function(json) {
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			// 템플릿 HTML을 로드한다.
			var template = Handlebars.compile($("#tmpl_comment_item").html());
			
			// JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			for (var i=0; i<json.item.length; i++) {
				json.item[i].content 
					= json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 덧글 아이템 항목 하나를 템플릿과 결합한다.
				var html = template(json.item[i]);
				// 결합된 결과를 덧글 목록에 추가한다.
				$("#comment_list").append(html);
			}
		});
		// 덧글 작성 폼의 submit 이벤트 ajax 구현
		$("#comment_form").ajaxForm(function(json){
			if(json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			// 줄 바꿈에 대한 처리
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			// --> JSON에 포함된 'lt;br/&gt;' 을 검색에서 <br/>로 변경함.
			json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
			// 템플릿 HTML을 로드한다.
			var template = Handlebars.compile($("#tmpl_comment_item").html());
			// 덧글 아이템 항목 하나를 템플릿과 결합한다.
			var html = template(json.item);
			// 결합된 결과를 덧글 목록에 추가한다.
			$("#comment_list").append(html);
			// 폼 태그의 입력값을 초기화 하기 위해서 reset이벤트를 강제로 호출
			$("#comment_form").trigger('reset');
		});
	
	
	/** 모든 모달창의 캐시 방지 처리 */
	$('.modal').on('hidden.bs.modal', function (e) {
		// 모달창 내의 내용을 강제로 지움.
		$(this).removeData('bs.modal');
	});
	
	/** 동적으로 로드된 폼 안에서의 submit 이벤트 */
	$(document).on('submit', "#plan_comment_delete_form", function(e) {
		e.preventDefault();
		
		// AjaxForm 플러그인의 강제 호출
		$(this).ajaxSubmit(function(json) {
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			
			alert("삭제되었습니다.");
			// modal 강제로 닫기
			$("#comment_delete_modal").modal('hide');
			
			// JSON 결과에 포함된 덧글일련번호를 사용하여 삭제할 <li>의 id값을 찾는다.
			var comment_id = json.commentId;
			$("#comment_" + comment_id).remove();
		});
	});
	
	$(document).on('submit', "#plan_comment_edit_form", function(e) {
		e.preventDefault();
		
		// AjaxForm 플로그인의 강제 호출
		$(this).ajaxSubmit(function(json) {
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			
			// 줄 바꿈에 대한 처리
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			// --> JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
			json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
			
			// 템플릿 HTML을 로드한다.
			var template = Handlebars.compile($("#tmpl_comment_item").html());
			// JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
			var html = template(json.item);
			// 결합된 결과를 기존의 덧글 항목과 교체한다.
			$("#comment_" + json.item.id).replaceWith(html);
			
			// 덧글 수정 모달 강제로 닫기
			$("#comment_edit_modal").modal('hide');
		});
	});//end submit edit_form
});
</script>

<script type="text/javascript">
/* 상세일정 만들기 클릭시 날짜 출력  */
$(function() {
		var startdate ="${planInfo[0].startDate}"; //시작일
		var period = ${planInfo[0].term}; //여행기간
		var prd = parseInt(period) - 1;//여행기간 >> 숫자형변환 - 1
		
		function date_add(startdate, prd) { //함수 : startdate에서 (period - 1)일 후 날짜를 리턴함.
			var yy = parseInt(startdate.substr(0,4),10);
			var mm = parseInt(startdate.substr(5,2),10);
			var dd = parseInt(startdate.substr(8),10);
			d= new Date(yy, mm-1, dd + prd);
			
			yy = d.getFullYear();
			mm = d.getMonth() + 1; mm = (mm < 10)?'0' + mm : mm;
			dd = d.getDate(); dd = (dd <10)?'0' + dd : dd;
			return '' + yy + '-' +  mm  + '-' + dd;
		}
		
		var lastdate = date_add(startdate, prd);//마지막일
		$("#period").html(startdate + "~" + lastdate);//"시작일 ~ 마지막일"을  head page에 출력"
});
/*//상세일정 만들기 클릭시 날짜 출력  */
</script>
</body>
</html>