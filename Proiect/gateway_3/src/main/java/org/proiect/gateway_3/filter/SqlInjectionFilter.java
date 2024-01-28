package org.proiect.gateway_3.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Component
public class SqlInjectionFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            HttpMethod requestMethod = exchange.getRequest().getMethod();

            if (requestMethod == HttpMethod.GET || requestMethod == HttpMethod.DELETE) {
                String queryString = exchange.getRequest().getURI().getQuery();
                if (containsSqlInjection(queryString)) {
                    System.out.println("SQL INJECTION DETECTED");
                    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST));
                }
            }

            if (requestMethod == HttpMethod.POST || requestMethod == HttpMethod.PUT) {
                ServerHttpRequest request = exchange.getRequest().mutate().build();
                return request.getBody()
                        .publishOn(Schedulers.boundedElastic())
                        .map(body -> {
                            try {
                                if (containsSqlInjection(new String(body.asInputStream().readAllBytes()))) {
                                    System.out.println("SQL INJECTION DETECTED");
                                    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST));
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return exchange;
                        })
                        .defaultIfEmpty(exchange).then();
            }

            return chain.filter(exchange);
        };
    }

    private boolean containsSqlInjection(String input) {
        return !StringUtils.isEmpty(input) && (input.toLowerCase().contains("select")
                || input.toLowerCase().contains("delete")
                || input.toLowerCase().contains("update")
                || input.toLowerCase().contains("drop")
        );
    }
}

