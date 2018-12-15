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
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.File;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.Notice;
import study.spring.seoul4u.service.NoticeFileService;
import study.spring.seoul4u.service.NoticeService;

@Controller
public class SCNoticeController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(SCNoticeController.class);
	@Autowired
	WebHelper web;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	NoticeService noticeService;
	@Autowired
	NoticeFileService noticeFileService;
	
	/** 서비스 센터 메인페이지 - 공지사항 */
	@RequestMapping(value = {"/service/service_center.do", "/service/notice.do"})
	public ModelAndView ServiceCenterNotice(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		//검색어
		String keyword = null;
		keyword = web.getString("keyword");
		String search = web.getString("search");
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);
		
		// 제목으로 검색하기
		Notice notice = new Notice();		
		notice.setSearch(search);
		notice.setSubject(keyword);
		notice.setContent(keyword);
		
		//로그인 확인(세션에 로그인 정보 없을시 고객센터 접근불가)
		Member loginInfo = new Member(); 
		loginInfo = (Member) web.getSession("loginInfo");
		
		if (loginInfo == null) {
			return web.redirect(null, "로그인하셔야 합니다.");
		}
		
		/** (3) 서비스 호출 */
		int totalCount = 0;
		List<Notice> list = null; // 공지사항 게시판 리스트 조회를 위한 객체
		try {
			// 전체 게시물 수
			totalCount = noticeService.selectNoticeCount(notice);
			
			// 나머지 페이지 번호 계산하기
			// --> 현재 페이지, 전체 게시물 수 , 한 페이지의 목록 수 , 그룹갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);
			
			// 페이지 번호 계산 결과에서 Limit절에 필요한 값을 Beans에 추가
			notice.setLimitStart(pageHelper.getLimitStart());
			notice.setListCount(pageHelper.getListCount());
			
			list = noticeService.selectNoticeList(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** (4) 객체 view에 전달 */
		model.addAttribute("loginInfo", loginInfo.getName());// qna게시판에서 게시물 추가시 사용
		model.addAttribute("keyword", keyword);
		model.addAttribute("noticeList", list);
		model.addAttribute("pageHelper", pageHelper);
		
		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) 
				* pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);
		
		// "/WEB-INF/views/index.jsp"파일을 View로 사용한다.
		return new ModelAndView("/sc/service_center_notice");
	}
	
	/** 서비스 센터 공지사항 상세조회*/
	@RequestMapping(value = {"/service/notice_read.do"})
	public ModelAndView NoticeRead(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int noticeId = web.getInt("customer_center_id");
		if (noticeId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		

		// 파라미터를 Beans로 묶기
		Notice notice = new Notice();
		notice.setId(noticeId);
		
		File file = new File();
		file.setCustomerCenterId(noticeId);
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Notice readNotice = null;
		// 이전글이 저장될 객체
		Notice prevNotice = null;
		// 다음글이 저장될 객체
		Notice nextNotice = null;
		// 첨부파일 정보가 저장될 객체
		List<File> fileList = null;
		
		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		// ex) notice_15
		String cookieKey = "notice_" +noticeId;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		
		try {
			// 쿠키값이 없다면 조회수 갱신
			if (cookieVar == null) {
				noticeService.updateNoticeHit(notice);
				// 준비한 문자열에 대한 쿠키를 24시간동안 저장 (초단위가 기준 : 하루는 60*60*24)
				web.setCookie(cookieKey, "Y", 60);// 60초 저장
			}
			readNotice = noticeService.selectNoticeItem(notice);
			prevNotice = noticeService.selectPrevNotice(notice);
			nextNotice = noticeService.selectNextNotice(notice);
			fileList = noticeFileService.selectFileList(file);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("readNotice", readNotice);
		model.addAttribute("prevNotice", prevNotice);
		model.addAttribute("nextNotice", nextNotice);
		model.addAttribute("fileList", fileList);
		System.out.println("filelist="+ fileList);
		
		return new ModelAndView("sc/notice_view");
	}
}