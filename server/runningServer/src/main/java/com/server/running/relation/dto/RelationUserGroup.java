package com.server.running.relation.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "relationUserGroup")
public class RelationUserGroup {
	// 기본키
	@Column
	@Id
	private Integer num;
	
	// 유저 번호
	@Column
	private Integer userNum;
	
	// 그룹 번호
	@Column
	private Integer groupNum;
}
