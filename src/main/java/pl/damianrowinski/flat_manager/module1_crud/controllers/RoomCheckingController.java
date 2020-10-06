package pl.damianrowinski.flat_manager.module1_crud.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room.RoomCheckInOutDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.room.RoomListDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.tenant.TenantListDTO;
import pl.damianrowinski.flat_manager.module1_crud.services.RoomService;
import pl.damianrowinski.flat_manager.module1_crud.services.TenantService;
import pl.damianrowinski.flat_manager.app_common.utils.LoggedUsername;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomCheckingController {

    private final RoomService roomService;
    private final TenantService tenantService;

    @GetMapping("/checkout/from_room/{roomId}")
    public String generateCheckoutFromRoom(@PathVariable Long roomId, Model model) {
        RoomCheckInOutDTO roomToCheckout = roomService.findRoomToCheckout(roomId);
        model.addAttribute("roomData", roomToCheckout);
        return "/room/checkout_confirm";
    }

    @PostMapping("/checkout/from_room/{roomId}")
    public String checkoutFromRoom(@ModelAttribute("roomData") RoomCheckInOutDTO roomData) {
        roomService.checkout(roomData.getRoomId());
        return "redirect:/tenant";
    }


    @GetMapping("/checkout/from_property/from_room/{roomId}")
    public String generateCheckoutInApartment(@PathVariable Long roomId, Model model) {
        RoomCheckInOutDTO roomToCheckout = roomService.findRoomToCheckout(roomId);
        model.addAttribute("roomData", roomToCheckout);
        return "/room/checkout_confirm";
    }

    @PostMapping("/checkout/from_property/from_room/{roomId}")
    public String checkoutInApartment(@ModelAttribute("roomData") RoomCheckInOutDTO roomData) {
        roomService.checkout(roomData.getRoomId());
        return "redirect:/room/edit_in_property/" + roomData.getPropertyId();
    }

    @GetMapping("/checkout/in_rooms_list/{roomId}")
    public String generateCheckoutRoomsList(@PathVariable Long roomId, Model model) {
        RoomCheckInOutDTO roomToCheckout = roomService.findRoomToCheckout(roomId);
        model.addAttribute("roomData", roomToCheckout);
        return "/room/checkout_confirm";
    }

    @PostMapping("/checkout/in_rooms_list/{roomId}")
    public String checkoutRoomsList(@ModelAttribute("roomData") RoomCheckInOutDTO roomData) {
        roomService.checkout(roomData.getRoomId());
        return "redirect:/room";
    }

    @GetMapping("/checkin/tenants_list/tenant/{tenantId}")
    public String checkInTenantGenerate(@PathVariable Long tenantId, Model model) {
        List<RoomListDTO> availableRooms = roomService.findAllAvailableRooms(LoggedUsername.get());
        TenantListDTO tenantCheckinData = tenantService.findForCheckIn(tenantId);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("tenantData", tenantCheckinData);
        return "/tenant/checkin_room_list";
    }

    @PostMapping("/checkin/tenants_list/tenant/{tenantId}")
    public String checkInTenant(@ModelAttribute("tenantData") TenantListDTO tenantData) {
        if (tenantData.getRoomId() != null)
            roomService.checkInTenant(tenantData.getTenantId(), tenantData.getRoomId());
        return "redirect:/tenant";
    }

    @GetMapping("/checkin/in_rooms_list/for_room/{roomId}")
    public String checkInForRoomGenerate(@PathVariable Long roomId, Model model) {
        List<TenantListDTO> tenantsWithoutRooms = tenantService.findAllWithoutRooms(LoggedUsername.get());
        RoomCheckInOutDTO roomData = new RoomCheckInOutDTO();
        roomData.setRoomId(roomId);
        model.addAttribute("tenantListData", tenantsWithoutRooms);
        model.addAttribute("roomData", roomData);
        return "/room/checkin_tenant_list";
    }

    @PostMapping("/checkin/in_rooms_list/for_room/{roomId}")
    public String checkInForRoom(@ModelAttribute("roomData") RoomCheckInOutDTO roomData) {
        if (roomData.getTenantId() != null)
            roomService.checkInTenant(roomData.getTenantId(), roomData.getRoomId());
        return "redirect:/room";
    }

    @GetMapping("/checkin/in_property/for_room/{roomId}")
    public String checkInInApartmentGenerate(@PathVariable Long roomId, Model model) {
        List<TenantListDTO> tenantsWithoutRooms = tenantService.findAllWithoutRooms(LoggedUsername.get());
        RoomCheckInOutDTO roomToCheckin = roomService.findRoomToCheckout(roomId);
        RoomCheckInOutDTO roomData = new RoomCheckInOutDTO();
        roomData.setRoomId(roomToCheckin.getRoomId());
        model.addAttribute("tenantListData", tenantsWithoutRooms);
        model.addAttribute("roomData", roomData);
        return "/property/checkin_tenant_list";
    }

    @PostMapping("/checkin/in_property/for_room/{roomId}")
    public String checkInInApartment(@ModelAttribute("roomData") RoomCheckInOutDTO roomData) {
        if (roomData.getTenantId() != null)
            roomService.checkInTenant(roomData.getTenantId(), roomData.getRoomId());
        return "redirect:/room";
    }


}
