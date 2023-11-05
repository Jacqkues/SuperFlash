package fr.cytech.superflash.service.impl;

import fr.cytech.superflash.dto.UserDto;
import fr.cytech.superflash.entity.Score;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.repository.UserRepository;
import fr.cytech.superflash.repository.ScoreRepository;
import fr.cytech.superflash.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ScoreRepository scoreRepository;

	@Override
	public void saveUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		userRepository.save(user);


		Score score = new Score();
		score.setScore(0);
		score.setUser(user);
		score.setNbDeckCree(0);
		user.setScore(score);
		
		scoreRepository.save(score);
		userRepository.save(user);
		
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map((user) -> mapToUserDto(user))
				.collect(Collectors.toList());

	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User getAuthUser() {
		String email = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			email = userDetails.getUsername();
		}
		return userRepository.findByEmail(email);
	}


	@Override
	public void increaseScore() {
		User user = getAuthUser();
		user.getScore().setScore(user.getScore().getScore() + 15);
		userRepository.save(user);
	}

	@Override
	public void decreaseScore() {
		User user = getAuthUser();
		user.getScore().setScore(user.getScore().getScore() - 5);
		userRepository.save(user);
	}

	@Override
	public void updateNbDeckCree(User user) {
		user.getScore().setNbDeckCree(user.getScore().getNbDeckCree() + 1);
		userRepository.save(user);
	}

	private UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		String[] str = user.getName().split(" ");
		userDto.setFirstName(str[0]);
		userDto.setLastName(str[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}

}