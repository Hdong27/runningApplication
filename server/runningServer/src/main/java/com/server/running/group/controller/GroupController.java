package com.server.running.group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.server.running.group.dto.Group;
import com.server.running.group.service.GroupService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	// 그룹 생성
	@PostMapping("/createTeam.run")
	public ResponseEntity<String> createTeam(@RequestBody Group group) {
		log.debug("그룹 생성 요청");
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	// 그룹 수정
	
	// 그룹 삭제
	
	// 그룹에 참가
	
	// 그룹 탈퇴
	
}
