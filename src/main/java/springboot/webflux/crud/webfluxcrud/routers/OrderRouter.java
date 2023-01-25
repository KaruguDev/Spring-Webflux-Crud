package springboot.webflux.crud.webfluxcrud.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.server.*;
import springboot.webflux.crud.webfluxcrud.handlers.OrderHandler;

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
                        orderHandler::deleteOrder);



    }
}
