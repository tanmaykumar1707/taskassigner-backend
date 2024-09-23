package com.assinger.taskusers.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "users")
@Getter @Setter
public class DetailsInfo {

    private String developer;
    private Map<String,String> contacts;

}
