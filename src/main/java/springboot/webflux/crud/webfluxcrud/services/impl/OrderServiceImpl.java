package springboot.webflux.crud.webfluxcrud.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springboot.webflux.crud.webfluxcrud.models.Order;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderRequest;
import springboot.webflux.crud.webfluxcrud.models.requests.OrderResponse;
import springboot.webflux.crud.webfluxcrud.repositories.OrderRepository;
import springboot.webflux.crud.webfluxcrud.services.OrderService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Mono<ServerResponse> getAllOrders(){
        // get all orders that have a false delete status
        Flux<Order> orderFlux = orderRepository.findAll().filter(order -> order.getDeleteStatus() == false);

        return orderFlux.hasElements().flatMap(aBoolean -> {
            // return orders if flux is not empty
            if (aBoolean == true){
                return ServerResponse.ok()
                        .body(orderFlux, Order.class);
            }
            // return no orders message because flux is empty
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setMessage("No orders found at the moment");
            Mono<OrderResponse> orderResponseMono = Mono.just(orderResponse);
            return ServerResponse.ok()
                    .body(orderResponseMono, OrderResponse.class);
        });

    }

    public Mono<ServerResponse> getOrder(Long orderId){
        // get order with id and delete status is false
        Mono<Order> orderMono = orderRepository.findById(orderId)
                                    .filter(order -> !order.getDeleteStatus());

        return orderMono.hasElement().flatMap(aBoolean -> {
            // return order if mono is not empty
            if(aBoolean == true){
                return ServerResponse.ok()
                        .body(orderMono, Order.class);
            }
            // return error 404 - no order found
            Mono<ServerResponse> serverResponseNotFoundMono = ServerResponse.notFound().build();
            return serverResponseNotFoundMono;
        });
    }

    public Mono<ServerResponse> addOrder(Order order) {
        Mono<Order> orderMono = orderRepository.save(order);

        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderMono, Order.class);

    }

    public Mono<ServerResponse> updateOrder(Long orderId, OrderRequest orderRequest) {

        return orderRepository.existsById(orderId).flatMap(aBoolean -> {
            // return order if mono is empty
            if (aBoolean == false) {
                // return error 404 - no order found
                Mono<ServerResponse> serverResponseNotFoundMono = ServerResponse.notFound().build();
                return serverResponseNotFoundMono;
            }
            //
            Mono<Order> orderMono = orderRepository.findById(orderId).flatMap(order -> {

                // update the amount of the order
                order.setAmount(orderRequest.getAmount());

                // update transaction date
                order.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));

                // restore a deleted order record
                if (orderRequest.getDeleteStatus() != null
                        && orderRequest.getDeleteStatus() == false) {
                    order.setDeleteStatus(false);
                }

                // update the order in the database
                return orderRepository.save(order);

            });
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(orderMono, Order.class);
        });

    }

    public Mono<ServerResponse> deleteOrder(Long orderId) {
        Mono<Order> orderMono = orderRepository.findById(orderId);

        return orderMono.hasElement().flatMap(aBoolean -> {
            if(aBoolean == false){
                // return error 404 - no order found
                Mono<ServerResponse> serverResponseNotFoundMono = ServerResponse.notFound().build();
                return serverResponseNotFoundMono;
            }
            // delete record
            Mono<Order> orderMono1 = orderMono.flatMap(order -> {
                order.setDeleteStatus(true);
                return orderRepository.save(order);
            });
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(orderMono1, Order.class);

        });

    }

}
