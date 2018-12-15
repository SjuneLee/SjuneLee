package study.spring.seoul4u.controller.event;


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

import study.spring.seoul4u.controller.admin.BBSCommon;
import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.service.MemberTravelService;
import study.spring.seoul4u.service.TravelDocumentService;

@Controller
public class TripMenuEvent {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(TripMenuEvent.class);
	
	@Autowired
	WebHelper web;
	
	@Autowired
	UploadHelper upload;
	
	@Autowired
	BBSCommon bbs;
	
	@Autowired
	PageHelper pageHelper;
	
	@Autowired
	TravelDocumentService travelDocumentService;
	
	@Autowired
	MemberTravelService memberTravelService;

	
	/** 메인페이지 */
	@RequestMapping(value = {"/content/travel_list.do"})
	public ModelAndView Index(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,TravelDocument travel) {
		
		web.init();
		
		/** (3) 게시판 카테고리 값을 받아서 View 에 전달*/
		String category = web.getString("category");
		model.addAttribute("category", category);		

		/** (4) 존재하는 게시판인지 판별하기*/
		try {
			String bbsName = bbs.getBbsName(category);
			model.addAttribute("bbsName", bbsName);
		} catch (Exception e) {
			//sqlSession.close();
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		System.out.println("category는???" + category);
		
		
		/** (5) 조회할 정보에 대한 Beans 생성 */
		
		String search = web.getString("search");
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page",1);
	
		travel.setSearch(search);
		System.out.println("search???" +search);
		
		/** (6) 게시글 목록 조회 */
		int totalCount = 0;
		List<TravelDocument> list = null;
		
		
		try {
			//전체 게시물 수
			totalCount = memberTravelService.selectTravelCount(travel);
			//나머지 페이지 번호 계산하기
			//현재 페이지,전체 게시물 수,한 페이지의 목록 수, 그룹 갯수
			pageHelper.pageProcess(page, totalCount, 12, 5);
			

			//페이지 번호 계산 결과에서 Limit 절에 필요한 값을 Beans에 추가
			travel.setLimitStart(pageHelper.getLimitStart());
			travel.setListCount(pageHelper.getListCount());
			
			list =  memberTravelService.selectTravelList(travel);
		} catch (Exception e) {
			//예외 발생시, service layer에서 전달하는 메시지 내용을 사용자에게 제시하고 이전페이지로 이동한다.
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		
		if( list != null) {
			for (int i=0; i< list.size(); i++ ) {
				TravelDocument item = list.get(i);
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
		//사용자가 입력한 검색어를 view에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("list", list);
		model.addAttribute("pageHelper", pageHelper);
		/*model.addAttribute("search", search);*/

		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("/content/travel_list");
	}
}