package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Notice;

/**
 * @author sjune
 * 공지사항 관련 기능을 제공하기 위한 Service 계층
 */
public interface NoticeService {
	/**
	 * notice를 저장한다.(관리자 View에서 customer_service 테이블에 insert)
	 * @param notice -customer_service 데이터
	 * @throws Exception
	 */
	public void insertNotice(Notice notice) throws Exception;
	/**
	 * notice 목록을 가져온다.(customer_center 테이블 중 notice)
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public List<Notice> selectNoticeList(Notice notice) throws Exception;
	/**
	 * notice notice 중 하나를 가져온다. (조건: customer_center테이블의 PR키)
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public Notice selectNoticeItem(Notice notice) throws Exception;
	/**
	 * 현재글을 기준으로 이전글을 읽어들인다.
	 * @param notice - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Notice - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Notice selectPrevNotice(Notice notice) throws Exception;
	/**
	 * 현재글을 기준으로 다음글을 읽어들인다.
	 * @param notice - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Notice - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Notice selectNextNotice(Notice notice) throws Exception;
	/**
	 * 관리자가 등록한 게시물인지 검사한다.
	 * @param notice
	 * @return int
	 * @throws Exception
	 */
	public int selectNoticeCountByMemberId(Notice notice) throws Exception;
	/**
	 * 전체 게시물 수 조회 (페이지네이션)
	 * @param notice
	 * @return int
	 * @throws Exception
	 */
	public int selectNoticeCount(Notice notice) throws Exception;
	/**
	 * notice를 업데이트 한다. (조건: customer_center테이블의 PR키/ 업데이트 될수 있는 값: subject, content, edit_date,ip_address,member_id)
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	public void updateNotice(Notice notice) throws Exception;
	/**
	 * notic의 조회수를 업데이트 한다. (조건 : customer_center 테이블의 PR키(id) 업데이트 되는 값: hit + 1)
	 * @param notice
	 * @throws Exception
	 */
	public void updateNoticeHit(Notice notice) throws Exception;
	/**
	 * notice를 삭제한다. (조건: customer_center_id)
	 * @param notice
	 * @throws Exception
	 */
	public void deleteNotice(Notice notice) throws Exception;
}
