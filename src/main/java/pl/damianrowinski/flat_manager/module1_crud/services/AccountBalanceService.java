package pl.damianrowinski.flat_manager.module1_crud.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.module1_crud.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.payment_balance.PaymentBalanceCreateDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.module1_crud.domain.repositories.PaymentBalanceRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountBalanceService {

    private final PaymentBalanceRepository paymentBalanceRepository;
    private final PaymentBalanceAssembler paymentBalanceAssembler;

    public void createPaymentBalance(PaymentBalanceCreateDTO accountData) {

        PaymentBalance accountToSave = paymentBalanceAssembler.convertAccountCreateDTO(accountData);
        paymentBalanceRepository.save(accountToSave);

    }

}
