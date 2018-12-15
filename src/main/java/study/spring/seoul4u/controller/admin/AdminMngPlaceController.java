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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import study.spring.seoul4u.helper.PageHelper;
import study.spring.seoul4u.helper.RegexHelper;
import study.spring.seoul4u.helper.UploadHelper;
import study.spring.seoul4u.helper.WebHelper;
import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.model.DetailPlan;
import study.spring.seoul4u.model.Likes;
import study.spring.seoul4u.model.Member;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.service.DetailPlanService;
import study.spring.seoul4u.service.LikesService;
import study.spring.seoul4u.service.TravelCommentService;
import study.spring.seoul4u.service.TravelDocumentService;
import study.spring.seoul4u.service.TravelFileService;
import study.spring.seoul4u.helper.FileInfo;
import study.spring.seoul4u.model.TravelFile;



@Controller
public class AdminMngPlaceController {
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(AdminMngPlaceController.class);
	@Autowired
	WebHelper web;	
	@Autowired
	TravelDocumentService travelDocumentService;	
	@Autowired
	RegexHelper regex;	
	@Autowired
	UploadHelper upload;	
	@Autowired
	BBSCommon bbs;	
	@Autowired
	PageHelper pageHelper;	
	@Autowired
	TravelFileService travelFileService;	
	@Autowired
	LikesService likesService;	
	@Autowired
	TravelCommentService travelCommentService;
	@Autowired
	DetailPlanService detailPlanService;
	

