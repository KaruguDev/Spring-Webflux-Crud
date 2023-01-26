package springboot.webflux.crud.webfluxcrud.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springboot.webflux.crud.webfluxcrud.models.Order;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderRequest;
import springboot.webflux.crud.webfluxcrud.services.OrderService;

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
