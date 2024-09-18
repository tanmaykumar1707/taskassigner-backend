package com.assinger.taskusers.service;

import com.assinger.taskusers.dto.UserDto;

import java.util.List;

public interface IUsersService {

    void createUser(UserDto users);

    List<UserDto> allUsers();

    void deleteUser(Long id) ;

    UserDto findUserById(Long id) ;

    UserDto findUserByEmail(String email);

    UserDto updateUser(Long userId,UserDto userDto) ;

    void updateUserStatus(Long userId);

    List<UserDto> enabledUsersList();
}
