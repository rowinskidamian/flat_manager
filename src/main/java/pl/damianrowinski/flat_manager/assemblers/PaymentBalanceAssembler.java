package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PayBalUpdateType;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceShowDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceUpdateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantShowDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.user.UserListDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.services.UserService;
import pl.damianrowinski.flat_manager.utils.CurrentLocalDateFormatted;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;


@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentBalanceAssembler {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public PaymentBalance createTenantPaymentBalance(TenantPayBalCreateDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(CurrentLocalDateFormatted.get());
        accountToCreate.setBalanceHolderId(tenantData.getTenantId());
        accountToCreate.setBalanceHolderName(tenantData.getTenantName());
        accountToCreate.setCurrentBalance(-tenantData.getRoomRent());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.TENANT);
        return accountToCreate;
    }

    public PaymentBalance createPropertyBalanceForTenant(PaymentBalanceUpdateDTO tenantData) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(CurrentLocalDateFormatted.get());
        accountToCreate.setBalanceHolderId(tenantData.getPropertyId());
        accountToCreate.setBalanceHolderName(tenantData.getPropertyName());
        accountToCreate.setCurrentBalance(-tenantData.getPaymentAmount());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.PROPERTY);
        return accountToCreate;
    }

    public PaymentBalance createUserBalance(PaymentBalanceUpdateDTO paymentBalanceChanges) {
        PaymentBalance accountToCreate = new PaymentBalance();
        accountToCreate.setCurrentBalanceDate(CurrentLocalDateFormatted.get());
        UserListDTO userData = userService.findByLoginPreview(LoggedUsername.get());
        accountToCreate.setBalanceHolderId(userData.getId());
        accountToCreate.setBalanceHolderName(userData.getFullName());
        accountToCreate.setCurrentBalance(-paymentBalanceChanges.getPaymentAmount());
        accountToCreate.setPaymentHolderType(PaymentBalanceType.USER);
        return accountToCreate;
    }

    public PaymentBalance updatePropertyPaymentBalance
            (PaymentBalanceUpdateDTO changesForBalance, PaymentBalance currentBalance) {
        return updatePaymentBalance(changesForBalance, currentBalance, PaymentBalanceType.PROPERTY);
    }

    public PaymentBalance updateTenantPaymentBalance(PaymentBalanceUpdateDTO updateData,
                                                     PaymentBalance paymentBalanceToUpdate) {
        return updatePaymentBalance(updateData, paymentBalanceToUpdate, PaymentBalanceType.TENANT);
    }

    public PaymentBalance updateUserPaymentBalance(PaymentBalanceUpdateDTO paymentBalanceChanges,
                                                   PaymentBalance currentBalance) {
        return updatePaymentBalance(paymentBalanceChanges, currentBalance, PaymentBalanceType.USER);
    }

    private PaymentBalance updatePaymentBalance(PaymentBalanceUpdateDTO changesForBalance,
                                                PaymentBalance paymentBalanceToUpdate,
                                                PaymentBalanceType paymentBalanceType) {
        PaymentBalance updatedPaymentBalance = new PaymentBalance();
        updatedPaymentBalance.setCurrentBalanceDate(CurrentLocalDateFormatted.get());
        updatedPaymentBalance.setBalanceHolderId(paymentBalanceToUpdate.getBalanceHolderId());
        updatedPaymentBalance.setBalanceHolderName(paymentBalanceToUpdate.getBalanceHolderName());

        Double paymentBalanceAmountUpdated = updateAmountBalance(changesForBalance, paymentBalanceToUpdate);

        updatedPaymentBalance.setCurrentBalance(paymentBalanceAmountUpdated);
        updatedPaymentBalance.setPaymentHolderType(paymentBalanceType);

        return updatedPaymentBalance;
    }

    private Double updateAmountBalance(PaymentBalanceUpdateDTO changesForBalance,
                                       PaymentBalance paymentBalanceToUpdate) {
        Double paymentBalanceAmount = paymentBalanceToUpdate.getCurrentBalance();
        PayBalUpdateType updateType = changesForBalance.getUpdateType();
        double paymentBalanceAmountUpdated = 0d;
        Double amountToChange = changesForBalance.getPaymentAmount();

        if (updateType.equals(PayBalUpdateType.INCOME)) {
            paymentBalanceAmountUpdated = paymentBalanceAmount + amountToChange;
        } else if (updateType.equals(PayBalUpdateType.OUTCOME)) {
            paymentBalanceAmountUpdated = paymentBalanceAmount - amountToChange;
        }
        return paymentBalanceAmountUpdated;
    }

    public PaymentBalance updateTenantPaymentBalanceByCheckin(PaymentBalance paymentBalanceToUpdate, Double roomRent) {
        PaymentBalance tenantBalanceUpdated = getBalanceToUpdate(paymentBalanceToUpdate);

        Double balanceAmountUpdated = paymentBalanceToUpdate.getCurrentBalance() - roomRent;
        tenantBalanceUpdated.setCurrentBalance(balanceAmountUpdated);
        return tenantBalanceUpdated;
    }

    public PaymentBalanceUpdateDTO convertFromPaymentToUpdateData(PaymentEditDTO paymentToConvert,
                                                                  RoomTransferDTO roomData) {
        PaymentBalanceUpdateDTO paymentConverted = new PaymentBalanceUpdateDTO();
        paymentConverted.setPaymentAmount(paymentToConvert.getAmount());

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
        paymentData.setPaymentAmount(tenantData.getRoomRent());
        log.info("Converted tenantData to paymentData: " + paymentData);
        return paymentData;
    }

    public PaymentBalanceUpdateDTO convertToAccountUpdateWithRentRequest(TenantShowDTO tenantData) {
        PaymentBalanceUpdateDTO paymentData = new PaymentBalanceUpdateDTO();
        paymentData.setTenantId(tenantData.getId());
        paymentData.setPropertyId(tenantData.getPropertyId());
        paymentData.setTenantName(tenantData.getFullName());
        paymentData.setPropertyName(tenantData.getApartmentName());
        paymentData.setPaymentAmount(tenantData.getCurrentRent());
        paymentData.setUpdateType(PayBalUpdateType.OUTCOME);
        return paymentData;
    }

    private PaymentBalance getBalanceToUpdate(PaymentBalance paymentBalanceToUpdate) {
        PaymentBalance tenantBalanceUpdated = new PaymentBalance();
        tenantBalanceUpdated.setCurrentBalanceDate(CurrentLocalDateFormatted.get());
        tenantBalanceUpdated.setBalanceHolderId(paymentBalanceToUpdate.getBalanceHolderId());
        tenantBalanceUpdated.setBalanceHolderName(paymentBalanceToUpdate.getBalanceHolderName());
        tenantBalanceUpdated.setPaymentHolderType(PaymentBalanceType.TENANT);
        tenantBalanceUpdated.setCurrentBalance(paymentBalanceToUpdate.getCurrentBalance());
        return tenantBalanceUpdated;
    }

    public PaymentBalanceShowDTO convertToPaymentBalanceShow(PaymentBalance paymentBalance) {
        return modelMapper.map(paymentBalance, PaymentBalanceShowDTO.class);
    }

    public TenantPayBalCreateDTO convertFromUpdateToTenantPayBal(PaymentBalanceUpdateDTO paymentData) {
        TenantPayBalCreateDTO tenantData = modelMapper.map(paymentData, TenantPayBalCreateDTO.class);
        tenantData.setRoomRent(paymentData.getPaymentAmount());
        return tenantData;
    }
}
