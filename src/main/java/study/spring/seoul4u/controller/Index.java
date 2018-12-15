package study.spring.seoul4u.controller;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.MemberTravelService;
import study.spring.seoul4u.service.TravelDocumentService;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class Index {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(Index.class);
	@Autowired
	SqlSession sqlSession;
	@Autowired
	WebHelper web;
	@Autowired
	MemberTravelService memberTravelService;
	@Autowired
	UploadHelper upload;
	@Autowired
	TravelDocumentService traveldocumentService;
	@Autowired
	TravelPlanService travelplanService;
	
	/** 메인페이지 */
	@RequestMapping(value = {"/", "/index.do"})
	public ModelAndView IndexPage(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		/** (3) 각 게시판 종류별로 최근 게시물을 조회한다. */
		List<TravelDocument> festivalList = null;
		List<TravelDocument> showList = null;
		List<TravelDocument> foodList = null;
		
		try {
			festivalList = this.getTravelDocumentList("festival", 3);
			showList = this.getTravelDocumentList("show", 3);
			foodList = this.getTravelDocumentList("food", 3);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		} 		
				
		if( festivalList != null) {
			for (int i=0; i< festivalList.size(); i++ ) {
				TravelDocument item = festivalList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		
		if( showList != null) {
			for (int i=0; i< showList.size(); i++ ) {
				TravelDocument item = showList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		
		if( foodList != null) {
			for (int i=0; i< foodList.size(); i++ ) {
				TravelDocument item = foodList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
				}
			}
		}
		System.out.println("festTest="+ festivalList);
		/** (4) 최신 글 목록을 View에 전달 */
		model.addAttribute("festivalList", festivalList);
		model.addAttribute("showList", showList);
		model.addAttribute("foodList", foodList);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("index");
	}
	/**
	 * 특정 카테고리에 대한 상위 n개의 게시물 가져오기
	 * @param category	- 가져올 카테고리
	 * @param listCount - 가져올 게시물 수
	 * @return
	 * @throws Exception
	 */
	private List<TravelDocument> getTravelDocumentList(String category, int listCount) throws Exception {
		List<TravelDocument> list = null;
		// 조회할 조건 생성하기
		// --> 지정된 카테고리의 0번째부터 listCount개 만큼 조회
		TravelDocument travel = new TravelDocument();
		travel.setCategory(category);
		travel.setLimitStart(0);
		travel.setListCount(listCount);
		
		list = memberTravelService.selectTravelList(travel);
		return list;
	}
	
	@RequestMapping(value = {"/content/index_search.do"})
	public ModelAndView IndexSearch(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		

		String category = web.getString("category");
		model.addAttribute("category", category);
		
		// 검색어
		String keyword = web.getString("keyword");
		
		TravelPlan travelplan = new TravelPlan();
		travelplan.setSubject(keyword);
		
		System.out.println("keyword>>>"+ keyword);
		TravelDocument travel = new TravelDocument();
		travel.setSubject(keyword);
		travel.setContent(keyword);
		travel.setCategory(category);

		
		
		/** (3) 서비스 호출 */
		List<TravelPlan> planlist = null;
		
		try {	
		
			planlist = travelplanService.selectTravelPlanSearch(travelplan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		List<TravelDocument> list = null;
		
		
		try {	
			list = traveldocumentService.selectTravelSearch(travel);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** (4) 객체 view에 전달 */
		model.addAttribute("list", list);
		System.out.println("list>>>>>" + list);
		model.addAttribute("planlist", planlist);
		System.out.println("planlist>>>>>" + planlist);
		model.addAttribute("keyword", keyword);
		
		// "/WEB-INF/views/admin/admin_notice.jsp"파일을 View로 사용한다.
		return new ModelAndView("content/index_search");		
	}
}
