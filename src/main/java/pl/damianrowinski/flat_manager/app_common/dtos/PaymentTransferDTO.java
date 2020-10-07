package pl.damianrowinski.flat_manager.app_common.dtos;

import lombok.Data;

@Data
public class PaymentTransferDTO {
    private Double paymentAmount;
    private Long tenantId;
    private Long propertyId;
    private String tenantName;
    private String propertyName;
}
