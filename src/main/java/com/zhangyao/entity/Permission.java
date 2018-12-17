package com.zhangyao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 2:59:16 PM
 */
@Data
@Table(name = "sys_permission")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	@Column(name = "id")
	private Long id;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "res_name")
	private String resName;

	@Column(name = "res_type")
	private String resType;

	@Column(name = "permission")
	private String permission;

	@Column(name = "url")
	private String url;
	
	//子菜单
	private List<Permission> children = new ArrayList<>();

}
