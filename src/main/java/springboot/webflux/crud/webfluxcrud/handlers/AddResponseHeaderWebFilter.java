package springboot.webflux.crud.webfluxcrud.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AddResponseHeaderWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String sourceSystem = exchange.getRequest().getHeaders().getFirst("x-source-system");
        String traceId = exchange.getRequest().getHeaders().getFirst("x-trace-id");

        exchange.getResponse().getHeaders().add("x-source-system", sourceSystem);
        exchange.getResponse().getHeaders().add("x-trace-id", traceId);

        return chain.filter(exchange);
    }
}
