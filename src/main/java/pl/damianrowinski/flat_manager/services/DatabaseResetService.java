package pl.damianrowinski.flat_manager.services;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Address;
import com.devskiller.jfairy.producer.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.TenantAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.room.RoomEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseResetService {

    private final PropertyService propertyService;
    private final RoomService roomService;
    private final TenantService tenantService;
    private final PaymentService paymentService;
    private final TenantAssembler tenantAssembler;

    public void generateRandomizedDatabase() {

        final int NO_OF_RANDOM_PROPERTIES = 3;
        final double ROOM_CATALOG_RENT = 1000d;

        for (int i = 0; i < NO_OF_RANDOM_PROPERTIES; i++) {
            int roomsNumber = generateRandomRoomsNumber();

            Property savedProperty = getRandomPropertyData(roomsNumber);

            for (int i1 = 0; i1 < roomsNumber; i1++) {
                Tenant savedTenant = generateAndAddRandomTenant();
                generateAndAddRoom(savedProperty, savedTenant, ROOM_CATALOG_RENT);
                generatePayment(savedTenant, ROOM_CATALOG_RENT);
            }
        }

    }

    private Tenant generateAndAddRandomTenant() {
        Fairy fairy = Fairy.create(Locale.forLanguageTag("pl"));
        Person person = fairy.person();
        Address address = person
                .getAddress();

        TenantEditDTO tenantToAdd = new TenantEditDTO();

        String leaseDateStart = generateRandomStartDate();
        tenantToAdd.setLeaseDateStart(leaseDateStart);
        tenantToAdd.setLeaseDateEnd(generateRandomEndDate(leaseDateStart));
        tenantToAdd.setRentDiscount(generateRentDiscount());
        tenantToAdd.setPaymentDeadline(generateRandomMonthDay());

        String firstName = person.getFirstName();
        String lastName = person.getLastName();

        tenantToAdd.setFirstName(firstName);
        tenantToAdd.setLastName(lastName);
        tenantToAdd.setFullName(firstName + " " + lastName);
        tenantToAdd.setEmail(person.getEmail());

        tenantToAdd.setCityName(address.getCity());
        tenantToAdd.setStreetName(address.getStreet());
        tenantToAdd.setStreetNumber(Integer.parseInt(address.getStreetNumber()));
        if (!("").equals(address.getApartmentNumber()))
            tenantToAdd.setApartmentNumber(Integer.parseInt(address.getApartmentNumber()));

        return tenantService.save(tenantToAdd);
    }

    private void generateAndAddRoom(Property savedProperty, Tenant savedTenant, double catalogRent) {
        RoomEditDTO roomToAdd = new RoomEditDTO();

        roomToAdd.setCatalogRent(catalogRent);
        roomToAdd.setPropertyId(savedProperty.getId());
        roomToAdd.setTenantFullName(savedTenant.getPersonalDetails()
                .getFullName());
        roomToAdd.setTenantId(savedTenant.getId());
        Room savedRoom = roomService.save(roomToAdd);

        generatePaymentBillForTenant(savedTenant, savedRoom);
    }

    private void generatePaymentBillForTenant(Tenant savedTenant, Room savedRoom) {
        TenantEditDTO tenantDataWithRoom = tenantAssembler.convertTenantToEditWithRoom(savedTenant, savedRoom.getId());
        tenantService.save(tenantDataWithRoom);
    }

    private void generatePayment(Tenant tenant, double catalogRent) {

        PaymentEditDTO paymentToAdd = new PaymentEditDTO();

        paymentToAdd.setPaymentDate(LocalDate.now().toString());
        paymentToAdd.setAmount(catalogRent - tenant.getRentDiscount());
        paymentToAdd.setTenantId(tenant.getId());

        paymentService.save(paymentToAdd);
    }

    private Double generateRentDiscount() {
        final int RENT_MULTIPLIER = 10;
        Random random = new Random();
        int i = random.nextInt(RENT_MULTIPLIER);
        return (double) (i * RENT_MULTIPLIER);
    }

    private String generateRandomStartDate() {
        final int NO_OF_RANDOM_MONTH_DAYS = 30;
        Random random = new Random();
        int randomAddDays = random.nextInt(NO_OF_RANDOM_MONTH_DAYS);
        LocalDate date = LocalDate.now().minusDays(randomAddDays);
        return date.toString();
    }

    private String generateRandomEndDate(String startDate) {
        final int MAX_MONTHS_CONTRACT_LENGTH = 12;
        final int MIN_MONTHS_CONTRACT_LENGTH = 6;
        Random random = new Random();
        int randomMonths;
        do {
            randomMonths = random.nextInt(MAX_MONTHS_CONTRACT_LENGTH);
        } while (randomMonths < MIN_MONTHS_CONTRACT_LENGTH);

        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate date = parsedStartDate.plusMonths(randomMonths);
        return date.toString();
    }

    private Property getRandomPropertyData(int roomsNumber) {
        final int AVG_BULK_PRICE_PER_ROOM = 700;
        double rentAmount = AVG_BULK_PRICE_PER_ROOM * roomsNumber;

        Fairy fairy = Fairy.create(Locale.forLanguageTag("pl"));
        Person person = fairy.person();
        Address address = person
                .getAddress();
        PropertyEditDTO propertyToAdd = new PropertyEditDTO();
        String streetName = address.getStreet();
        propertyToAdd.setWorkingName(streetName);

        propertyToAdd.setBillsRentAmount(rentAmount);
        propertyToAdd.setBillsUtilityAmount(generateUtilitiesAmount());
        propertyToAdd.setBillsPaymentDay(generateRandomMonthDay());

        propertyToAdd.setCityName(address.getCity());
        propertyToAdd.setStreetName(streetName);
        propertyToAdd.setStreetNumber(Integer.parseInt(address.getStreetNumber()));
        if (!("").equals(address.getApartmentNumber()))
            propertyToAdd.setApartmentNumber(Integer.parseInt(address.getApartmentNumber()));

        propertyToAdd.setFirstName(person.getFirstName());
        propertyToAdd.setLastName(person.getLastName());
        propertyToAdd.setEmail(person.getEmail());
        return propertyService.save(propertyToAdd);
    }

    private int generateRandomRoomsNumber() {
        final int ROOMS_NUMBER_MAX_RANGE = 6;
        Random random = new Random();
        int roomsNumber;
        do {
            roomsNumber = random.nextInt(ROOMS_NUMBER_MAX_RANGE);
        } while (roomsNumber <= 1);
        return roomsNumber;
    }

    private int generateRandomMonthDay() {
        return new Random()
                .nextInt(30);
    }

    private Double generateUtilitiesAmount() {
        int i = new Random()
                .nextInt(10);
        return (double) i;
    }
}
