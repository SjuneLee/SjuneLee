<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
    /* 게시물 항목 하나 */
    .item {padding:0px 5px; }
    /* 게시물 제목 */
    .item h4 {margin-top: 10px; margin-bottom: 5px; }
    /* 썸네일 이미지 크기*/
    .img-responsive { width:100%; }
    
body {
    padding-top: 50px;
    padding-bottom: 20px;
    background-image: url(${pageContext.request.contextPath}/assets/img/sea5.jpg);
    background-size: 100% 100%;
}
</style>
</head>

<body>
    <!--//페이지 상단 -->
    <%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
    <!--//페이지 상단 -->

    <!--//페이지 중단 -->
    <div class="container">
         <!-- big 이미지 / 시작 -->
        <c:if test="${category == 'festival'}">
        	<img src="${pageContext.request.contextPath}/assets/img/festival.jpg" id="background_image" style="width: 100%; height: 40vh;">
        </c:if>
        <c:if test="${category == 'show'}">
        	<img src="${pageContext.request.contextPath}/assets/img/show.jpg" id="background_image" style="width: 100%; height: 40vh;">
        </c:if>
        <c:if test="${category == 'food'}">
        	<img src="${pageContext.request.contextPath}/assets/img/travel.jpg" id="background_image" style="width: 100%; height: 40vh;">
        </c:if>
        <!-- big 이미지 / 끝 -->

        <!-- 드롭다운 -->
        <form method="get" action="${pageContext.request.contextPath}/content/travel_list.do">
        <input type="hidden" name="category" value="${category}" />
        <div style="padding:2%; height:5em;">
        <select name="search" class="form-control pull-right" style="width: 20%;">
        	<option value='' selected>-- 선택 --</option>
            <option value='latest' ${param.option eq "latest" ? "selected" :""}>최신순</option>
            <option value='last' ${param.option eq "last" ? "selected" :""}>오래된순</option>
            <!-- <option value='popular'<c:if test="${search.category=='popular'}">selected</c:if>>인기순</option>  -->   
        </select>
        <button type="submit" class="btn btn-primary pull-right" >검색</button>      
        </div>
        
        </form>
        <!-- // 드롭다운 --> 
		
        <!-- 글 목록 시작 -->
        <div class="row multi-columns-row">
            <!-- 조회된 글이 있는 경우 시작 -->
            <c:choose>
                <c:when test="${fn:length(list) > 0 }">
                    <c:forEach var="travel" items="${list}">
                        <!-- 게시물 항목 하나 -->
                        <div class="col-lg-3 col-md-3 com-sm-6 col-xs-12">
                              
                            <div class="thumbnail">
                                <c:url var="readUrl" value="/content/travel_read.do">
                                    <c:param name="category" value="${travel.category}" />
                                    <c:param name="travel_id" value="${travel.id}" />
                                </c:url>
                                <!-- 링크 +썸네일 -->
                                <a href="${readUrl}" class="thumbnail imghvr-zoom-out-right">
                                   <c:choose>
                                        <c:when test="${travel.imagePath != null }">
                                            <c:url var="downloadUrl" value="/download.do">
                                                <c:param name="file" value="${travel.imagePath}"/>
                                            </c:url>                                      
                                            <img src="${downloadUrl}" class="img-responsive" />
                                            <figcaption>
                                             <h3>${travel.subject}</h3>
                                             <div class="clearfix">
		                                        <div class="pull-left">${travel.regDate}</div> 
                                    		</div>	
                                            </figcaption>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-responsive" />
                                        	<figcaption>
                                            <h3>${travel.subject}</h3>
                                             <div class="clearfix">
		                                        <div class="pull-left">${travel.regDate}</div> 
                                    		</div>	
                                            </figcaption>
                                        </c:otherwise>
                                     
                                    </c:choose>
                                    
                                </a>
                                <!-- 제목 + 작성자 +조회수 -->
<%--                                 <div class="item">
                                    <h4>${travel.subject}</h4>
                                    <div class="clearfix">
                                        <div class="pull-left">${travel.regDate}</div>
                                        <div class="pull-right">${travel.hit}view</div>
                                    </div>
                                </div> --%>
                            </div>
                             
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-md-12">
                        <p class="text-center">조회된 글이 없습니다.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <!-- //글 목록 끝 -->
<!-- 페이지 번호 시작 --> 
<nav class="text-center">
	<ul class="pagination">
		<!-- 이전 그룹으로 이동 -->
		<c:choose>
			<c:when test="${pageHelper.prevPage > 0}">
				<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
				<!-- 이전 그룹으로 이동하기 위한 URL 을 생성해서 "prevUrl"에 저장 -->
				<c:url var="prevUrl" value="/content/travel_list.do">
					<c:param name="category" value="${category}"></c:param>
					<c:param name="keyword" value="${travelkeyowrd}"></c:param>
					<c:param name="page" value="${pageHelper.prevPage}"></c:param>
				</c:url>
				
				<li><a href="${prevUrl}">&laquo;</a></li>
			</c:when>
			
			<c:otherwise>
				<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
				<li class='disabled'><a href="#">&laquo;</a></li>
			</c:otherwise>
		</c:choose>
		
		<!-- 페이지 번호 -->
		<!-- 현재 그룹의 시작페이지~끝 페이지 사이를 1씩 증가하면서 반복 -->
		<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}" step="1">
			<!-- 각 페이지 번호로 이동할 수 있는 URL 을 생성하여 page_url에 저장 -->
			<c:url var="pageUrl" value="/content/travel_list.do">
				<c:param name="category" value="${category}"></c:param>
				<c:param name="keyword" value="${travelkeyowrd}"></c:param>
				<c:param name="page" value="${i}"></c:param>
			</c:url>
			
			<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
			<c:choose>
				<c:when test="${pageHelper.page == i }">
					<li class='active'><a href="#">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageUrl}">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- 다음 그룹으로 이동 -->
		<c:choose>
			<c:when test="${pageHelper.nextPage > 0}">
				<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
				<!-- 다음 그룹으로 이동하기 위한 URL 을 생성해서 "nextUrl"에 저장 -->
				<c:url var="nextUrl" value="/content/travel_list.do">
					<c:param name="category" value="${category}"></c:param>
					<c:param name="keyword" value="${travelkeyowrd}"></c:param>
					<c:param name="page" value="${pageHelper.nextPage}"></c:param>
				</c:url>
				
				<li><a href="${nextUrl}">&raquo;</a></li>
			</c:when>
			
			<c:otherwise>
				<!-- 다음 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
				<li class='disabled'><a href="#">&raquo;</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav> 
<!-- //페이지 번호 끝 -->
      
</div>
    <%@ include file="/WEB-INF/inc/footer.jsp"%>
        <!--//페이지 하단 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/ripple/jquery.ripples-min.js"></script>
    <script>
    $(document).ready(function() {
        $("body").ripples({
            resolution : 512,
            dropRadius : 10, //px
            perturbance : 0.005,
        });
    });
    </script>
</body>
</html>