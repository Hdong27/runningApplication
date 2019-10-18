package com.server.running.group.service;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.UserGroup;
import com.server.running.user.dto.User;

public interface GroupService {
	// 그룹 생성
	public Boolean createTeam(Group group, User user);
	
	// 그룹 수정
	public Boolean updateTeam(Group group);
	
	// 그룹 삭제
	public Boolean deleteTeam(Group group);
	
	// 그룹에 참가
	public Boolean joinTeam(Group group);
	
	// 그룹 탈퇴
	public Boolean outTeam(Group group);
	
	// 테스트
	public UserGroup test(UserGroup userGroup);
}
