package com.zhangyao.entity.app;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="sys_paper_type")
public class PaperType {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="type_name")
	private String typeName;
}
