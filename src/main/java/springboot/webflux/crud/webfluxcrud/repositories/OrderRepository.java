package springboot.webflux.crud.webfluxcrud.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import springboot.webflux.crud.webfluxcrud.models.Order;

@Repository
public interface OrderRepository extends R2dbcRepository<Order, Long> {

}
