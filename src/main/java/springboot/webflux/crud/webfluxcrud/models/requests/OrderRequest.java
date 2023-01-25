package springboot.webflux.crud.webfluxcrud.models.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class OrderRequest {
    private Integer amount;
    private Boolean deleteStatus;

}
