package com.assinger.taskservice.service.client;

import com.assinger.taskservice.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UsersFallback implements UserFeignClient{


    @Override
    public ResponseEntity<UserDto> fetchUserById(Long id, String serviceSource) {

        return null;
    }
}
