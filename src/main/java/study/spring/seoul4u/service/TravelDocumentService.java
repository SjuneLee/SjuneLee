package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.TravelDocument;

public interface TravelDocumentService {


	/**
	 * 게시글 목록 조회
	 * 
	 * @param travel - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelDocument> selectTravelList(TravelDocument travel) throws Exception;

	/**
	 * 게시글 목록 조회
	 * 
	 * @param travel - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelDocument> selectTravelSearch(TravelDocument travel) throws Exception;
	
	/**
	 * 게시물을 저장한다.
	 * 
	 * @param travel
	 *            - 게시물 데이터
	 * @throws Exception
	 */
	public void insertTravel(TravelDocument travel) throws Exception;

	/**
	 * 하나의 게시물을 읽어들인다.
	 * 
	 * @param travel
	 *            - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return TravelDocument - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public TravelDocument selectTravel(TravelDocument travel) throws Exception;

	/**
	 * 현재글을 기준으로 이전글을 읽어들인다.
	 * 
	 * @param travel
	 *            - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return TravelDocument - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public TravelDocument selectPrevTravel(TravelDocument travel) throws Exception;

	/**
	 * 현재글을 기준으로 다음글을 읽어들인다.
	 * 
	 * @param travel
	 *            - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @return TravelDocument - 읽어들인 게시물 내용 (없을 경우 null)
	 * @throws Exception
	 */
	public TravelDocument selectNextTravel(TravelDocument travel) throws Exception;

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


	/**
	 * 게시물을 삭제한다.
	 * 
	 * @param travel
	 * @throws Exception
	 */
	public void deleteTravel(TravelDocument travel) throws Exception;
	
	/**
	 * 게시물을 수정한다.
	 * @param travel - 게시물 데이터
	 * @throws Exception
	 */
	public void updateTravel(TravelDocument travel) throws Exception;
	/**
	 * travel의 Like_sum 1증가
	 * @param travel
	 * @throws Exception
	 */
	public void plusLikeSum(TravelDocument travel) throws Exception;
	/**
	 * travel의 Like_sum 1감소
	 * @param travel
	 * @throws Exception
	 */
	public void minusLikeSum(TravelDocument travel) throws Exception;
	
	/**
	 * 특정 여행지에 대한 평점 평균을 수정한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public float selectTravelRatingAvg(TravelDocument travel) throws Exception;

}

