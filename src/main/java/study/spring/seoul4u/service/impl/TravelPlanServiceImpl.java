package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.TravelPlanService;

@Service
public class TravelPlanServiceImpl implements TravelPlanService{

	private static Logger logger = LoggerFactory.getLogger(TravelDocumentServiceImpl.class);
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<TravelPlan> selectTravelPlanList(TravelPlan travelPlan) throws Exception {
		List<TravelPlan> result = null;

		try {
			result = sqlSession.selectList("TravelPlanMapper.selectTravelPlanList",travelPlan);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<TravelPlan> selectTravelPlanSearch(TravelPlan travelPlan) throws Exception {
		List<TravelPlan> result = null;

		try {
			result = sqlSession.selectList("TravelPlanMapper.selectTravelPlanSearch",travelPlan);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}

		return result;
	}
	
	
	@Override
	public List<TravelPlan> selectGalleryPlanList(TravelPlan travelPlan) throws Exception {
		List<TravelPlan> result = null;
 		try {
			result = sqlSession.selectList("TravelPlanMapper.selectGalleryPlanList",travelPlan);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}
 		return result;
	}
	
	@Override
	public TravelPlan selectTravelPlan(TravelPlan travelPlan) throws Exception {
		TravelPlan result = null;

		try {
			result = sqlSession.selectOne("TravelPlanMapper.selectTravelPlan", travelPlan);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void insertTravelPlan(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.insert("TravelPlanMapper.insertTravelPlan", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			System.out.println("저장된 게시물이 없습니다.");
			throw new Exception("저장된 게시물이 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			System.out.println("게시물 정보 등록에 실패했습니다.");
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 정보 등록에 실패했습니다.");
		}
		
	}

	@Override
	public void updateTravelPlanHit(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.update("TravelPlanMapper.updateTravelPlanHit", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("조회수 갱신에 실패했습니다.");
		}
	}

	@Override
	public int selectTravelPlanCount(TravelPlan travelPlan) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("TravelPlanMapper.selectTravelPlanCount", travelPlan);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void deleteTravelPlan(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.delete("TravelPlanMapper.deleteTravelPlan", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateTravelPlan(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.update("TravelPlanMapper.updateTravelPlan", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수정에 실패했습니다.");
		}
	}

	@Override
	public void plusLikeSum(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.update("TravelPlanMapper.plusLikeSum", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("좋아요 총계가 바뀐 것이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 총계 증가에 실패했습니다.");
		}
	}

	@Override
	public void minusLikeSum(TravelPlan travelPlan) throws Exception {
		try {
			int result = sqlSession.update("TravelPlanMapper.minusLikeSum", travelPlan);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("좋아요 총계가 바뀐 것이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 총계 감소에 실패했습니다.");
		}
	}
	
	@Override
	public void updatePlanMamberOut(TravelPlan travelPlan) throws Exception {
		try {
			//게시글을 작성한 적이 없는 회원도 있을 수 있기 때문에,
			//NullPointException을 발생시키지 않는다.
			sqlSession.update("TravelPlanMapper.updatePlanMamberOut", travelPlan);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}
	
	@Override
	public float updateTravelPlanRatingAvg(TravelPlan travelPlan) throws Exception {
		float result = 0;

		try {
			result = sqlSession.update("TravelPlanMapper.updateTravelPlanRatingAvg", travelPlan);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 평점 평균 수정에 실패했습니다.");
		}

		return result;
	}
	
}
