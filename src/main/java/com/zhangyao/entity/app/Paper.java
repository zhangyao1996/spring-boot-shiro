package com.zhangyao.entity.app;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Table(name = "sys_paper")
public class Paper {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "type_id")
	private Integer typeId;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "upload_time")
	private Date uploadTime;

	@Column(name = "status")
	private Integer status;

	@Column(name = "content")
	private String content;
	
	@Transient
	private PaperType paperType;
}
