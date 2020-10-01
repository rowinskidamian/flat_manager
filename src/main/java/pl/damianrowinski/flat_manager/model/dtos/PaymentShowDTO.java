package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;

@Data
public class PaymentShowDTO {

    private Long id;
    private String paymentDate;
    private Double amount;
    private Long tenantId;
    private String tenantFullName;

}
