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
public class NoticeFileInsertTest {

	@Autowired
	// 객체주입-> 선언만 해 놓으면 할당은 자동으로 수행된다.
	SqlSession sqlSession;
	@Test
	public void testFactory() {
		
		File file = new File();
		// 몇 번 게시물에 속한 파일인지 지정한다.
		file.setCustomerCenterId(4);
		
		// 데이터 복사
		file.setOriginName("파일");
		file.setFileDir("C://file/file");
		file.setFileName("file");
		file.setContentType("jpg");
		file.setFileSize(123456);
		try {
			sqlSession.insert("NoticeFileMapper.insertFile",file);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("입력완료");
	}
}
