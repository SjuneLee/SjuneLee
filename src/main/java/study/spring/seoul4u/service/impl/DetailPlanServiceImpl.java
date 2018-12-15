package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.DetailPlan;
import study.spring.seoul4u.service.DetailPlanService;

@Service
public class DetailPlanServiceImpl implements DetailPlanService{

	private static Logger logger = LoggerFactory.getLogger(TravelDocumentServiceImpl.class);
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<DetailPlan> selectlDetailPlanList(DetailPlan detailPlan) throws Exception {
		List<DetailPlan> result = null;
		try {
			result = sqlSession.selectList("DetailPlanMapper.selectDetailPlanList", detailPlan);
			System.out.println("구현체 상세일정 테스트 = " + result);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("상세일정 리스트 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("상세일정 리스트 조회 실패!!");
		}
		
		return result;
	}
	
	@Override
	public List<DetailPlan> selectDetailIngPlanList(DetailPlan detailPlan) throws Exception {
		List<DetailPlan> result = null;
		try {
			result = sqlSession.selectList("DetailPlanMapper.selectDetailIngPlanList", detailPlan);
			System.out.println("계획중인일정 -> 상세일정 리스트 = " + result);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("계획중인일정 -> 상세일정 리스트 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("계획중인일정 -> 상세일정 리스트 조회 실패!!");
		}
		
		return result;
	}
	
	@Override
	public DetailPlan selectDetailPlan(DetailPlan detailPlan) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 상세 여행코스 추가 */
	@Override
	public void insertDetailPlan(DetailPlan detailPlan) throws Exception {
		try {
			int result = sqlSession.insert("DetailPlanMapper.insertDetailPlan", detailPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("저장된 상세 여행코스가 없습니다.");
			throw new Exception("저장된 상세 여행코스가 없습니다.");
		} catch (Exception e) {
			System.out.println("상세 여행코스 추가 실패했습니다.");
			logger.error(e.getLocalizedMessage());
			throw new Exception("상세 여행코스 추가 실패했습니다.");
		}
	}
	
	/** 상세일정 삭제 */
	@Override
	public void deleteDetailPlan(DetailPlan detailPlan) throws Exception {
		try {
			int result = sqlSession.delete("DetailPlanMapper.deleteDetailPlan", detailPlan);
			if(result == 0) {
				throw new NullPointerException();
			}
		} catch(NullPointerException e) {
			System.out.println("삭제할 상세일정이 없습니다.");
			//e.printStackTrace();
			//throw new Exception("삭제할 상세일정이 없습니다.");
		} catch(Exception e) {
			throw new Exception("상세일정 삭제 실패!");
		}
		
	}

	/** travel의 category를 뽑아내기 위한 용도 */
	@Override
	public List<DetailPlan> selectCategoryList(DetailPlan detailPlan) throws Exception {
		List<DetailPlan> result = null;
		try {
			result = sqlSession.selectList("DetailPlanMapper.selectCategoryList", detailPlan);
			System.out.println("상세일정 카테고리 리스트 테스트 = " + result);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("상세일정 카테고리 리스트 조회결과가 없습니다.");
		}catch(Exception e) {
			throw new Exception("상세일정 카테고리 리스트 조회 실패!!");
		}
		
		return result;
	}

	@Override
	public void updateDetailPlanMamberOut(DetailPlan detailPlan) throws Exception {
		try {
			sqlSession.update("DetailPlanMapper.updateDetailPlanMamberOut", detailPlan);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}
	@Override
	public void deleteDetailAll(DetailPlan detailPlan) throws Exception {
		try {
			int result = sqlSession.delete("DetailPlanMapper.deleteDetailAll", detailPlan);
			if(result == 0) {
				throw new NullPointerException();
			}
		} catch(NullPointerException e) {
			System.out.println("삭제할 상세일정이 없습니다.");
			//e.printStackTrace();
			//throw new Exception("삭제할 상세일정이 없습니다.");
		} catch(Exception e) {
			throw new Exception("상세일정 삭제 실패!");
		}
		
		
	}
}
