package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Qna;

public interface MemberQnaService {
	/**
	 * QNA 리스트 조회
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Qna qna) throws Exception;
	
	/**
	 * Qna 제목 클릭 시 상세 정보 보기
	 * @param qna
	 * @return
	 * @throws Exception
	 */
	public Qna selectQnaItem(Qna qna) throws Exception;
	/**
	 * 현재글을 기준으로 이전글을 읽어들인다.
	 * @param Qna - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Qna - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Qna selectPrevQna(Qna qna) throws Exception;
	/**
	 * 현재글을 기준으로 다음글을 읽어들인다.
	 * @param Qna - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return Qna - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public Qna selectNextQna(Qna qna) throws Exception;	
	/**
	 * 전체 게시물 수 조회 (페이지네이션)
	 * @param qna
	 * @return int
	 * @throws Exception
	 */
	public int selectMemberQnaCount(Qna qna) throws Exception;
	/**
	 * 고객센터에서 고객의 질문 등록
	 * @param qna
	 * @throws Exception
	 */
	public void insertMemberQna(Qna qna) throws Exception;
	
	/**
	 * 고객센터 qna글 수정
	 * @param qna
	 * @throws Exception
	 */
	public void editMemberQna(Qna qna) throws Exception;
	
	/**
	 * 고객센테 qna글 삭제
	 * @param qna
	 * @throws Exception
	 */
	public void deleteMemberQna(Qna qna) throws Exception;

	/**
	 * 조회수를 1 증가시킨다.
	 * 
	 * @param qna- 현재글에 대한 게시물 번호가 저장된 Beans
	 * @throws Exception
	 */
	public void updateMemberQnaHit(Qna qna) throws Exception;
	
	/**
	 * 회원탈퇴시 고객센터 qna글 유지
	 * @param qna
	 * @throws Exception
	 */
	public void updateQnaByMamberOut(Qna qna) throws Exception;
	/**
	 * 수정을 위한 로그인한 회원 수 조회 
	 * @param qna
	 * @return int
	 * @throws Exception
	 */
	public int selectCountByMemberId(Qna qna) throws Exception;
}
