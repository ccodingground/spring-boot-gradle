package board.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_jpa_file")
//@SequenceGenerator(
//		name="FILE_SEQ_GENERATOR",
//		sequenceName="FILE_SEQ",
//		initialValue=1,allocationSize=1
//	)
@Data
public class BoardFileEntity {

	@Id
	//@GeneratedValue(
	//		strategy = GenerationType.SEQUENCE,
	//		generator = "FILE_SEQ_GENERATOR"
	//)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fileNo;
	
	@Column(nullable = false)
	private String file_name;
	
	@Column(nullable = false)
	private String file_path;
	
	@Column(nullable = false)
	private long file_size;
	
	@Column(nullable = false)
	private LocalDateTime reg_date=LocalDateTime.now();
	
	private LocalDateTime edit_date;
	
	@Column(nullable = false)
	private char delete_yn='N';
}
