package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.Qna;

public interface AdminQnaService {
	/**
	 * QNA 리스트 조회
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Qna qna) throws Exception;
	/**
	 * 전체 게시물 수 조회
	 * 
	 * @param qna
	 * @return int
	 * @throws Exception
	 */
	public int selectQnaCount(Qna qna) throws Exception;
	
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
	public int selectAdminQnaCount(Qna qna) throws Exception;
	/**
	 * qna 게시글 삭제
	 * @param qna
	 * @throws Exception
	 */
	public void deleteAdminQna(Qna qna) throws Exception;
	
	/**
	 * qna 게시글 수정
	 * @param qna
	 * @throws Exception
	 */
	public void updateAminQna(Qna qna) throws Exception;
}
