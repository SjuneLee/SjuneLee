package study.spring.seoul4u.test.detailplan;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.DetailPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class DetailPlanDeleteTest {
	
		/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
		// Spring으로 부터 주입받는다.
		@Autowired
		SqlSession sqlSession;
		
		@Test
		public void testFactory() {
			
		DetailPlan detailplan = new DetailPlan();
		detailplan.setId(1);

			
		try {
			sqlSession.delete("DetailPlanMapper.deleteDetailPlan", detailplan);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("삭제완료");
		}
}
