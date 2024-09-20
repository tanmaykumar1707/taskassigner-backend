package com.assinger.taskservice.service.client;

import com.assinger.taskservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "taskusers",fallback = UsersFallback.class)
public interface UserFeignClient {

    @GetMapping(value = "/api/users/{id}",consumes = "application/json")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long id,@RequestHeader("Service-Source") String serviceSource);
}
