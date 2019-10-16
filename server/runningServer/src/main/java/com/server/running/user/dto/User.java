package com.server.running.user.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	// 기본키
	@Column
	@Id
	private Integer num;
	
	// 아이디
	@Column
	private String id;
	
	// 비밀번호
	@Column
	private String password;
	
}
