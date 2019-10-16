package com.server.running.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.server.running.user.dto.User;
import com.server.running.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	
	// 회원가입
	@PostMapping("/signup.run")
	public ResponseEntity<Boolean> signup(@RequestBody User user){
		log.debug("회원가입 요청");
		return new ResponseEntity<Boolean>(userService.signup(user), HttpStatus.OK);
	}
	
	// 로그인
	@PostMapping("/login.run")
	public ResponseEntity<User> login(@RequestBody User user){
		log.debug("로그인 요청");
		// 로그인 실패할 경우 빈 User 객체가 넘어간다.
		return new ResponseEntity<User>(userService.login(user), HttpStatus.OK);
	}
	
	// 회원 정보 수정
	@PostMapping("/updateUser.run")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		log.debug("회원정보 수정 요청");
		return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
	}
	
	// 회원 탈퇴
	@PostMapping("/deleteUser.run")
	public ResponseEntity<Boolean> deleteUser(@RequestBody User user) {
		log.debug("회원 탈퇴 요청");
		return new ResponseEntity<Boolean>(userService.deleteUser(user), HttpStatus.OK);
	}
}
