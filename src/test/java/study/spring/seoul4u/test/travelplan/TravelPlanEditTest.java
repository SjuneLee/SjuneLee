package study.spring.seoul4u.test.travelplan;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.TravelPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelPlanEditTest {
	
	/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
	// Spring으로 부터 주입받는다.
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testFactory() {
		
	TravelPlan travelplan = new TravelPlan();
	
	travelplan.setId(3);
	travelplan.setStartDate("21001105");
	travelplan.setTerm(19);
	travelplan.setSubject("수정t입니다");
	travelplan.setSeason(1);
	travelplan.setTheme(1);
	travelplan.setEditDate("20181105");
	travelplan.setIpAddress("ip수정");
	
	
	try {
		sqlSession.update("TravelPlanMapper.updateTravelPlan", travelplan);
	} catch (NullPointerException e) {
		System.out.println("수정된데이터가 없습니다");
		return;
	}	catch (Exception e) {
		System.out.println(e.getLocalizedMessage());
		return;
	}
	System.out.println("수정완료");
	}

}
