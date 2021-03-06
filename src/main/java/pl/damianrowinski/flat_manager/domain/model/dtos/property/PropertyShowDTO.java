package pl.damianrowinski.flat_manager.domain.model.dtos.property;

import lombok.*;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomShowDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PropertyShowDTO {

    private Long id;
    private Integer roomsNumber;
    private List<RoomShowDTO> rooms;
    private LocalDateTime createdDate;
    private String workingName;
    private Double billsRentAmount;
    private Double billsUtilityAmount;
    private Integer billsPaymentDay;

    private String cityName;
    private String streetName;
    private String addressFullNumber;

    private String ownerName;
    private String email;

}
