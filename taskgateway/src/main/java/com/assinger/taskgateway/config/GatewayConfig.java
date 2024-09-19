package com.assinger.taskgateway.config;

import com.assinger.taskgateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;


    //tracking the circuit breakser  actuator endpoint http://localhost:8003/actuator/circuitbreakerevents/taskUserCircuitBreaker
    //http://localhost:8003/actuator/circuitbreakers
    // three states of circuite breaker
    //CLOSED   , OPEN , HALF_OPEN - When service is down , state will go closed to open and accoding config it will half open and then open or close again



    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){

      return   routeLocatorBuilder.routes()
              .route("auth",p -> p
                .path("/api/taskassigner/users/**")
                .filters(
                        f -> f
                               .rewritePath("/api/taskassigner/(?<segment>.*)", "/api/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config->config.setName("taskUserCircuitBreaker")
                                                .setFallbackUri("forward:/fallback").addStatusCode("INTERNAL_SERVER_ERROR")
                                        )

                        ) .uri("lb://TASKUSERS"))
              .route("taskservice",p -> p
                .path("/api/taskassigner/tasks/**")
                .filters(
                        f -> f
                                .rewritePath("/api/taskassigner/(?<segment>.*)", "/api/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config->config.setName("taskServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback").addStatusCode("INTERNAL_SERVER_ERROR")
                                )

                )
                .uri("lb://TASKSERVICE")).build();
    }


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000"); //Need to Replace with your specific frontend origin
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true); // Optional: Allow credentials (cookies, authorization headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

   // @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                HttpHeaderss requestHeaders = exchange.getRequest().getHeaders();
//                String correlationId = filterUtility.getCorrelationId(requestHeaders);
//
//                if(!(exchange.getResponse().getHeaders().containsKey(filterUtility.CORRELATION_ID))) {
//                    logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
//                    exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
//                }

               // exchange.getResponse().getHeaders().add("x-response-time", LocalDateTime.now().toString());


            }));
        };
    }

}
