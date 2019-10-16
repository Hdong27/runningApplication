package com.server.running.group.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Integer num;
	
	// 그룹명
	@Column
	private String name;
}
