package study.spring.seoul4u.test.travelplan;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.PlanTravelFileJoinByDetail;
import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.PlanningService;
import study.spring.seoul4u.service.TravelPlanService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelPlanSelectListTest {			
			/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
			// Spring으로 부터 주입받는다.
			@Autowired
			SqlSession sqlSession;
			@Autowired
			TravelPlanService travelPlanService;
			@Autowired
			PlanningService planningService;
			@Test
			public void testFactory() {

			List<PlanTravelFileJoinByDetail> list =null;
			PlanTravelFileJoinByDetail joinResult = new PlanTravelFileJoinByDetail();
			joinResult.setMemberId(3);
			
			try {
				list = planningService.listForPlanInfo(joinResult);
				//list = sqlSession.selectList("PlanTravelDetailFileJoinMapper.listForPlanInfo", joinResult);
				//list = travelPlanService.selectTravelPlanList(travelPlan);
				//list = sqlSession.selectList("TravelPlanMapper.selectTravelPlanList", travelPlan);
				
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				return;
			}
			System.out.println(list+"출력완료");
			}
	}

