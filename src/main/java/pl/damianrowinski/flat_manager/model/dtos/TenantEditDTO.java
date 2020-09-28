package pl.damianrowinski.flat_manager.model.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class TenantEditDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate leaseDateStart;

    @NotNull
    private LocalDate leaseDateEnd;

    private Integer roomId;

    private Double rentDiscount;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer paymentDeadline;

    @Email
    @NotBlank
    private String email;

    private String cityName;
    private String streetName;
    private Integer streetNumber;
    private Integer apartmentNumber;

}
