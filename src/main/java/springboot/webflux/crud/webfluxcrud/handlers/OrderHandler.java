package springboot.webflux.crud.webfluxcrud.handlers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import springboot.webflux.crud.webfluxcrud.models.Order;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderRequest;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderResponse;
import springboot.webflux.crud.webfluxcrud.services.OrderService;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class OrderHandler {

    @Autowired
    OrderService orderService;


    public Mono<ServerResponse> getAllOrders(ServerRequest serverRequest){
        return orderService.getAllOrders();
    }
    public Mono<ServerResponse> getOrder(ServerRequest serverRequest){
        Long orderId = Long.valueOf(serverRequest.pathVariable("orderId"));
        return orderService.getOrder(orderId);
    }
    public Mono<ServerResponse> addOrder(ServerRequest serverRequest) {
        Mono<OrderRequest> orderRequestMono = serverRequest.bodyToMono(OrderRequest.class);

        return orderRequestMono.flatMap(orderRequest -> {
            Order order = new Order(orderRequest.getAmount());
            return orderService.addOrder(order);

        });

    }

    public Mono<ServerResponse> updateOrder(ServerRequest serverRequest) {

        Long orderId = Long.valueOf(serverRequest.pathVariable("orderId"));
        Mono<OrderRequest> orderRequestMono = serverRequest.bodyToMono(OrderRequest.class);

        return orderRequestMono.flatMap(orderRequest -> {
            return orderService.updateOrder(orderId, orderRequest);
        });

    }

    public Mono<ServerResponse> deleteOrder(ServerRequest serverRequest){
        Mono<ServerResponse> serverResponseNotFoundMono = ServerResponse.notFound().build();

        Long orderId = Long.valueOf(serverRequest.pathVariable("orderId"));
        return orderService.deleteOrder(orderId);
    }

}
