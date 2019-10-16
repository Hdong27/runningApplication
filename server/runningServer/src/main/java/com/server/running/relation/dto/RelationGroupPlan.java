package com.server.running.relation.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "relationGroupPlan")
public class RelationGroupPlan {
	// 기본키
	@Column
	@Id
	private Integer num;
	
	// 그룹 번호
	@Column
	private Integer groupNum;
	
	// 플랜 번호
	@Column
	private Integer planNum;
}
