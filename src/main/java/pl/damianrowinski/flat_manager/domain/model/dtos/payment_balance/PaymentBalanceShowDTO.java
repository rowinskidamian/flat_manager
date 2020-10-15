package pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;

import java.time.LocalDate;

@Data
public class PaymentBalanceShowDTO {
    private Long id;
    private LocalDate currentBalanceDate;
    private Double currentBalance;
    private String balanceHolderName;
    private PaymentBalanceType paymentHolderType;
}
