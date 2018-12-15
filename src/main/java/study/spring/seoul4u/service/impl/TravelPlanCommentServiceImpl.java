package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.Comment;
import study.spring.seoul4u.service.TravelPlanCommentService;

@Service
public class TravelPlanCommentServiceImpl implements TravelPlanCommentService {

	private static Logger logger = LoggerFactory.getLogger(TravelCommentServiceImpl.class);

	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertTravelPlanComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.insert("TravelPlanCommentMapper.insertTravelPlanComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 여행지 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 덧글 등록에 실패했습니다");
		}
	}
	
	@Override
	public List<Comment> selectTravelPlanCommentList(Comment comment) throws Exception {
		List<Comment> result = null;
		try {
			result = sqlSession.selectList("TravelPlanCommentMapper.selectTravelPlanCommentList", comment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("TravelPlanComment 리스트 조회결과가 없습니다.");
		} catch (Exception e) {
			throw new Exception("TravelPlanComment 리스트 조회 실패");
		}
		return result;
	}

	@Override
	public Comment selectTravelPlanCommentItem(Comment comment) throws Exception {
		Comment result = null;

		try {
			result = sqlSession.selectOne("TravelPlanCommentMapper.selectTravelPlanCommentItem", comment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectPlanCommentCountByMemberId(Comment comment) throws Exception {
		int result = 0;

		try {
			// 자신의 덧글이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("TravelPlanCommentMapper.selectPlanCommentCountByMemberId", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int selectPlanCommentCountByPw(Comment comment) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("TravelPlanCommentMapper.selectPlanCommentCountByPw", comment);
			// 비밀번호가 일치하는 데이터의 수가 0이라면 비밀번호가 잘못된 것으로 간주한다.
			if (result < 1) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("비밀번호를 확인하세요.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int commentCountByMemberIdTravelPlanId(Comment comment) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("TravelPlanCommentMapper.commentCountByMemberIdTravelPlanId", comment);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("특정 회원의 특정 플랜 평가 검사가 실패했습니다.");
		}
		return result;
	}
	@Override
	public int selectPlanCountByTravelPlanId(Comment comment) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("TravelPlanCommentMapper.selectPlanCountByTravelPlanId", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 댓글 수 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public float selectRatingAvgByTravelPlanId(Comment comment) throws Exception {
		float result = 0;

		try {
			result = sqlSession.selectOne("TravelPlanCommentMapper.selectRatingAvgByTravelPlanId", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("해당 게시물 평점 평균 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void updateTravelPlanComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.update("TravelPlanCommentMapper.updateTravelPlanComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 수정에 실패했습니다.");
		}

	}

	@Override
	public void deleteTravelPlanComment(Comment comment) throws Exception {
		try {
			int result = sqlSession.delete("TravelPlanCommentMapper.deleteTravelPlanComment", comment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void deleteTravelPlanCommentAll(Comment comment) throws Exception {
		try {
			sqlSession.delete("TravelPlanCommentMapper.deleteTravelPlanCommentAll", comment);
			// 평가수가 0일 수도 있으니 Null 예외 처리는 없다.
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}
	}
	
	@Override
	public void updatePlanCommentMamberOut(Comment comment) throws Exception {
		try {
			sqlSession.update("TravelPlanCommentMapper.updatePlanCommentMamberOut", comment);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("참조관계 해제에 실패했습니다.");
		}
		
	}

}
