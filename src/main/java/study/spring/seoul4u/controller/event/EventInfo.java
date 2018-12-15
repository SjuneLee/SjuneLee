package study.spring.seoul4u.controller.event;



import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelFile;
import study.spring.seoul4u.service.LikesService;
import study.spring.seoul4u.service.MemberService;
import study.spring.seoul4u.service.TravelCommentService;
import study.spring.seoul4u.service.TravelDocumentService;
import study.spring.seoul4u.service.TravelFileService;

@Controller
public class EventInfo {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(EventInfo.class);
	@Autowired
	WebHelper web;
	@Autowired
	RegexHelper regex;
	@Autowired
	BBSCommon bbs;
	@Autowired
	MemberService memberService;
	@Autowired
	TravelDocumentService travelService;
	@Autowired
	TravelFileService travelFileService;
	@Autowired
	TravelCommentService travelCommentService;
	@Autowired
	LikesService likesService;
	
	/** 여행지 상세 페이지 */
	@RequestMapping(value = {"/content/travel_read.do"})
	public ModelAndView TravelRead(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		
		Member loginInfo = (Member)web.getSession("loginInfo");
		//System.out.println("세션테스트 = " + loginInfo);
		
		/** (3) 게시판 카테고리 값을 받아서 View에 전달(get파라미터 url) */
		String category = web.getString("category");
		model.addAttribute("category", category);
		
		/** (4) 존재하는 게시판인지 판별하기 */
		try {
			String bbsName = bbs.getBbsName(category);
			model.addAttribute("bbsName", bbsName);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 글 번호 파라미터 받기 */
		int travelId = web.getInt("travel_id");
		logger.debug("travelId=" + travelId);
		
		if (travelId == 0) {
			return web.redirect(null, "여행지 번호가 지정되지 않았습니다.");
		}
		
		// 파라미터를 Beans로 묶기
		TravelDocument travel = new TravelDocument();
		travel.setId(travelId);
		travel.setCategory(category);
		
		TravelFile file = new TravelFile();
		file.setTravelId(travelId);
		
		/** (6) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		TravelDocument readTravel = null;
		// 첨부파일 정보가 저장될 객체
		List<TravelFile> fileList = null;
		
		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		//ex> travel_festival_15
		String cookieKey = "travel_" + category + "_" + travelId;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		// 해당 여행지에 대한 평가 개수를 담을 변수
		int commentCount = 0;
		// 평가 개수 조회를 위한 comment 객체
		Comment travelComment = new Comment();
		travelComment.setTravelId(travelId);
		// 특정 여행지에 대한 평점 평균을 담을 객체
		float ratingAvg = 0;
		
		try {
			//쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				travelService.updateTravelHit(travel);
				// 준비한 문자열에 대한 쿠키를 60초동안 저장(24시간은:60*60*24)
				web.setCookie(cookieKey, "Y", 60);
			}
			commentCount = travelCommentService.selectCommentCountByTravelId(travelComment);
			//comment가 0개면 ratingAvg도 0, 0개가 아니면 아래의 if연산을 수행(이렇게 하는 이유는? ratingAvg가 null일 경우, 0으로 받지 못하기 때문)
			if (commentCount != 0) {
			ratingAvg = travelCommentService.selectRatingAvgByTravelId(travelComment);
			}
			readTravel = travelService.selectTravel(travel);
			fileList = travelFileService.selectFileList(file);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (7) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("ratingAvg", ratingAvg);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("readTravel", readTravel);
		model.addAttribute("fileList", fileList);
		
		model.addAttribute("loginInfo", loginInfo);
		
		System.out.println("파일리스트 = " + fileList);
		
		// "/WEB-INF/views/content/travel_read.jsp"파일을 View로 사용한다.
		return new ModelAndView("content/travel_read");
	}
	
	/** 여행지 댓글 달기 */
	@ResponseBody
	@RequestMapping(value = "/content/travel_read/comment_insert.do")
	public void TravelCommentInsert(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		int travelId = web.getInt("travel_id");
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
		String email = loginInfo.getEmail();	 // 댓글 작성자 메일
		memberId = loginInfo.getId();			 // 댓글 작성자 일련번호
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("writerId=" + writerId);
		logger.debug("writerPw=" + writerPw);
		logger.debug("email=" + email);
		logger.debug("content=" + content);
		logger.debug("rating=" + rating);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("categoryComment=" + categoryComment);
		logger.debug("memberId=" + memberId);
		logger.debug("travelId=" + travelId);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (travelId == 0) {web.printJsonRt("여행지 게시물 일련번호가 없습니다.");}
		// 평점 검사
		//if (rating == 0) {web.printJsonRt("평점을 입력하세요.");
		// 내용 검사
		if (!regex.isValue(content)) {web.printJsonRt("내용을 입력하세요.");}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		Comment travelComment = new Comment();
		travelComment.setWriterId(writerId);
		travelComment.setWriterPw(writerPw);
		travelComment.setEmail(email);
		travelComment.setContent(content);
		travelComment.setRating(rating);
		travelComment.setIpAddress(ipAddress);
		travelComment.setCategoryComment(categoryComment);
		travelComment.setMemberId(memberId);
		travelComment.setTravelId(travelId);
		logger.debug("travelComment >>" + travelComment.toString());

		//rating avg 추가를 위한 beans
		TravelDocument travel = new TravelDocument();
		travel.setTravelId(travelId);
		travel.setRating(rating);
		
		/** (6) Service를 통한 덧글 저장 */
		// 작성 결과를 저장할 객체
		Comment item = null;
		int count = 0;// 작성자가 이미 평점을 매겼는가에 대한 결과 저장
		try {
			//이미 평가를 한 회원은 다시 평가하지 못한다.
			count = travelCommentService.commentCountByMemberIdTravelId(travelComment);
			if (count != 0) {
				web.printJsonRt("이미 평가하신 여행지입니다.");
				return;
			}
			travelCommentService.insertTravelComment(travelComment);
			item = travelCommentService.selectTravelComment(travelComment);
			
			// rating avg 추가를 위한 구문
			travelService.selectTravelRatingAvg(travel);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		logger.debug("item >>" + item.toString());
		
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setWriterId(web.convertHtmlTag(item.getWriterId()));
		item.setEmail(web.convertHtmlTag(item.getEmail()));
		item.setContent(web.convertHtmlTag(item.getContent()));
		
		Map<Object, Object> data = new HashMap<Object,Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	/** 여행지 댓글 리스트 조회 */
	@ResponseBody
	@RequestMapping(value = "/content/travel_read/comment_list.do")
	public void TravelCommentList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		int travelId = web.getInt("travel_id");
		logger.debug("travel_id=" + travelId);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 덧글이 소속될 게시물의 일련번호
		if (travelId == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다.");
			return;
		}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		Comment travelComment = new Comment();
		travelComment.setTravelId(travelId);

		
		/** (6) Service를 통한 덧글 목록 조회 */
		// 작성 결과를 저장할 객체
		List<Comment> item = null;
		try {
			item = travelCommentService.selectTravelCommentList(travelComment);
			
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		for (int i=0; i<item.size(); i++) {
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
	
	/** 여행지 댓글 삭제하기 */
	@RequestMapping(value = "/content/travel_read/comment_delete.do")
	public ModelAndView TravelCommentDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		
		/** (3) 덧글 번호 받기 */
		int commentId = web.getInt("comment_id");
		if (commentId == 0) {
			return web.redirect(null, "덧글 번호가 없습니다.");
		}

		int travelId = web.getInt("travel_id");
		
		// 파라미터를 Beans로 묶기
		Comment travelComment = new Comment();
		travelComment.setId(commentId);
		travelComment.setTravelId(travelId);
		
		
		// 로그인 한 회원의 일런번호 추가
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(null, "로그인 후 덧글 삭제 가능합니다.");
		}
		
		travelComment.setMemberId(loginInfo.getId());
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int commentCount = 0;
		try {
			commentCount = travelCommentService.selectTravelCommentCountByMemberId(travelComment);
		
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myComment = commentCount > 0;
		logger.debug("myComment=" + myComment);
		
		model.addAttribute("myComment", myComment);
		model.addAttribute("commentId", commentId);
		model.addAttribute("travelId", travelId);
		
		return new ModelAndView("content/comment_delete");
	}
	
	/** 여행지 댓글 삭제완료 */
	@RequestMapping(value = "/content/travel_read/comment_delete_ok.do")
	public void TravelCommentDeleteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		response.setContentType("application/json");
		web.init();
		
		/** (3) 덧글 번호와 비밀번호 받기 */
		
		int commentId = web.getInt("comment_id");
		String writerPw = web.getString("writer_pw");
		// rating_avg 삭제 업데이트
		int travelId = web.getInt("travel_id");
		int rating = 0;
		logger.debug("travelId>>>>>>>" + travelId);
		
		logger.debug("commentId=" + commentId);
		logger.debug("writerPw=" + writerPw);
		
		if (commentId == 0) {
			web.printJsonRt("덧글 번호가 없습니다.");
		}
		

		
		/** (4) 파라미터를 Beans로 묶기 */
		Comment travelComment = new Comment();
		travelComment.setId(commentId);
		travelComment.setWriterPw(writerPw);
	
		//rating avg beans 저장
		TravelDocument travel = new TravelDocument();
		travel.setTravelId(travelId);
		travel.setRating(rating);
		
		/** (5) 데이터 삭제 처리 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			travelComment.setMemberId(loginInfo.getId());
			travelComment.setWriterPw(loginInfo.getUserPw());
		}
		
		try {
			// Beans에 추가된 자신의 회원번호를 사용하여 자신의 덧글임을 판별한다.
			// --> 자신의 덧글이 아니라면 비밀번호 검사
			if (travelCommentService.selectTravelCommentCountByMemberId(travelComment) < 1) {
				travelCommentService.selectTravelCommentCountByPw(travelComment);
			}
			travelCommentService.deleteTravelComment(travelComment);
		
			// rating avg 삭제시 수정
			travelService.selectTravelRatingAvg(travel);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
		
		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("commentId", commentId);
		data.put("travelId", travelId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	/** 여행지 댓글 수정하기 */
	@RequestMapping(value = "/content/travel_read/comment_edit.do")
	public ModelAndView TravelCommentEdit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int commentId = web.getInt("comment_id");
		if (commentId == 0) {
			return web.redirect(null, "덧글 번호가 지정되지 않았습니다.");
		}
		
		// 파라미터를 Beans로 묶기
		Comment travelComment = new Comment();
		travelComment.setId(commentId);
		
		/** (4) 덧글 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 덧글이 저장될 객체
		Comment readComment = null;
		
		try {
			readComment = travelCommentService.selectTravelComment(travelComment);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("comment", readComment);
		
		return new ModelAndView("content/comment_edit");
	}
	
	/** 여행지 댓글 수정완료 */
	@ResponseBody
	@RequestMapping(value = "/content/travel_read/comment_edit_ok.do")
	public void TravelCommentEditOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
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
		int travelId = web.getInt("travel_id");
		
		/** (4) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체한다. */
		// 소유권 검사 정보
		boolean myComment = false;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			web.printJsonRt("로그인을 하셔야 수정가능합니다. ^^");
			return;
		}
		
		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				Comment temp = new Comment();
				temp.setId(commentId);
				temp.setMemberId(loginInfo.getId());
				
				if (travelCommentService.selectTravelCommentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myComment = true;
					
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
			}
		}
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("comment_id=" + commentId);
		logger.debug("content=" + content);
		logger.debug("rating=" + rating);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("memberId=" + memberId);
		logger.debug("travelId=" + travelId);
		
		
		/** (5) 입력 받은 파라미터에 대한 유효성 검사 */
		if (commentId == 0) {web.printJsonRt("덧글 번호가 없습니다.");}
		//if (rating == 0) {web.printJsonRt("평점을 입력하세요.");}
		if (!regex.isValue(content)) {web.printJsonRt("내용을 입력하세요.");}
		
		/** (6) 입력 받은 파라미터를 Beans로 묶기 */
		Comment travelComment = new Comment();
		// UPDATE문의 WHERE절에서 사용해야 하므로 덧글 번호 추가
		travelComment.setId(commentId); // update WHERE절에서 사용
		travelComment.setRating(rating); // update 되는 값
		travelComment.setContent(content); // update 되는 값
		travelComment.setIpAddress(ipAddress); //update 되는 값
		travelComment.setMemberId(memberId);
		logger.debug(travelComment.toString());	
		
		//rating avg beans 묶기
		TravelDocument travel = new TravelDocument();
		travel.setTravelId(travelId);
		travel.setRating(rating);
		
		/** (7) 게시물 변경을 위한 Service 기능을 호출 */
		Comment item = null;
		try {
			// 자신의 글이 아니라면 비밀번호 검사를 먼저 수행한다.
			if (!myComment) {
				travelCommentService.selectTravelCommentCountByPw(travelComment);
			}
			travelCommentService.updateTravelComment(travelComment);
			// 변경된 결과를 조회
			item = travelCommentService.selectTravelComment(travelComment);

			// rating avg 수정
			travelService.selectTravelRatingAvg(travel);
			
		} catch (Exception e) {
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
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
		}
	}
	
	/** 좋아요 기능 */
	@ResponseBody
	@RequestMapping(value = "/content/travel_read/likes.do", method = RequestMethod.POST)
	public void TravelLikes(Locale locale, Model model, HttpServletResponse response) {
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");
		
		/** (2) webHelper 초기화, 필요한 객체 생성 */
		web.init();
				
		/** (3) 파라미터 받기 */
		int travelId = web.getInt("travel_id");
		//int memberId = 0;
		// 파라미터 로그 확인
		logger.debug("travelId=" + travelId);
		
		// memberId는 세션에서 가져온다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		System.out.println("로그인 세션 로드 =" + loginInfo);
		//memberId = loginInfo.getId();
		if (loginInfo == null) {
			web.printJsonRt("로그인 하셔야 합니다.");
			return;
		}
		
		/** (4) 파라미터를 빈즈에 세팅 */
		Likes travelLikes = new Likes();
		if (loginInfo != null) {
			travelLikes.setMemberId(loginInfo.getId());
		}
		
		
		
		travelLikes.setTravelId(travelId);


		logger.debug("memberId=" + travelLikes.getMemberId());
		/** (5) Service 기능 호출 */
		int likesSearch; //여행지에 좋아요를 눌럿는지 확인하는 결과값
		int result;// 사용자의 좋아요 버튼 클릭 후의 상태를 담을 객체
		
		// likeSum 업데이트를 위한 자바빈즈
		TravelDocument travel = new TravelDocument();
		travel.setId(travelId);
		try {
			// 특정 여행지에 사용자가 좋아요 했는지 검사
			likesSearch = likesService.selectCountByTravelId(travelLikes);
			
			// 없으면 등록
			if (likesSearch == 0) {
				likesService.insertTravelLikes(travelLikes);
				travelService.plusLikeSum(travel);// 좋아요가 추가되면 like_sum + 1
			}
			// 있으면 삭제
			if (likesSearch != 0) {
				likesService.deleteTravelLikes(travelLikes);
				travelService.minusLikeSum(travel);// 좋아요가 삭제되면 like_sum - 1
			}
			
			// 다시 결과 조회 (해당 여행지가 좋아요 표시 있으면 0, 없으면 1)
			result = likesService.selectCountByTravelId(travelLikes);
		} catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
			//return web.redirect(null, msg)
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