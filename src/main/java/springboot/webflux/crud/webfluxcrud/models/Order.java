package springboot.webflux.crud.webfluxcrud.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Data
@Getter @Setter @ToString
@Table("orders")
public class Order {
    @Id
    @Column("id")
    private Long id;
    @Column("amount")
    private Integer amount;
    @Column("transaction_date")
    private Timestamp transactionDate;

    @Column("delete_status")
    private Boolean deleteStatus;

    public Order(Integer amount){
        this.amount = amount;
        this.transactionDate = Timestamp.valueOf(LocalDateTime.now());
        this.deleteStatus = false;
    }
}
