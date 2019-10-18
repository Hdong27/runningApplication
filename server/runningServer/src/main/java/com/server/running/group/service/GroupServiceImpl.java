package com.server.running.group.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.UserGroup;
import com.server.running.group.repository.GroupRepository;
import com.server.running.user.dto.User;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	// 그룹 생성
	@Override
	public Boolean createTeam(Group group, User user) {
		groupRepository.save(group);
		group.addUsers(user);
		user.addGroups(group);
		return true;
	}
	
	// 그룹 수정
	@Override
	public Boolean updateTeam(Group group) {
		groupRepository.save(group);
		return true;
	}
	
	// 그룹 삭제
	@Override
	public Boolean deleteTeam(Group group) {
		groupRepository.delete(group);
		return true;
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

	// 테스트
	@Override
	public UserGroup test(UserGroup userGroup) {
		userGroup.getGroup().addUsers(userGroup.getUser());
		userGroup.getUser().addGroups(userGroup.getGroup());
		groupRepository.save(userGroup.getGroup());
		return userGroup;
	}
}
