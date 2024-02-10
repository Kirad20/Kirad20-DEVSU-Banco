package com.devsu.microservices.gatewayservice.configuracion;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("clientes_route", r -> r.path("/clientes/**")
                        .filters(f -> f.rewritePath("/clientes/(?<segment>.*)", "/clientes/${segment}"))
                        .uri("http://cliente-microservice:8001"))
                .route("cuentas_route", r -> r.path("/cuentas/**")
                        .filters(f -> f.rewritePath("/cuentas/(?<segment>.*)", "/cuentas/${segment}"))
                        .uri("http://cuentas-microservice:8002"))
                .route("movimientos_route", r -> r.path("/movimientos/**")
                        .filters(f -> f.rewritePath("/movimientos/(?<segment>.*)", "/movimientos/${segment}"))
                        .uri("http://cuentas-microservice:8002"))
                .route("reportes_route", r -> r.path("/reportes/**")
                        .filters(f -> f.rewritePath("/reportes/(?<segment>.*)", "/reportes/${segment}"))
                        .uri("http://cuentas-microservice:8002"))
                .build();
    }
}
