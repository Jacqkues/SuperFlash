package fr.cytech.superflash.service;

import fr.cytech.superflash.dto.UserDto;
import fr.cytech.superflash.entity.User;

import java.util.List;

public interface UserService {

	void saveUser(UserDto userDto);
	
	User findUserByEmail(String email);
	
	List<UserDto> findAllUsers();

	void updateUser(User user);

	User getAuthUser();

	void increaseScore();

	void decreaseScore();

	void updateNbDeckCree(User user);
}
