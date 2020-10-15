package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.RoomAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.TenantPayBalCreateDTO;
import pl.damianrowinski.flat_manager.assemblers.PaymentTenantAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomTransferDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;
import pl.damianrowinski.flat_manager.domain.model.common.Address;
import pl.damianrowinski.flat_manager.domain.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.*;
import pl.damianrowinski.flat_manager.domain.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.domain.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TenantService {

    private final ModelMapper modelMapper;
    private final PaymentTenantAssembler tenantAssembler;
    private final PaymentBalanceService paymentBalanceService;
    private final RoomRepository roomRepository;
    private final RoomAssembler roomAssembler;
    private final TenantRepository tenantRepository;

    public void deleteAll() {
        tenantRepository.deleteAll();
    }

    public List<TenantListDTO> findAllWithoutRooms(String loggedUserName) {
        List<Tenant> tenantList = tenantRepository.findAllByLoggedUserNameAndRoomIsNull(loggedUserName);
        List<TenantListDTO> tenantDataList = new ArrayList<>();
        for (Tenant tenant : tenantList) {
            log.info("Tenant without room:");
            log.info(tenant.toString());

            TenantListDTO tenantData = new TenantListDTO();
            tenantData.setTenantId(tenant.getId());
            tenantData.setTenantFullName(tenant.getPersonalDetails().getFullName());
            tenantData.setLoggedUserName(tenant.getLoggedUserName());
            tenantDataList.add(tenantData);
        }
        return tenantDataList;
    }

    public List<TenantListDTO> findAll() {
        List<Tenant> tenantList = tenantRepository.findAllByLoggedUserName(LoggedUsername.get());
        List<TenantListDTO> tenantDataList = new ArrayList<>();
        for (Tenant tenant : tenantList) {
            TenantListDTO tenantData = new TenantListDTO();
            tenantData.setTenantId(tenant.getId());
            tenantData.setTenantFullName(tenant.getPersonalDetails().getFullName());
            tenantData.setLoggedUserName(tenant.getLoggedUserName());
            tenantDataList.add(tenantData);
        }
        return tenantDataList;
    }

    public List<TenantShowDTO> findAllLoggedUser(String loggedUserName) {
        List<Tenant> tenantList = tenantRepository.findAllByLoggedUserName(loggedUserName);
        List<TenantShowDTO> tenantDataList = new ArrayList<>();

        for (Tenant tenant : tenantList) {
            TenantShowDTO tenantData = modelMapper.map(tenant, TenantShowDTO.class);

            tenantData.setFullName(tenant.getPersonalDetails().getFullName());

            Room tenantRoom = tenant.getRoom();
            if (tenantRoom != null) {
                Property property = tenantRoom.getProperty();
                tenantData.setApartmentName(property.getWorkingName());
                Double currentRent = calculateCurrentRent(tenantData, tenantRoom);
                tenantData.setCurrentRent(currentRent);
                tenantData.setPropertyId(property.getId());
                tenantData.setRoomId(tenantRoom.getId());
            }

            tenantData.setEmail(tenant.getPersonalDetails().getEmail());
            tenantDataList.add(tenantData);
        }

        return tenantDataList;
    }

    private Double calculateCurrentRent(TenantShowDTO tenantData, Room tenantRoom) {
        Double catalogRent = tenantRoom.getCatalogRent();
        Double rentDiscount = tenantData.getRentDiscount();
        return rentDiscount != null ? catalogRent - rentDiscount : catalogRent;
    }

    public Tenant save(TenantEditDTO tenantDataToAdd) {
        Tenant tenantToAdd = modelMapper.map(tenantDataToAdd, Tenant.class);
        Address address = modelMapper.map(tenantDataToAdd, Address.class);
        tenantToAdd.setContactAddress(address);
        PersonNameContact personalDetails = modelMapper.map(tenantDataToAdd, PersonNameContact.class);
        tenantToAdd.setPersonalDetails(personalDetails);

        formatLeaseDates(tenantDataToAdd, tenantToAdd);

        Tenant savedTenant = tenantRepository.save(tenantToAdd);

        Long tenantRoomId = tenantDataToAdd.getRoomId();

        if (tenantRoomId != null) {
            Optional<Room> optionalRoom = roomRepository.findById(tenantRoomId);
            if (optionalRoom.isEmpty()) throw new ElementNotFoundException("Nie znalazłem pokoju o podanym id.");

            Room room = optionalRoom.get();
            room.setTenant(savedTenant);
            tenantDataToAdd.setId(savedTenant.getId());

            Room roomWithTenant = roomRepository.save(room);

            RoomTransferDTO roomToTransfer = roomAssembler.convertRoomToTransferData(roomWithTenant);

            TenantPayBalCreateDTO tenantPayBalCreateDTO = tenantAssembler
                    .getTenantPaymentBalanceData(tenantDataToAdd, roomToTransfer);

            paymentBalanceService.createPaymentBalanceForTenant(tenantPayBalCreateDTO);
        }

        return savedTenant;
    }

    public long findNoOfTotalTenantsLoggedUser() {
        return tenantRepository.findNoOfUserTenants(LoggedUsername.get());
    }

    private void formatLeaseDates(TenantEditDTO tenantDataToAdd, Tenant tenantToAdd) {
        String leaseDateStart = tenantDataToAdd.getLeaseDateStart();
        String leaseDateEnd = tenantDataToAdd.getLeaseDateEnd();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate leaseDateStartFormatted = LocalDate.parse(leaseDateStart, formatter);
        LocalDate leaseDateEndFormatted = LocalDate.parse(leaseDateEnd, formatter);
        tenantToAdd.setLeaseDateStart(leaseDateStartFormatted);
        tenantToAdd.setLeaseDateEnd(leaseDateEndFormatted);
    }

    public TenantAddressDTO findTenantAddress(Long tenantId) {

        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id.");

        Tenant tenant = optionalTenant.get();

        TenantAddressDTO tenantAddressDTO = modelMapper.map(tenant.getPersonalDetails(), TenantAddressDTO.class);
        Address contactAddress = tenant.getContactAddress();
        tenantSetAddress(tenantAddressDTO, contactAddress);
        return tenantAddressDTO;
    }

    private void tenantSetAddress(TenantAddressDTO tenantAddressDTO, Address contactAddress) {
        if (contactAddress != null) {
            tenantAddressDTO.setCityName(contactAddress.getCityName());
            tenantAddressDTO.setStreetName(contactAddress.getStreetName());
            if (contactAddress.getApartmentNumber() != null) {
                tenantAddressDTO.setAddressNumber((contactAddress.getCombinedAddressNumber()));
            } else {
                tenantAddressDTO.setAddressNumber(contactAddress.getStreetNumber().toString());
            }
        }
    }

    public TenantEditDTO findById(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id.");

        Tenant tenant = optionalTenant.get();

        TenantEditDTO tenantEditData = modelMapper.map(tenant, TenantEditDTO.class);

        tenantEditData.setLeaseDateStart(tenant.getLeaseDateStart().toString());
        tenantEditData.setLeaseDateEnd(tenant.getLeaseDateEnd().toString());

        PersonNameContact personalDetails = tenant.getPersonalDetails();
        tenantEditData.setFirstName(personalDetails.getFirstName());
        tenantEditData.setLastName(personalDetails.getLastName());
        tenantEditData.setEmail(personalDetails.getEmail());

        if (tenant.getRoom() != null)
            tenantEditData.setRoomId(tenant.getRoom().getId());

        Address contactAddress = tenant.getContactAddress();
        if (contactAddress != null) {
            tenantEditData.setCityName(contactAddress.getCityName());
            tenantEditData.setStreetName(contactAddress.getStreetName());
            if (contactAddress.getApartmentNumber() != null) {
                tenantEditData.setApartmentNumber((contactAddress.getApartmentNumber()));
            }
            tenantEditData.setStreetNumber(contactAddress.getStreetNumber());
        }

        return tenantEditData;
    }

    public TenantDeleteDTO findTenantToDelete(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Brak najemcy o podanym id");
        Tenant tenant = optionalTenant.get();

        TenantDeleteDTO tenantToDeleteData = new TenantDeleteDTO();
        tenantToDeleteData.setId(tenant.getId());
        tenantToDeleteData.setLoggedUserName(tenant.getLoggedUserName());
        if (tenant.getRoom() != null) throw new ObjectInRelationshipException("Najemca zajmuje pokój, " +
                "najpierw wykwateruj najemcę, następnie usuń najemcę.");
        return tenantToDeleteData;
    }

    public void delete(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id.");
        Tenant tenant = optionalTenant.get();
        if (tenant.getRoom() != null) throw new ObjectInRelationshipException("Najemca zajmuje pokój, " +
                "najpierw wykwateruj najemcę, następnie usuń najemcę.");
        tenantRepository.delete(tenant);
    }

    public TenantListDTO findForCheckIn(Long tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id.");
        Tenant tenant = optionalTenant.get();

        TenantListDTO tenantData = new TenantListDTO();
        tenantData.setTenantId(tenant.getId());
        tenantData.setTenantFullName(tenant.getPersonalDetails().getFullName());
        tenantData.setLoggedUserName(tenant.getLoggedUserName());
        return tenantData;
    }


}
