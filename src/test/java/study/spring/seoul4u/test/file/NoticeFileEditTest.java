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
public class NoticeFileEditTest {

	@Autowired
	private SqlSession sqlSession; // 객체주입
	
	@Test
	public void testFactory() {
		File file = new File();
		file.setId(3);//3번 파일을 수정
		file.setOriginName("파일수정");
		file.setFileDir("C://fileedit/fileedit");
		file.setFileName("fileedit");
		file.setContentType("edit");
		file.setFileSize(1234);
		file.setCustomerCenterId(4);//임의값
		try {
			sqlSession.update("NoticeFileMapper.updateFile",file);
		} catch (NullPointerException e) {
			System.out.println("수정된 데이터가 없습니다.");
			return;
		} catch (Exception e) {
			System.out.println("데이터 수정에 실패했습니다.");
			return;
		}
		System.out.println("수정완료");
	}
}
