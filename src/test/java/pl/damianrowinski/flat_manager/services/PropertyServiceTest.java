package pl.damianrowinski.flat_manager.services;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.flat_manager.domain.model.common.Address;
import pl.damianrowinski.flat_manager.domain.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyDeleteDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyEditDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.property.PropertyShowDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.Property;
import pl.damianrowinski.flat_manager.domain.model.entities.Room;
import pl.damianrowinski.flat_manager.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.domain.model.entities.common.BaseEntityState;
import pl.damianrowinski.flat_manager.domain.repositories.PropertyRepository;
import pl.damianrowinski.flat_manager.domain.repositories.RoomRepository;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for PropertyService")
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private RoomRepository roomRepository;

    @Spy
    private ModelMapper modelMapper = createModelMapper();

    private ModelMapper createModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);
        return mapper;
    }

    @InjectMocks
    private PropertyService propertyService;

    @Test
    @DisplayName("Should save property correctly")
    void shouldSaveProperty() {
        //given
        PropertyEditDTO editDTO = new PropertyEditDTO();
        editDTO.setWorkingName("Test Property");
        editDTO.setState(BaseEntityState.ACTIVE);
        editDTO.setFirstName("John");
        editDTO.setLastName("Doe");
        editDTO.setEmail("john@example.com");
        editDTO.setCityName("City");
        editDTO.setStreetName("Street");
        editDTO.setStreetNumber(10);
        editDTO.setApartmentNumber(5);

        Property property = new Property();
        when(propertyRepository.save(any(Property.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Property savedProperty = propertyService.save(editDTO);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(savedProperty).isNotNull();
        softly.assertThat(savedProperty.getWorkingName()).isEqualTo("Test Property");
        softly.assertThat(savedProperty.getState()).isEqualTo(BaseEntityState.ACTIVE);
        softly.assertThat(savedProperty.getOwnerDetails().getFirstName()).isEqualTo("John");
        softly.assertThat(savedProperty.getAddress().getCityName()).isEqualTo("City");
        softly.assertAll();

        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    @DisplayName("Should find property to edit by id")
    void findToEditById() {
        //given
        Long propertyId = 1L;
        Property property = new Property();
        property.setId(propertyId);
        property.setState(BaseEntityState.ACTIVE);
        Address address = new Address();
        address.setCityName("City");
        property.setAddress(address);
        PersonNameContact owner = new PersonNameContact();
        owner.setFirstName("John");
        property.setOwnerDetails(owner);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        //when
        PropertyEditDTO result = propertyService.findToEditById(propertyId);

        //then
        assertThat(result).isNotNull();
        verify(propertyRepository).findById(propertyId);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getCityName()).isEqualTo("City");
        softly.assertThat(result.getFirstName()).isEqualTo("John");
        softly.assertThat(result.getState()).isEqualTo(BaseEntityState.ACTIVE);
        softly.assertAll();
    }

    @Test
    @DisplayName("Should throw ElementNotFoundException when finding to edit by non-existent id")
    void findToEditByIdNotFound() {
        //given
        Long propertyId = 1L;
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        //when //then
        assertThatThrownBy(() -> propertyService.findToEditById(propertyId))
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessageContaining("Nie znalazłem mieszkania o podanym id.");
    }

    @Test
    @DisplayName("Should find property to delete when no tenants in rooms")
    void findPropertyToDeleteSuccess() {
        //given
        Long propertyId = 1L;
        Property property = new Property();
        property.setId(propertyId);
        property.setLoggedUserName("user");

        Room room = new Room();
        room.setTenant(null);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(roomRepository.findAllByPropertyId(propertyId)).thenReturn(List.of(room));

        //when
        PropertyDeleteDTO result = propertyService.findPropertyToDelete(propertyId);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result).isNotNull();
        softly.assertThat(result.getPropertyId()).isEqualTo(propertyId);
        softly.assertThat(result.getLoggedUserName()).isEqualTo("user");
        softly.assertAll();
    }

    @Test
    @DisplayName("Should throw ObjectInRelationshipException when finding property to delete has tenants")
    void findPropertyToDeleteHasTenants() {
        //given
        Long propertyId = 1L;
        Property property = new Property();

        Room room = new Room();
        room.setTenant(new Tenant());

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(roomRepository.findAllByPropertyId(propertyId)).thenReturn(List.of(room));

        //when //then
        assertThatThrownBy(() -> propertyService.findPropertyToDelete(propertyId))
                .isInstanceOf(ObjectInRelationshipException.class)
                .hasMessageContaining("Mieszkanie ma najemców");
    }

    @Test
    @DisplayName("Should delete property and its rooms")
    void delete() {
        //given
        Long propertyId = 1L;
        PropertyDeleteDTO deleteDTO = new PropertyDeleteDTO();
        deleteDTO.setPropertyId(propertyId);

        Property property = new Property();
        Room room1 = new Room();
        Room room2 = new Room();

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(roomRepository.findAllByPropertyId(propertyId)).thenReturn(List.of(room1, room2));

        //when
        propertyService.delete(deleteDTO);

        //then
        verify(roomRepository, times(2)).delete(any(Room.class));
        verify(propertyRepository).delete(property);
    }

    @Test
    @DisplayName("Should find property by id with rooms")
    void findByIdWithRooms() {
        //given
        Long propertyId = 1L;
        Property property = new Property();
        property.setId(propertyId);
        property.setAddress(new Address());
        property.setOwnerDetails(new PersonNameContact());

        Room room = new Room();
        room.setId(2L);
        room.setProperty(property);
        room.setCatalogRent(100.0);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(roomRepository.findAllByPropertyId(propertyId)).thenReturn(List.of(room));

        //when
        PropertyShowDTO result = propertyService.findByIdWithRooms(propertyId);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result).isNotNull();
        softly.assertThat(result.getRooms()).hasSize(1);
        softly.assertThat(result.getRooms().get(0).getId()).isEqualTo(2L);
        softly.assertAll();

        verify(propertyRepository).findById(propertyId);
        verify(roomRepository).findAllByPropertyId(propertyId);
    }

    @Test
    @DisplayName("Should find all properties for user show list")
    void findAllByUserShowList() {
        //given
        String username = "user";
        Property property = new Property();
        property.setId(1L);
        property.setWorkingName("Property 1");
        property.setLoggedUserName(username);

        when(propertyRepository.findAllByLoggedUserName(username)).thenReturn(List.of(property));

        //when
        var result = propertyService.findAllByUserShowList(username);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result).hasSize(1);
        softly.assertThat(result.get(0).getPropertyWorkingName()).isEqualTo("Property 1");
        softly.assertAll();
    }

    @Test
    @DisplayName("Should find all properties for user")
    void findAllByUser() {
        //given
        String username = "user";
        Property property = new Property();
        property.setId(1L);
        property.setAddress(new Address());
        property.setOwnerDetails(new PersonNameContact());

        when(propertyRepository.findAllByLoggedUserName(username)).thenReturn(List.of(property));
        when(roomRepository.findAllByPropertyId(1L)).thenReturn(List.of(new Room()));

        //when
        var result = propertyService.findAllByUser(username);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result).hasSize(1);
        softly.assertThat(result.get(0).getRoomsNumber()).isEqualTo(1);
        softly.assertAll();
    }

    @Test
    @DisplayName("Should delete all properties")
    void deleteAll() {
        //when
        propertyService.deleteAll();

        //then
        verify(propertyRepository).deleteAll();
    }
}
