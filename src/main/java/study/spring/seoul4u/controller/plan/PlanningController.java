package study.spring.seoul4u.controller.plan;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.DetailPlan;
import study.spring.seoul4u.model.FileTravelComment;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.DetailPlanService;
import study.spring.seoul4u.service.PlanningService;
import study.spring.seoul4u.service.TravelDocumentService;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class PlanningController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(PlanningController.class);
	@Autowired
	WebHelper web;
	@Autowired
	UploadHelper upload;
	@Autowired
	TravelDocumentService travelService;
	@Autowired
	PlanningService planningService;
	@Autowired
	TravelPlanService travelPlanService;
	@Autowired
	DetailPlanService detailPlanService;
	
	/** planning1 */
	@RequestMapping(value = {"/content/planning1.do"})
	public ModelAndView Planning1(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		/** (3) 로그인하지 않은 사람은 진입금지 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/content/tripMenu_plan.do", "로그인 하셔야 일정을 만들 수 있습니다.");
		}
		 
		// "/WEB-INF/views/planning1.jsp"파일을 View로 사용한다.
		return new ModelAndView("plan/planning/planning1");
	}    
	
	/** travel_plan을 저장한다. */
	@RequestMapping(value = {"/content/insertTravelPlan.do"})
	public ModelAndView InsertTravelPlan(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) webHelper 초기화 */
		web.init();
		
		/** (3)GET 파라미터 수신. 값이 없을 경우 기본값 "여행제목이 없습니다." */  
		String subject = web.getString("plan_name", "여행제목이 없습니다.");
		String plan_date = web.getString("plan_date", "");
		String planPeriod = web.getString("plan_period", "");
		//여행코스 시작일
		String start_date = web.getString("start_date");
		//여행코스 마감일
		String last_date = web.getString("last_date");
		
		System.out.println("시작일  = " + start_date);
		System.out.println("마감일  = " + last_date);
		
		int period = Integer.parseInt(planPeriod);
		period = period + 1;// get 파라미터 string타입으로 온 plan_period를 int로 파싱 >> +1
		
		if(period > 15) {
			return web.redirect(web.getRootPath() + "/content/planning1.do", "15일 초과!!");
		}
		
		/** (4) 빈즈에 세팅 */
		TravelPlan travelPlan = new TravelPlan();
		Member loginInfo = (Member) web.getSession("loginInfo");
		String ipAddress = web.getClientIP();
		
		travelPlan.setStartDate(start_date);
		travelPlan.setTerm(period);
		travelPlan.setSubject(subject);
		travelPlan.setSeason(0);// 계획중인 플랜은 계절, 테마가 0값이다.
		travelPlan.setTheme(0);// 계획중인 플랜은 계절, 테마가 0값이다.
		travelPlan.setIpAddress(ipAddress);
		travelPlan.setMemberId(loginInfo.getId());
		
		/** (5) Service 실행(travelPlan 저장) */
		try {
			travelPlanService.insertTravelPlan(travelPlan);
			//travelPlanService.selectTravelPlan(travelPlan);
		}catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		return web.redirect(web.getRootPath() + "/mypage/ing_plan.do" , "새로운 여행일정을 만들었습니다. 마이페이지에서 상세일정을 만듭니다.");
		//return new ModelAndView("plan/planning/planning2");
	}
	
	/** planning2 */
	@RequestMapping(value = {"/content/planning2.do"})
	public ModelAndView Planning2(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2)WebHelper 객체 생성 */
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/content/tripMenu_plan.do", "로그인 하셔야 일정을 만들 수 있습니다.");
		}
		  
		//int detail_plan_id = web.getInt("detail_plan_id");
		//logger.debug("여행컨트롤러  상세일정 detail_plan 아디디  = " + detail_plan_id);
		 
		/** (3)GET 파라미터 수신 */
		int travelPlanId = web.getInt("travel_plan_id");
		System.out.println("여행컨트롤러 여행일정 travelPlan 아이디=" + travelPlanId);
		if (travelPlanId == 0) {
			return web.redirect(null, "여행 일정 일련번호가 미지정되었습니다.");
		}
		  
		/** 서비스 실행(travelPlanId에 맞는 travel_plan 행 가져오기 */
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setId(travelPlanId);
		TravelPlan result = new TravelPlan();
		try {
			result = travelPlanService.selectTravelPlan(travelPlan);
		} catch(Exception e) {
			e.getLocalizedMessage();
		}
		
		/** 브라우져에 뿌리기 위한 준비 */
		String startDate = result.getStartDate();
		int term = result.getTerm();
		term = term - 1;
		String subject = result.getSubject();
		
		String yy = startDate.substring(0,4);
		String mm = startDate.substring(5,7);
		String dd = startDate.substring(8,10);
		
		int YY = Integer.parseInt(yy);
		int MM = Integer.parseInt(mm);
		int DD = Integer.parseInt(dd);// Date 생성자에 넣기 위한 파싱.

		// 날짜값 전달 
		model.addAttribute("year",YY);
		model.addAttribute("month",MM);
		model.addAttribute("day",DD);
		model.addAttribute("plan_name",subject);
		model.addAttribute("plan_date",startDate);
		model.addAttribute("plan_period",term);
		model.addAttribute("travel_plan_id",travelPlanId);
		 
		/** 여행지 리스트 서비스 실행 준비 */
		//selectTravelList 서비스를 실행하기 위한 객체 생성
		FileTravelComment festival = new FileTravelComment();
		FileTravelComment show = new FileTravelComment();
		FileTravelComment food = new FileTravelComment();
		//각각 카테고리 값을 담는다
		festival.setTravelCategory("festival");
		show.setTravelCategory("show");
		food.setTravelCategory("food");
		 
		/** 여행지 리스트 서비스 실행 */
		//리스트 결과를 담을 객체 생성
		List<FileTravelComment> festivalList = null;
		List<FileTravelComment> showList = null;
		List<FileTravelComment> foodList = null;
		   
		List<DetailPlan> detailPlanList2 = null; //상세여행지 리스트를 담음
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setMemberId(loginInfo.getId());
		detailPlan.setTravelPlanId(travelPlanId); 
		try { 
			festivalList = planningService.listForPlanning(festival);
			showList = planningService.listForPlanning(show);
			foodList = planningService.listForPlanning(food);
			
			detailPlanList2 = detailPlanService.selectlDetailPlanList(detailPlan);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		List<TravelPlan> travelPlanList = null;
		try {
			travelPlanList = travelPlanService.selectTravelPlanList(null);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		System.out.println("축제리스트 = " + festivalList);
		
		if( festivalList != null) {
			for (int i=0; i< festivalList.size(); i++ ) {
				FileTravelComment item = festivalList.get(i);
				String imagePath = item.getImagePath();
				System.out.println("축제리스트이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create >" + item.getImagePath());
				}
			}
		}
		
		System.out.println("공연리스트 = " + showList);
		//이미지 크기 맞추기		
		if( showList != null) {
			for (int i=0; i< showList.size(); i++ ) {
				FileTravelComment item = showList.get(i);
				String imagePath = item.getImagePath();
				System.out.println("공연리스트이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("공연리스트 thumbnail create >" + item.getImagePath());
				}
			}
		}
		//이미지 크기 맞추기		
		if( foodList != null) {
			for (int i=0; i< foodList.size(); i++ ) {
				FileTravelComment item = foodList.get(i);
				String imagePath = item.getImagePath();
				System.out.println("맛집리스트이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create >" + item.getImagePath());
				}
			}
		}
		
		//이미지 크기 맞추기
		if( detailPlanList2 != null) {
			for (int i=0; i< detailPlanList2.size(); i++ ) {
				DetailPlan item = detailPlanList2.get(i);
				String imagePath = item.getImagePath();
				System.out.println("상세일정 이미지 = " + imagePath);
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 360, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create >" + item.getImagePath());
				}
			}
		}
		
		System.out.println("상세일정 CROP = " + detailPlanList2);
	
		    
		logger.debug("컨트롤러 detailPlanList2 = " + detailPlanList2);
		
		List<DetailPlan> detailCategorylist = null;
		try {
			detailCategorylist = detailPlanService.selectCategoryList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		logger.debug("컨트롤러 카테고리 리스트 = " + detailCategorylist);
		 
		/** View에 객체 전달 */
		model.addAttribute("festival", festivalList);
		model.addAttribute("show", showList);
		model.addAttribute("food", foodList);
		  
		model.addAttribute("detailPlanList", detailPlanList2);
		//model.addAttribute("detailPlanList", detailPlanList2);
		model.addAttribute("detailCategorylist", detailCategorylist);
		
		// "/WEB-INF/views/planning2.jsp"파일을 View로 사용한다.
		return new ModelAndView("plan/planning/planning2");
	}                                                                     
	
	@ResponseBody 
	@RequestMapping(value= {"/content/plan_save.do"}, method = RequestMethod.POST)
	public void Save(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** webHelper 초기화 */    
		web.init();  
		response.setContentType("application/json");
		 
		/** 파라미터 */
		//로그인 검사
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("로그인 하셔야 일정을 만들 수 있습니다.");
		} 
		System.out.println("회원 id = " + loginInfo.getId());
		//planning2.jsp -> ajax -> travel_plan_id
		int travelPlanId = web.getInt("travel_plan_id");
		System.out.println("travelPlanId=" + travelPlanId);
		
		JSONArray jsonArray;
		JSONParser jsonParser = new JSONParser();
		
		String subject = request.getParameter("subject");
		String detail_plan = request.getParameter("detailPlan");
		String term_num = request.getParameter("term_num");
		 
		int term = Integer.parseInt(term_num);
		model.addAttribute("term_num", term_num);
		
		System.out.println("subject = " + subject); 
		System.out.println("상세일정 = " + detail_plan);
		System.out.println("상세일정 term = " + term_num);
		
		DetailPlan detailPlan2 = new DetailPlan();
		detailPlan2.setMemberId(loginInfo.getId());
		detailPlan2.setTravelPlanId(travelPlanId);
		
		try {
			detailPlanService.deleteDetailPlan(detailPlan2);
		} catch (Exception e) {
			System.out.println("상세일정 삭제 실패");
			web.printJsonRt("상세일정 삭제 실패");
		}   
		
		/** Service를 통한 detail_plan 저장 */
		int result = 0; // 서비스 성공 여부 0:실패, 1:성공
		try {
			jsonArray = (JSONArray) jsonParser.parse(detail_plan);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				String day = object.get("day").toString();
				String content_no = object.get("content_no").toString();
				String travel_id = object.get("travel_id").toString();
				int day_num = Integer.parseInt(day);
				int content_num = Integer.parseInt(content_no);
				int travel_num = Integer.parseInt(travel_id);
				
				System.out.println("for문 일정 테스트=" + day_num);
				System.out.println("for문 content 테스트=" + content_num);
				System.out.println();
				
				DetailPlan detailPlan = new DetailPlan();
				detailPlan.setMemberId(loginInfo.getId());
				detailPlan.setTravelId(travel_num);
				detailPlan.setTravelPlanId(travelPlanId);
				detailPlan.setDay(day_num);
				detailPlan.setContentNo(content_num);
				
				System.out.println("상세일정 리스트 = " + detailPlan);
				
				/** 상세일정 추가 */
				detailPlanService.insertDetailPlan(detailPlan);
			}   
			result = 1;
		} catch (Exception e) {
			System.out.println("상세일정 등록 실패");
			web.printJsonRt("상세일정 등록 실패");
		}
		   
		/** planning2.jsp에서 저장닫기 클릭 시 day갯수를 저장하기 위한 목적 */
		//String start_date = request.getParameter("start_date");
		int season = Integer.parseInt(request.getParameter("season"));
		int theme = Integer.parseInt(request.getParameter("theme"));
		
		System.out.println("계절 = " + season);
		System.out.println("테마 = " + theme);
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setId(travelPlanId);
		travelPlan.setTerm(term);
		travelPlan.setSeason(season);
		travelPlan.setTheme(theme);
		 
		TravelPlan travelPlan2 = new TravelPlan();
		travelPlan2.setId(travelPlanId);
		
		TravelPlan travelPlanItem = null;
		try {
			travelPlanService.updateTravelPlan(travelPlan);
			travelPlanItem = travelPlanService.selectTravelPlan(travelPlan2);
		}catch(Exception e) {
			System.out.println("상세일정 수정 실패");
			web.printJsonRt("상세일정 수정 실패");
		}
		 
		System.out.println("상세일정 컨트롤러 수정결과 리스트 = " + travelPlanItem);
		
		/** 처리 결과를 JSON으로 출력하기 */
		Map<Object, Object> data = new HashMap<Object,Object>();
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