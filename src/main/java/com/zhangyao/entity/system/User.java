package com.zhangyao.entity.system;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author zhangyao:
 * @date 创建时间：Dec 12, 2018 2:53:57 PM
 */
@Data
@Table(name = "sys_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "password")
	private String password;

	@Column(name = "salt")
	private String salt;

	@Column(name = "email")
	private String email;

	@Column(name = "sex")
	private Integer sex;

	@Column(name = "phonenumber")
	private String phonenumber;

	@Column(name = "birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@Transient 
	private Long roleId;
	
	public String getCredentialsSalt() {
		return userName + salt;
	}

}
