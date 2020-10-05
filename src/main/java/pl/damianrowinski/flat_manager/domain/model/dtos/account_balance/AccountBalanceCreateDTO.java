package pl.damianrowinski.flat_manager.domain.model.dtos.account_balance;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.model.entities.AccountBalanceType;

@Data
public class AccountBalanceCreateDTO {

    private Double initialBalance;
    private Long AccountHolderId;
    private AccountBalanceType accountHolderType;

}
