package study.spring.seoul4u.service;

import java.util.List;

import study.spring.seoul4u.model.File;

/**
 * customer_center(공지사항, 질문답변), travel 테이블과 연계된 파일 정보
 * @author sjune
 *
 */
public interface NoticeFileService {
	/**
	 * file 리스트를 불러온다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<File> selectFileList(File file) throws Exception;
	/**
	 * file 하나를 불러온다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public File selectFileItem(File file) throws Exception;
	/**
	 * file 정보를 저장한다.
	 * @param file
	 * @throws Exception
	 */
	public void insertFile(File file) throws Exception;
	/**
	 * file 정보를 삭제한다.
	 * @param file
	 * @throws Exception
	 */
	public void deleteFile(File file) throws Exception;
	/**
	 * file 정보를 삭제한다.(게시물과 관련된 모든 파일들)
	 * @param fileList
	 * @throws Exception
	 */
	public void deleteFileAll(File file) throws Exception;
	/**
	 * file 정보를 수정한다.
	 * @param file
	 * @throws Exception
	 */
	public void updateFile(File file) throws Exception;
	}
