package com.server.running.group.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.UserGroup;
import com.server.running.group.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	// 그룹 생성
	@Override
	public Boolean createTeam(UserGroup userGroup) {
		userGroup.getGroup().addUsers(userGroup.getUser());
		groupRepository.save(userGroup.getGroup());
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
	public Boolean joinTeam(UserGroup userGroup) {
		Optional<Group> maybeGroup = groupRepository.findById(userGroup.getGroup().getGid());
		maybeGroup.get().addUsers(userGroup.getUser());
		groupRepository.save(maybeGroup.get());
		return true;
	}
	
	// 그룹 탈퇴
	@Override
	public Boolean outTeam(UserGroup userGroup) {
		Optional<Group> maybeGroup = groupRepository.findById(userGroup.getGroup().getGid());
		int size = maybeGroup.get().deleteUsers(userGroup.getUser());
		if(size > 0) {
			groupRepository.save(maybeGroup.get());
		} else {
			groupRepository.delete(maybeGroup.get());
		}
		return true;
	}

	@Override
	public List<Group> findAllTeam() {
		List<Group> groups = groupRepository.findAll();
		for (Group group : groups) {
			group.setRunnerSum(group.getUsers().size());
		}
		return groups;
	}
}
