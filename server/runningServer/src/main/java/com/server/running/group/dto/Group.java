package com.server.running.group.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.server.running.plan.dto.GroupPlan;
import com.server.running.user.dto.User;

import lombok.Data;

@Data
@Entity
@Table(name="team")
public class Group {
	// 기본키
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gid;
	
	// 그룹명
	@Column
	private String name;
	
	// 그룹의 플랜 리스트
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="groupId")
	private List<GroupPlan> groupPlans;
	
	// 그룹의 유저 리스트
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "userGroup", 
				joinColumns = @JoinColumn(name = "gid"),
				inverseJoinColumns = @JoinColumn(name = "uid"))
	private List<User> users;
	
	// 유저 추가(relationship)
	public boolean addUsers(User user) {
		if(users == null) {
			users = new ArrayList<>();
		}
		return users.add(user);
	}
}
