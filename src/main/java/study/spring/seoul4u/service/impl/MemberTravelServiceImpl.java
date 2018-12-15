package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.service.MemberTravelService;

@Service
public class MemberTravelServiceImpl implements MemberTravelService {
	
	private static Logger logger = LoggerFactory.getLogger(MemberTravelServiceImpl.class);
	
	/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
	// Spring으로 부터 주입받는다.
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<TravelDocument> selectTravelList(TravelDocument travel) throws Exception {
		List<TravelDocument> result = null;

		try {
			result = sqlSession.selectList("MemberTravelMapper.selectMemberTravelList",travel);
			System.out.println("리스트 결과는" + result);
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
	public void updateTravelHit(TravelDocument travel) throws Exception {
		try {
			int result = sqlSession.update("TravelDocumentMapper.updateTravelHit", travel);
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
		} finally {
			// sqlSession.commit();
		}
	}



	@Override
	public int selectTravelCount(TravelDocument travel) throws Exception {
		int result = 0;

		try {
			// 게시물 수가 0건인 경우도 있으므로, 
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("TravelDocumentMapper.selectTravelCount", travel);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}

		return result;
	}


}
