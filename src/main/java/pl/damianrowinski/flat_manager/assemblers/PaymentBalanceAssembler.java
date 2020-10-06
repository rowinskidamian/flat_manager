package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;

@RequiredArgsConstructor
@Component
public class PaymentBalanceAssembler {

    private final ModelMapper modelMapper;

    public PaymentBalance convertAccountCreateDTO(PaymentBalanceCreateDTO accountDataToConvert) {
        return modelMapper.map(accountDataToConvert, PaymentBalance.class);
    }

}
