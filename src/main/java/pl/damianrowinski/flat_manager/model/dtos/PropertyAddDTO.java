package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;
import pl.damianrowinski.flat_manager.domain.entities.Room;

import java.time.LocalDate;
import java.util.List;

@Data
public class PropertyAddDTO {

    private Double billsRentAmount;
    private Double billsUtilityAmount;
    private LocalDate billsPaymentDate;
    private List<Room> rooms;
}
