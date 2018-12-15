package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.TravelDocument;

public interface MemberTravelService {
	
	/**
	 * 게시글 목록 조회
	 * 
	 * @param travel - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelDocument> selectTravelList(TravelDocument travel) throws Exception;
	

	/**
	 * 조회수를 1 증가시킨다.
	 * 
	 * @param travel
	 *            - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @throws Exception
	 */
	public void updateTravelHit(TravelDocument travel) throws Exception;


	/**
	 * 전체 게시물 수 조회
	 * 
	 * @param travel
	 * @return int
	 * @throws Exception
	 */
	public int selectTravelCount(TravelDocument travel) throws Exception;


}
