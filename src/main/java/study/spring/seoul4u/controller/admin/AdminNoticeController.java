package study.spring.seoul4u.controller.admin;


import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.FileInfo;
import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.RegexHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.File;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.Notice;
import study.spring.seoul4u.service.NoticeFileService;
import study.spring.seoul4u.service.NoticeService;

@Controller
public class AdminNoticeController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(AdminNoticeController.class);
	@Autowired
	WebHelper web;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	UploadHelper upload;
	@Autowired
	RegexHelper regex;
	@Autowired
	NoticeService noticeService;
	@Autowired
	NoticeFileService noticeFileService;
	
	/** 메인페이지, notice 게시판(selectList) */
	@RequestMapping(value = {"/admin/admin_main_page.do","/admin/admin_notice.do"})
	public ModelAndView AdminNotice(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		// 검색어
		String search = web.getString("search");
		String keyword = web.getString("keyword");
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page", 1);
		
		//제목과 내용에 대한 검색으로 활용하기 위해서 입력값을 설정한다.
		Notice notice = new Notice();
		notice.setSearch(search);
		notice.setSubject(keyword);
		notice.setContent(keyword);
		
		/** (3) 서비스 호출 */
		int totalCount = 0;
		List<Notice> list = null;
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
		model.addAttribute("noticeList", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageHelper", pageHelper);
		
		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) 
				* pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);
		
		// "/WEB-INF/views/admin/admin_notice.jsp"파일을 View로 사용한다.
		return new ModelAndView("admin/admin_notice");
	}
	
	/** 공지사항 등록 */
	@RequestMapping(value = {"/admin/admin_notice_write_ok.do"})
	public ModelAndView NoticeWriteOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}
		
		/** (4) UploadHelper에서 텍스트 형식의 값을 추출 */
		Map<String, String> paramMap = upload.getParamMap();
		String category = paramMap.get("category");
		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		// 작성자 아이피 주소 가져오기
		String ipAddress = web.getClientIP();
		
		// 관리자 정보 세션에서 가져오기
		Member adminInfo = (Member) web.getSession("adminInfo");
		int memberId = adminInfo.getId();//관리자의 member 테이블 id(PK)
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("category=" + category);
		logger.debug("subject=" + subject);
		logger.debug("content=" + content);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("memberId=" + memberId);
		
		/** (5) 게시판 카테고리 값을 받아서 View에 전달 */
		request.setAttribute("category", category);
		
		/** (6) 입력 받은 파라미터에 대한 유효성 검사 */
		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {return web.redirect(null, "글 제목을 입력하세요.");}
		if (!regex.isValue(content)) {return web.redirect(null, "내용을 입력하세요.");}
		
		/** (7) 입력 받은 파라미터를 Beans로 묶기 */
		Notice notice = new Notice();
		notice.setCategory(category);
		notice.setSubject(subject);
		notice.setContent(content);
		notice.setIpAddress(ipAddress);
		notice.setMemberId(memberId);
		logger.debug("notice>>" + notice.toString());
		
		/** (7) Service를 통한 게시물 저장 */
		try {
			//for (int i=1; i<=50; i ++) {
			//	notice.setSubject(subject + "(" + i + ")");
				noticeService.insertNotice(notice);
			//}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (8) 첨부파일 목록 처리 */
		// 업로드 된 파일 목록
		// --> import study.jsp.helper.FileInfo
		List<FileInfo> fileList = upload.getFileList();
		
		try {
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i = 0; i < fileList.size(); i++) {
				// 업로드 된 정보 하나 추출하여 데이터 베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileList.get(i);
				
				// DB에 저장하기 위한 항목생성
				File file = new File();
				
				// 몇 번 게시물에 속한 파일인지 지정한다.
				file.setCustomerCenterId(notice.getId());
				
				// 데이터 복사
				file.setOriginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());
				
				// 저장처리
				noticeFileService.insertFile(file);
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (9) 저장 완료 후 읽기 페이지로 이동하기 */
		// 읽어들일 게시물을 식별하기 위한 게시물 일련번호값을 전달해야 한다.
		String url = "%s/admin/admin_notice_read.do?&customer_center_id=%d";
		url = String.format(url, web.getRootPath(), notice.getId());
		return web.redirect(url,null);
	}
	
	/** 공지사항 상세보기  */
	@RequestMapping(value = {"/admin/admin_notice_read.do"})
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
		
		try {
			// 관리자 조회수는 필요없다. 쿠키 NONO
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
		
		return new ModelAndView("admin/admin_notice_read");
	}
	
	/** 공지사항 수정하기  */
	@RequestMapping(value = {"/admin/admin_notice_edit.do"})
	public ModelAndView NoticeEdit(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int noticeId = web.getInt("customer_center_id");
		logger.debug("noticeId=" + noticeId);
		
		if (noticeId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		
		/** (4) 파라미터를 Beans로 묶기 */
		Notice notice = new Notice();
		notice.setId(noticeId);
		
		File file = new File();
		file.setCustomerCenterId(noticeId); //file테이블의 특정 customer_center_id와 관련된 모든 데이터를 가져온다.
		
		/** (5) 해당 공지사항 정보 가져오기 */
		// 지금 읽고 있는 게시물이 저장될 객체
		Notice noticeEdit = null;
		// 첨부파일 정보가 저장될 객체
		List<File> fileList = null;
		try {
			noticeEdit = noticeService.selectNoticeItem(notice);
			fileList = noticeFileService.selectFileList(file);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (6) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("fileList", fileList);
		model.addAttribute("noticeEdit", noticeEdit);
		
		return new ModelAndView("admin/notice_edit");
	}
	
	/** 공지사항 수정완료  */
	@RequestMapping(value = {"/admin/notice_edit_ok.do"})
	public ModelAndView NoticeEditOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}
		
		/** (4) UploadHelepr에서 텍스트 형식의 값을 추출 */
		Map<String, String> paramMap = upload.getParamMap();
		
		// 글 번호 가져오기 --> Map에서 가져오는 값은 모두 String이므로 형변환 필요
		int noticeId = 0;
		try {
			noticeId = Integer.parseInt(paramMap.get("noticeId"));
		} catch (NumberFormatException e) {
			return web.redirect(null, "글 번호가 제대로 있지 않습니다.");
		}
		
		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		//작성자 아이피 주소 가져오기
		String ipAddress = web.getClientIP();
		// 관리자 일련번호 --> 세션에서 가져온다.
		Member adminInfo = (Member) web.getSession("adminInfo");
		int adminId = adminInfo.getId();
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("noticeId=" + noticeId);
		logger.debug("subject=" + subject);
		logger.debug("content=" + content);
		logger.debug("ipAddress=" + ipAddress);
		logger.debug("adminId=" + adminId);// customer_center 테이블의 참조키 member_id
		
		/** (8) 입력 받은 파라미터에 대한 유효성 검사 */
		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {
			return web.redirect(null, "글 제목을 입력하세요.");
		}

		if (!regex.isValue(content)) {
			return web.redirect(null, "내용을 입력하세요.");
		}
		
		/** (9) 입력 받은 파라미터를 Beans로 묶기 */
		Notice notice = new Notice();
		// UPDATE문의 WHERE절에서 사용해야 하므로 글 번호 추가
		// --> 글 번호는 숫자로 변환해서 처리해야 한다.
		notice.setId(noticeId);
		notice.setSubject(subject);
		notice.setContent(content);
		notice.setIpAddress(ipAddress);
		notice.setMemberId(adminId);
		logger.debug(notice.toString());
		
		/** (10) 게시물 변경을 위한 Service 기능을 호출 */
		try {
			noticeService.updateNotice(notice);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (11) 삭제를 선택한 첨부파일에 대한 처리 */
		// 삭제할 파일 목록에 대한 체크결과 --> 체크박스의 선택값을 paramMap에서 추출
		String delFile = paramMap.get("del_file");
		
		if (delFile != null) {
			// 콤파 단위로 잘라서 배열로 변환
			String[] delFileList = delFile.split(",");
			
			for (int i = 0; i < delFileList.length; i++) {
				try {
					// 체크박스에 의해서 전달된 id값으로 개별 파일에 대한 Beans 생성
					File file = new File();
					file.setId(Integer.parseInt(delFileList[i]));
					
					// 개별 파일에 대한 정보를 조회하여 실제 파일을 삭제한다.
					File item = noticeFileService.selectFileItem(file);
					upload.removeFile(item.getFileDir() + "/" + item.getFileName());
					
					// DB에서 파일정보 삭제처리
					noticeFileService.deleteFile(file);
				} catch (Exception e) {
					return web.redirect(null, e.getLocalizedMessage());
				}
			}
		}
		
		/** (12) 추가적으로 업로드 된 파일 정보 처리 */
		// 업로드 된 파일 목록
		List<FileInfo> fileInfoList = upload.getFileList();
		
		// 업로드 된 파일의 수 만큼 반복 처리 한다.
		for (int i = 0; i < fileInfoList.size(); i++) {
			// 업로드 된 정보 하나 추출
			// --> 업로드 된 정보를 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
			FileInfo info = fileInfoList.get(i);
			
			// DB에 저장하기 위한 항목 하나 생성
			File file = new File();
			
			// 데이터 복사
			file.setOriginName(info.getOrginName());
			file.setFileDir(info.getFileDir());
			file.setFileName(info.getFileName());
			file.setContentType(info.getContentType());
			file.setFileSize(info.getFileSize());
			
			// 어느 게시물에 속한 파일인지 인식해야 하므로 글 번호 추가
			file.setCustomerCenterId(noticeId);
			
			// 복사된 데이터를 DB에 저장
			try {
				noticeFileService.insertFile(file);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
			}
		}
		
		String url = "%s/admin/admin_notice_read.do?&customer_center_id=%d";
		url = String.format(url, web.getRootPath(), noticeId);
		return web.redirect(url, null);
	}
	
	/** 공지사항 삭제하기  */
	@RequestMapping(value = {"/admin/admin_notice_delete_ok.do"})
	public ModelAndView NoticeDeleteOK(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int noticeId = web.getInt("customer_center_id");
		
		logger.debug("noticeId=" + noticeId);
		
		if (noticeId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		
		/** (4) 파라미터를 Beans로 묶기*/
		Notice notice = new Notice();
		notice.setId(noticeId);
		
		// 관리자 로그인 한 경우 관리자의 일련번호를 추가한다.
		Member adminLogin = (Member) web.getSession("adminLogin");
		if (adminLogin != null) {
			notice.setMemberId(adminLogin.getId());
		}
		
		File file = new File();
		file.setCustomerCenterId(noticeId);
		
		/** (5) 데이터 삭제 처리 */
		
		List<File> fileList = null;
		
		try {
			fileList = noticeFileService.selectFileList(file); //게시글에 포함된 파일목록을 조회
			noticeFileService.deleteFileAll(file); //게시글에 속한 파일목록 모두 삭제
			noticeService.deleteNotice(notice);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (6) 실제 파일을 삭제한다. */
		// 조회한 파일 목록만큼 반복하면서 저장되어 있는 파일을 삭제한다.
		if (fileList != null) {
			for(int i=0;i<fileList.size();i++) {
				File f = fileList.get(i);
				String filePath = f.getFileDir() + "/" + f.getFileName();
				logger.debug("fileRemove:" + filePath);
				upload.removeFile(filePath);
			}
		}
		
		/** (7) 페이지 이동 */
		return web.redirect(web.getRootPath() + "/admin/admin_notice.do", "삭제되었습니다.");
	}
	
	/** 체크된 공지사항 삭제하기  */
	@ResponseBody
	@RequestMapping(value = {"/admin/admin_notice_delete_check.do"})
	public void NoticeCheckedDel(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		web.init();
		response.setContentType("application/json"); 
		String[] del_notice = web.getStringArray("chkArr", null);
		
		Notice notice = new Notice();
				
		for (int i=0; i<del_notice.length; i++) {
			System.out.println("test=" + del_notice[i]);
			int result = Integer.parseInt(del_notice[i]);
			notice.setId(result);
			try {
				noticeService.deleteNotice(notice);
				
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