package study.spring.seoul4u.controller.sc;

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
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.RegexHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.Qna;
import study.spring.seoul4u.service.MemberQnaService;
import study.spring.seoul4u.service.MemberService;


@Controller
public class ServiceCenterController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(ServiceCenterController.class);
	@Autowired
	WebHelper web;
	@Autowired
	RegexHelper regex;
	@Autowired
	UploadHelper upload;
	@Autowired
	PageHelper pageHelper;
	@Autowired 
	MemberQnaService memberQnaService; 
	@Autowired
	MemberService memberService;
	 
	
	@RequestMapping(value = {"/service/service_center_qna.do"})
	public ModelAndView ServiceCenterQna(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		//검색어
		String search = web.getString("search");
		String keyword= web.getString("keyword");
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);
		
		//제목과 내용에 대한 검색으로 활용하기 위해서 입력값을 설정한다.
		Qna qna = new Qna();
		qna.setSearch(search);
		qna.setSubject(keyword);
		qna.setContent(keyword);
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		model.addAttribute("loginInfo", loginInfo);
		
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인하셔야 합니다.");
		}
		/** (3) 서비스 호출 */
		int totalCount = 0;
		List<Qna> list = null;
		
		try {
			// 전체 게시물 수
			totalCount = memberQnaService.selectMemberQnaCount(qna);
			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수 , 한 페이지의 목록 수 , 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);
			
			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			qna.setLimitStart(pageHelper.getLimitStart());
			qna.setListCount(pageHelper.getListCount());
			
			list = memberQnaService.selectQnaList(qna);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("키워드리스트 = " + list);
		
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageHelper", pageHelper);
		System.out.println("list???" +list);
		
		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) 
				* pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("/sc/service_center_qna");
	}
	
	@RequestMapping(value = {"/service/service_qna_view.do"})
	public ModelAndView ServiceCenterQnaView(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인하셔야 합니다.");
		}
		
		/** (3) 글 번호 파라미터 받기 */
		int qnaId = web.getInt("customer_center_id");
		if (qnaId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		
		logger.debug("qnaId=" + qnaId);
		
		Qna qna = new Qna();
		qna.setId(qnaId);
		
		/*File file = new File();
		file.setCustomerCenterId(qnaId);*/
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Qna readQna = null;
		// 이전글이 저장될 객체
		Qna prevQna = null;
		// 다음글이 저장될 객체
		Qna nextQna = null;
		// 첨부파일 정보가 저장될 객체
		/*List<File> fileList = null;*/
		
		/**조회수 중복 갱신 방지 처리 */
		//카테고리와 게시물 일련번호를 조합한 문자열을 생성
		//ex) document_Qna_15
		String cookieKey = "qna_"+ qnaId;
		//준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		
		try {
			//쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				memberQnaService.updateMemberQnaHit(qna);
				//준비한 문자열에 대한 쿠키를 24시간동안 저장
				web.setCookie(cookieKey, "Y", 60);
			}
			readQna = memberQnaService.selectQnaItem(qna);
			prevQna = memberQnaService.selectPrevQna(qna);
			nextQna = memberQnaService.selectNextQna(qna);
			/*fileList = qnaFileService.selectFileList(file);*/
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		
		model.addAttribute("readQna", readQna);
		model.addAttribute("prevQna", prevQna);
		model.addAttribute("nextQna", nextQna);
		
		System.out.println("readQna=" + readQna);
		System.out.println("prevQna=" + prevQna);
		System.out.println("nextQna=" + nextQna);
		
		model.addAttribute("loginInfo", loginInfo);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("/sc/service_center_qna_view");
	}
	
	/** 사용자 질문 추가 페이지 */
	@RequestMapping(value = {"/service/service_center_qnaAdd.do"})
	public ModelAndView QnaAdd(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		Member loginInfo = null;
		loginInfo = (Member) web.getSession("loginInfo");
		model.addAttribute("loginInfo", loginInfo);
		
		// "/WEB-INF/views/service/service_center.jsp"파일을 View로 사용한다.
		return new ModelAndView("/sc/service_center_qna_add");
	}
	
	/** 게시글 작성 시 유효성 검사 */
	@RequestMapping(value = {"/service/service_center_qnaAdd_ok.do"}, method=RequestMethod.POST)
	public ModelAndView QnaAddOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		int member_id = web.getInt("member_id");
		String category = "qna";
		String subject = web.getString("subject");
		String content = web.getString("content");
		int hit = 0;
		String ip_address = web.getClientIP();
		//관리자 답변상태
		char qna_answer_condition = 'N';
		//비밀글 선택상태
		char qna_secret_condition = ' ';
		String temp = web.getString("secret");
		//비밀글 선택 시
		if(temp != null) {
			qna_secret_condition = 'Y';
		//비밀글 선택 안할 경우
		}else {
			qna_secret_condition = 'N';
		}
		
		System.out.println("비밀글 테스트 = " + qna_secret_condition);
		
		logger.debug("member_id = " + member_id);
		logger.debug("category = " + category);
		logger.debug("subject = " + subject);
		logger.debug("content = " + content);
		logger.debug("hit = " + hit);
		logger.debug("ip_address = " + ip_address);
		logger.debug("qna_answer_condition = " + qna_answer_condition);
		
		System.out.println("member_id = " + member_id);
		System.out.println("category = " + category);
		System.out.println("subject = " + subject);
		System.out.println("content = " + content);
		System.out.println("hit = " + hit);
		System.out.println("ip_address = " + ip_address);
		System.out.println("qna_answer_condition = " + qna_answer_condition);
		
		if(!regex.isValue(subject)) {
			return web.redirect(null, "글제목을 입력하세요.");
		}
		if(!regex.isValue(content)) { 
			return web.redirect(null, "내용을 입력해 주세요.");
		}
		
		Qna qna = new Qna();
		qna.setMemberId(member_id);
		qna.setCategory(category);
		qna.setSubject(subject);
		qna.setContent(content);
		qna.setHit(hit);
		qna.setIpAddress(ip_address);
		qna.setQnaAnswerCondition(qna_answer_condition);
		qna.setQnaSecretCondition(qna_secret_condition);
		
		System.out.println("고객센터 리스트=" + qna);
	
		try {
			memberQnaService.insertMemberQna(qna);
		}catch(Exception e) {
			return web.redirect(null, "글 작성 완료 실패했습니다.");
		}		
		
		return web.redirect(web.getRootPath() + "/service/service_center_qna.do", "글작성이 완료되었습니다.");
	}
	
	/** 사용자 Qna 수정 페이지 */
	@RequestMapping(value = {"/service/service_center_qna_edit.do"})
	public ModelAndView QnaEdit(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인하셔야 합니다.");
		}
		
		int id = web.getInt("customer_center_id");
		Qna qna = new Qna();
		qna.setId(id);
		qna.setMemberId(loginInfo.getId());
		
		System.out.println("수정테스트=" + qna);
		Qna item = null;
		int count = 0; //다른사람이 수정하려하는지 알아보기 위한 변수
		try {
			count = memberQnaService.selectCountByMemberId(qna);
			System.out.println("아이디=" + count);
			if (count == 0) {
				return web.redirect(null, "다른 사람의 글은 수정할 수 없습니다");
			}
			item = memberQnaService.selectQnaItem(qna);
		} catch(Exception e) {
			return web.redirect(null, "고객센터 질문글 수정 진입에 실패했습니다.");
		}
		
		model.addAttribute("item", item);		
		
		// "/WEB-INF/views/service/service_center.jsp"파일을 View로 사용한다.
		return new ModelAndView("/sc/service_center_qna_edit");
	}
	
	/** 사용자 Qna 수정 완료 페이지 */
	@RequestMapping(value = {"/service/service_center_qna_edit_ok.do"}, method = RequestMethod.POST)
	public ModelAndView QnaEditOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		int id = web.getInt("customer_center_id");
		String subject = web.getString("subject");
		String content = web.getString("content");
		String ipAddress = web.getClientIP();
		int memberId = 0;
		
		if(!regex.isValue(subject)) {
			return web.redirect(null, "글제목을 입력하세요.");
		}
		if(!regex.isValue(content)) {
			return web.redirect(null, "고객 질문을 입력하세요.");
		}
		
		Qna qna = new Qna();
		qna.setId(id);
		qna.setSubject(subject);
		qna.setContent(content);
		qna.setIpAddress(ipAddress);
		qna.setMemberId(memberId);
		
		try {
			memberQnaService.editMemberQna(qna);
		}catch(Exception e) {
			return web.redirect(null, "수정완료에 실패했습니다.");
		}
				
		
		String url = "%s/service/service_qna_view.do?customer_center_id=%d";
		url = String.format(url, web.getRootPath(), id);
		return web.redirect(url, "수정이 완료되었습니다.");
	}
	
	/** 사용자 Qna 삭제 페이지 */
	@RequestMapping(value = {"/service/service_center_qna_delete.do"})
	public ModelAndView QnaDelete(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인하셔야 합니다.");
		}
		
		int id = web.getInt("customer_center_id");
		Qna qna = new Qna();
		qna.setId(id);
		qna.setMemberId(loginInfo.getId());
		System.out.println("아이디=" + id);

		int count = 0; //다른사람이 수정하려하는지 알아보기 위한 변수
		try {
			count = memberQnaService.selectCountByMemberId(qna);
			System.out.println("아이디=" + count);
			if (count == 0) {
				return web.redirect(null, "다른 사람의 글은 수정할 수 없습니다");
			}
			memberQnaService.deleteMemberQna(qna);
		} catch(Exception e) {
			return web.redirect(null, "고객센터 질문글 삭제 실패했습니다.");
		}
				
		
		return web.redirect(web.getRootPath() + "/service/service_center_qna.do", "삭제에 성공하였습니다.");
		
	}
	
}