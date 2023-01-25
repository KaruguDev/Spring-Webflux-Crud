package springboot.webflux.crud.webfluxcrud.services;


import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springboot.webflux.crud.webfluxcrud.models.Order;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderRequest;

public interface OrderService {
    public Mono<ServerResponse> getAllOrders();
    public Mono<ServerResponse> getOrder(Long orderId);
    public Mono<ServerResponse> addOrder(Order order);
    public Mono<ServerResponse> updateOrder(Long orderId, OrderRequest orderRequest);
    public Mono<ServerResponse> deleteOrder(Long orderId);
}
