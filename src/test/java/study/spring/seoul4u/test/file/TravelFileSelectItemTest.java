package study.spring.seoul4u.test.file;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class TravelFileSelectItemTest {

	@Autowired
	// 객체주입-> 선언만 해 놓으면 할당은 자동으로 수행된다.
	SqlSession sqlSession;
	
	@Test
	public void testFactory() {
		File file = new File();
		file.setId(13);
		File result = null;
		
		try {
			result = sqlSession.selectOne("TravelFileMapper.selectFile", file);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			System.out.println("조회된 데이터가 없습니다.");
			return;
		} catch (Exception e) {
			System.out.println("데이터 조회에 실패했습니다.");
			return;
		}
		System.out.println(result.toString());
	}
}
