package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.DetailPlan;

public interface DetailPlanService {
	/**
	 * 상세 여행코스 리스트 조회
	 * @throws Exception
	 */
	public List<DetailPlan> selectlDetailPlanList(DetailPlan detailPlan) throws Exception;
	
	/**
	 * 마이페이지 - 계획중인 일정에 보여줄 상세여행지 이미지
	 * @param detailPlan
	 * @return
	 * @throws Exception
	 */
	public List<DetailPlan> selectDetailIngPlanList(DetailPlan detailPlan) throws Exception;
	
	/**
	 * travel의 category를 뽑아내기 위한 용도
	 * @param detailPlan
	 * @return
	 * @throws Exception
	 */
	public List<DetailPlan> selectCategoryList(DetailPlan detailPlan) throws Exception;
	
	/**
	 * 상세 여행코스 단일조회
	 * @param detailPlan
	 * @return
	 * @throws Exception
	 */
	public DetailPlan selectDetailPlan(DetailPlan detailPlan) throws Exception;
	
	/**
	 * 상세 여행코스 삽입
	 * @param detailPlan
	 * @throws Exception
	 */
	public void insertDetailPlan(DetailPlan detailPlan) throws Exception;
	
	/**
	 * 상세 여행코스 삭제
	 * @param detailPlan
	 * @throws Exception
	 */
	public void deleteDetailPlan(DetailPlan detailPlan) throws Exception;
	
	/**
	* 회원과 여행상세일정에 참조관계를 해제한다.
	* @return TravelPlan -게시물 데이터
	* @throws Exception
	*/
	public void updateDetailPlanMamberOut(DetailPlan detailPlan) throws Exception;
	/**
	 * 여행지 삭제시 상세 여행코스 삭제
	 * @param detailPlan
	 * @throws Exception
	 */
	public void deleteDetailAll(DetailPlan detailPlan) throws Exception;
}
