<%@page import="study.spring.seoul4u.helper.WebHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>

<style type="text/css">
html{
   height: 100%;
}
body {
   padding: 0;
   margin-top: 2vh;
   background-color: #48a3ff;
   z-index: -10px;
   /* height: 100%; */
}
#content{
   margin-top: 3%;
}
#column1 {
   /* width: 100%; */
   height: 100vh;
   margin:0;
   padding: 0;
   /* margin-top: 5%; */
   
}
#column2 {
   /* width: 100%; */
   height: 100%;
   background-color: #48a3ff;
}
#gmap {
   width: 100%;
   height: 100%;
}
/* #column2 > div > div {
   border-bottom: 0.5px solid;
} */
#column2 > #close {
   height: 10vh;
   /* vertical-align: middle; */
   padding-top: 10%;
   margin-top: 3vh;
}
#column2 .glyphicon {
   color: black;
}
#day_count #day {
   color: orange;
}
#day_count #day:hover {
   color: red;
   text-decoration: none;
}
</style>

</head>
<body>
   <!-- 페이지 상단 -->
   <div class="row">
   <%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>
   </div>
   <!--//페이지 상단 -->

   <!-- 페이지 중단 -->
   <div id="content">

      
      <!-- 일정선택 창 -->
      <div class="col-md-2 col-sm-3 clo-xs-12" id="column2">
         <div class="text-center" id="close">
            <h4>
               <b style="color:white;">여행지도 닫기</b>
               <a href="javascript:closeWindow();"><span class="glyphicon glyphicon-remove" style=""></span></a>
            </h4>
         </div>
         <!-- 일정 생기는 부분 -->
         <div class="new" id="day_count"></div>
         <!--// 일정 생기는 부분 끝-->
      </div>
      <!--//일정선택 창 -->
            <!-- google Map 화면 -->
      <div id="column1" class="col-md-10 col-sm-9 col-xs-12">
         <div id="gmap"></div>
      </div>
      <!--//google Map 화면 -->
   </div>
   <!--//페이지 중단 -->

   <!-- 페이지 하단 -->
   <!--//페이지 하단 -->

<!-- 현재 탭 닫기 함수 -->
<script>
   function closeWindow() {
      window.open('','_parent','');
      window.close();
   }
</script>
<!-- 현재 탭 닫기 함수 -->

<!-- 일정만큼 탭 표시 -->
<script type="text/javascript">
var term = '<c:out value="${planInfo[0].term}"/>';
for (var i=0; i < term; i++) {
   var div = $('<div>').addClass("text-center").html('<br/><h1><b><a href="day' + (i+1) + '" id="day">DAY' + (i+1) + '</a></b></h1><hr/>');
   $(".new").append(div);
   }
</script>
<!--// 일정만큼 탭 표시 끝-->


<!-- 구글 맵 스크립트 참조 -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCW32UIKoxVxO7MYMmf-arhD2Np3F_-IrM"></script>
<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- gmap 플러그인 참조 -->
<script src="${pageContext.request.contextPath}/assets/plugins/gmaps/gmaps.min.js"></script>

<!-- gmap API -->
<script type="text/javascript">

