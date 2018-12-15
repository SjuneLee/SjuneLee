package study.spring.seoul4u.test.customercenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import study.spring.seoul4u.model.Notice;
import study.spring.seoul4u.service.NoticeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class NoticeInsertTest {

	@Autowired
	// 객체주입-> 선언만 해 놓으면 할당은 자동으로 수행된다.
	NoticeService noticeService;
	@Test
	public void testFactory() {
		
		Notice notice = new Notice();
		notice.setSubject("test");
		notice.setContent("test");
		notice.setIpAddress("test");
		notice.setMemberId(1);
		try {
			noticeService.insertNotice(notice);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		System.out.println("입력완료");
	}
}
