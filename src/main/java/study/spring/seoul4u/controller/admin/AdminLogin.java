package study.spring.seoul4u.controller.admin;


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

import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.service.MemberService;

@Controller
public class AdminLogin {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(AdminLogin.class);
	@Autowired
	WebHelper web;
	@Autowired
	MemberService memberService;
	
	/** 관리자 메인페이지 */
	@RequestMapping(value = {"/admin/admin_login.do"})
	public ModelAndView Index(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 회원 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
			if (web.getSession("loginInfo") != null) {
				//return web.redirect(web.getRootPath() + "/index.do", "회원은 접근이 불가합니다.");
				return web.redirect(null, "회원은 접근이 불가합니다.");
			}
		
		// "/WEB-INF/views/admin/admin_login.jsp"파일을 View로 사용한다.
		return new ModelAndView("admin/admin_login");
	}
	
	/** 관리자 로그인 */
	@RequestMapping(value = {"/admin/admin_login_ok.do"})
	public ModelAndView AdminLoginOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 회원 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
			if (web.getSession("loginInfo") != null) {
				return web.redirect(web.getRootPath() + "/index.do", "회원은 접근이 불가합니다.");
			}
		
		/** (4) 파라미터 처리 */
		String adminId = web.getString("admin_id");
		String adminPw = web.getString("admin_pw");
		
		logger.debug("adminId=" + adminId);
		logger.debug("adminPw=" + adminPw);
		
		if (adminId == null || adminPw == null) {
			return web.redirect(null, "아이디나 비밀번호가 없습니다.");
		}
		
		/** (5) 전달받은 파라미터를 Beans에 설정한다. */
		Member member = new Member();
		member.setUserId(adminId);
		member.setUserPw(adminPw);
		
		/** (6) Service를 통한 관리자인증 */
		Member adminInfo = null;
		
		try {
			// 아이디와 비밀번호가 일치하는 관리자 정보를 조회하여 리턴한다.
			adminInfo = memberService.selectAdminInfo(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (7) 조회된 관리자 정보를 세션에 저장 */
		web.setSession("adminInfo", adminInfo);
		
		/** (8) 페이지 이동 */
		// "/WEB-INF/views/admin/admin_notice.jsp"파일을 View로 사용한다.
		return web.redirect(web.getRootPath() + "/admin/admin_notice.do", "환영합니다." + adminInfo.getName() + "님");
	}
}
