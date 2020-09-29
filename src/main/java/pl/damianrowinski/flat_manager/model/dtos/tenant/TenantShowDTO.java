package pl.damianrowinski.flat_manager.model.dtos.tenant;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TenantShowDTO {

    private String id;
    private String fullName;
    private String apartmentName;
    private Long propertyId;
    private Long roomId;
    private Double currentRent;
    private Double rentDiscount;
    private String email;
    private LocalDate leaseDateStart;
    private LocalDate leaseDateEnd;

}
