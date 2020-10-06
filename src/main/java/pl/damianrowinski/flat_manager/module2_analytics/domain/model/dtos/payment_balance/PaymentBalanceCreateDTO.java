package pl.damianrowinski.flat_manager.module2_analytics.domain.model.dtos.payment_balance;

import lombok.Data;
import pl.damianrowinski.flat_manager.module2_analytics.domain.model.entities.PaymentBalanceType;

import java.time.LocalDateTime;

@Data
public class PaymentBalanceCreateDTO {

    private LocalDateTime currentBillingPeriod;
    private Double initialBalance;
    private Long balanceHolderId;
    private String balanceHolderName;
    private PaymentBalanceType balanceHolderType;

    public PaymentBalanceCreateDTO() {
        currentBillingPeriod = LocalDateTime.now();
    }

}
