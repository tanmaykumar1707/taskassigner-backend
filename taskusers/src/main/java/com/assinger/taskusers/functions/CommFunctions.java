package com.assinger.taskusers.functions;


import com.assinger.taskusers.dto.UserMqDto;
import com.assinger.taskusers.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CommFunctions {


    @Bean
    public Consumer<UserMqDto> updateCommunication(IUsersService usersService){
        return userMqDto -> {
            log.info("message recv that communication sent for the user "+userMqDto);
        };
    }
}
