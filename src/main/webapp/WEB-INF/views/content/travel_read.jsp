<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang='ko'>
<head>
<!-- navermap -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=jXfQcBQu5cG9ioO_wzwi"></script>

<%@ include file="/WEB-INF/inc/common.jsp"%>
<!-- star rating -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/star-rating.min.css" media="all">
<!-- <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
<script src="${pageContext.request.contextPath}/assets/js/star-rating.min.js" type="text/javascript"></script>
<!-- // star rating -->
<!-- lightbox -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/lightbox/css/lightbox.min.css" />
<script src="${pageContext.request.contextPath}/assets/plugins/lightbox/js/lightbox.min.js"></script>
<!--//lightbox -->

<style type="text/css">

#bestloc {
  color: #fff;  
  text-shadow: 0 0 20px #FF0000;
}
.carousel-control {
	height: 15%;
	width: 5%;
	margin-top: 10%;
}
.head-page {
	margin-top: 7%;
	margin-bottom: 3%;
	height: 20%;
	border-bottom: 0.5px solid #E6E6E6;
}
#contentInfo {
	padding-top: 13.5%;
}
#header_subject, #rating, #address {
	color: white;
}
#bestloc {
	position: fixed;
	margin-right: 2em;
	bottom: 1em;
	right: 3em;;
}

.nav-tabs .active #navTab {
	/* background-color: orange; */
	background-color: #A5DF00;
}
	   
#header_subject,#address,#rating {
	text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black
}

