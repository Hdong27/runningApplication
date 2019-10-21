package com.server.running.group.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.server.running.group.dto.Group;
import com.server.running.group.dto.UserGroup;
import com.server.running.group.service.GroupService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	// 그룹 생성
	@PostMapping("/createTeam.run")
	public ResponseEntity<Boolean> createTeam(@RequestBody UserGroup userGroup) {
		log.debug("그룹 생성 요청");
		return new ResponseEntity<Boolean>(groupService.createTeam(userGroup), HttpStatus.OK);
	}
	
	// 그룹 수정
	
	// 그룹 삭제
	
	// 그룹에 참가
	
	// 그룹 탈퇴
	
	// 테스트
	@GetMapping("/test.run")
	public ResponseEntity<List<Group>> test(HttpServletRequest request) {
		log.debug("테스트");
		return new ResponseEntity<List<Group>>(groupService.test(), HttpStatus.OK);
	}
}
