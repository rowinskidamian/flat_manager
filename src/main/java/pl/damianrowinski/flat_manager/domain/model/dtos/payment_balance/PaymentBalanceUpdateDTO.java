package pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance;

import lombok.Data;

@Data
public class PaymentBalanceUpdateDTO {
    private Double paymentAmount;
    private Long tenantId;
    private Long propertyId;
    private String tenantName;
    private String propertyName;
    private PayBalUpdateType updateType;
}
