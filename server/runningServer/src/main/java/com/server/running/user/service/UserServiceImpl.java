package com.server.running.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.running.user.dto.User;
import com.server.running.user.repository.UserRepository;
import com.server.running.util.SecurityUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	// 회원가입
	@Override
	public boolean signup(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent()) {
			// 중복된 아이디가 있음
			return false;
		} else {
			// 회원가입 성공
			user.setPassword(new SecurityUtil().encodeSHA256(user.getPassword()));
			userRepository.save(user);
			return true;
		}
	}

	// 로그인
	@Override
	public User login(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent() && 
				new SecurityUtil().encodeSHA256(user.getPassword()).equals(maybeUser.get().getPassword())) {
			// 아이디가 있음
			return maybeUser.get();
		} else {
			// 아이디가 없음
			return new User();
		}
	}
	
	// 회원 정보 수정
	@Override
	public User updateUser(User user) {
		userRepository.save(user);
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		return maybeUser.get();
	}
	
	// 회원 탈퇴
	@Override
	public boolean deleteUser(User user) {
		userRepository.delete(user);
		return true;
	}

	// 테스트
	@Override
	public List<User> test() {
		return userRepository.findAll();
	}
}
