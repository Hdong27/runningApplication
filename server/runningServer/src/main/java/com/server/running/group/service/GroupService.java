package com.server.running.group.service;

import java.util.List;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.UserGroup;

public interface GroupService {
	// 그룹 생성
	public Boolean createTeam(UserGroup userGroup);
	
	// 그룹 수정
	public Boolean updateTeam(Group group);
	
	// 그룹 삭제
	public Boolean deleteTeam(Group group);
	
	// 그룹에 참가
	public Boolean joinTeam(Group group);
	
	// 그룹 탈퇴
	public Boolean outTeam(Group group);
	
	// 테스트
	public List<Group> test();
}
