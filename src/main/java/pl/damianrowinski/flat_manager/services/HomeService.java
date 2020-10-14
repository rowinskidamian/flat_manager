package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceShowDTO;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class HomeService {

    private final PaymentBalanceService paymentBalanceService;
    private final RoomService roomService;
    private final TenantService tenantService;
    private final PropertyService propertyService;

    public double getCurrentBalance() {
        List<PaymentBalanceShowDTO> userBalance = paymentBalanceService.getCurrentUserBalance();
        double userPaymentBalance = 0;
        if (userBalance.size() > 0)
            userPaymentBalance = userBalance.get(0).getCurrentBalance();
        return userPaymentBalance;
    }

    public long findNoOfTotalRooms() {
        return roomService.findNoOfRoomsForLoggedUser();
    }

    public long findNoOfRentedRooms(){
        return roomService.findNoOfRentedRooms();
    }

    public long findNoOfTotalTenants() {
        return tenantService.findNoOfTotalTenantsLoggedUser();
    }

    public long findNoOfTotalProperties() {
        return propertyService.findNoOfTotalPropertiesLoggedUser();
    }

}