	/** 여행지 관리 게시판  */
	@RequestMapping(value = {"/admin/admin_mng_place.do"})
	public ModelAndView Index(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response, TravelDocument travel,String searchType) {
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
		
		/** (5) 조회할 정보에 대한 Beans 생성 */
		//검색어
		String search = web.getString("search");
		String keyword= web.getString("keyword");
		
		TravelDocument travelkeyword = new TravelDocument();
		travelkeyword.setCategory(category);
		
		// 현재 페이지 수 --> 기본값은 1페이지로 설정함
		int page = web.getInt("page",1);
		
		//제목과 내용에 대한 검색으로 활용하기 위해서 입력값을 설정한다.
		travel.setSearch(search);
		System.out.println("search???" +search);
		travel.setSubject(keyword);
		travel.setContent(keyword);
		
		/** (6) 게시글 목록 조회 */
		int totalCount = 0;
		List<TravelDocument> list = null;
		
		try {
			//전체 게시물 수
			totalCount = travelDocumentService.selectTravelCount(travel);
			//나머지 페이지 번호 계산하기
			//현재 페이지,전체 게시물 수,한 페이지의 목록 수, 그룹 갯수
			pageHelper.pageProcess(page, totalCount, 10, 5);
			

			//페이지 번호 계산 결과에서 Limit 절에 필요한 값을 Beans에 추가
			travel.setLimitStart(pageHelper.getLimitStart());
			travel.setListCount(pageHelper.getListCount());
			
			list =  travelDocumentService.selectTravelList(travel);
		} catch (Exception e) {
			//예외 발생시, service layer에서 전달하는 메시지 내용을 사용자에게 제시하고 이전페이지로 이동한다.
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		
		
		//사용자가 입력한 검색어를 view에 되돌려준다. --> 자동완성 구현을 위함
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageHelper", pageHelper);
		
		// 현재 페이지의 가장 큰 번호 구하기
		// --> 전체갯수 - (페이지번호-1) * 한페이지에 표시할 갯수
		int maxPageNo = pageHelper.getTotalCount() - (pageHelper.getPage() - 1) 
				* pageHelper.getListCount();
		// 구해진 최대 수치를 View에 전달하기 (이 값을 1씩 감소시키면서 출력한다.)
		model.addAttribute("maxPageNo", maxPageNo);
		
		// 갤러리 종류라면 View 의 이름을 다르게 설정한다.
//		String view = "admin/admin_mng_place";

		
//		return new ModelAndView(view); 
		return new ModelAndView("admin/admin_mng_place");
	}
	
	@RequestMapping(value = "/admin/travel_write_ok.do")
	public ModelAndView TravelWriteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	
		web.init();

		/** (3) 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			System.out.println("글없음");
			return web.redirect(null, "multipart 데이터가 아닙니다.");
		}

		//** (3) 로그인 여부 검사 
		Member adminInfo = (Member) web.getSession("adminInfo");
		if (web.getSession("adminInfo") == null) {
			return web.redirect(web.getRootPath() + "/admin/admin_login.do", "관리자 로그인이 필요합니다.");
		}
			
		/** (4) UploadHelper에서 텍스트 형식의 값을 추출 */
		Map<String, String> paramMap = upload.getParamMap();
		String category = paramMap.get("category");
		String subject = paramMap.get("subject");
		String address = paramMap.get("address");
		String content = paramMap.get("content");
		String summary = paramMap.get("summary");
		Double mapX = Double.parseDouble(paramMap.get("mapX"));
		Double mapY = Double.parseDouble(paramMap.get("mapY"));		
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("category=" + category);
		logger.debug("subject=" + subject);
		logger.debug("address=" + address);
		logger.debug("content=" + content);
		logger.debug("summary=" + summary);
		logger.debug("mapX=" + mapX);
		logger.debug("mapY=" + mapY);

		/** (5) 게시판 카테고리 값을 받아서 View에 전달 */
		// 파일이 첨부된 경우 WebHelper를 사용할 수 없다.
		// String category = web.getString("category");
		request.setAttribute("category", category);
		
		/** (6) 존재하는 게시판인지 판별하기 */
		try {
			String bbsName = bbs.getBbsName(category);
			request.setAttribute("bbsName", bbsName);	
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (7) 입력 받은 파라미터에 대한 유효성 검사 */

		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {
			return web.redirect(null, "글 제목을 입력하세요.");
		}

		if (!regex.isValue(address)) {
			return web.redirect(null, "주소를 입력하세요.");
		}
		
		if (!regex.isValue(content)) {
			return web.redirect(null, "내용을 입력하세요.");
		}
		
		if (!regex.isValue(summary)) {
			return web.redirect(null, "요약정보를 입력하세요.");
		}


		/** (8) 입력 받은 파라미터를 Beans로 묶기 */	
		TravelDocument travel = new TravelDocument();
		travel.setCategory(category);
		travel.setSubject(subject);
		travel.setAddress(address);
		travel.setContent(content);
		travel.setSummary(summary);
		travel.setMemberId(adminInfo.getId());
		travel.setMapX(mapX);
		travel.setMapY(mapY);

		logger.debug("travel >> " + travel.toString());

		/** (9) Service를 통한 게시물 저장 */
		try {
			//for (int i=1; i<=100; i++) {
			//	document.setSubject(subject + "(" + i + ")");
				travelDocumentService.insertTravel(travel);
			//}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		/** (10) 첨부파일 목록 처리 */
		// 업로드 된 파일 목록
		// --> import study.jsp.helper.FileInfo;
		List<FileInfo> fileList = upload.getFileList();
		
		try {
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i = 0; i < fileList.size(); i++) {
				// 업로드 된 정보 하나 추출하여 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileList.get(i);

				// DB에 저장하기 위한 항목 생성
				TravelFile file = new TravelFile();

				// 몇 번 게시물에 속한 파일인지 지정한다.
				file.setTravelId(travel.getId());
				
				// 데이터 복사
				file.setOriginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());
				
				// 저장처리
				travelFileService.insertFile(file);
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		
		//** (11) 저장 완료 후 읽기 페이지로 이동하기 *//
		// 읽어들일 게시물을 식별하기 위한 게시물 일련번호값을 전달해야 한다.
		String url = "%s/admin/mngPlace_read.do?category=%s&travel_id=%d";
		url = String.format(url, web.getRootPath(), travel.getCategory(), travel.getId());
		return web.redirect(url, "저장되었습니다" );

	
	}	
	
	@RequestMapping(value="/admin/mngPlace_read.do")
	public ModelAndView TravelRead(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** (3) 게시판 카테고리 값을 받아서 View 에 전달*/
		String category = web.getString("category");
		model.addAttribute("category", category);
		
		/** (4) 존재하는 게시판인지 판별하기*/
		try {
			String bbsName = bbs.getBbsName(category);
			model.addAttribute("bbsName", bbsName);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 글 번호 파라미터 받기 */
		int travelId = web.getInt("travel_id");
		logger.debug("travelId="+ travelId);
		
		if (travelId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		
		//파라미터를 Beans로 묶기
		TravelDocument travel = new TravelDocument();
		travel.setId(travelId);
		travel.setCategory(category);
		
		TravelFile file = new TravelFile();
		file.setTravelId(travelId);
		
		/** (6) 게시물 일련번호를 사용한 데이터 조회*/
		// 지금 읽고 있는 게시물이 저장될 객체
		TravelDocument readTravel = null;
		// 이전글이 저장될 객체 
		TravelDocument prevTravel = null;
		// 다음글이 저장될 객체
		TravelDocument nextTravel = null;
		//첨부파일 정보가 저장될 객체
		List<TravelFile> fileList = null;
		
		// 해당 여행지에 대한 평가 개수를 담을 변수
		int commentCount = 0;
		// 평가 개수 조회를 위한 comment 객체
		Comment travelComment = new Comment();
		travelComment.setTravelId(travelId);
		// 특정 여행지에 대한 평점 평균을 담을 객체
		float ratingAvg = 0;
		
		try {
			commentCount = travelCommentService.selectCommentCountByTravelId(travelComment);
			if (commentCount != 0) {
				ratingAvg = travelCommentService.selectRatingAvgByTravelId(travelComment);
				}
			readTravel = travelDocumentService.selectTravel(travel);
			prevTravel = travelDocumentService.selectPrevTravel(travel);
			nextTravel = travelDocumentService.selectNextTravel(travel);
			fileList = travelFileService.selectFileList(file);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		} 		
		
		/** (7) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("ratingAvg", ratingAvg);
		model.addAttribute("commentCount", commentCount);
		model.addAttribute("readTravel", readTravel);
		model.addAttribute("prevTravel", prevTravel);
		model.addAttribute("nextTravel", nextTravel);
		model.addAttribute("fileList", fileList);
		System.out.println("평균평점=" + ratingAvg );
		
		return new ModelAndView ("admin/mngPlace_read");
	}
	
	
	@RequestMapping(value="/admin/mngPlace_delete_ok.do")
	public ModelAndView TravelDeleteOk(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) {
		
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
		

		/** (5) 게시글 번호 받기 */
		int travelId= web.getInt("travel_id");
		
		logger.debug("travelId" + travelId);
		
		if (travelId == 0) {
			//sqlSession.close();
			return web.redirect(null, "글 번호가 없습니다.");
			//return null;
		}
		
		/** (6) 파라미터를 beans로 묶기 */
		TravelDocument travel = new TravelDocument();
		travel.setId(travelId);
		travel.setCategory(category);
		
		TravelFile file = new TravelFile();
		file.setTravelId(travelId);
		
		Likes likes = new Likes();
		likes.setTravelId(travelId);
		
		//게시물에 속한 덧글 삭제를 위해 생성
		Comment comment = new Comment();
		comment.setTravelId(travelId);
		
		DetailPlan detailPlan = new DetailPlan();
		detailPlan.setTravelId(travelId);
		
		/** (7) 데이터 삭제 처리 */
		
		List<TravelFile> fileList = null;
		List<Likes> clipList = null;
		
		try {
			
			fileList = travelFileService.selectFileList(file); //게시글에 포함된 파일목록을 조회
			travelFileService.deleteFileAll(file); 			//게시글에 속한 파일목록 모두 삭제
			
			clipList = likesService.selectClipList(likes);
			likesService.deleteClipAll(likes);
			
			//덧글 먼저 삭제
			travelCommentService.deleteCommentAll(comment);
			detailPlanService.deleteDetailAll(detailPlan); // 상세일정 삭제
			travelDocumentService.deleteTravel(travel);    //게시글 삭제
			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
			//return null; 
		} 
		
		/** (8) 실제 파일을 삭제한다. */
		// DB에서 파일 정보가 삭제되더라도 실제 저장되어 있는 파일 자체가 삭제되는 것은 아니다.
		// 실제 파일도 함께 삭제하기 위해서 (7)번 절차에서 파일정보를 삭제하기 전에 미리 조회해 둔 것이다.
		// 조회한 파일 목록만큼 반복하며서 저장되어 있는 파일을 삭제한다.
		if (fileList != null) {
			for(int i=0;i<fileList.size();i++) {
				TravelFile f= fileList.get(i);
				String filePath = f.getFileDir() + "/" +f.getFileName();
				logger.debug("fileRemove:"+ filePath);
				upload.removeFile(filePath);
			}
		}
		/** (9) 페이지 이동*/
		String url = "%s/admin/admin_mng_place.do?category=%s";
		url = String.format(url, web.getRootPath(),category);
		
		return web.redirect(url, "삭제되었습니다.");
		//return null;
		}

	
	@RequestMapping(value="/admin/mngPlace_edit_ok.do")
	public ModelAndView doRun(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		
		/** (3) 파일이 포함된 POST 파라미터 받기*/
		try {
			upload.multipartRequest();
		} catch(Exception e) {
			//sqlSession.close();
			return web.redirect(null, "multipart 데이터가 아닙니다.");
			//return null;
		}
		//** (3) 로그인 여부 검사 
		Member adminInfo = (Member) web.getSession("adminInfo");
		if (web.getSession("adminInfo") == null) {
			return web.redirect(web.getRootPath() + "/admin/admin_login.do", "관리자 로그인이 필요합니다.");
		}
		
		/** (4) UploadHelper에서 텍스트 형식의 값을 추출*/
		Map<String,String> paramMap = upload.getParamMap();
		
		// 글 번호 가져오기 --> Map 에서 가져오는 값은 모두 String 이므로 형변환필요하다.
		int travelId = 0;
	
		try {
			travelId = Integer.parseInt(paramMap.get("travel_id"));
		} catch(NumberFormatException e) {
			//sqlSession.close();
			return web.redirect(null, "여행지글 번호가 올바르지 않습니다.");
			//return null;
		}
		
		String category = paramMap.get("category");
		String subject = paramMap.get("subject");
		String address = paramMap.get("address");
		String content = paramMap.get("content");
		String summary = paramMap.get("summary");
		Double mapX = Double.parseDouble(paramMap.get("mapX"));
		Double mapY = Double.parseDouble(paramMap.get("mapY"));
		
		/** (5) 게시판 카테고리 값을 받아서 view 에 전달*/
		// 파일이 첨부된 경우 webHelper를 사용할 수 없다.
		//String category = web.getString("category");
		model.addAttribute("category", category);
		
		/** (6) 존재하는 게시판인지 판별하기*/
		
		try {
			String bbsName = bbs.getBbsName(category);
			model.addAttribute("bbsName", bbsName);
		} catch (Exception e) {
			//sqlSession.close();
			 return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		
		
		//전달된 파라미터는 로그로 확인한다.
		logger.debug("traveltId=" + travelId);
		logger.debug("category=" + category);
		logger.debug("subject=" + subject);
		logger.debug("address=" + address);
		logger.debug("content=" + content);
		logger.debug("summary=" + summary);
		logger.debug("mapX=" + mapX);
		logger.debug("mapY=" + mapY);

		/** (8) 입력받은 파라미터에 대한 유효성 검사*/
		// 제목 및 내용 검사
		if (!regex.isValue(subject)) {
			return web.redirect(null, "글 제목을 입력하세요.");
		}

		if (!regex.isValue(address)) {
			return web.redirect(null, "주소를 입력하세요.");
		}
		
		if (!regex.isValue(content)) {
			return web.redirect(null, "내용을 입력하세요.");
		}
		
		if (!regex.isValue(summary)) {
			return web.redirect(null, "요약정보를 입력하세요.");
		}
		
		/** (9) 입력받은 파라미터를 beans로 묶기*/
		
		TravelDocument travel = new TravelDocument();
		//UPDATE 문의 WHERE 절에서 사용해야 하므로 글 번호 추가
		// --> 글 번호는 숫자로 변환해서 처리해야 한다.
		travel.setId(travelId);
		travel.setCategory(category);
		travel.setSubject(subject);
		travel.setAddress(address);
		travel.setContent(content);
		travel.setSummary(summary);
		travel.setMapX(mapX);
		travel.setMapY(mapY);
		travel.setMemberId(adminInfo.getId());


		logger.debug("travel >> " + travel.toString());
		
		
		/** (10) 게시물 변경을 위한 service 기능을 호출*/
		try {
			travelDocumentService.updateTravel(travel);
		}catch (Exception e) {
			//sqlSession.close();
			return web.redirect(null, e.getLocalizedMessage());
			//return null;
		}
		
		/** (11) 삭제를 선택한 첨부파일에 대한 처리*/
		// 삭제할 파일 목록에 대한 체크결과 --> 체크밗의 선택값을 paramMap에서 추출
		String delFile = paramMap.get("del_file");
		
		if (delFile != null) {
			//콤마 단위로 잘라서 배열로 변환
			String[] delFileList = delFile.split(",");
			
			for (int i=0; i<delFileList.length; i++) {
				try {
					//체크박스에 의해서 전달된 id값으로 개별 파일에 대한 beans 생성
					TravelFile file = new TravelFile();
					file.setId(Integer.parseInt(delFileList[i]));
					
					// 개별 파일에 대한 정보를 조회하여 실제 파일을 삭제한다.
					TravelFile item = travelFileService.selectFile(file);
					upload.removeFile(item.getFileDir() + "/" + item.getFileName());
					
					// DB 에서 파일정보 삭제처리
					travelFileService.deleteFile(file);
				} catch (Exception e) {
					//sqlSession.close();
					return web.redirect(null, e.getLocalizedMessage());
					//return null;
				}
			}
		}
		/** (12) 추가적으로 업로드 된 파일 정보 처리*/
		// 업로드 된 파일 목록
		// --> import study.jsp.helper.FileInfo;
		List<FileInfo> fileInfoList = upload.getFileList();
		
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i = 0; i < fileInfoList.size(); i++) {
				// 업로드 된 정보 하나 추출
				// --> 업로드 된 정보 하나 추출하여 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileInfoList.get(i);
				
				// DB에 저장하기 위한 항목 하나 생성
				TravelFile file = new TravelFile();
				
				// 데이터 복사
				file.setOriginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());
				
				// 몇 번 게시물에 속한 파일인지 지정한다.
				file.setTravelId(travelId);
				
				// 복사된 데이터를 DB에 저장
				try {
				
				travelFileService.insertFile(file);
				} catch (Exception e) {
					//sqlSession.close();
					return web.redirect(null, e.getLocalizedMessage());
					//return null;
					} 
				}

		/** (13) 모든 절차가 종료되었으므로 DB 접속 해제 후 페이지 이동*/
			//sqlSession.close();
			
			String url = "%s/admin/mngPlace_read.do?category=%s&travel_id=%d";
			url = String.format(url, web.getRootPath(), category, travelId);
			return web.redirect(url, null);
			
			//return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/admin_MngPlace_delete_check.do", method = RequestMethod.POST)
	public void AdminMemberDeleteCheck(Locale locale, Model model, 
			HttpServletResponse response) {
		web.init();
		response.setContentType("application/json"); 
		String[] del_place = web.getStringArray("chkArr", null);
				
		TravelDocument travel = new TravelDocument();
		TravelFile file = new TravelFile();
		Likes likes = new Likes();
		DetailPlan detailPlan = new DetailPlan();//상세일정 삭제		
		Comment comment = new Comment();	//게시물에 속한 덧글 삭제를 위해 생성
		/** (7) 데이터 삭제 처리 */		
		List<TravelFile> fileList = null;			
		
		for (int i=0; i<del_place.length; i++) {
			System.out.println("test=" + del_place[i]);
			int result = Integer.parseInt(del_place[i]);
			travel.setId(result);
			file.setTravelId(result);
			likes.setTravelId(result);
			comment.setTravelId(result);
			detailPlan.setTravelId(result);
			System.out.println("result값=" + result);
			
			try {
				fileList = travelFileService.selectFileList(file);
				System.out.println("fileList값=" + fileList);
				travelFileService.deleteFileAll(file); 
				likesService.deleteClipAll(likes);
				//덧글 먼저 삭제
				travelCommentService.deleteCommentAll(comment);
				detailPlanService.deleteDetailAll(detailPlan);
				travelDocumentService.deleteTravel(travel); 
				
			}catch (Exception e) {
				 web.printJsonRt(e.getLocalizedMessage());
				 return;
			}	
			/** (8) 실제 파일을 삭제한다. */
			if (fileList != null) {
				for(int j=0;j<fileList.size();j++) {
					TravelFile f= fileList.get(j);
					String filePath = f.getFileDir() + "/" +f.getFileName();
					logger.debug("fileRemove:"+ filePath);
					upload.removeFile(filePath);
				}
			}
		}
		
		
		/** (3) 목록페이지로 이동 */
		/*return web.redirect(web.getRootPath() + "/admin/admin_member.do", "회원탈퇴를 완료했습니다.");*/
		web.printJsonRt("ok");
	}
}