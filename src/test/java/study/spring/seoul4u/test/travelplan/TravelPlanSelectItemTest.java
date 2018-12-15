package study.spring.seoul4u.test.travelplan;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.TravelPlan;
import study.spring.seoul4u.service.TravelPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelPlanSelectItemTest {
	/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
	// Spring으로 부터 주입받는다.
	@Autowired
	SqlSession sqlSession;
	@Autowired
	TravelPlanService travelPlanService;
	@Test
	public void testFactory() {
		
	TravelPlan travelplan = new TravelPlan();

	travelplan.setId(19);
	TravelPlan result = null;
	
	
	try {
		result = travelPlanService.selectTravelPlan(travelplan);
		//result = sqlSession.selectOne("TravelPlanMapper.selectTravelPlan", travelplan);
		
		if (result == null) {
			throw new NullPointerException();
		}
	} catch (Exception e) {
		System.out.println(e.getLocalizedMessage());
		return;
	}
	System.out.println("조회완료");
	}

}
