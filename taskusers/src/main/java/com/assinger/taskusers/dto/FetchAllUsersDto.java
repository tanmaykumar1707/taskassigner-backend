package com.assinger.taskusers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class FetchAllUsersDto extends ResponseDto{

    private List<UserDto> users;

}
