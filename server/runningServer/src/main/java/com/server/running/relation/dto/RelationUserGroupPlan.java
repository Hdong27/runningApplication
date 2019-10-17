package com.server.running.relation.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "relationUserGroupPlan")
public class RelationUserGroupPlan {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 유저 번호
	@Column
	private Integer userNum;
	
	// 플랜 번호
	@Column
	private Integer planNum;
	
	// 진행 거리
	@Column
	private Double dir;
}
