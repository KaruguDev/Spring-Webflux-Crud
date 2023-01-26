package springboot.webflux.crud.webfluxcrud.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import springboot.webflux.crud.webfluxcrud.handlers.OrderHandler;
import springboot.webflux.crud.webfluxcrud.handlers.OrderHandlerFilterFunction;

@Configuration
public class OrderRouter {

    @Autowired
    OrderHandler orderHandler;

    @Bean
    public RouterFunction<ServerResponse> orderRoutes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/api/orders/get-all"),
                        orderHandler::getAllOrders)
                .andRoute(RequestPredicates.GET("/api/orders/{orderId}"),
                        orderHandler::getOrder)
                .andRoute(RequestPredicates.POST("/api/orders"),
                        orderHandler::addOrder)
                .andRoute(RequestPredicates.PUT("/api/orders/{orderId}"),
                        orderHandler::updateOrder)
                .andRoute(RequestPredicates.DELETE("/api/orders/{orderId}"),
                        orderHandler::deleteOrder)
                .filter(new OrderHandlerFilterFunction());



    }
}
