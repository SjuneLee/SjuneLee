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
public class TravelPlanInsertTest {


			
		/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
		// Spring으로 부터 주입받는다.
		@Autowired
		SqlSession sqlSession;
		
		@Test
		public void testFactory() {
			
		TravelPlan travelplan = new TravelPlan();

		travelplan.setStartDate("20181105");
		travelplan.setTerm(4);
		travelplan.setSubject("test3입니다");
		travelplan.setSeason(1);
		travelplan.setTheme(1);
		travelplan.setIpAddress("ip주소");
		travelplan.setMemberId(1);
		
		try {
			sqlSession.insert("TravelPlanMapper.insertTravelPlan", travelplan);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("입력완료");
		}
}