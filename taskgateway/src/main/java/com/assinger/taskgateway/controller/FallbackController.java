package com.assinger.taskgateway.controller;

import com.assinger.taskgateway.Dto.FallbackDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<ResponseEntity<FallbackDto>>  fallBackHttpMethod(){
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(

                new FallbackDto("500","Service is Down, Please try again later")));
    }
}
