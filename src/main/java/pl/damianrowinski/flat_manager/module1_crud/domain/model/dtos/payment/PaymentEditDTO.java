package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.payment;

import lombok.Data;
import pl.damianrowinski.flat_manager.module1_crud.validation.constraints.CheckDatePattern;

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

}
