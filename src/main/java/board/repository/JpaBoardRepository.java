package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer>{
	//Jpa에서 제공하고있는 CrudRepository 인터페이스 상속 도메인클래스와 id 타입을 파라미터로 받는다.

	List<BoardEntity> findAllByOrderByBoardNoDesc();

	@Query("select file from BoardFileEntity file where file_no= :fileNo")
	BoardFileEntity findBoardFile(@Param("fileNo") int fileNo);
	
}
