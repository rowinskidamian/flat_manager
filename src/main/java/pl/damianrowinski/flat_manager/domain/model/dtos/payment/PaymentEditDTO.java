package pl.damianrowinski.flat_manager.domain.model.dtos.payment;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.model.entities.EntityState;
import pl.damianrowinski.flat_manager.validation.constraints.CheckDatePattern;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentEditDTO {

    private Long id;

    @CheckDatePattern
    @NotBlank
    private String paymentDate;
    @NotNull
    private Double amount;
    @NotNull
    private Long tenantId;

    private EntityState state;

}
