package pl.damianrowinski.flat_manager.domain.model.dtos;

import lombok.Data;

@Data
public class PaymentTransferDTO {
    private Double paymentAmount;
    private Long tenantId;
    private Long propertyId;
    private String tenantName;
    private String propertyName;
}
