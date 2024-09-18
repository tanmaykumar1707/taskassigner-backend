package com.assinger.taskgateway.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RoutePathChecker {

    public static final List<String> openEndPoints = List.of(
            "/taskusers/api/user",
            "/eureka","/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openEndPoints.stream().noneMatch(uri-> request.getURI().getPath().contains(uri));


}
