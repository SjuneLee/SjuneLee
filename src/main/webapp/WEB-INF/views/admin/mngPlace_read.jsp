<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<!-- naver map -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=jXfQcBQu5cG9ioO_wzwi" ></script>
<!-- // naver map -->
<%@ include file="/WEB-INF/inc/common.jsp" %>
</head>
<body>
<!-- 게시물등록 모달창 구현부분 -->
	<%@ include file="mngPlace_edit.jsp"%>
<div class="container">
	<h1 class="page-header">${bbsName} - <small>글 읽기</small></h1>
		
	<!-- 제목, 작성자, 작성일시, 조회수 -->
	<div class="alert alert-info">
		<h1 style="margin: 0">
			${readTravel.subject}
			<br/>
				<small>
					주소: ${readTravel.address} 
				</small>
			<br/>
				<small>
					<i class="glyphicon glyphicon-paperclip"></i>${readTravel.likeSum}&nbsp;&nbsp; 평점:${ratingAvg}&nbsp;&nbsp; ${commentCount}개의 평가 / 작성일시 : ${readTravel.regDate}
				</small>
		</h1>
	</div>
	
	<!-- 첨부파일 목록 표시하기 -->
	<c:if test="${fileList != null}">
		<!-- 첨부파일 목록 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="warning" style="width: 100px">첨부파일</th>
					<td>
						<c:forEach var="file" items="${fileList}">
							<!-- 다운로드를 위한 URL만들기 -->
							<c:url var="downloadUrl" value="/download.do">
								<c:param name="file" value="${file.fileDir}/${file.fileName}" />
								<c:param name="orgin" value="${file.originName}" />
							</c:url>
							<!-- 다운로드 링크 -->
							<a href="${downloadUrl}" class="btn btn-link btn-xs">${file.originName}</a>
						</c:forEach>
					</td>
				</tr>
			</tbody>
		</table>
		
		<!-- 이미지만 별도로 화면에 출력하기 -->
		<c:forEach var="file" items="${fileList}">
			<c:if test="${fn:substringBefore(file.contentType, '/') == 'image'}">
				<c:url var="downloadUrl" value="/download.do">
					<c:param name="file" value="${file.fileDir}/${file.fileName}" />
				</c:url>
				<p>
					<img src="${downloadUrl}" class="img-responsive" style="margin: auto"/>
				</p>
			</c:if>
		</c:forEach>
	</c:if>
	
	<!-- 내용 -->
	<section style="padding: 0 10px 20px 10px">
		${readTravel.content}
	</section>
		
	<!-- 다음글/이전글 -->
	<table class="table">
		<tbody>
			<tr>
				<th class="success" style="width: 100px">다음글</th>
				<td>
					<c:choose>
						<c:when test="${nextTravel != null}">
							<c:url var="nextUrl" value="/admin/mngPlace_read.do">
								<c:param name="category" value="${category}" />
								<c:param name="travel_id" value="${nextTravel.id}" />
							</c:url>
							<a href="${nextUrl}">${nextTravel.subject}</a>
						</c:when>
						<c:otherwise>
							다음글이 없습니다.
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="success" style="width: 100px">이전글</th>
				<td>
					<c:choose>
						<c:when test="${prevTravel != null}">
							<c:url var="prevUrl" value="/admin/mngPlace_read.do">
								<c:param name="category" value="${category}" />
								<c:param name="travel_id" value="${prevTravel.id}" />
							</c:url>
							<a href="${prevUrl}">${prevTravel.subject}</a>
						</c:when>
						<c:otherwise>
							이전글이 없습니다.
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
		
	<!-- 버튼들 : category값에 대한 상태유지 필요함 -->
	<div class="clearfix">
		<div class="pull-right">
			<a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=${category}" class="btn btn-info">목록보기</a>
			<!-- 수정,삭제 대상을 지정하기 위해서 글 번호가 전달되어야 한다. -->
			<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#placeeditModal">수정하기</button>
			<a href="${pageContext.request.contextPath}/admin/mngPlace_delete_ok.do?category=${category}&travel_id=${readTravel.id}" class="btn btn-danger" onclick="return confirm('삭제하시겠습니까?')">삭제하기</a>
		</div>
	</div>
</div>

		<!-- naver map -->
		<script>
		var nmap = new naver.maps.Map('naverMap', { // naverMap 값은 div 의 id 값
			center : new naver.maps.LatLng($("#mapX").val(),$("#mapY").val()), // 지도 중앙 위치 : 위도, 경도 설정
			zoom : 8, // 줌 설정 : 1~14, 수치가 클수록 지도 확대(줌인), 이 옵션 생략시 기본값 9
			zoomControl : true, // 줌 컨트롤 표시 (기본값 표시안함)
			zoomControlOptions : { // 줌 컨트롤 오른쪽 위로 위치 설정
				position : naver.maps.Position.TOP_RIGHT // 오른쪽 위로 설정값
			},
			mapTypeControl : true, // 일반ㆍ위성 지도보기 컨트롤 표시 (기본값 표시안함)
		});


		naver.maps.Event.addListener(nmap, 'click', function(e){
			$('[name=namp_lat]').val(e.coord.lat()); // 위도
			$('[name=namp_lng]').val(e.coord.lng()); // 경도
			
			$('input[name=mapX]').val(e.coord.lat());
			$('input[name=mapY]').val(e.coord.lng());
		});
		</script>
		<!-- // naver map -->

</body>
</html>