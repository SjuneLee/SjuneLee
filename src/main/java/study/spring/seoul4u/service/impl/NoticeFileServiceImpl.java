package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.File;
import study.spring.seoul4u.service.NoticeFileService;

//--> import org.springframeworklstereotype.Service;
@Service
public class NoticeFileServiceImpl implements NoticeFileService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<File> selectFileList(File file) throws Exception {
		List<File> result = null;
		
		try {
			result = sqlSession.selectList("NoticeFileMapper.selectFileList", file);
			if(result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public File selectFileItem(File file) throws Exception {
		File result = null;
		
		try {
			result = sqlSession.selectOne("NoticeFileMapper.selectFile", file);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일정보 조회에 실패했습니다.");
		}
		return result;
	}
	
	@Override
	public void insertFile(File file) throws Exception {
		try {
			int result = sqlSession.insert("NoticeFileMapper.insertFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 파일정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일정보 등록에 실패했습니다.");
		}
	}

	@Override
	public void deleteFile(File file) throws Exception {
		try {
			int result = sqlSession.delete("NoticeFileMapper.deleteFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 삭제된 파일정보입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일정보 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateFile(File file) throws Exception {
		try {
			int result = sqlSession.update("NoticeMapper.updateFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("변경된 파일정보가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일정보 수정에 실패했습니다.");
		}
	}

	@Override
	public void deleteFileAll(File file) throws Exception {
		try {
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인 경우 예외를 발생하지 않는다.
			sqlSession.delete("NoticeFileMapper.deleteFileAll", file);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 정보 삭제에 실패했습니다.");
		}
		
	}

}
