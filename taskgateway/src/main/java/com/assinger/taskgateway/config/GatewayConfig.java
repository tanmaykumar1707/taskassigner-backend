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

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){

      return   routeLocatorBuilder.routes()
              .route("auth",p -> p
                .path("/api/taskassigner/users/**")
                .filters(
                        f -> f
                               .rewritePath("/api/taskassigner/(?<segment>.*)", "/api/${segment}")
                                .addRequestHeader("X-Current-Auditor","automaticDetected")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())

                        ) .uri("lb://TASKUSERS"))
              .route("taskservice",p -> p
                .path("/api/taskassigner/tasks/**")
                .filters(
                        f -> f
                                .rewritePath("/api/taskassigner/(?<segment>.*)", "/api/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        //.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                        //      .setFallbackUri("forward:/contactSupport")

                )
                .uri("lb://TASKSERVICE")).build();
    }


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Replace with your specific frontend origin
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
