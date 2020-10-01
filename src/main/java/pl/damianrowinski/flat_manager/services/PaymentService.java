package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.PaymentDataAssembler;
import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PaymentRepository;
import pl.damianrowinski.flat_manager.model.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;


    public PaymentEditDTO findPaymentToEdit(Long paymentId) {
        Payment payment = getPayment(paymentId);
        return PaymentDataAssembler.convertToPaymentEdit(payment);
    }

    public void edit(PaymentEditDTO paymentData) {
        Payment paymentToEdit = getPayment(paymentData.getId());
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

    public List<PaymentShowDTO> getListOfPayments() {
        List<Payment> paymentList =
                paymentRepository.findAllByLoggedUserNameOrderByPaymentDateDesc(LoggedUsername.get());
        return PaymentDataAssembler.convertToPaymentShowList(paymentList);
    }

    public void delete(Long paymentDeleteId) {
        Payment paymentToDelete = isPossibleToDeleteOrThrow(paymentDeleteId);
        log.info("Attempt to delete payment: " + paymentToDelete);
        paymentRepository.delete(paymentToDelete);
    }

    public Payment isPossibleToDeleteOrThrow(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if(optionalPayment.isEmpty()) throw new ElementNotFoundException("Nie zaleziono płatności o podanym id");
        return optionalPayment.get();
    }

    private Tenant getTenant(PaymentEditDTO paymentData) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(paymentData.getTenantId());
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        return optionalTenant.get();
    }

    private Payment getPayment(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) throw new ElementNotFoundException("Nie znaleziono płatności.");
        return optionalPayment.get();
    }

}
