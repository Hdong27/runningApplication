package com.server.running.group.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="team")
public class Group {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 그룹명
	@Column
	private String name;
	
	// 그룹을 생성하거나 참가하려할 떄 필요한 유저번호
	private Integer userId;
}
