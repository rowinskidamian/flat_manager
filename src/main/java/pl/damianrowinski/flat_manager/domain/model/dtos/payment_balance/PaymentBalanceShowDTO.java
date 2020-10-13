package pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;

import java.time.LocalDateTime;

@Data
public class PaymentBalanceShowDTO {
    private Long id;
    private LocalDateTime currentBalanceDate;
    private Double currentBalance;
    private String balanceHolderName;
    private PaymentBalanceType paymentHolderType;
}
