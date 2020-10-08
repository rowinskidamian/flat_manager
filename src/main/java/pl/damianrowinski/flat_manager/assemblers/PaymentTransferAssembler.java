package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.model.dtos.PaymentTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.services.RoomService;

@Component
@RequiredArgsConstructor
public class PaymentTransferAssembler {

    private final RoomService roomService;

    public PaymentTransferDTO convertFromEditToTransferData(PaymentEditDTO paymentToConvert) {
        PaymentTransferDTO paymentTransferData = new PaymentTransferDTO();
        paymentTransferData.setPaymentAmount(paymentToConvert.getAmount());

        RoomTransferDTO roomData = roomService.findByTenantId(paymentToConvert.getTenantId());
        paymentTransferData.setPropertyId(roomData.getPropertyId());
        paymentTransferData.setPropertyName(roomData.getPropertyName());

        paymentTransferData.setTenantId(paymentToConvert.getTenantId());
        paymentTransferData.setTenantName(roomData.getTenantName());
        return paymentTransferData;
    }
}
