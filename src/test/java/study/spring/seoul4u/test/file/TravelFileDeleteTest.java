package study.spring.seoul4u.test.file;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TravelFileDeleteTest {

	@Autowired
	private SqlSession sqlSession; // 객체주입
	
	@Test
	public void testFactory() {
		File file = new File();
		file.setId(10);
		
		
		try {
			sqlSession.delete("TravelFileMapper.deleteFile",file);
		} catch (NullPointerException e) {
			System.out.println("삭제된 데이터가 없습니다.");
			return;
		} catch (Exception e) {
			System.out.println("데이터 삭제에 실패했습니다.");
			return;
		}
		System.out.println("삭제완료");
	}
}
