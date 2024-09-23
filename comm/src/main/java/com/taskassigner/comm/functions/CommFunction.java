package com.taskassigner.comm.functions;


import com.taskassigner.comm.dto.UserMqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration

public class CommFunction {

    private static final Logger logger = LoggerFactory.getLogger(CommFunction.class);

    @Bean
    public Function<UserMqDto, UserMqDto> email(){

        return userDto -> {
            logger.info("email sent triggered from rabbit mq event "+userDto);
            return  userDto;
        };

    }


}
