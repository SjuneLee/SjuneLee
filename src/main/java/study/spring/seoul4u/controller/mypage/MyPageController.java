package study.spring.seoul4u.controller.mypage;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.model.DetailPlan;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.DetailPlanService;
import study.spring.seoul4u.service.LikePlanService;
import study.spring.seoul4u.service.LikesService;
import study.spring.seoul4u.service.TravelDocumentService;
import study.spring.seoul4u.service.TravelPlanCommentService;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class MyPageController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(MyPageController.class);
	@Autowired
	WebHelper web;
	
	@Autowired
	UploadHelper upload;
	
	@Autowired
	PageHelper pageHelper;
	
	@Autowired
	TravelDocumentService travelDocumentService;
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	LikePlanService likePlanService;
	
	@Autowired
	TravelPlanService travelPlanService;
	
	@Autowired
	DetailPlanService detailPlanService;
	
	@Autowired
	TravelPlanCommentService travelPlanCommentService;

	/** 마이페이지 클립보드 */
	@RequestMapping(value = {"/mypage/mypage.do"})

	public ModelAndView ClipboardRead(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {
				
		web.init();
		
		//** (3) 로그인 여부 검사 *//*
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		// 페이지 형식을 JSON으로 설정한다.
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		
		// category를 web에서 가져온다.
		String category = web.getString("category");
		model.addAttribute("category", category);
		
		
		// travel Id를 web에서 가져온다.
		int travelId = web.getInt("travel_id");
		logger.debug("travelId=" + travelId);
		
		String subject = web.getString("subject");
		logger.debug("subject=" + subject);
		
		// memberId는 세션에서 가져온다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(null, "로그인 하셔야 합니다.");
		}
			
		
		/** (5) 조회할 정보에 대한 Beans 생성 */
		
		Likes likes = new Likes();
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page",1);
		if (loginInfo != null) {
			likes.setMemberId(loginInfo.getId());
			logger.debug("memberId=" + likes.getMemberId());
		}
		
		likes.setTravelId(travelId);
		likes.setCategory(category);
		likes.setCategory(category);
		
		/** (6) 게시글 목록 조회 */
		int totalCount = 0;
	
		try {
			//전체 게시물 수
			totalCount = likesService.selectClipCount(likes);
			//나머지 페이지 번호 계산하기
			//현재 페이지,전체 게시물 수,한 페이지의 목록 수, 그룹 갯수
			pageHelper.pageProcess(page, totalCount, 12, 5);
	
			//페이지 번호 계산 결과에서 Limit 절에 필요한 값을 Beans에 추가
			likes.setLimitStart(pageHelper.getLimitStart());
			likes.setListCount(pageHelper.getListCount());
			
			/*list =  likesService.selectClipList(likes);*/
		} catch (Exception e) {
			//예외 발생시, service layer에서 전달하는 메시지 내용을 사용자에게 제시하고 이전페이지로 이동한다.
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}		
	
		/** (3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Likes> list = null;
		try {
			list =  likesService.selectClipList(likes);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if( list != null) {
			for (int i=0; i< list.size(); i++ ) {
				Likes item = list.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		//사용자가 입력한 검색어를 view에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("list", list);
		model.addAttribute("pageHelper", pageHelper);

		return new ModelAndView("mypage/mypage");
	}
	
	/** 클립보드 삭제 */
	@RequestMapping(value="/mypage/clip_delete.do")
	public ModelAndView ClipBoardDeleteOk(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}

		/** (5) 게시글 번호 받기 */
		int id= web.getInt("id");
		
		logger.debug("Id" + id);
		
		if (id == 0) {
			//sqlSession.close();
			return web.redirect(null, "클립보드가 없습니다.");
			//return null;
		}
		
		/** (6) 파라미터를 beans로 묶기 */
		Likes likes = new Likes();
		likes.setId(id);
		
		/** (7) 데이터 삭제 처리 */
		try {
		likesService.deleteClip(likes);
		
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			//return null; 
		} 
		
		/** (9) 페이지 이동*/
		String url = "%s/mypage/mypage.do";
		url = String.format(url, web.getRootPath());
		
		return web.redirect(url, "삭제되었습니다.");
		//return null;
	}
	
	/** 계획중인 일정 삭제 */
	@RequestMapping(value="/mypage/ing_plan_delete.do")
	public ModelAndView ingPlanDelete(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		int id= web.getInt("id");
		logger.debug("Id" + id);
		if (id == 0) {
			return web.redirect(null, "계획중인 일정이 없습니다.");
		}
		
		Likes likes = new Likes();
		likes.setTravelPlanId(id);
		
		//게시물에 속한 덧글 삭제를 위해 생성
		Comment comment = new Comment();
		comment.setTravelPlanId(id);
		
		List<Likes> likeList = null;
		
		try {
			//좋아요 삭제
			likeList = likePlanService.selectLikePlanList(likes);
			likePlanService.deleteLikePlanAll(likes);
			//덧글삭제
			travelPlanCommentService.deleteTravelPlanCommentAll(comment);			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		} 
		
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setMemberId(loginInfo.getId());
		detailPlan.setTravelPlanId(id);
		try {
			detailPlanService.deleteDetailPlan(detailPlan);
		} catch(Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setId(id);		
		try {
			travelPlanService.deleteTravelPlan(travelPlan);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (9) 페이지 이동*/
		String url = "%s/mypage/ing_plan.do";
		url = String.format(url, web.getRootPath());
		
		return web.redirect(url, "삭제되었습니다.");
		//return null;
	}
	
	/** 완성된 일정 삭제 */
	@RequestMapping(value="/mypage/end_plan_delete.do")
	public ModelAndView EndPlanDelete(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		} 
		
		int id= web.getInt("id");
		logger.debug("Id" + id);
		if (id == 0) {
			return web.redirect(null, "완성된 일정이 없습니다.");
		}
		
		Likes likes = new Likes();
		likes.setTravelPlanId(id);
		
		//게시물에 속한 덧글 삭제를 위해 생성
		Comment comment = new Comment();
		comment.setTravelPlanId(id);
		
		List<Likes> likeList = null;
		
		try {
			//좋아요 삭제
			likeList = likePlanService.selectLikePlanList(likes);
			likePlanService.deleteLikePlanAll(likes);
			//덧글삭제
			travelPlanCommentService.deleteTravelPlanCommentAll(comment);			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		} 
		
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setMemberId(loginInfo.getId());
		detailPlan.setTravelPlanId(id);
		try {
			detailPlanService.deleteDetailPlan(detailPlan);
		} catch(Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setId(id);		
		try {
			travelPlanService.deleteTravelPlan(travelPlan);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (9) 페이지 이동*/
		String url = "%s/mypage/end_plan.do";
		url = String.format(url, web.getRootPath());
		
		return web.redirect(url, "삭제되었습니다.");
		//return null;
	}

	
	/** 완성된 일정 */
	@RequestMapping(value = {"/mypage/end_plan.do"})
	public ModelAndView MyPageEndPlan(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		/** 로그인 검사 */
		Member loginInfo = (Member) web.getSession("loginInfo");
				
		if (loginInfo == null) {
			return web.redirect(null, "로그인하셔야 완성된 일정을 볼수 있습니다.");
		}
		
		/** 서비스 실행(selectTravelList) */
		TravelPlan travelPlan = new TravelPlan();// 로그인 정보를 담을 객체
		DetailPlan detailPlan = new DetailPlan();
		
		travelPlan.setMemberId(loginInfo.getId());
		detailPlan.setMemberId(loginInfo.getId());
		
		DetailPlan detailPlan2 = new DetailPlan();
		detailPlan2.setMemberId(loginInfo.getId());		
		
		logger.debug("travelPlan을 만든 회원 일련번호=" + travelPlan.getMemberId());
		
		List<TravelPlan> travelPlanList = null;// travelPlan list들을 담을 객체
		List<DetailPlan> detailPlanList = null; //상세여행지 리스트를 담음
		List<DetailPlan> detailPlanList2 = null; //상세여행지 리스트를 담음
		try {
			travelPlanList = travelPlanService.selectTravelPlanList(travelPlan);
			detailPlanList = detailPlanService.selectlDetailPlanList(detailPlan);
			detailPlanList2 = detailPlanService.selectDetailIngPlanList(detailPlan2);
			  
		} catch(Exception e) {
			e.getLocalizedMessage();
		}
		
		if( detailPlanList2 != null) {
			for (int i=0; i< detailPlanList2.size(); i++ ) {
				DetailPlan item = detailPlanList2.get(i);
				String imagePath = item.getImagePath();
				System.out.println("상세일정2 이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		
		
		logger.debug(travelPlan.getMemberId()+"번 회원의 계획중인 일정은="+ travelPlanList);
		logger.debug("상세일정리스트 = " + detailPlanList);
		logger.debug("완성된 일정 상세일정 리스트2 = " + detailPlanList2);
		
		/** 결과 전송 */
		model.addAttribute("travelPlanList", travelPlanList);
		model.addAttribute("detailPlanList", detailPlanList);
		model.addAttribute("detailPlanList2", detailPlanList2);
		
		return new ModelAndView("/mypage/end_plan");
	}
	
	/** 계획중인 일정 */
	@RequestMapping(value = {"/mypage/ing_plan.do"})
	public ModelAndView MyPageIngPlan(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		/** 로그인 검사 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(null, "로그인하셔야 계획중인 일정을 볼수 있습니다.");
		}
		
		/** 서비스 실행(selectTravelList) */
		TravelPlan travelPlan = new TravelPlan();// 로그인 정보를 담을 객체
		DetailPlan detailPlan = new DetailPlan();
		
		travelPlan.setMemberId(loginInfo.getId());
		detailPlan.setMemberId(loginInfo.getId());
		
		DetailPlan detailPlan2 = new DetailPlan();
		detailPlan2.setMemberId(loginInfo.getId());
		
		logger.debug("travelPlan을 만든 회원 일련번호=" + travelPlan.getMemberId());
		
		List<TravelPlan> travelPlanList = null;// travelPlan list들을 담을 객체
		List<DetailPlan> detailPlanList = null; //상세여행지 리스트를 담음
		List<DetailPlan> detailPlanList2 = null; //상세여행지 리스트를 담음
		try {
			travelPlanList = travelPlanService.selectTravelPlanList(travelPlan);
			detailPlanList = detailPlanService.selectlDetailPlanList(detailPlan);
			detailPlanList2 = detailPlanService.selectDetailIngPlanList(detailPlan2);
		} catch(Exception e) {
			e.getLocalizedMessage();
		}
		
		if( travelPlanList != null) {
			for (int i=0; i< travelPlanList.size(); i++ ) {
				TravelPlan item = travelPlanList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		System.out.println("상세일정2 리스트 이미지 = " + detailPlanList2);
		if( detailPlanList2 != null) {
			for (int i=0; i< detailPlanList2.size(); i++ ) {
				DetailPlan item = detailPlanList2.get(i);
				String imagePath = item.getImagePath();
				System.out.println("상세일정2 이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		
		System.out.println("상세일정2 리스트 이미지 = " + detailPlanList2);
		
		logger.debug(travelPlan.getMemberId()+"번 회원의 계획중인 일정은="+ travelPlanList);
		logger.debug("계획중인 일정 상세일정 리스트 = " + detailPlanList);
		logger.debug("계획중인 일정 상세일정 리스트2 = " + detailPlanList2);
		/** 결과 전송 */
		model.addAttribute("travelPlanList", travelPlanList);
		model.addAttribute("detailPlanList", detailPlanList);
		model.addAttribute("detailPlanList2", detailPlanList2);
		   
		return new ModelAndView("/mypage/ing_plan");
	}
	
	/** 좋아한 일정 */
	@RequestMapping(value = {"/mypage/like_plan.do"})
	public ModelAndView MyPageLikePlan(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		/** 로그인 검사 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(null, "로그인하셔야 좋아한인 일정을 볼수 있습니다.");
		}				
		
		/** (5) 조회할 정보에 대한 Beans 생성 */		
		Likes likes = new Likes();
		likes.setMemberId(loginInfo.getId());
		
		/** (3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Likes> likePlanList = null;
		try {
			likePlanList = likePlanService.selectLikePlanList(likes);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if( likePlanList != null) {
			for (int i=0; i< likePlanList.size(); i++ ) {
				Likes item = likePlanList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		
		//사용자가 입력한 검색어를 view에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("likePlanList", likePlanList);
		return new ModelAndView("/mypage/like_plan");
	}
	
	/** 좋아한 일정 삭제 */
	@RequestMapping(value = {"/mypage/like_plan_delete.do"})
	public ModelAndView MyPageLikePlanDelete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		int id = web.getInt("id");
		logger.debug("id="+id);
		
		/** (6) 파라미터를 beans로 묶기 */
		Likes likes = new Likes();
		likes.setId(id);
		
		/** (7) 데이터 삭제 처리 */
		try {
			likePlanService.deleteLikePlan(likes);
		
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			//return null; 
		} 
		
		/** (9) 페이지 이동*/
		String url = "%s/mypage/like_plan.do";
		url = String.format(url, web.getRootPath());
		
		return web.redirect(url, "삭제되었습니다.");
	}
	
	/** 개인정보 수정 */
	@RequestMapping(value = {"/mypage/member_edit.do"})
	public ModelAndView MyPageMemberEdit(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		return new ModelAndView("/mypage/member_edit");
	}
	
	/** 개인정보 수정 */
	@RequestMapping(value = {"/mypage/member_edit_ok.do"})
	public ModelAndView MyPageMemberEditOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		System.out.println("수정할 회원정보 = " + loginInfo);
		
		return web.redirect(web.getRootPath() + "/mypage/mypage.do", "회원수정이 완료되었습니다.");

	}
	
	/** 회원탈퇴 */
	@RequestMapping(value = {"/mypage/member_out.do"})
	public ModelAndView MyPageMemberOut(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		if (web.getSession("loginInfo") == null) {	
			return web.redirect(web.getRootPath() + "/index.do", "로그인 하셔야 합니다.");
		}
		
		return new ModelAndView("/mypage/member_out");
	}
	
//	/** 회원탈퇴 확인 */
//	@RequestMapping(value = {"/mypage/member_out_ok.do"})
//	public ModelAndView MyPageMemberOutOk(Locale locale, Model model,
//			HttpServletRequest request, HttpServletResponse response) {
//		/** (2) webHelper 초기화 */
//		web.init();
//		
//		return new ModelAndView("/mypage/member_out");
//	}
	
	
}