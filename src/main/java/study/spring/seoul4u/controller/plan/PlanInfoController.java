package study.spring.seoul4u.controller.plan;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.spring.seoul4u.controller.admin.BBSCommon;
import study.spring.seoul4u.helper.RegexHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.PlanTravelFileJoinByDetail;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.LikePlanService;
import study.spring.seoul4u.service.MemberService;
import study.spring.seoul4u.service.PlanningService;
import study.spring.seoul4u.service.TravelPlanCommentService;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class PlanInfoController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(PlanInfoController.class);
	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	RegexHelper regex;
	@Autowired
	BBSCommon bbs;
	@Autowired
	UploadHelper upload;
	@Autowired
	MemberService memberService;
	@Autowired
	PlanningService planningService;
	@Autowired
	TravelPlanService travelPlanService;
	@Autowired
	TravelPlanCommentService travelPlanCommentService;
	@Autowired
	LikePlanService likePlanService;

	/** 여행지 상세 페이지 >> 스케줄 */
	@RequestMapping(value = { "/plan/plan_info/schedule.do" })
	public ModelAndView PlanInfoSchedule(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** webHelper 초기화 */
		web.init();

		/** 파라미터 받기 */
		int travelPlanId = web.getInt("travel_plan_id");
		logger.debug("travel_plan_id=" + travelPlanId);
		
		/** 자바빈스에 세팅 */
		PlanTravelFileJoinByDetail travelPlan = new PlanTravelFileJoinByDetail();
		travelPlan.setPlanId(travelPlanId);
		
		TravelPlan plan = new TravelPlan();
		plan.setId(travelPlanId);
		
		/** 서비스 실행 */
		//서비스 실행한 리스트를 담을 객체
		TravelPlan item = null;
		List<PlanTravelFileJoinByDetail> planInfo = null;
		
		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		//ex> travel_festival_15
		String cookieKey = "travelplan_" + travelPlanId;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		// 해당 여행지에 대한 평가 개수를 담을 변수
		int commentCount = 0;
		// 평가 개수 조회를 위한 comment 객체
		Comment travelplanComment = new Comment();
		travelplanComment.setTravelPlanId(travelPlanId);
		// 특정 여행지에 대한 평점 평균을 담을 객체
		float ratingAvg = 0;	
		
		try {
			
			//쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				travelPlanService.updateTravelPlanHit(plan);
				// 준비한 문자열에 대한 쿠키를 60초동안 저장(24시간은:60*60*24)
				web.setCookie(cookieKey, "Y", 60);
			}
			commentCount = travelPlanCommentService.selectPlanCountByTravelPlanId(travelplanComment);
			
			if (commentCount != 0) {
			ratingAvg = travelPlanCommentService.selectRatingAvgByTravelPlanId(travelplanComment);		
			}
			
			item =travelPlanService.selectTravelPlan(plan);
			planInfo = planningService.listForPlanInfo(travelPlan);
		
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		if (planInfo != null) {	
			for (int i=0; i<planInfo.size(); i++) {
				PlanTravelFileJoinByDetail Fitem = planInfo.get(i);
				String imagePath = Fitem.getImagePath();
				if(imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath,480,320,true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Fitem.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + Fitem.getImagePath());
					System.out.println("imagePath???"+ imagePath);
				}
			}
		}
		/** 브라우저에 객체를 전달 */
		model.addAttribute("planInfo", planInfo);
		model.addAttribute("ratingAvg", ratingAvg);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("item", item);
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("plan/plan_info/plan_info_schedule");
	}

	/** 여행지 상세 페이지 >> 지도 탭 */
	@RequestMapping(value = { "/plan/plan_info/map.do" })
	public ModelAndView PlanInfoMap(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** webHelper 초기화 */
		web.init();

		/** 파라미터 받기 */
		int travelPlanId = web.getInt("travel_plan_id");
		logger.debug("travel_plan_id=" + travelPlanId);
		
		/** 자바빈스에 세팅 */
		PlanTravelFileJoinByDetail travelPlan = new PlanTravelFileJoinByDetail();
		travelPlan.setPlanId(travelPlanId);
		
		/** 서비스 실행 */
		//서비스 실행한 리스트를 담을 객체
		List<PlanTravelFileJoinByDetail> planInfo = null;
		
		try {
			planInfo = planningService.listForPlanInfo(travelPlan);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
		/** 브라우저에 객체를 전달 */
		model.addAttribute("planInfo", planInfo);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("plan/plan_info/plan_info_map");
	}

	/** 여행지 상세 페이지 >> 댓글과 평점 */
	@RequestMapping(value = { "/plan/plan_info/comments.do" })
	public ModelAndView PlanInfoComment(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** WebHelper 초기화 */
		web.init();
		
		/** 파라미터 받기 */
		// 세션에서 로그인 정보를 가져온다.
		Member loginInfo = (Member)web.getSession("loginInfo");
		logger.debug("loginInfo="+loginInfo);
		
		// get파라미터 travelPlan 일련번호를 가져온다.
		int travelPlanId = web.getInt("travel_plan_id");
		logger.debug("travel_plan_id=" + travelPlanId);
		
		if (travelPlanId == 0) {
			return web.redirect(null, "여행코스 번호가 지정되지 않았습니다.");
		}
		
		
		/** 자바빈스에 세팅 */
		PlanTravelFileJoinByDetail travelplan = new PlanTravelFileJoinByDetail();
		travelplan.setPlanId(travelPlanId);
		
		TravelPlan plan = new TravelPlan();
		plan.setId(travelPlanId);
		
		/** 서비스 실행 */
		//서비스 실행한 리스트를 담을 객체 :comment.jsp 페이지에서 head page를 위해 쓴다.
		List<PlanTravelFileJoinByDetail> planInfo = null;
		try {
			planInfo = planningService.listForPlanInfo(travelplan);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		
		/** 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		TravelPlan item = null;
		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		//ex> travel_festival_15
		String cookieKey = "travelplan_" +  travelPlanId;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		// 해당 여행지에 대한 평가 개수를 담을 변수
		int commentCount = 0;
		// 평가 개수 조회를 위한 comment 객체
		Comment planComment = new Comment();
		planComment.setTravelPlanId(travelPlanId);
		// 특정 여행지에 대한 평점 평균을 담을 객체
		float ratingAvg = 0;
		
		/** 서비스 실행 */
		try {
			//쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				travelPlanService.updateTravelPlanHit(plan);
				// 준비한 문자열에 대한 쿠키를 60초동안 저장(24시간은:60*60*24)
				web.setCookie(cookieKey, "Y", 60*60*24);
			}
			commentCount = travelPlanCommentService.selectPlanCountByTravelPlanId(planComment);
			//comment가 0개면 ratingAvg도 0, 0개가 아니면 아래의 if연산을 수행(이렇게 하는 이유는? ratingAvg가 null일 경우, 0으로 받지 못하기 때문)
			if (commentCount != 0) {
			ratingAvg = travelPlanCommentService.selectRatingAvgByTravelPlanId(planComment);
			}
			item =travelPlanService.selectTravelPlan(plan);			
			planInfo = planningService.listForPlanInfo(travelplan);	
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("planInfo", planInfo);//리스트 객체 : PlanTravelFileJoinByDetail 형. 이 여행지와 관계된 많은 정보를 담음.
		model.addAttribute("ratingAvg", ratingAvg);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("item", item);
		model.addAttribute("loginInfo", loginInfo);

		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("plan/plan_info/plan_info_comments");
	}

	@RequestMapping(value = { "/plan/plan_info/comment_insert.do" })
	public void PlanCommentInsert(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** 2) WebHelper 초기화 및 파라미터 처리 */
		response.setContentType("application/json");
		web.init();

		// 파라미터 받기
		int travelPlanId = web.getInt("travel_plan_id");
		int memberId = 0;// 로그인 안하면 댓글 작성 X
		String content = web.getString("content");
		int rating = web.getInt("rating");
		// 작성자 아이피 주소 가져오기
		String ipAddress = web.getClientIP();
		String categoryComment = web.getString("category_comment");
		
		// 로그인 시의 세션 정보를 이용한다.
		Member loginInfo = (Member) web.getSession("loginInfo");

		// 로그인 안하면? 댓글 작성x
		if (loginInfo == null) {
			web.printJsonRt("로그인 해야 댓글작성 가능합니다.");
			return;
		}

		// 로그인을 하면? selectMember 서비스를 실행 >> 아이디 비번 이메일을 가져온다.
		String writerId = loginInfo.getUserId(); // 댓글 작성자 아이디
		String writerPw = loginInfo.getUserPw(); // 댓글 작성자 비번
		String email = loginInfo.getEmail(); // 댓글 작성자 메일
		memberId = loginInfo.getId(); // 댓글 작성자 일련번호

		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("writerId=" + writerId);
		logger.debug("writerPw=" + writerPw);
		logger.debug("email=" + email);
		logger.debug("content=" + content);
		logger.debug("rating=" + rating);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("categoryComment=" + categoryComment);
		logger.debug("memberId=" + memberId);
		logger.debug("travelPlanId=" + travelPlanId);

		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (travelPlanId == 0) {
			web.printJsonRt("여행코스 게시물 일련번호가 없습니다.");
		}
		// 평점 검사
		// if (rating == 0) {web.printJsonRt("평점을 입력하세요.");
		// 내용 검사
		if (!regex.isValue(content)) {
			web.printJsonRt("내용을 입력하세요.");
		}

		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		Comment planComment = new Comment();
		planComment.setWriterId(writerId);
		planComment.setWriterPw(writerPw);
		planComment.setEmail(email);
		planComment.setContent(content);
		planComment.setRating(rating);
		planComment.setIpAddress(ipAddress);
		planComment.setCategoryComment(categoryComment);
		planComment.setMemberId(memberId);
		planComment.setTravelPlanId(travelPlanId);
		logger.debug("planComment >>" + planComment.toString());

		
		//rating avg 추가를 위한 beans
		TravelPlan travelplan = new TravelPlan();
		travelplan.setTravelPlanId(travelPlanId);
		travelplan.setRating(rating);
		
		
		/** (6) Service를 통한 덧글 저장 */
		Comment item = null;
		int count = 0;// 작성자가 이미 평점을 매겼는가에 대한 결과 저장
		try {
			//이미 평가를 한 회원은 다시 평가하지 못한다.
			count = travelPlanCommentService.commentCountByMemberIdTravelPlanId(planComment);
			if (count != 0) {
				web.printJsonRt("이미 평가하신 여행플랜입니다.");
				return;
			}
			travelPlanCommentService.insertTravelPlanComment(planComment);
			item = travelPlanCommentService.selectTravelPlanCommentItem(planComment);
		
			// rating avg 추가를 위한 구문
			travelPlanService.updateTravelPlanRatingAvg(travelplan);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setWriterId(web.convertHtmlTag(item.getWriterId()));
		item.setEmail(web.convertHtmlTag(item.getEmail()));
		item.setContent(web.convertHtmlTag(item.getContent()));

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);

		// --> import com.fasterxml.jackson.databind.ObjectMapper;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/plan/plan_info/comment_list.do")
	public void PlanCommentList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");

		// 파라미터 받기
		int travelPlanId = web.getInt("travel_plan_id");
		logger.debug("travelPlanId=" + travelPlanId);
		// 덧글이 소속될 게시물의 일련번호
		if (travelPlanId == 0) {
			web.printJsonRt("여행코스 게시물 일련번호가 없습니다.");
			return;
		}

		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		Comment planComment = new Comment();
		planComment.setTravelPlanId(travelPlanId);

		/** (6) Service를 통한 덧글 목록 조회 */
		// 작성 결과를 저장할 객체
		List<Comment> item = null;
		try {
			item = travelPlanCommentService.selectTravelPlanCommentList(planComment);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}

		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		for (int i = 0; i < item.size(); i++) {
			Comment temp = item.get(i);
			temp.setWriterId(web.convertHtmlTag(temp.getWriterId()));
			temp.setEmail(web.convertHtmlTag(temp.getEmail()));
			temp.setContent(web.convertHtmlTag(temp.getContent()));
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value = "/plan/plan_info/plan_comment_delete.do")
	public ModelAndView PlanCommentDelete(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		web.init();

		/** (3) 덧글 번호 받기 */
		int commentId = web.getInt("comment_id");
		logger.debug("commentId="+commentId);
		int travelPlanId = web.getInt("travelPlanId");
		
		if (commentId == 0) {
			return web.redirect(null, "덧글 번호가 없습니다.");
		}

		// 파라미터를 Beans로 묶기
		Comment planComment = new Comment();
		planComment.setId(commentId);
		planComment.setTravelPlanId(travelPlanId);
		
		// 로그인 한 회원의 일런번호 추가
		Member loginInfo = (Member) web.getSession("loginInfo");

		if (loginInfo == null) {
			return web.redirect(null, "로그인 후 덧글 삭제 가능합니다.");
		}

		planComment.setMemberId(loginInfo.getId());
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int commentCount = 0;
		try {
			commentCount = travelPlanCommentService.selectPlanCommentCountByMemberId(planComment);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myComment = commentCount > 0;
		logger.debug("myComment=" + myComment);
		
		model.addAttribute("myComment", myComment);
		model.addAttribute("commentId", commentId);
		model.addAttribute("travelPlanId", travelPlanId);
		
		return new ModelAndView("plan/plan_info/plan_comment_delete");
	}
	
	@RequestMapping(value = "/plan/plan_info/plan_comment_delete_ok.do")
	public void PlanCommentDeleteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		response.setContentType("application/json");
		web.init();
		
		/** (3) 덧글 번호와 비밀번호 받기 */
		int commentId = web.getInt("comment_id");
		logger.debug("commentId=" + commentId);
		//rating_avg 업데이트를 위한 travelPlanId 가져오기
		int travelPlanId = web.getInt("travelPlanId");
		int rating = 0;
		
		logger.debug("commentId=" + commentId);
		logger.debug("travelPlanId>>>>>"+travelPlanId);
		
		if (commentId == 0) {
			web.printJsonRt("덧글 번호가 없습니다.");
			return;
		}

		// 파라미터를 Beans로 묶기
		Comment planComment = new Comment();
		planComment.setId(commentId);
		
		//rating avg beans 묶기
		TravelPlan travelplan = new TravelPlan();
		travelplan.setTravelPlanId(travelPlanId);
		travelplan.setRating(rating);
		
		/** (5) 데이터 삭제 처리 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			planComment.setMemberId(loginInfo.getId());
			planComment.setWriterPw(loginInfo.getUserPw());
		}
		
		try {
			// Beans에 추가된 자신의 회원번호를 사용하여 자신의 덧글임을 판별한다.
			// --> 자신의 덧글이 아니라면 비밀번호 검사
			if (travelPlanCommentService.selectPlanCommentCountByMemberId(planComment)<1) {
				travelPlanCommentService.selectPlanCommentCountByPw(planComment);
			}
			travelPlanCommentService.deleteTravelPlanComment(planComment);
			

			// rating avg 수정
			travelPlanService.updateTravelPlanRatingAvg(travelplan);
			
		}catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("commentId", commentId);
		data.put("travelPlanId", travelPlanId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	/** 여행지 댓글 수정하기 */
	@RequestMapping(value = "/plan/plan_info/plan_comment_edit.do")
	public ModelAndView PlanCommentEdit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();

		/** (3) 덧글 번호 받기 */
		int commentId = web.getInt("comment_id");
		logger.debug("commentId="+commentId);
		if (commentId == 0) {
			return web.redirect(null, "덧글 번호가 없습니다.");
		}

		// 파라미터를 Beans로 묶기
		Comment planComment = new Comment();
		planComment.setId(commentId);
		
		/** (4) 덧글 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 덧글이 저장될 객체
		Comment readPlan = null;
		
		try {
			readPlan = travelPlanCommentService.selectTravelPlanCommentItem(planComment);
		}catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("comment", readPlan);
		
		return new ModelAndView("plan/plan_info/plan_comment_edit");
	}
		
	/** 여행지 댓글 수정하기 */
	@RequestMapping(value = "/plan/plan_info/plan_comment_edit_ok.do")
	public void PlanCommentEditOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		response.setContentType("application/json");
		web.init();
		
		/** (3) 파라미터 받기 */
		int commentId = web.getInt("comment_id");
		String content = web.getString("content");
		int rating = web.getInt("rating");
		// 작성자 아이피 주소 가져오기
		String ipAddress = web.getClientIP();
		// 회원 일련번호 >> 로그인 안한사람은 불가
		int memberId = 0;
		//rating_avg 업데이트를 위한 travelPlanId 가져오기
		int travelPlanId = web.getInt("travelPlanId");
		
		/** (4) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체한다. */
		// 소유권 검사 정보
		boolean myComment = false;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			web.printJsonRt("로그인을 하셔야 수정가능합니다.");
		}
		
		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				Comment temp = new Comment();
				temp.setId(commentId);
				temp.setMemberId(loginInfo.getId());
				if(travelPlanCommentService.selectPlanCommentCountByMemberId(temp)>0) {
					myComment = true;
				}				
			}catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
			}
		}
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("comment_id=" + commentId);
		logger.debug("content=" + content);
		logger.debug("rating>>>>>=" + rating);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("memberId=" + memberId);
		logger.debug("travelPlanId>>>>>=" + travelPlanId);
		/** (5) 입력 받은 파라미터에 대한 유효성 검사 */
		if (commentId == 0) {web.printJsonRt("덧글 번호가 없습니다.");}
		//if (rating == 0) {web.printJsonRt("평점을 입력하세요.");}
		if (!regex.isValue(content)) {web.printJsonRt("내용을 입력하세요.");}
		
		/** (6) 입력 받은 파라미터를 Beans로 묶기 */
		Comment planComment = new Comment();
		planComment.setId(commentId);
		planComment.setRating(rating);
		planComment.setContent(content);
		planComment.setIpAddress(ipAddress);
		planComment.setMemberId(memberId);
		logger.debug(planComment.toString());
		
		//rating avg beans 묶기
		TravelPlan travelplan = new TravelPlan();
		travelplan.setTravelPlanId(travelPlanId);
		travelplan.setRating(rating);
		
		/** (7) 게시물 변경을 위한 Service 기능을 호출 */
		Comment item = null;
		try {
			// 자신의 글이 아니라면 비밀번호 검사를 먼저 수행한다.
			if(!myComment) {
				travelPlanCommentService.selectPlanCommentCountByPw(planComment);
			}
			travelPlanCommentService.updateTravelPlanComment(planComment);
			//변경된 결과를 조회
			item = travelPlanCommentService.selectTravelPlanCommentItem(planComment);
			
			// rating avg 수정
			travelPlanService.updateTravelPlanRatingAvg(travelplan);
			
		}catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		
		/** (8) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setWriterId(web.convertHtmlTag(item.getWriterId()));
		item.setEmail(web.convertHtmlTag(item.getEmail()));
		item.setContent(web.convertHtmlTag(item.getContent()));
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		data.put("travelPlanId", travelPlanId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/plan/plan_info/likes.do", method = RequestMethod.POST)
	public void PlanLikes(Locale locale, Model model, HttpServletResponse response) {
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		
		/** (3) 파라미터 받기 */
		int travelPlanId = web.getInt("travel_plan_id");
		logger.debug("travelPlanId="+travelPlanId);
		
		// memberId는 세션에서 가져온다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		System.out.println("로그인 세션 로드 =" + loginInfo);
		//memberId = loginInfo.getId();
		if (loginInfo == null) {
			web.printJsonRt("로그인 하셔야 합니다.");
			return;
		}
		
		/** (4) 파라미터를 빈즈에 세팅 */
		Likes planLikes = new Likes();
		if (loginInfo != null) {
			planLikes.setMemberId(loginInfo.getId());
		}	
		
		planLikes.setTravelPlanId(travelPlanId);
		logger.debug("memberId=" + planLikes.getMemberId());
		
		/** (5) Service 기능 호출 */
		int likesSearch; //여행지에 좋아요를 눌럿는지 확인하는 결과값
		int result;// 사용자의 좋아요 버튼 클릭 후의 상태를 담을 객체
		
		// likeSum 업데이트를 위한 자바빈즈
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setId(travelPlanId);
		try {
			// 특정 여행지에 사용자가 좋아요 했는지 검사
			likesSearch = likePlanService.selectCountByTravelPlanId(planLikes);
			// 없으면 등록
			if (likesSearch == 0) {
				likePlanService.insertTravelPlanLikes(planLikes);
				travelPlanService.plusLikeSum(travelPlan);// 좋아요가 추가되면 like_sum + 1
			}
			// 있으면 삭제
			if (likesSearch != 0) {
				likePlanService.deleteTravelPlanLikes(planLikes);
				travelPlanService.minusLikeSum(travelPlan);// 좋아요가 삭제되면 like_sum - 1
			}
			// 다시 결과 조회 (해당 여행지가 좋아요 표시 있으면 0, 없으면 1)
			result = likePlanService.selectCountByTravelPlanId(planLikes); 
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		/** (6) 처리결과 JSON으로 출력 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("result", result);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		
	}

}