package com.server.running.user.service;

import java.util.List;

import com.server.running.user.dto.User;

public interface UserService {
	// 회원가입
	public boolean signup(User user);
	
	// 로그인
	public User login(User user);
	
	// 회원 정보 수정
	public User updateUser(User user);
	
	// 회원 탈퇴
	public boolean deleteUser(User user);
	
	// 테스트
	public List<User> test();
	
}
