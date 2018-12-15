package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.FileTravelComment;
import study.spring.seoul4u.model.PlanTravelFileJoinByDetail;
import study.spring.seoul4u.service.PlanningService;

@Service
public class PlanningServiceImpl implements PlanningService {
	
	private static Logger logger = LoggerFactory.getLogger(PlanningServiceImpl.class);
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<FileTravelComment> listForPlanning(FileTravelComment fileTravelComment) throws Exception {
		List<FileTravelComment> result = null;
		
		try {
			result = sqlSession.selectList("FileTravelCommentMapper.listForPlanning", fileTravelComment);
			System.out.println("리스트 결과" + result);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 여행지 리스트가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("여행지 리스트 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public List<PlanTravelFileJoinByDetail> listForPlanInfo(PlanTravelFileJoinByDetail joinResult) throws Exception {
		List<PlanTravelFileJoinByDetail> result = null;
		
		try {
			result = sqlSession.selectList("PlanTravelDetailFileJoinMapper.listForPlanInfo", joinResult);
		} catch (NullPointerException e) {
			throw new Exception("조회된 상세 여행플랜이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("상세 여행플랜 조회에 실패했습니다.");
		}
		return result;
	}

}
