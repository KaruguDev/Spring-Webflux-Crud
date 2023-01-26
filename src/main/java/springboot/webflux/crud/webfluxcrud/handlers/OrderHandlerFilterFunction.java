package springboot.webflux.crud.webfluxcrud.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderResponse;

public class OrderHandlerFilterFunction
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest, HandlerFunction<ServerResponse> handlerFunction) {
        String sourceSystem = serverRequest.headers().firstHeader("x-source-system");
        String traceId = serverRequest.headers().firstHeader("x-trace-id");

        OrderResponse orderResponse = new OrderResponse();

        if (sourceSystem == null || traceId == null) {

            orderResponse.setMessage(""+(sourceSystem == null ? "x-source-system" : "x-trace-id") + " header is missing");
            return ServerResponse
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Mono.just(orderResponse), OrderResponse.class);

        }

        return handlerFunction.handle(serverRequest);
    }
}