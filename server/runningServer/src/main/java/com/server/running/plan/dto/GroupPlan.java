package com.server.running.plan.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "groupPlan")
public class GroupPlan {
	// 기본키
	@Column
	@Id
	private Integer num;
	
	// 플랜명
	@Column
	private String name;
	
	// 목표 거리
	@Column
	private Double dir;
}
