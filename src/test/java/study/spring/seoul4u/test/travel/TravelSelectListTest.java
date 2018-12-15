package study.spring.seoul4u.test.travel;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.FileTravelComment;
import study.spring.seoul4u.model.TravelDocument;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.PlanningService;
import study.spring.seoul4u.service.TravelDocumentService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelSelectListTest {			
	/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
	// Spring으로 부터 주입받는다.
	@Autowired
	SqlSession sqlSession;
	@Autowired
	PlanningService planningService;
	@Test
	public void testFactory() {
		/** 여행지 리스트 서비스 실행 준비 */
		//서비스를 실행하기 위한 객체 생성
		FileTravelComment festival = new FileTravelComment();
		FileTravelComment show = new FileTravelComment();
		FileTravelComment food = new FileTravelComment();
		//각각 카테고리 값을 담는다
		festival.setTravelCategory("festival");
		show.setTravelCategory("show");
		food.setTravelCategory("food");
		
		/** 여행지 리스트 서비스 실행 */
		//리스트 결과를 담을 객체 생성
		List<FileTravelComment> festivalList = null;
		List<FileTravelComment> showList = null;
		List<FileTravelComment> foodList = null;
		
		try {
			festivalList = planningService.listForPlanning(festival);
			showList = planningService.listForPlanning(show);
			foodList = planningService.listForPlanning(food);
			//festivalList = sqlSession.selectList("FileTravelCommentMapper.listForPlanning", festival);
			//showList = sqlSession.selectList("FileTravelCommentMapper.listForPlanning", show);
			//foodList = sqlSession.selectList("FileTravelCommentMapper.listForPlanning", food);
		} catch (Exception e) {
			return;
		}
		System.out.println("출력완료");
		}
	}

