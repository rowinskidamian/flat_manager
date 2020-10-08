package pl.damianrowinski.flat_manager.domain.model.dtos.property;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityState;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class PropertyEditDTO {

    private Long id;
    private String loggedUserName;

    @NotBlank
    private String workingName;

    @NotNull
    @Min(0)
    private Double billsRentAmount;
    @NotNull
    @Min(0)
    private Double billsUtilityAmount;
    @NotNull
    @Min(1)
    @Max(31)
    private Integer billsPaymentDay;
    private List<Room> rooms;

    @NotBlank
    private String cityName;
    @NotBlank
    private String streetName;
    @NotNull
    private Integer streetNumber;
    @NotNull
    private Integer apartmentNumber;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    private BaseEntityState state;

}
