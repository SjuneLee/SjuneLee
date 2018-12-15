package study.spring.seoul4u.test.detailplan;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.DetailPlan;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class DetailPlanSelectListTest {
	
	/**MyBatis 의 Mapper를 호출하기 위한 sqlSESSION 객체 */
	// Spring으로 부터 주입받는다.
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testFactory() {

	List<DetailPlan> list =null;
	
	
	try {
		list = sqlSession.selectList("DetailPlanMapper.selectDetailPlanList", null);
		
	} catch (Exception e) {
		System.out.println(e.getLocalizedMessage());
		return;
	}
	System.out.println("출력완료");
	}

}
