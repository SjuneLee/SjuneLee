package study.spring.seoul4u.controller.admin;


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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Qna;
import study.spring.seoul4u.service.AdminQnaService;

@Controller
public class AdminQNAController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(AdminQNAController.class);
	@Autowired
	WebHelper web;
	@Autowired
	AdminQnaService adminQnaService;
	@Autowired
	PageHelper pageHelper;
	
	/** 여행지 관리 게시판 */
	@RequestMapping(value = {"/admin/admin_qna.do"})
	public ModelAndView Index(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		
		//검색어
		String search = web.getString("search");
		String keyword= web.getString("keyword");
		String condition= web.getString("condition");
		
		/*String category= web.getString("category");*/
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);
						
		//제목과 내용에 대한 검색으로 활용하기 위해서 입력값을 설정한다.
		Qna qna = new Qna();
		qna.setCategory("qna");
		qna.setSearch(search);
		qna.setCondition(condition);
		qna.setSubject(keyword);
		qna.setContent(keyword);
		qna.setQnaAnswer(keyword);
		
		System.out.println("keyword???" +keyword);			
		System.out.println("search???" +search);
		System.out.println("condition???" +condition);	
		int totalCount = 0;
		List<Qna> list = null;
		
		try {
			// 전체 게시물 수
			totalCount = adminQnaService.selectQnaCount(qna);
						
			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수 , 한 페이지의 목록 수 , 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);
						
			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			qna.setLimitStart(pageHelper.getLimitStart());
			qna.setListCount(pageHelper.getListCount());
						
			list =  adminQnaService.selectQnaList(qna);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		System.out.println("list???" +list);
		model.addAttribute("pageHelper", pageHelper);
		
		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) 
				* pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("admin/admin_qna");
	}
	
	/** qna 상세뵤기 */
	@RequestMapping(value = {"/admin/admin_qna_view.do"})
	public ModelAndView QnaView(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int qnaId = web.getInt("qna_id");
		if (qnaId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		logger.debug("qnaId=" + qnaId);
		
		Qna qna = new Qna();
		qna.setId(qnaId);
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Qna readQna = null;
		// 이전글이 저장될 객체
		Qna prevQna = null;
		// 다음글이 저장될 객체
		Qna nextQna = null;
		
		try {
			readQna = adminQnaService.selectQnaItem(qna);
			prevQna = adminQnaService.selectPrevQna(qna);
			nextQna = adminQnaService.selectNextQna(qna);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		model.addAttribute("readQna", readQna);
		model.addAttribute("prevQna", prevQna);
		model.addAttribute("nextQna", nextQna);
		
		return new ModelAndView("admin/admin_qna_view");
	}
	
	/** Qna 삭제  */
	@RequestMapping(value = {"/admin/admin_qna_delete.do"})
	public ModelAndView QnaDelete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		int id = web.getInt("qna_id");
		Qna qna = new Qna();
		qna.setId(id);
		System.out.println("삭제할 qna id 번호 = " + id);
		
		try {
			adminQnaService.deleteAdminQna(qna);
		} catch (Exception e) {
			return web.redirect(null, "삭제에 실패했습니다.");
		}
		
		return web.redirect(web.getRootPath() + "/admin/admin_qna.do", "삭제되었습니다.");
	}
	
	/** Qna 수정  */
	@RequestMapping(value = {"/admin/admin_qna_edit.do"})
	public ModelAndView QnaEdit(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		int id = web.getInt("qna_id");
		Qna qna = new Qna();
		qna.setId(id);
		
		Qna item = null;
		try {
			item = adminQnaService.selectQnaItem(qna);
		}catch(Exception e) {
			return web.redirect(null, "수정 페이지 이동에 실패했습니다.");
		}
		
		System.out.println("수정테스트=" + item);
		
		model.addAttribute("item", item);
		
		return new ModelAndView("admin/admin_qna_edit");
	}
	
	/** Qna 수정 완료  */
	@RequestMapping(value = {"/admin/admin_qna_edit_ok.do"})
	public ModelAndView QnaEditOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		 
		//Member loginInfo = (Qna)
		
		int id = web.getInt("qna_id");
		String qna_answer = web.getString("qna_answer");
		//관리자가 답변을 하지 않는다면
		
		Qna qna = new Qna();
		qna.setId(id);
		
		if(qna_answer == null) {
			qna.setQnaAnswerCondition('N');
		}else {
			qna.setQnaAnswerCondition('Y');
		}
		qna.setQnaAnswer(qna_answer);
		
		System.out.println("질문답변=" + qna );
		try {
			adminQnaService.updateAminQna(qna);
		}catch(Exception e) {
			return web.redirect(null, "수정 페이지 이동에 실패했습니다.");
		}
		System.out.println("처리결과 qna id = " + id);
		System.out.println("처리결과 qna answer = " + qna_answer);
		
		String url = "%s/admin/admin_qna_view.do?qna_id=%d";
		url = String.format(url, web.getRootPath(), id, qna_answer);
		return web.redirect(url, "수정이 완료되었습니다.");
		//return web.redirect(web.getRootPath() + "/admin/admin_qna_view.do?qna_id=", "수정이 완료되었습니다.");
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/admin_qna_delete_check.do", method = RequestMethod.POST)
	public void AdminMemberDeleteCheck(Locale locale, Model model, 
			HttpServletResponse response) {
		web.init();
		response.setContentType("application/json"); 
		String[] del_qna = web.getStringArray("chkArr", null);
		
		Qna qna = new Qna();
						
		for (int i=0; i<del_qna.length; i++) {
			System.out.println("test=" + del_qna[i]);
			int result = Integer.parseInt(del_qna[i]);
			qna.setId(result);
			try {
				adminQnaService.deleteAdminQna(qna);
				
			}catch (Exception e) {
				 web.printJsonRt(e.getLocalizedMessage());
				 return;
			}
		}	
		
		/** (3) 목록페이지로 이동 */
		/*return web.redirect(web.getRootPath() + "/admin/admin_member.do", "회원탈퇴를 완료했습니다.");*/
		web.printJsonRt("ok");
	}
}