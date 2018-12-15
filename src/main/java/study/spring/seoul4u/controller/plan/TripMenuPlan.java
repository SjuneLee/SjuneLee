package study.spring.seoul4u.controller.plan;


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
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class TripMenuPlan {

	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(TripMenuPlan.class);
	
	@Autowired
	WebHelper web;
	
	@Autowired
	TravelPlanService travelPlanService;
	
	@Autowired
	UploadHelper upload;
	
	@Autowired
	PageHelper pageHelper;
	
	/** 메인페이지 */
	@RequestMapping(value = {"/content/tripMenu_plan.do"})
	public ModelAndView Index(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		// 페이지 형식을 JSON 으로 설정한다.
		//response.setContentType("application/json");
		
		/**(1) 파라미터 받기 **/
		
		String travelDays = web.getString("travelDays");
		int travelMoment = web.getInt("travelMoment");
		int travelTheme = web.getInt("travelTheme");
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setTravelDays(travelDays);
		travelPlan.setTravelMoment(travelMoment);
		travelPlan.setTravelTheme(travelTheme);
			
		System.out.println("travelDays???" +travelDays);	
		System.out.println("travelMoment???" +travelMoment);	
		System.out.println("travelTheme???" +travelTheme);	
			
		/** (3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<TravelPlan> list = null;
		try {
			list = travelPlanService.selectGalleryPlanList(travelPlan);
		}	catch(Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
		}
		
		if (list != null) {	
			for (int i=0; i<list.size(); i++) {
				TravelPlan item = list.get(i);
				String imagePath = item.getImagePath();
				if(imagePath != null) {
					String thumbPath = null;
					try {
						thumbPath = upload.createThumbnail(imagePath,360,320,true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					item.setImagePath(thumbPath);
					logger.debug("thumbnail create>" + item.getImagePath());
					System.out.println("imagePath???"+ imagePath);
				}
			}
		}

		System.out.println("list???"+ list);
		//사용자가 입력한 검색어를 view에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("list", list);
		
		return new ModelAndView("plan/tripMenu_plan");		

	}
}