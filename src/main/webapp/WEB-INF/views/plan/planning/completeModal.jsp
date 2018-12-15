<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
	
<!-- Modal -->
<div class="modal fade" id="completeModal" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="padding:20px 20px;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4>일정만들기 완료</h4>
			</div>
			<div class="modal-body" style="padding:20px 20px;">
				<!-- <form name="plan_info" id="plan_info" role="form" method="get" action="#"> -->
					<div class="form-group">
						<label for="season">여행 계절</label>
						<input type="radio" name="season" value="1"> 봄 
						<input type="radio" name="season" value="2"> 여름 
						<input type="radio" name="season" value="3"> 가을 
						<input type="radio" name="season" value="4"> 겨울 
					</div>
					<div class="form-group">
						<label for="theme">여행 테마</label>
						<input type="radio" name="theme" value="1"> 나홀로 
						<input type="radio" name="theme" value="2"> 커플 
						<input type="radio" name="theme" value="3"> 친구 
						<input type="radio" name="theme" value="4"> 가족 
						<input type="radio" name="theme" value="5"> 단체 
						<input type="radio" name="theme" value="6"> 비즈니스
					</div>
					<div class="text-right">
						<button id="completeSave" class="btn btn-success">완료</button>
					</div>
				<!-- </form> -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).on('click', '#completeSave', function(e) {
		var start_date=${year}+'-'+${month}+'-'+ ${day};
		//var term=${plan_period}+1;
		var term=${plan_period}+1;
		var subject="${plan_name}";
		//var season = 0;
		var season = $('input[name="season"]:checked').val();
		var theme = $('input[name="theme"]:checked').val();
		
		//detailPlan json 만들기
		var filter = $("#detail_plan").find(".content");
		// 각각의 Day마다의 여행지 content 갯수
		var content_no = filter.length;
		var filter2 = $("#day_count").find(".text-center");
		//전체 Day 여행일 갯수
		var daycount = filter2.length;
		
		var detailPlan = [];
		for(i=0; i<daycount; i++){
			for(k=0; k < $( "#tab" + (i+1) ).find(".content").length; k++){
					detailPlan.push({"day" :i+1,
									"content_no" :k+1,
									"travel_id" : $("#tab"+ (i+1)+">.content:nth-child("+(k+1)+")").find("#travel_id").val()
									//"term" : daycount
						});
				}//day i의 컨텐츠 길이만큼 반복
		}//day길이 : i만큼 반복
		
		var StringDetailPlan = JSON.stringify(detailPlan);
		var test = JSON.parse(StringDetailPlan);
		//var jsonDetailPlanArray = JSON.stringify(detailPlanArray);
		console.log(filter);
		console.log("각 Day에 해당하는 여행지 content 갯수 = " + content_no);
		console.log("현재 여행코스의 여행일 갯수=" + daycount);    	
		console.log("StringDetailPlan=" + StringDetailPlan);
		
		$.post("${pageContext.request.contextPath}/content/plan_save.do", {
			//start_date: "${year}-${month}-${day}",
			//term: "${plan_period}+1",
			term_num: daycount,
			//subject: "${plan_name}",
			travel_plan_id: "${travel_plan_id}",
			//subject: subject,
			season: season,
			theme: theme,
			detailPlan: StringDetailPlan
		},
			function(data){ // 통신이 성공했을 때 실행되는 함수.
				alert("여행일정이 완성되었습니다.");
				//alert("planning2.jsp 저장 성공");
				//if (data.result > 0){
				//	alert("상세일정 저장 성공");
				//}
				window.location.href="${pageContext.request.contextPath}/mypage/end_plan.do";
			});
	});
</script>