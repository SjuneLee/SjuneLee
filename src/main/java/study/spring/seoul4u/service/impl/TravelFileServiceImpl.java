package study.spring.seoul4u.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.seoul4u.model.TravelFile;
import study.spring.seoul4u.service.TravelFileService;



//--> import org.springframework.stereotype.Service; 
@Service
public class TravelFileServiceImpl implements TravelFileService {
	
	/** 처리 결과를 기록할 Log4J 객체 생성 */
	// --> import org.slf4j.Logger;
	// --> import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger(TravelFileServiceImpl.class);

	/** MyBatis */
	// --> import org.springframework.beans.factory.annotation.Autowired;
	// --> import org.apache.ibatis.session.SqlSession
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void insertFile(TravelFile file) throws Exception {
		try {
			int result = sqlSession.insert("TravelFileMapper.insertFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("저장된 파일정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일정보 등록에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}

	@Override
	public List<TravelFile> selectFileList(TravelFile file) throws Exception {
		List<TravelFile> result = null;
		
		try {
			// 첨부파일이 없는 경우도 있으므로, 조회결과가 null인 경우 예외를 발생하지 않는다.
			result = sqlSession.selectList("TravelFileMapper.selectFileList", file);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일 정보 조회에 실패했습니다.");
		}
		
		return result;
	}
	
	@Override
	public void deleteFileAll(TravelFile file) throws Exception {
		try {
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인 경우 예외를 발생하지 않는다.
			sqlSession.delete("TravelFileMapper.deleteFileAll", file);
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 정보 삭제에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}

	@Override
	public TravelFile selectFile(TravelFile file) throws Exception {
		TravelFile result = null;
		
		try {
			result = sqlSession.selectOne("TravelFileMapper.selectFile", file);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("존재하지 않는 파일에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("파일 정보 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public void deleteFile(TravelFile file) throws Exception {
		try {
			int result = sqlSession.delete("TravelFileMapper.deleteFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// sqlSession.rollback();
			throw new Exception("삭제된 파일 정보가 없습니다.");
		} catch (Exception e) {
			// sqlSession.rollback();
			logger.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 정보 삭제에 실패했습니다.");
		} finally {
			// sqlSession.commit();
		}
	}
}
