package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.account_balance.AccountBalanceCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.AccountBalance;

@RequiredArgsConstructor
@Component
public class AccountBalanceAssembler {

    private final ModelMapper modelMapper;

    public AccountBalance convertAccountCreateDTO(AccountBalanceCreateDTO accountDataToConvert) {
        return modelMapper.map(accountDataToConvert, AccountBalance.class);
    }

}
