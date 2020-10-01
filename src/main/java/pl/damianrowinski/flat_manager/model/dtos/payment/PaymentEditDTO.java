package pl.damianrowinski.flat_manager.model.dtos.payment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentEditDTO {

    @NotBlank
    private String paymentDate;
    @NotNull
    private Double amount;
    @NotNull
    private Long tenantId;

}
