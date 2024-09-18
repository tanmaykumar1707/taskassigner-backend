package com.assinger.taskgateway.filter;

import com.assinger.taskgateway.utils.JwtService;
import com.assinger.taskgateway.utils.RoutePathChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class AuthenticationFilter implements GlobalFilter {

    @Autowired
    private RoutePathChecker routePathChecker;

    @Autowired
    private JwtService jwtService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        String path = exchange.getRequest().getURI().getPath();
       System.out.println("Method ===>"+exchange.getRequest().getMethod()+" Path ==> "+path);
        // Allow open endpoints
        if ( (path.equals("/api/users") || path.equals("/api/users/login")) && exchange.getRequest().getMethod().equals(HttpMethod.POST) ) {
            System.out.println("Returning with checking Method ===>"+exchange.getRequest().getMethod()+" Path ==> "+path);

            return chain.filter(exchange);
        }

        //HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        //doing nothing for register and login
//        if(!routePathChecker.isSecured.test(exchange.getRequest())){
//            return chain.filter(exchange);
//        }
//        if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                throw new RuntimeException("Header is missing");
//        }

            String jwtToken = extractJwtFromRequest(exchange.getRequest().getHeaders());
            if(jwtToken!=null &&  jwtService.validateToken(jwtToken)) {

                exchange.getRequest().mutate()
                        .header("Authorization", jwtToken)
                        .build();
                return chain.filter(exchange);
            }else{
                 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                 return exchange.getResponse().setComplete();
            }


    }


    private String extractJwtFromRequest(HttpHeaders headers) {
        String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }


}