div.media-body{display: inline-block; width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */ white-space: normal;}      
</style>
</head>
<body>

	<!-- container -->
	<div class="container">
		<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
		<!-- 여행지 정보 헤더 -->
		<div class="row head-page">
		<!-- 첨부파일 이미지와 제목, 주소, 좋아요 총갯수, 평점, 댓글수 부분 시작 -->
		
		<!-- 첨부파일이 있을 때와 없을 때로 분기 처리 -->
		<c:choose>
			<c:when test="${fileList[0] != null}">
				<c:url var="backGroundUrl" value="/download.do">
					<c:param name="file" value="${fileList[0].fileDir}/${fileList[0].fileName}" />
					<c:param name="orgin" value="${fileList[0].originName}" />
				</c:url>
			<div class="col-md-8 col-sm-12 col-xs-12" id="contentInfo"
				style= "background: url('${backGroundUrl}') no-repeat 100% 100%/100% 100%;">
				<h1 id = "header_subject">
					${readTravel.subject}

				</h1>
				<form class="form-horizontal" method="post" name="likes_change" id="likes_change">			
					<input type="hidden" name="travelId" value="${travel.id}">
					<button type="submit" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-paperclip"></span> 클립보드 </button><br><br>
				</form>
				<h2 id = "likes_condition" class ="text-warning">&nbsp;&nbsp;</h2>
				<p id = "address">
					<span class="glyphicon glyphicon-map-marker"></span>${readTravel.address}
				</p>
				<p id = "rating">
					<span class="glyphicon glyphicon-paperclip"></span><span id ="like_sum">${readTravel.likeSum}</span>&nbsp;&nbsp;평점:${ratingAvg}&nbsp;&nbsp;${commentCount}개의 평가
				</p>
			</div>
			</c:when>
			
			<c:otherwise>
			<div class="col-md-8 col-sm-12 col-xs-12" id="contentInfo"
				style= "background: url('${pageContext.request.contextPath}/assets/img/no_image.jpg') no-repeat 100% 100%/100% 100%;">
				<h1 id = "header_subject">
					${readTravel.subject}
					<!-- <a href="#"><span class="glyphicon glyphicon-paperclip"></span></a> -->
				</h1>
				<form class="form-horizontal" method="post" name="likes_change" id="likes_change">			
					<input type="hidden" name="travelId" value="${travel.id}">
					<button type="submit" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-paperclip"></span> 클립보드 </button>
				</form>
				<h2 id = "likes_condition" class ="text-warning">&nbsp;&nbsp;</h2>
				<p id = "address">
					<span class="glyphicon glyphicon-map-marker"></span>${readTravel.address}
				</p>
				<p id = "rating">
					<span class="glyphicon glyphicon-paperclip"></span>${readTravel.likeSum}&nbsp;&nbsp;평점:${readTravel.ratingAvg}&nbsp;&nbsp;${commentCount}개의 평가
				</p>
			</div>
			</c:otherwise>
		</c:choose>
		<!--// 첨부파일 이미지와 제목, 주소, 좋아요 총갯수, 평점, 댓글수 부분 끝-->
		
			<!-- 네이버 맵 부분 시작 -->
			<div class="pull-left col-md-4 col-sm-12 col-xs-12"
				style="padding: 0;">
				<!-- Naver map -->
				<div id="map" style="width: 100%; height: 410px; margin:0; padding: 0;"></div>
				<!-- // Naver map -->
			</div>
			<!--//네이버 맵 부분 끝 -->
		</div>
		<!--// 여행지 정보 헤더 끝 -->
		
		<!-- 여행지 상세정보 -->
		<!-- <div class=" col-md-offset-2 col-md-7 col-xs-12"> -->
		<div class="col-md-offset-1 col-md-10 col-xs-12">
			<!-- 여행지 이미지 케러셀 (아직 구현이 제대로 안되었음 3개까지만 됨)-->
			<div id="carousel" class="carousel slide">
				<ol class="carousel-indicators">
					<li data-target="#carousel" data-slide-to="0" class="active"></li>
					<li data-target="#carousel" data-slide-to="1"></li>
					<li data-target="#carousel" data-slide-to="2"></li>
				</ol>

				<!-- carousel-inner -->
				<div class="carousel-inner">
					<!-- item -->
					<div class="item active">
						<div class="row">
							<c:forEach var="image" items="${fileList}" varStatus="status">
								<c:url var="imageFileUrl" value="/download.do">
									<c:param name="file" value="${fileList[status.index].fileDir}/${fileList[status.index].fileName}" />
									<c:param name="orgin" value="${fileList[status.index].originName}" />
								</c:url>
								<c:choose>
									<c:when test="${status.index lt 3}">
										<div class="col-md-4 col-xs-4">
											<a href="${imageFileUrl}" class="thumbnail" data-lightbox="my_thumbnail">
												<img src="${imageFileUrl}">
											</a>
										</div>
									</c:when>
									<c:otherwise>
										<a href="${imageFileUrl}" class="thumbnail" data-lightbox="my_thumbnail" style="display:none">
											<img src="${imageFileUrl}">
										</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>
					<!-- // item -->

					<!-- carousel-화살표 -->
					<div>
						<a data-slide="prev" href="#carousel"
							class="left carousel-control"><span class="icon-prev"></span></a>
						<a data-slide="next" href="#carousel"
							class="right carousel-control"><span class="icon-next"></span></a>
					</div>
					<!-- // carousel-화살표 -->
				</div>
				<!-- // carousel-inner -->
			</div>
			<!--// 여행지 이미지 케러셀 끝 -->
			
			<!-- tab menu -->
			<ul class="nav nav-tabs nav-tabs-responsive nav-justified list-inline" style="margin:0;">
				<li class="active" style="margin:0; padding:0;">
					<a href="#tab1" data-toggle="tab" id="navTab">여행지 정보</a>
				</li>
				<li style="margin:0; padding:0;">
					<a href="#tab2" data-toggle="tab" id="navTab">평점</a>
				</li>
			</ul>
			<!-- // tab menu -->

			<!-- tab contents -->
			<!-- <div class="tab-content" style="border: groove 2px #eee; height: 100%;"> -->
			<div class="tab-content">
			
				<!-- 여행지 정보 탭(tab1) -->
				<div class="tab-pane fade in active" id="tab1">
					<div style="border: groove; border-width: 2px 2px 0px 2px;">
						<h4>여행지 요약 정보</h4>
						<h4>${readTravel.summary}</h4>
					</div>
					<div style="border: groove; border-width: 2px 2px 2px 2px;">
						<h4>여행지 상세 설명</h4>
						<h4>${readTravel.content}</h4>
					</div>
					<!-- <div style="border: groove; border-width: 2px 2px 2px 2px; height: 10em;">
						<h4>주변 여행지</h4>
					</div> -->
				</div>
				<!--// 여행지 정보 탭(tab1) 끝 -->
				
				<!-- 평점 탭(tab2) -->
				<div class="tab-pane fade in" id="tab2">
					
					<div class="col-md-12 col-sm-12 col-xs-12" style="border: groove; border-width: 2px 2px 2px 2px">
						<h3 class="text-center">&nbsp;&nbsp;평점 내용</h3>
						<!-- 덧글 등록 -->
						<form class="form-inline" id="comment_form" method="post"
							action="${pageContext.request.contextPath}/content/travel_read/comment_insert.do">
							<!-- POST 상태 유지 : category, id -->
							<input type="hidden" value="${readTravel.category}" name="category_comment"/>
							<input type="hidden" value="${readTravel.id}" name="travel_id"/>
							<!-- 별점 -->
							<p>이 장소가 어떠셨나요?</p>
							<input id="input-21b" value="1" type="text" class="rating" name="rating"
								data-min=0 data-max=5 data-step=1 data-size="sm" required title="">
							<!--// 별점 끝 -->
							<textarea class="form-control" name="content" placeholder="내용을 입력하시오." style="width: 80%; height: 100%;" ></textarea>
							<input type="submit" value="등록" class="btn btn-primary btn-md" style="font-size: 1.5rem;" />
						</form> 
						<!--// 덧글 등록 끝 -->
						
						<!-- 덧글 리스트 -->
						<ul class="media-list" id="comment_list">
						</ul>
						
						<!-- 덧글 항목에 대한 템플릿 참조 -->
						<script id="tmpl_comment_item" type="text/x-handlebars-template">
							<li class="media" style='border-top: 0.5px dotted #ccc; padding-top: 15px' id="comment_{{id}}">
								<div class="media-body" style='display: block;'>
									<h4 class="media-heading clearfix">
										<!-- 작성자,작성일시 -->
										<div class='pull-left'>
											<img src="${pageContext.request.contextPath}/download.do?file={{profileImg}}" class="img-thumbnail" width="40" height="40">
											{{writerId}}
											<small>
												<a href="#">
													<i class='glyphicon glyphicon-envelope'></i>{{email}}</a>
													 / 평점: {{rating}} / {{regDate}}&nbsp;&nbsp;
												<!-- 수정,삭제 버튼 -->
												<a href='${pageContext.request.contextPath}/content/travel_read/comment_edit.do?comment_id={{id}}'
													data-toggle="modal" data-target="#comment_edit_modal" class='btn btn-warning btn-xs'>
													<i class='glyphicon glyphicon-edit'></i>
												</a>
												<a href='${pageContext.request.contextPath}/content/travel_read/comment_delete.do?comment_id={{id}}&travel_id={{travelId}}'
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
				</div>
				<!--// 평점 탭(tab2) 끝 -->
			</div>
			<!-- // tab contents -->
		</div>
		<!-- //상세정보 끝 -->

		<%-- <!-- best 여행지(사이드바)-->
		<div id="bestloc" class="hidden-xs hidden-sm" style=" height: 65%; width: 20%; background-color: #FFE4B5; border:solid 2px #000; z-index: 1000;">
			<h2 style="text-align: center; margin-top: 10%;">Best 여행지</h2>
			<div class="row" style="height: 33%;">
				<div class="col-sm-12 col-xs-12 text-center">
					<a href="#"><img src="${pageContext.request.contextPath}/assets/img/slide-1.jpg"
						style="margin-top: 10%; width: 90%; height: 100%;"></a>
				</div><br/>
				<div class="col-sm-6 col-xs-6 clearfix pull-left" style="margin-left: 5%;">
					<h4 style="padding-top: 10%;">여행지명</h4>
					<p>
						<span class="glyphicon glyphicon-paperclip"></span>123&nbsp;&nbsp;평점:5.0
					</p>
				</div>
			</div>

			<div class="row" style="height: 50%;">
				<div class="col-sm-12 col-xs-12 text-center">
					<a href="#"><img src="${pageContext.request.contextPath}/assets/img/slide-1.jpg"
						style="margin: 0; width: 90%; height: 100%;"></a>
				</div><br/>
				<div class="col-sm-6 col-xs-6 clearfix pull-left" style="margin-left: 5%;">
					<h4 style="padding-top: 10%;">여행지명</h4>
					<p>
						<span class="glyphicon glyphicon-paperclip"></span>123&nbsp;&nbsp;평점:5.0
					</p>
				</div>
			</div>
		</div>
		<!-- // best 여행지(사이드 바)--> --%>
	</div>
	<!-- // container -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	
	<!-- 네이버 지도 관련 스크립트 -->
	<script type="text/javascript">
	//여행지의 X,Y좌표를 변수 mapX, mapY에 저장
	var mapX = '<c:out value="${readTravel.mapX}"/>';
	var mapY = '<c:out value="${readTravel.mapY}"/>';
	
	// 초기 지도설정
	//var navermap = new naver.maps.LatLng(37.5666805, 126.9784147); 
	var navermap = new naver.maps.LatLng(mapX, mapY);
	var map = new naver.maps.Map('map', {
		//center: new naver.maps.LatLng(37.5666805, 126.9784147),
		center: new naver.maps.LatLng(mapX, mapY),
		zoom: 10
	});
	var marker = new naver.maps.Marker({
		position: navermap,
		map: map
	});
	// 지도 infowindow에 contentString을 담는 함수
	var infowindow = new naver.maps.InfoWindow({
		content: null
	});
	// 지도아이콘 클릭시 정보창 닫히고,열리는 함수
	naver.maps.Event.addListener(marker, "click", function(e) {
	if (infowindow.getMap()) {
		infowindow.close();
	} else {
		infowindow.open(map, marker);
		}
	});
	</script>
	<!--//네이버 지도 관련 스크립트 끝-->
	<!-- 좋아요기능 관련 스크립트 -->
	<script type="text/javascript">
		$(function() {
			// 좋아요 버튼 클릭시 이벤트
			$("#likes_change").click(function(e) {
				e.preventDefault();
				var travel_id_val = ${readTravel.id};
				var loginInfo = '<c:out value="${loginInfo}"/>';
				$.ajax({
					"url" : "${pageContext.request.contextPath}/content/travel_read/likes.do",
					"type": "post",
					"dataType": "json",
					"data": { travel_id: travel_id_val, login_info: loginInfo },
					"success" : function(req) {
						if(loginInfo == 0){//test
							alert("로그인 하셔야 합니다.");
							return false;	
						}
						
						if (req.result == 0) {
							
							$("#likes_condition").empty();
							$("#likes_condition").append('클립보드에 없는 곳입니다.');
						} else {
							$("#likes_condition").empty();
							$("#likes_condition").append('클립보드에 저장되었습니다.');
						}
					}
				})
			})
		})
	</script>
	<!--//좋아요기능 관련 스크립트 끝-->
	<!-- 덧글기능 관련 스크립트 -->
	<script type="text/javascript">
		$(function() {
			/** 페이지가 열리면서 동작하도록 이벤트 정의 없이 Ajax요청 */
			$.get("${pageContext.request.contextPath}/content/travel_read/comment_list.do", {
				travel_id: "${readTravel.id}"
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
			
			/** 덧글 작성 폼의 submit 이벤트 Ajax 구현  */
			// <form>요소의 method, action속성과 <input>태그를
			// Ajax요청으로 자동 구성한다.
			$("#comment_form").ajaxForm(function(json) {
				// json은 API에서 표시하는 전체 데이터
				if (json.rt != "OK") {
					alert(json.rt);
					return false;
				}
				
				// 줄 바꿈에 대한 처리
				// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
				// --> JSON에 포함된 'lt;br/&gt;' 을 검색에서 <br/>로 변경함.
				json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 템플릿 HTML을 로드한다.
				var template = Handlebars.compile($("#tmpl_comment_item").html());
				// JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
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
			$(document).on('submit', "#comment_delete_form", function(e) {
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
			
			/** 동적으로 로드된 폼 안에서의 submit 이벤트 */
			$(document).on('submit', "#comment_edit_form", function(e) {
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
	<!-- //덧글기능 관련 스크립트 끝 -->
</body>

</html>