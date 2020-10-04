package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.PaymentDataAssembler;
import pl.damianrowinski.flat_manager.domain.model.entities.Payment;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyShowDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomShowDTO;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentRepository;
import pl.damianrowinski.flat_manager.domain.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;
    private final PropertyService propertyService;

    public void edit(PaymentEditDTO paymentData) {
        Payment paymentToEdit = getPaymentOrThrow(paymentData.getId());
        Tenant tenant = getTenant(paymentData);

        PaymentDataAssembler.setPaymentForEditSave(paymentData, tenant, paymentToEdit);

        log.info("Attempt to edit payment: " + paymentData);
        paymentRepository.save(paymentToEdit);
    }

    public void save(PaymentEditDTO paymentData) {
        Payment paymentToSave = new Payment();
        Tenant tenant = getTenant(paymentData);

        PaymentDataAssembler.setPaymentForEditSave(paymentData, tenant, paymentToSave);

        log.info("Attempt to save payment: " + paymentData);
        paymentRepository.save(paymentToSave);
    }

    public void delete(Long paymentDeleteId) {
        Payment paymentToDelete = getPaymentOrThrow(paymentDeleteId);
        log.info("Attempt to delete payment: " + paymentToDelete);
        paymentRepository.delete(paymentToDelete);
    }

    public PaymentEditDTO findPaymentToEdit(Long paymentId) {
        Payment payment = getPaymentOrThrow(paymentId);
        return PaymentDataAssembler.convertToPaymentEdit(payment);
    }

    public Payment getPaymentOrThrow(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if(optionalPayment.isEmpty()) throw new ElementNotFoundException("Nie zaleziono płatności o podanym id");
        return optionalPayment.get();
    }

    private Tenant getTenant(PaymentEditDTO paymentData) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(paymentData.getTenantId());
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        return optionalTenant.get();
    }

    public List<PaymentShowDTO> getListOfPayments() {
        List<Payment> paymentList =
                paymentRepository.findAllByLoggedUserNameOrderByPaymentDateDesc(LoggedUsername.get());
        return PaymentDataAssembler.convertToPaymentShowList(paymentList);
    }

    public List<PaymentShowDTO> findPaymentsForTenant(Long tenantId) {
        List<Payment> paymentList =
                paymentRepository.findAllByTenantIdOrderByPaymentDateDesc(tenantId);
        return PaymentDataAssembler.convertToPaymentShowList(paymentList);
    }

    public List<PaymentShowDTO> findPaymentsForProperty(Long propertyId) {
        List<PaymentShowDTO> listPaymentsForProperty = new LinkedList<>();
        PropertyShowDTO propertyWithRooms = propertyService.findByIdWithRooms(propertyId);
        List<RoomShowDTO> roomsListForProperty = propertyWithRooms.getRooms();

        for (RoomShowDTO roomData : roomsListForProperty) {
            Long tenantId = roomData.getTenantId();
            List<Payment> paymentList =
                    paymentRepository.findAllByTenantIdOrderByPaymentDateDesc(tenantId);
            List<PaymentShowDTO> paymentDataList = PaymentDataAssembler.convertToPaymentShowList(paymentList);

            paymentDataList.forEach(listPaymentsForProperty::add);
        }

        return listPaymentsForProperty;
    }


}
