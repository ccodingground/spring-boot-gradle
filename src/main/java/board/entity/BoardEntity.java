package board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity//Jpa엔티티 : 엔티티 클래스는 테이블과 매핑
@Table(name = "t_jpa_board")
//@SequenceGenerator(
//	name="BOARD_SEQ_GENERATOR",
//	sequenceName="BOARD_SEQ",
//	initialValue=1,allocationSize=1
//)
@Data
public class BoardEntity {
	
	@Id//pk임
	@GeneratedValue(strategy = GenerationType.AUTO)//mysql의 자동증가
	//@GeneratedValue(
	//		strategy = GenerationType.SEQUENCE,
	//		generator = "BOARD_SEQ_GENERATOR"
	//)
	//@Column(name = "board_no")
	private int boardNo;//
	
	@Column(nullable = false)//not null
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private int count;
	
	@Column(nullable = false)
	private String writer;
	
	@Column(nullable = false)
	private LocalDateTime reg_date=LocalDateTime.now();
	
	private LocalDateTime edit_date;
	
	//1:N관계를 표현하는 JPA어너테이션
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "board_no")
	private List<BoardFileEntity> fileList;
}
