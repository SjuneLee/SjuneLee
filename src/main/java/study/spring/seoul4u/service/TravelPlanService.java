package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelPlan;

public interface TravelPlanService {
	/**
	 * 게시글 목록 조회
	 * @return TravelPlanPlan - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelPlan> selectTravelPlanList(TravelPlan travelPlan) throws Exception;

	/**
	 * 게시글 목록 조회
	 * @return TravelPlanPlan - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelPlan> selectTravelPlanSearch(TravelPlan travelPlan) throws Exception;
	
	/**
	 * 게시글 목록 조회
	 * @return TravelPlanPlan - 여행지 카테고리 정보가 저장된 Beans
	 * @return List - 게시물 목록
	 * @throws Exception
	 */
	public List<TravelPlan> selectGalleryPlanList(TravelPlan travelPlan) throws Exception;
	
	/**
	 * 하나의 게시물을 읽어들인다.
	 * @return TravelPlan - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return TravelPlan - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public TravelPlan selectTravelPlan(TravelPlan travelPlan) throws Exception;
	/**
	 * 게시물을 저장한다.	 
	 * @return TravelPlan - 게시물 데이터
	 * @throws Exception
	 */
	public void insertTravelPlan(TravelPlan travelPlan) throws Exception;
	/**
	 * 조회수를 1 증가시킨다.
	 * @return TravelPlan - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @throws Exception
	 */
	public void updateTravelPlanHit(TravelPlan travelPlan) throws Exception;
	/**
	 * 전체 게시물 수 조회
	 * @return TravelPlan
	 * @return int
	 * @throws Exception
	 */
	public int selectTravelPlanCount(TravelPlan travelPlan) throws Exception;
	/**
	 * 게시물을 삭제한다.
	 * @return TravelPlan
	 * @throws Exception
	 */
	public void deleteTravelPlan(TravelPlan travelPlan) throws Exception;	
	/**
	 * 게시물을 수정한다.
	 * @return TravelPlan - 게시물 데이터
	 * @throws Exception
	 */
	public void updateTravelPlan(TravelPlan travelPlan) throws Exception;
	/**
	 * travel의 Like_sum 1증가
	 * @return TravelPlan
	 * @throws Exception
	 */
	public void plusLikeSum(TravelPlan travelPlan) throws Exception;
	/**
	 * travel의 Like_sum 1감소
	 * @return TravelPlan
	 * @throws Exception
	 */
	public void minusLikeSum(TravelPlan travelPlan) throws Exception;
	
	/**
	* 회원과 여행일정에 참조관계를 해제한다.
	* @return TravelPlan -게시물 데이터
	* @throws Exception
	*/
	public void updatePlanMamberOut(TravelPlan travelPlan) throws Exception;
	
	/**
	 * 특정 여행지에 대한 평점 평균을 수정한다.
	 * @param comment
	 * @return int
	 * @throws Exception
	 */
	public float updateTravelPlanRatingAvg(TravelPlan travelPlan) throws Exception;
}