$(function() {
   
   /** 지도 표시하기 */
   var map = new GMaps({
      el: '#gmap',      // 지도를 표시할 div의 id값.
      lat: 36,      // 지도가 표시될 위도
      lng: 126.97,      // 지도가 표시될 경도
      zoom: 7,
      zoomControl: true    // 줌 컨트롤 사용 여부
   });
   //장소
   var locations = [
         <c:forEach var="plan" items="${planInfo}" varStatus="status">
         {lat: ${plan.travelMapX},// 숫자값이어서 쌍따옴표 없음
          lng: ${plan.travelMapY},// 숫자값이어서 쌍따옴표 없음
          day: ${plan.day},// 숫자값이어서 쌍따옴표 없음
          content_no: ${plan.contentNo},// 숫자값이어서 쌍따옴표 없음
          subject: "${plan.travelSubject}",
          address: "${plan.travelAddress}",
       },
         </c:forEach>
      ];
   console.log(locations);
   for (i=0; i < locations.length; i++) {
   /** 표시할 위치에 대한 위도,경도 */
   var lat_value = locations[i].lat;
   var lng_value = locations[i].lng;
   console.log(lat_value, lng_value);
   
   /** 표시중인 위치에 마커 추가 */
   // 필요한 만큼 반복하면 마커가 여러 개 표시된다.
   map.addMarker({
   // 마우스 오버시 노란 박스
      title: '그린컴퓨터아트학원 강남캠퍼스(본관)',
      lat: lat_value,      // 마커가 표시될 위도
      lng: lng_value,      // 마커가 표시될 경도
      icon: {            // 사용자 정의 아이콘
      url: "${pageContext.request.contextPath}/assets/img/marker.png",
      scaledSize: new google.maps.Size(50, 50)},
      infoWindow: {      // 클릭시 표시될 말풍선 <-- HTML 코딩 가능함.
         content:'<h4>DAY' + locations[i].day + '</h4>' +
               '<h4>' + locations[i].content_no + '번째 여행지</h4>' +
               '<p>장소 : ' + locations[i].subject + '</p>' +
               '<p>주소 : ' + locations[i].address + '</p>'
         }
      });
   }// end for
   
});
   $(function(){
      $(document).on('click', '#day', function(e) {
         e.preventDefault();
         $(this).attr("style", "color:red; text-decoration:none;");
         $("#day_count a").not(this).attr("style", "color: orange");
         // 클릭된 날짜를 변수화
         var clicked_day = parseInt($(this).html().substring(3,4));
         
         console.log("클릭된 일자=" + clicked_day);
         
      
         // 컨트롤러에서 받아온 변수로 배열을 만든다
         var locations_day = [
            <c:forEach var="plan" items="${planInfo}" varStatus="status">
            {lat: ${plan.travelMapX},// 숫자값이어서 쌍따옴표 없음
             lng: ${plan.travelMapY},// 숫자값이어서 쌍따옴표 없음
             day: ${plan.day},// 숫자값이어서 쌍따옴표 없음
             content_no: ${plan.contentNo},// 숫자값이어서 쌍따옴표 없음
             subject: "${plan.travelSubject}",
             address: "${plan.travelAddress}",
             },
            </c:forEach>
         ];
      
         // 이 일정에 속한 모든 장소의 좌표와 day
         console.log(locations_day);
         
         
         
         // 클릭한 day에 해당하는 장소의 좌표들 배열화
         var locations = [];
         for(i=0; i<locations_day.length; i++) {
         
            if (locations_day[i].day == clicked_day) {
               locations.push(
                     {lat: locations_day[i].lat,
                      lng: locations_day[i].lng,
                      day: locations_day[i].day,
                      content_no: locations_day[i].content_no,
                      subject: locations_day[i].subject,
                      address: locations_day[i].address,
                  })
            }
         }
         
         // 클릭한 day에 해당하는 장소 좌표의 배열
         console.log(locations);
         
         /** 지도 표시하기 */
         var map = new GMaps({
            el: '#gmap',      // 지도를 표시할 div의 id값.
            lat: locations[0].lat,      // 지도가 표시될 위도
         lng: locations[0].lng,      // 지도가 표시될 경도
            zoom: 11,
            zoomControl: true    // 줌 컨트롤 사용 여부
         });
         for (i=0; i < locations.length; i++) {
            /** 표시할 위치에 대한 위도,경도 */
            var lat_value = locations[i].lat;
            var lng_value = locations[i].lng;
            console.log(lat_value, lng_value);
            
            /** 표시중인 위치에 마커 추가 */
            // 필요한 만큼 반복하면 마커가 여러 개 표시된다.
            map.addMarker({
            // 마우스 오버시 노란 박스
               title: '그린컴퓨터아트학원 강남캠퍼스(본관)',
               lat: lat_value,      // 마커가 표시될 위도
               lng: lng_value,      // 마커가 표시될 경도
               icon: {            // 사용자 정의 아이콘
               url: "${pageContext.request.contextPath}/assets/img/marker.png",
               scaledSize: new google.maps.Size(50, 50)},
               infoWindow: {      // 클릭시 표시될 말풍선 <-- HTML 코딩 가능함.
                  content:'<h4>DAY' + locations[i].day + '</h4>' +
                        '<h4>' + locations[i].content_no + '번째 여행지</h4>' +
                        '<p>장소 : ' + locations[i].subject + '</p>' +
                        '<p>주소 : ' + locations[i].address + '</p>'
                  }
               });
            }// end for
      });
   });
</script>
<!--//gmap API 끝 -->


</body>
</html>