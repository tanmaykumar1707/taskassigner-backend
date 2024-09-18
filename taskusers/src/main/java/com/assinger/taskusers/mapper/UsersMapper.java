package com.assinger.taskusers.mapper;

import com.assinger.taskusers.dto.UserDto;
import com.assinger.taskusers.entity.UsersEntity;

public class UsersMapper {


    public static UsersEntity mapToUsersEntity(UsersEntity usersEntity, UserDto userDto){
            usersEntity.setUserId(userDto.getUserId());
            usersEntity.setEmail(userDto.getEmail());
            usersEntity.setName(userDto.getName());
            usersEntity.setMobileNumner(userDto.getMobileNumber());
            usersEntity.setEnabled(userDto.isEnabled());
            usersEntity.setRole(userDto.getRole());
            usersEntity.setPassword(userDto.getPassword());

            return usersEntity;
    }

    public static UserDto mapToUsersDto(UserDto userDto,UsersEntity usersEntity){
        userDto.setUserId(usersEntity.getUserId());
        userDto.setEmail(usersEntity.getEmail());
        userDto.setName(usersEntity.getName());
        userDto.setMobileNumber(usersEntity.getMobileNumner());
        userDto.setEnabled(usersEntity.isEnabled());
        userDto.setRole(usersEntity.getRole());
        userDto.setCreatedAt(usersEntity.getCreatedAt());
        userDto.setUpdatedAt(usersEntity.getUpdatedAt());

        return userDto;
    }

}
