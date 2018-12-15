package study.spring.seoul4u.controller.admin;

import java.util.List;
import java.util.Locale;

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

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.model.DetailPlan;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.Qna;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.DetailPlanService;
import study.spring.seoul4u.service.LikePlanService;
import study.spring.seoul4u.service.LikesService;
import study.spring.seoul4u.service.MemberQnaService;
import study.spring.seoul4u.service.MemberService;
import study.spring.seoul4u.service.TravelPlanCommentService;
import study.spring.seoul4u.service.TravelPlanService;

@Controller
public class AdminMemberController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(AdminMemberController.class);
	@Autowired
	WebHelper web;
	@Autowired
	PageHelper page;
	@Autowired
	MemberService memberService;
	@Autowired
	UploadHelper upload;
	@Autowired
	TravelPlanService travelPlanService;
	@Autowired
	TravelPlanCommentService travelPlanCommentService;
	@Autowired
	DetailPlanService detailPlanService;
	@Autowired
	LikesService likeService;
	@Autowired
	LikePlanService likePlanService;
	@Autowired
	MemberQnaService memberQnaService;
	
	/** 회원관리 게시판 */
	@RequestMapping(value = "/admin/admin_member.do", method = RequestMethod.GET)
	public ModelAndView AdminMemberList(Locale locale, Model model) {
		web.init();
		
		String search = web.getString("search");
		String keyword= web.getString("keyword");
		String date= web.getString("date");

		
		// 파라미터 저장 beans
		Member member = new Member();
		member.setSearch(search);
		member.setDate(date);
		member.setUserId(keyword);
		member.setTel(keyword);
		member.setGender(keyword);
		member.setEmail(keyword);
		
		// 현재 페이지 번호에 대한 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2) 페이지 번호 구현하기 */
		// 전체 데이터 수 조회하기
		int totalCount = 0;
		try {
			totalCount = memberService.selectMemberCount(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		// 페이지 번호에 대한 연산 수행 후 조회조건값 지정을 위한 Beans에 추가하기
		page.pageProcess(nowPage, totalCount, 10, 5);
		member.setLimitStart(page.getLimitStart());
		member.setListCount(page.getListCount());
		
		/** (3) Service를 통한 SQL 수행 */
		// 조회 결과를 저장하기 위한 객체
		List<Member> list = null;
		try {
			list = memberService.selectMemberList(member);
		} catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4) View 처리하기 */
		// 조회 결과를 view에게 전달한다.
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		return new ModelAndView("admin/admin_member");
	}
	
	@RequestMapping(value = "/admin/admin_member_view.do", method = RequestMethod.GET)
	public ModelAndView AdminMemberView(Locale locale, Model model) {
		web.init();
		
		int id = web.getInt("id");
		logger.debug("id=" + id);
		
		if (id == 0) {
			return web.redirect(null, "회원번호가 없습니다.");
		}
		
		Member member = new Member();
		member.setId(id);
		
		/** (2) Service를 통한 SQL 수행 */
		Member item = null;
		try {
			item = memberService.selectMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		/** (3) view 처리하기 */
		model.addAttribute("item", item);
	
		return new ModelAndView("/admin/admin_member_view");
	}
	
	@RequestMapping(value = "/admin/admin_member_delete.do", method = RequestMethod.GET)
	public ModelAndView AdminMemberDelete(Locale locale, Model model) {
		web.init();
		
		int id = web.getInt("id");
		System.out.println("아이디 값=" + id);
		
		if (id == 0) {
			return web.redirect(null, "회원번호가 없습니다.");
		}
		
		Member member = new Member();
		member.setId(id);
		
		Member profile = new Member();
		
		Likes likes = new Likes();
		likes.setMemberId(id);
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setMemberId(id);
		
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setMemberId(id);
		
		Comment comment = new Comment();
		comment.setMemberId(id);
		
		Qna qna = new Qna();
		qna.setMemberId(id);
		
		/** (2) Service를 통한 SQL 수행 */
		try {
			likeService.deleteClipByMemberId(likes);
			likePlanService.deletePlanLikeByMemberId(likes);
			travelPlanService.updatePlanMamberOut(travelPlan);
			detailPlanService.updateDetailPlanMamberOut(detailPlan);
			travelPlanCommentService.updatePlanCommentMamberOut(comment);
			memberQnaService.updateQnaByMamberOut(qna);
			profile = memberService.selectMember(member);
			memberService.deleteMemberByAdmin(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 탈퇴되었다면 프로필 이미지를 삭제한다.
		upload.removeFile(profile.getProfileImg());
		/** (3) 목록페이지로 이동 */
		return web.redirect(web.getRootPath() + "/admin/admin_member.do", "회원탈퇴를 완료했습니다.");
	}
	@ResponseBody
	@RequestMapping(value = "/admin/admin_member_delete_check.do", method = RequestMethod.POST)
	public void AdminMemberDeleteCheck(Locale locale, Model model, 
			HttpServletResponse response) {
		web.init();
		response.setContentType("application/json"); 
		String[] del_member = web.getStringArray("chkArr", null);
		
		Member member = new Member();
		
		Member profile = new Member();
		
		Likes likes = new Likes();		
		
		TravelPlan travelPlan = new TravelPlan();		
		
		DetailPlan detailPlan = new DetailPlan();		
		
		Comment comment = new Comment();
		
		Qna qna = new Qna();		
				
		for (int i=0; i<del_member.length; i++) {
			System.out.println("test=" + del_member[i]);
			int result = Integer.parseInt(del_member[i]);
			member.setId(result);
			likes.setMemberId(result);
			travelPlan.setMemberId(result);
			detailPlan.setMemberId(result);
			comment.setMemberId(result);
			qna.setMemberId(result);
			
			try {
				likeService.deleteClipByMemberId(likes);
				likePlanService.deletePlanLikeByMemberId(likes);
				travelPlanService.updatePlanMamberOut(travelPlan);
				detailPlanService.updateDetailPlanMamberOut(detailPlan);
				travelPlanCommentService.updatePlanCommentMamberOut(comment);
				memberQnaService.updateQnaByMamberOut(qna);
				profile = memberService.selectMember(member);
				memberService.deleteMemberByAdmin(member);
				
			}catch (Exception e) {
				 web.printJsonRt(e.getLocalizedMessage());
				 return;
			}
			// 탈퇴되었다면 프로필 이미지를 삭제한다.
			upload.removeFile(profile.getProfileImg());
		}
	
		
		/** (3) 목록페이지로 이동 */
		/*return web.redirect(web.getRootPath() + "/admin/admin_member.do", "회원탈퇴를 완료했습니다.");*/
		web.printJsonRt("ok");
	}
}