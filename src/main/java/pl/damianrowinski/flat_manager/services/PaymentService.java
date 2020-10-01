package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PaymentRepository;
import pl.damianrowinski.flat_manager.model.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;

    public void save(PaymentEditDTO paymentData) {
        Payment paymentToSave = new Payment();

        setPaymentDataEditSave(paymentData, paymentToSave);

        log.info("Attempt to save payment: " + paymentData);
        paymentRepository.save(paymentToSave);
    }

    public PaymentEditDTO findPaymentToEdit(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isEmpty()) throw new ElementNotFoundException("Nie znaleziono płatności.");

        Payment payment = optionalPayment.get();
        PaymentEditDTO paymentData = new PaymentEditDTO();

        paymentData.setAmount(payment.getAmount());
        paymentData.setPaymentDate(payment.getPaymentDate().toString());
        paymentData.setTenantId(payment.getTenant().getId());

        return paymentData;
    }

    public void edit(PaymentEditDTO paymentData) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentData.getId());
        if (optionalPayment.isEmpty()) throw new ElementNotFoundException("Nie znalazłem płatności o podanym id");
        Payment paymentToEdit = optionalPayment.get();

        setPaymentDataEditSave(paymentData, paymentToEdit);

        log.info("Attempt to edit payment: " + paymentData);
        paymentRepository.save(paymentToEdit);
    }

    private void setPaymentDataEditSave(PaymentEditDTO paymentData, Payment paymentToEdit) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(paymentData.getTenantId());
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        Tenant tenantToAddPayment = optionalTenant.get();
        paymentToEdit.setTenant(tenantToAddPayment);

        paymentToEdit.setAmount(paymentData.getAmount());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate paymentDate = LocalDate.parse(paymentData.getPaymentDate(), formatter);
        paymentToEdit.setPaymentDate(paymentDate);
    }

    public List<PaymentShowDTO> getListOfPayments() {
        List<Payment> paymentList =
                paymentRepository.findAllByLoggedUserNameOrderByPaymentDateDesc(LoggedUsername.get());
        List<PaymentShowDTO> paymentDataList = new ArrayList<>();

        for (Payment payment : paymentList) {
            PaymentShowDTO paymentData = new PaymentShowDTO();
            paymentData.setId(payment.getId());
            paymentData.setAmount(payment.getAmount());
            paymentData.setPaymentDate(payment.getPaymentDate());

            Tenant tenant = payment.getTenant();
            paymentData.setTenantId(tenant.getId());
            paymentData.setTenantFullName(tenant.getPersonalDetails().getFullName());

            Property tenantProperty = tenant.getRoom().getProperty();
            paymentData.setPropertyId(tenantProperty.getId());
            paymentData.setPropertyWorkingName(tenantProperty.getWorkingName());

            paymentDataList.add(paymentData);
        }
        return paymentDataList;
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


}
