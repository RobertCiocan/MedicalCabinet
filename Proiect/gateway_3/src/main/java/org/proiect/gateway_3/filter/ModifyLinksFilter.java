package org.proiect.gateway_3.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ModifyLinksFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            if (exchange.getResponse().getStatusCode() != null && exchange.getResponse().getStatusCode().is2xxSuccessful()) {
                modifyResponseBody(exchange);
            }
        }));
    }

    private void modifyResponseBody(ServerWebExchange exchange) {
        exchange.getResponse().getHeaders().setContentLength(0);
        exchange.getResponse().getHeaders().set("Content-Type", "application/json");

        // TODO: nu merge, nu gasesc informatii pe internet cum sa iau body-ul din response ( doar aici dar nu este clar deloc )
        // https://github.com/spring-cloud/spring-cloud-gateway/blob/main/spring-cloud-gateway-server/src/main/java/org/springframework/cloud/gateway/filter/factory/rewrite/ModifyRequestBodyGatewayFilterFactory.java
//        exchange.getResponse().writeWith(
//                Mono.just(exchange.getResponse().bufferFactory().wrap(modifyLinksInResponseBody(
//                        exchange.getResponse().getBodyAsString().block())))
//        );
    }

    private String modifyLinksInResponseBody(String responseBody) {
        return responseBody
                .replace(":8082/", ":8080/service1/")
                .replace(":8083/", ":8080/service2/");
    }
}
