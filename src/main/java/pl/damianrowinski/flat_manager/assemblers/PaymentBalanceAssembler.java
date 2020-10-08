package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PayBalUpdateType;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceUpdateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.services.RoomService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class PaymentBalanceAssembler {

    private final ModelMapper modelMapper;
    private final RoomService roomService;

    public PaymentBalance createTenantPaymentBalance(TenantPayBalCreateDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(LocalDateTime.now());
        accountToCreate.setBalanceHolderId(tenantData.getTenantId());
        accountToCreate.setBalanceHolderName(tenantData.getTenantName());
        accountToCreate.setCurrentBalance(-tenantData.getRoomRent());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.TENANT);
        return accountToCreate;
    }

    public PaymentBalance updatePropertyPaymentBalance
            (PaymentBalanceUpdateDTO changesForBalance, PaymentBalance currentBalance) {
        PaymentBalance updatedPaymentBalance = new PaymentBalance();
        updatedPaymentBalance.setCurrentBalanceDate(LocalDateTime.now());
        updatedPaymentBalance.setBalanceHolderId(currentBalance.getBalanceHolderId());
        updatedPaymentBalance.setBalanceHolderName(currentBalance.getBalanceHolderName());

        Double paymentBalanceAmount = currentBalance.getCurrentBalance();
        PayBalUpdateType updateType = changesForBalance.getUpdateType();
        Double paymentBalanceAmountUpdated = null;
        Double amountToChange = changesForBalance.getPaymentAmount();

        if (updateType.equals(PayBalUpdateType.INCOME)) {
            paymentBalanceAmountUpdated = paymentBalanceAmount + amountToChange;
        } else if (updateType.equals(PayBalUpdateType.OUTCOME)) {
            paymentBalanceAmountUpdated = paymentBalanceAmount - amountToChange;
        }

        updatedPaymentBalance.setCurrentBalance(paymentBalanceAmountUpdated);
        updatedPaymentBalance.setPaymentHolderType(PaymentBalanceType.PROPERTY);

        return updatedPaymentBalance;
    }

    public PaymentBalance createPropertyBalanceForTenant(PaymentBalanceUpdateDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(LocalDateTime.now());
        accountToCreate.setBalanceHolderId(tenantData.getPropertyId());
        accountToCreate.setBalanceHolderName(tenantData.getPropertyName());
        accountToCreate.setCurrentBalance(-tenantData.getPaymentAmount());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.PROPERTY);
        return accountToCreate;
    }

    public PaymentBalance updateTenantPaymentBalanceWithPayment(PaymentBalance paymentBalanceToUpdate,
                                                                Double paymentAmount) {
        PaymentBalance tenantBalanceUpdated = getBalanceToUpdate(paymentBalanceToUpdate);

        Double balanceAmountUpdated = paymentBalanceToUpdate.getCurrentBalance() + paymentAmount;
        tenantBalanceUpdated.setCurrentBalance(balanceAmountUpdated);
        return tenantBalanceUpdated;
    }

    public PaymentBalance updateTenantPaymentBalanceByCheckin(PaymentBalance paymentBalanceToUpdate, Double roomRent) {
        PaymentBalance tenantBalanceUpdated = getBalanceToUpdate(paymentBalanceToUpdate);

        Double balanceAmountUpdated = paymentBalanceToUpdate.getCurrentBalance() - roomRent;
        tenantBalanceUpdated.setCurrentBalance(balanceAmountUpdated);
        return tenantBalanceUpdated;
    }

    public PaymentBalanceUpdateDTO convertFromPaymentToUpdateData(PaymentEditDTO paymentToConvert) {
        PaymentBalanceUpdateDTO paymentConverted = new PaymentBalanceUpdateDTO();
        paymentConverted.setPaymentAmount(paymentToConvert.getAmount());

        RoomTransferDTO roomData = roomService.findByTenantId(paymentToConvert.getTenantId());
        paymentConverted.setPropertyId(roomData.getPropertyId());
        paymentConverted.setPropertyName(roomData.getPropertyName());

        paymentConverted.setTenantId(paymentToConvert.getTenantId());
        paymentConverted.setTenantName(roomData.getTenantName());
        paymentConverted.setUpdateType(PayBalUpdateType.INCOME);
        return paymentConverted;
    }

    public PaymentBalanceUpdateDTO convertToPaymentBalanceUpdate(TenantPayBalCreateDTO tenantData) {
        PaymentBalanceUpdateDTO paymentData = modelMapper.map(tenantData, PaymentBalanceUpdateDTO.class);
        paymentData.setUpdateType(PayBalUpdateType.OUTCOME);
        return paymentData;
    }

    private PaymentBalance getBalanceToUpdate(PaymentBalance paymentBalanceToUpdate) {
        PaymentBalance tenantBalanceUpdated = new PaymentBalance();
        tenantBalanceUpdated.setCurrentBalanceDate(LocalDateTime.now());
        tenantBalanceUpdated.setBalanceHolderId(paymentBalanceToUpdate.getBalanceHolderId());
        tenantBalanceUpdated.setBalanceHolderName(paymentBalanceToUpdate.getBalanceHolderName());
        tenantBalanceUpdated.setPaymentHolderType(PaymentBalanceType.TENANT);
        return tenantBalanceUpdated;
    }
}
