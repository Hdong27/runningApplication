package com.server.running.group.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.group.dto.Group;
import com.server.running.group.repository.GroupRepository;
import com.server.running.relation.dto.RelationUserGroup;
import com.server.running.relation.repository.RelationUserGroupRepository;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private RelationUserGroupRepository relationUserGroupRepository;
	
	// 그룹 생성
	@Override
	public Boolean createTeam(Group group) {
		return true;
	}
	
	// 그룹 수정
	@Override
	public Boolean updateTeam(Group group) {
		return null;
	}
	
	// 그룹 삭제
	@Override
	public Boolean deleteTeam(Group group) {
		return null;
	}
	
	// 그룹에 참가
	@Override
	public Boolean joinTeam(Group group) {
		return null;
	}
	
	// 그룹 탈퇴
	@Override
	public Boolean outTeam(Group group) {
		return null;
	}

}
