package com.zhangyao.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
* @author zhangyao:
* @date 创建时间：Dec 14, 2018 4:01:05 PM
*/
@Data
@Table(name = "sys_role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	@Column(name = "role_id")
	private Long RoleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	/*权限树节点*/
	@Transient  
	private String nodes;
}
