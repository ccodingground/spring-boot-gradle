package board.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.JpaFileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService{
	
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	JpaFileUtils jpaFileUtils;

	@Override
	public List<BoardEntity> selectBoardList() {
		
		return jpaBoardRepository.findAllByOrderByBoardNoDesc();
	}

	@Override
	public void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest mr) throws Exception {
		//fileList
		System.out.println("mr isEmpty:"+ObjectUtils.isEmpty(mr));
		if(ObjectUtils.isEmpty(mr)==false) {
			List<BoardFileEntity> fileList=jpaFileUtils.parseFileInfo(mr);
			if(fileList!=null) {
				boardEntity.setFileList(fileList);
			}
		}
		//첨부파일목록을 BoardEntity 클래스에 추가
		//@OneToMany 어노테이션으로 연관관계가 있기 때문에 쿼리를 따로 실행하지 않아도 목록도 자동 저장된다.
		
		//board테이블 데이터입력
		jpaBoardRepository.save(boardEntity);//insert update 두가지 역할을 수행
		
	}

	@Override
	public BoardEntity selectBoardDetail(int boardNo) {
		Optional<BoardEntity> optional= jpaBoardRepository.findById(boardNo);//jpa2.0이상에서
		if(optional.isPresent()) {
			BoardEntity board=optional.get();
			//read카운트증가 처리
			board.setCount(board.getCount()+1);
			jpaBoardRepository.save(board);
			return board;
		}else {
			throw new NullPointerException();
		}
	}

	@Override
	public BoardFileEntity selectBoardFileInformation(int fileNo) {
		BoardFileEntity boardFile=jpaBoardRepository.findBoardFile(fileNo);
		return boardFile;
	}

	@Override
	public void deleteBoard(int boardNo) {
		jpaBoardRepository.deleteById(boardNo);
	}

}
