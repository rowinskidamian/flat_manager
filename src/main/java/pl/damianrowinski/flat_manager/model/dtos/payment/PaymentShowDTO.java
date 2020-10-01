package pl.damianrowinski.flat_manager.model.dtos.payment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentShowDTO {

    private Long id;
    private LocalDate paymentDate;
    private Double amount;
    private Long tenantId;
    private String tenantFullName;
    private Long propertyId;
    private String propertyWorkingName;

}
