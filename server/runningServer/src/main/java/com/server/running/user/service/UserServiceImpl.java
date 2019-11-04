package com.server.running.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.running.user.dto.Friend;
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
			user = new User();
			user.setUid(0);
			return user;
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

	// 러닝 데이터 조회
	@Override
	public User findRunning(User user) {
		return userRepository.findById(user.getUid()).get();
	}

	// 아이디 중복체크 요청
	@Override
	public boolean overlap(User user) {
		Optional<User> maybeUser = userRepository.findByEmail(user.getEmail());
		if(maybeUser.isPresent()) {
			return false;
		} else {
			return true;
		}
	}

	// 친구 추가
	@Override
	public boolean meet(Friend friend) {
		Optional<User> user1 = userRepository.findById(friend.getLid());
		Optional<User> user2 = userRepository.findById(friend.getRid());
		user1.get().addFriends(user2.get());
		user2.get().addFriends(user1.get());
		userRepository.save(user1.get());
		userRepository.save(user2.get());
		return true;
	}

	// 친구 제거
	@Override
	public boolean meetOut(Friend friend) {
		Optional<User> user1 = userRepository.findById(friend.getLid());
		Optional<User> user2 = userRepository.findById(friend.getRid());
		for (int i = 0; i < user1.get().getFriends().size(); i++) {
			if(user2.get().getUid() == user1.get().getFriends().get(i).getUid()) {
				user1.get().getFriends().remove(i);
				break;
			}
		}
		for (int i = 0; i < user2.get().getFriends().size(); i++) {
			if(user1.get().getUid() == user2.get().getFriends().get(i).getUid()) {
				user2.get().getFriends().remove(i);
				break;
			}
		}
		userRepository.save(user1.get());
		userRepository.save(user2.get());
		return true;
	}
}
