package study.spring.seoul4u.controller.member;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
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

import study.spring.seoul4u.helper.FileInfo;
import study.spring.seoul4u.helper.MailHelper;
import study.spring.seoul4u.helper.RegexHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.Util;
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
public class MemberController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	WebHelper web;
	@Autowired
	SqlSession sqlSession;
	@Autowired
	RegexHelper regex;
	@Autowired
	UploadHelper upload;
	@Autowired
	MemberService memberService;
	@Autowired
	MailHelper mail;
	@Autowired
	Util util;
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
	
	/** 회원가입 페이지로 이동 */
	@RequestMapping(value = {"/member/join.do"})
	public ModelAndView Join(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) WebHelper 초기화 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
		}
		
		// "/WEB-INF/views/member/join.jsp파일을 View로 사용한다.
		return new ModelAndView("member/join");
	}
	
	/** 회원가입 완료 */
	@RequestMapping(value = {"/member/join_ok.do"})
	public ModelAndView JoinOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();

		/** (3) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
		}
		
		/** (4) 파일이 포함된 POST 파라미터 받기 */
		// <form>태그 안에 <input type="file">요소가 포함되어 있고,
		// <form>태그에 enctype="multipart/form-data"가 정의되어 있는 경우
		// WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없게 된다.
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}
		
		// UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
		Map<String, String> paramMap = upload.getParamMap();
		String userId = paramMap.get("user_id");
		String userPw = paramMap.get("user_pw");
		String userPwRe = paramMap.get("user_pw_re");
		String name = paramMap.get("name");
		String tel = paramMap.get("tel");
		String birthdate = paramMap.get("birthdate");
		String email = paramMap.get("email");
		String gender = paramMap.get("gender");
		
		//전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("userId=" + userId);
		logger.debug("userPw=" + userPw);
		logger.debug("userPwRe=" + userPwRe);
		logger.debug("name=" + name);
		logger.debug("tel=" + tel);
		logger.debug("birthdate=" + birthdate);
		logger.debug("email=" + email);
		logger.debug("gender=" + gender);
		
		/** (5) 입력값의 유효성 검사 */
		/* 아이디 값 검사하기 */
		if(!regex.isValue(userId)){
			return web.redirect(null, "아이디를 입력하세요");
		}
		
		if(!regex.isEngNum(userId)){
			return web.redirect(null, "아이디는 영문, 숫자만 입력 가능합니다.");
		}
		
		//글자 수 검사
		int user_id_len = userId.length();
		if(user_id_len < 4 || user_id_len > 20){
			return web.redirect(null, "아이디는 4~20자 이내로 입력하세요.");
		}
		
		/* 비밀번호 검사하기 */
		if(!regex.isValue(userPw)){
			return web.redirect(null, "비밀번호를 입력하세요.");
		}
		if(!regex.isEngNum(userPw)){
			return web.redirect(null, "비밀번호는 숫자와 영문 조합 20자까지만 가능합니다.");
		}
		//글자 수 검사
		int user_pw_len = userPw.length();
		if(user_pw_len < 4 || user_pw_len > 20){
			return web.redirect(null, "비밀번호는 숫자와 영문 조합 20자까지만 가능합니다.");
		}
		
		/* 비밀번호 확인 값 검사 */
		if(!userPw.equals(userPwRe)){
			return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
		}
		
		/* 이름 값 검사 */
		if(!regex.isValue(name)){
			return web.redirect(null, "이름을 입력하세요");
		}
		
		if(!regex.isKor(name)){
			return web.redirect(null, "한글만 입력가능합니다.");
		}
		
		int user_name_len = name.length();
		if(user_name_len > 10){
			return web.redirect(null, "이름은 10자 이내로 입력가능합니다.");
		}
		
		/* 휴대폰 검사 */
		if(!regex.isValue(tel)){
			if(!regex.isCellPhone(tel)){
				return web.redirect(null, "연락처의 형식이 잘못되었습니다.");
			}
		}
		
		/* 생년월일 */
		if(!regex.isValue(birthdate)){
			return web.redirect(null, "생년월일을 입력하세요");
		}
		//if(!regex.isBirthdate(birthdate)){
		//	return web.redirect(null, "생년월일 형식이 맞지 않습니다");
		//}
		
		/* 이메일 검사 */
		if(!regex.isValue(email)){
			return web.redirect(null, "이메일을 입력하세요.");
		}
		
		if(!regex.isEmail(email)){
			return web.redirect(null, "이메일 형식이 잘못되었습니다.");
		}
		
		if(!regex.isValue(gender)){
			return web.redirect(null, "성별을 입력하세요.");
		}
		
		if (!gender.equals("M") && !gender.equals("F")) {
			return web.redirect(null, "성별이 잘못되었습니다.");
		}
		
		/** (6) 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		// 업로드 된 프로필 사진 경로가 저장될 변수
		String profileImg = null;
		
		// 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
		if (fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			profileImg = info.getFileDir() + "/" + info.getFileName();
		}
		
		//파일경로를 로그로 기록
		logger.debug("profileImg=" + profileImg);
		
		/** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPw(userPw);
		member.setName(name);
		member.setEmail(email);
		member.setTel(tel);
		member.setBirthdate(birthdate);
		member.setProfileImg(profileImg);
		member.setGender(gender);
		member.setProfileImg(profileImg);
		
		/** (8) Service를 통한 데이터베이스 저장 처리 */
		try {
			memberService.insertMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (9) 가입이 완료되었으므로 메인페이지로 이동(미완) */
		return web.redirect(web.getRootPath() + "/index.do", "회원가입이 완료되었습니다. 로그인 해주세요.");
	}
	
	/** 회원정보 수정 */
	@RequestMapping(value = {"/member/edit_ok.do"})
	public ModelAndView EditOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) WebHelper 초기화 */
		web.init();
		
		/** (3) 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}
		
		// UploadHelper에서 텍스트 형식의 파라미터를 분류한  Map을 리턴받아서 값을 추출한다.
		Map<String, String> paramMap = upload.getParamMap();
		String userPw = paramMap.get("user_pw");
		String newUserPw = paramMap.get("new_user_pw");
		String newUserPwRe = paramMap.get("new_user_pw_re");
		String tel = paramMap.get("tel");
		// 추가 - 이미지 삭제 여부에 대한 체크박스
		String imgDel = paramMap.get("img_del");
		
		// 전달받은 파라미터는 값의 정상여부 확인을 위해서 로그로 확인
		logger.debug("userPw=" + userPw);
		logger.debug("newUserPw=" + newUserPw);
		logger.debug("newUserPwRe=" + newUserPwRe);
		logger.debug("imgDel=" + imgDel);
		logger.debug("tel=" + tel);
		
		/** (4) 입력값의 유효성 검사 (아이디 검사 수행안함) */
		
		// 현재 비밀번호 검사
		if (!regex.isValue(userPw)) { return web.redirect(null, "현재 비밀번호를 입력하세요."); }

		// 신규 비밀번호 검사
		// --> 신규 비밀번호가 입력된 경우는 변경으로 간주하고, 입력하지 않은 경우는
		//     변경하지 않도록 처리한다. 그러므로 입력된 경우만 검사해야 한다.
		if (regex.isValue(newUserPw)) {
			if (!regex.isEngNum(newUserPw) || newUserPw.length() > 20) {
					return web.redirect(null, "새로운 비밀번호는 숫자와 영문의 조합으로 20자까지만 가능합니다.");
			}
			// 비밀번호 확인
			if (!newUserPw.equals(newUserPwRe)) {
				return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
			}
		}
		
		// 연락처 검사
		if (!regex.isValue(tel)) { return web.redirect(null, "연락처를 입력하세요."); }
		if (!regex.isCellPhone(tel) && !regex.isTel(tel)) { return web.redirect(null, "연락처의 형식이 잘못되었습니다."); }
		
		// 로그인 정보 검사
		Member loginInfo = (Member) web.getSession("loginInfo");
		/** (5) 프로필 사진의 삭제가 요청된 경우 */
		if (regex.isValue(imgDel) && imgDel.equals("Y")) {
			
			// 세션에 보관되어 있는 이미지 경로를 취득
			upload.removeFile(loginInfo.getProfileImg());
		}
		
		/** (6) 업로드 된 파일 정보 추출 */
		// --> 이미지 수정을 원하지 않는 경우, 삭제만 원하는 경우
		//     데이터 없음
		List<FileInfo> fileList = upload.getFileList();
		// 업로드 된 프로필 사진 경로가 저장될 변수
		String profileImg = null;

		// 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
		if (fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			profileImg = info.getFileDir() + "/" + info.getFileName();
		}

		// 파일경로를 로그로 기록
		logger.debug("profileImg=" + profileImg);
		
		/** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
		//  아이디는 변경할 수 없으므로 제외한다.
		Member member = new Member();
		// WHERE절에 사용할 회원번호는 세션에서 취득
		member.setId(loginInfo.getId());
		member.setUserPw(userPw);
		member.setTel(tel);
		
		// 변경할 신규 비밀번호
		member.setNewUserPw(newUserPw);
		
		if (profileImg != null) {
			// 이미지가 업로드 되었다면?
			// --> 이미지 교체를 위해서 업로드 된 파일의 정보를 Beans에 등록
			member.setProfileImg(profileImg);
		} else if (profileImg == null) {
			// 이미지가 업로드 되지 않았다면?
			// --> 삭제만 체크했을 경우
			if (imgDel != null && imgDel.equals("Y")) {
				// SQL에서는 공백일 경우 null로 업데이트 하도록 분기하고 있다.
				member.setProfileImg("");
			}
		}
		
		/** (8) Service를 통한 데이터베이스 저장 처리 */
		// 변경된 정보를 저장하기 위한 객체
		Member editInfo = null;
		try {
			memberService.selectMemberPasswordCount(member);
			memberService.updateMember(member);
			editInfo = memberService.selectMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			
		}
		
		/** (10) 세션, 쿠키 갱신 */
		// 일단 쿠키의 썸네일 정보를 삭제한다.
		if (imgDel != null && imgDel.equals("Y")) {
			web.removeCookie("profileThumbnail");
		}
		
		// 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
		String newProfileImg = editInfo.getProfileImg();
		if (newProfileImg != null) {
			try {
				String profileThumbnail = upload.createThumbnail(newProfileImg, 40, 40, true);
				web.setCookie("profileThumbnail", profileThumbnail, -1);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
				
			}
		}
		
		// 세션을 갱신한다.
		web.removeSession("loginInfo");
		web.setSession("loginInfo", editInfo);
		
		/** 마지막 */
		return web.redirect(web.getRootPath() + "/index.do", "회원정보 수정이 완료되었습니다.");
	}
	/** 로그인 */
	@RequestMapping(value = {"/member/login_ok.do"})
	public ModelAndView LoginOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
			if (web.getSession("loginInfo") != null) {
				// 이미 SqlSession 객체를 생성했으므로, 데이터베이스 접속을 해제해야 한다.
				return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
			}
		
		/** (4) 파라미터 처리 */
		// -->navbarLoginmodal.jsp에 배치된 폼으로부터 전송받는 입력값.(user_id1, user_pw1)
		String userId = web.getString("user_id1");
		String userPw = web.getString("user_pw1");
		
		logger.debug("userId=" + userId);
		logger.debug("userPw=" + userPw);
		
		if (userId == null || userPw == null) {
			return web.redirect(null, "아이디나 비밀번호가 없습니다.");
		}
		
		/** (5) 전달받은 파라미터를 Beans에 설정한다. */
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPw(userPw);
		
		/** (6) Service를 통한 회원인증 */
		Member loginInfo = null;
		
		try {
			// 아이디와 비밀번호가 일치하는 회원 정보를 조회하여 리턴한다.
			loginInfo = memberService.selectLoginInfo(member);
		} catch (Exception e) {
			//sqlSession.close();
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		/** (7) 프로필 이미지 처리 */
		// 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
		String profileImg = loginInfo.getProfileImg();
		if (profileImg != null) {
			try {
				String profileThumbnail = upload.createThumbnail(profileImg, 40, 40, true);
				web.setCookie("profileThumbnail", profileThumbnail, -1);
			} catch (Exception e) {
				return web.redirect(null, e.getLocalizedMessage());
				//return null;
			}
		}
		
		/** (8) 조회된 회원 정보를 세션에 저장 */
		// 로그인 처리는 아이디와 비밀번호를 기반으로 조회된 정보를
		// 세션에 보관하는 과정을 말한다.
		// 로그인에 대한 판별은 저장된 세션정보의 존재 여부로 판별한다.
		web.setSession("loginInfo", loginInfo);
		
		/** (9) 페이지 이동 */
		// 이전 페이지 구하기 (javascript로 이동된 경우 조회 안됨)
		String movePage = request.getHeader("referer");
		if (movePage == null) {
			movePage = web.getRootPath() + "/index.do";
		}
		
		return web.redirect(movePage, "환영합니다." + loginInfo.getName() + "님");
	}
	
	/** 로그아웃 */
	@RequestMapping(value = {"/member/logout.do"})
	public ModelAndView LoginOut(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 필요한 헬퍼 객체 생성 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 로그인중인 회원 정보 가져오기
		Member loginInfo =(Member) web.getSession("loginInfo");
		// 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
		}
		
		/** (4) 로그아웃 */
		// 로그아웃은 모든 세션 정보를 삭제하는 처리.
		web.removeAllSession();
		
		/** (5) 페이지 이동 */
		return web.redirect(web.getRootPath() + "/index.do", "로그아웃 되었습니다.");
	}
	
	/** 아이디 비번 찾기 페이지 이동*/
	@RequestMapping(value = {"/member/find_idpw.do"})
	public ModelAndView FindIDPW(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// "/WEB-INF/views/member/find_idpw.jsp"파일을 View로 사용한다.
		return new ModelAndView("member/find_idpw");
	}
	
	/** 아이디 찾기 */
	@RequestMapping(value = {"/member/find_id_ok.do"})
	public ModelAndView FindIDOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 로그인 중이라면 이 페이지를 이용할 수 없다.
		if (web.getSession("loginInfo") != null) {
			return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 중입니다.");
		}
		
		/** (4) 파라미터 받기 */
		// 입력된 이름, 폰번호를 받는다.
		String name = web.getString("user_name");
		String tel = web.getString("user_tel");
		
		logger.debug("name=" + name);
		logger.debug("tel=" + tel);
		
		if (name == null) { return web.redirect(null, "이름을 입력하세요."); }
		if (tel == null) { return web.redirect(null, "폰번호를 입력하세요."); }
		
		/** (5) 입력값을 JavaBeans에 저장하기 */
		Member member = new Member();
		member.setName(name);
		member.setTel(tel);
		
		
		/** (6) Service를 통해 user_id값 찾기 */
		Member findId = null;
		
		try {
			findId = memberService.selectUserIdByTel(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		/** (7) 메인페이지 이동 + 아이디 화면에 띄우기 */
		// 메인페이지로 이동
		return web.redirect(web.getRootPath() + "/index.do", "고객님의 아이디는 " + findId.getUserId() + "입니다.");
	}
	
	/** 새 비밀번호 이메일 발송 */
	@RequestMapping(value = {"/member/find_pw_ok.do"})
	public ModelAndView FindPWOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) 사용하고자 하는 Helper+Service 객체 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		if (web.getSession("loginInfo") != null) {
			//sqlSession.close();
			return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 중입니다.");
			//return null;
		}
		
		/** (4) 파라미터 받기 */
		// 입력된 메일 주소를 받는다.
		String userId = web.getString("user_id");
		String email = web.getString("user_email");
		
		logger.debug("name=" + userId);
		logger.debug("email=" + email);
		
		if (userId == null) {
			return web.redirect(null, "아이디를 입력하세요.");
		}
		if (email == null) {
			return web.redirect(null, "이메일 주소를 입력하세요.");
		}
		
		/** (5) 1.등록된 회원인지, 2.작성한 아이디와 이메일이 일치하는지 확인할 용도로 JavaBeans 생성 */
		Member membercheck = new Member();
		membercheck.setUserId(userId);
		membercheck.setEmail(email);
		
		/** (6) Service를 통한 아이디 중복 검사 */
		int result = 0;
		try {
			result = memberService.selectUserIdCount(membercheck);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if (result == 0) {
			return web.redirect(null, "회원가입을 하셔야 합니다.");
		}
		
		/** (7) 가입된 아이디에 해당하는 이메일을 작성했는지 확인 */
		int check = 0;
		try {
			check = memberService.selectUserIdEmailCount(membercheck);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if (check == 0) {
			return web.redirect(null, "아이디와 이메일을 다시 확인하세요.");
		}
		
		/** (8) 임시 비밀번호 생성하기 */
		String newPassword = Util.getInstance().getRandomPassword();
		
		/** (9) 입력값을 JavaBeans에 저장하기 */
		Member member = new Member();
		member.setEmail(email);
		member.setUserPw(newPassword);
		
		/** (10) Service를 통한 비밀번호 갱신 */
		try {
			memberService.updateMemberPasswordByEmail(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (11) 발급된 비밀번호를 메일로 발송하기 */
		String sender = "admin@gmail.com";//root-context.xml - mailsender 확인
		String subject = "Seoul4U 비밀번호 변경 안내 입니다.";
		String content = "회원님의 새로운 비밀번호는 <strong>" + newPassword + "</strong>입니다.";
		
		try {
			// 사용자가 입력한 메일주소를 수신자로 설정하여 메일 발송하기
			mail.sendMail(sender, email, subject, content);
		} catch (MessagingException e) {
			return web.redirect(null, "메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
			//return null;
		}
		
		/** (12) 결과 페이지로 이동 */
		// 여기서는 이전 페이지로 이동함
		return web.redirect(web.getRootPath() + "/index.do", "새로운 비밀번호가 메일로 발송되었습니다.");
	}
	
	/** 회원탈퇴 */
	@RequestMapping(value = {"/mypage/member_out_ok.do"})
	public ModelAndView MemberOutOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** (2) 사용하고자 하는 Helper+Service 객체 */
		web.init();
		
		/** (3) 로그인 여부 검사 */
		// 로그인 중이 아니라면 탈퇴할 수 없다.
		if (web.getSession("loginInfo") == null) {
			return web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
			//return null;
		}
		
		/** (4) 파라미터 받기 */
		String userId = web.getString("user_id");
		String userPw = web.getString("user_pw");
		
		logger.debug("userId=" + userId);
		logger.debug("userPw=" + userPw);
		
		if (userId == null) {
			//sqlSession.close();
			return web.redirect(null, "아이디를 입력하세요.");
			//return null;
		}
		
		if (userPw == null) {
			//sqlSession.close();
			return web.redirect(null, "비밀번호를 입력하세요.");
			//return null;
		}
		
		// 회원번호는 세션을 통해서 획득한 로그인 정보에서 취득.
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		/** (5) 서비스에 전달하기 위하여 파라미터를 Beans로 묶는다. */
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPw(userPw);
		
		Likes likes = new Likes();
		if (loginInfo != null) {
			likes.setMemberId(loginInfo.getId());
		}	
		
		TravelPlan travelPlan = new TravelPlan();
		travelPlan.setMemberId(loginInfo.getId());
		System.out.println("로그인 아이디="+loginInfo.getId());
		
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setMemberId(loginInfo.getId());
		
		Comment comment = new Comment();
		comment.setMemberId(loginInfo.getId());
		
		Qna qna = new Qna();
		qna.setMemberId(loginInfo.getId());
		
		/** (6) Service를 통한 탈퇴 시도 */
		try {
			loginInfo = memberService.selectLoginInfo(member);
			likeService.deleteClipByMemberId(likes);
			likePlanService.deletePlanLikeByMemberId(likes);
			travelPlanService.updatePlanMamberOut(travelPlan);
			detailPlanService.updateDetailPlanMamberOut(detailPlan);
			travelPlanCommentService.updatePlanCommentMamberOut(comment);
			memberQnaService.updateQnaByMamberOut(qna);
			// 비밀번호 검사 --> 비밀번호가 잘못된 경우 예외발생
			memberService.selectMemberPasswordCount(member);
			// 탈퇴처리
			memberService.deleteMember(member);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		// 탈퇴되었다면 프로필 이미지를 삭제한다.
		upload.removeFile(loginInfo.getProfileImg());
		
		
		/** (7) 정상적으로 탈퇴된 경우 강제 로그아웃 및 페이지 이동 */
		web.removeAllSession();
		return web.redirect(web.getRootPath() + "/index.do", "탈퇴되었습니다.");
		//return null;
	}
	
	/** 아이디 중복검사 */
	@ResponseBody
	@RequestMapping(value = "/member/id_unique_check.do", method = RequestMethod.GET)
	public void IdUniqueCheck(Locale locale, Model model, HttpServletResponse response) {
		web.init();
		response.setContentType("application/json");
		
		String userId = web.getString("user_id");
		
		if (userId == null) {
			web.printJsonRt("아이디가 없습니다.");
			return;
		}
		Member member = new Member();
		member.setUserId(userId);
		int have = 0;// 아이디 중복검사 결과 저장할 값: 중복되면 0이 아님. 중복되면 0.
		try {
			have = memberService.selectUserIdCount(member);
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		String result= "OK";// have가 0이면 result는 OK 
		if (have != 0) {
			result = "FAIL";// have가 0이 아니면 result는 FAIL
		}
		
		/** 처리 결과를 JSON으로 출력하기 */
		Map<String, String> data = new HashMap<String,String>();
		data.put("result", result);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}