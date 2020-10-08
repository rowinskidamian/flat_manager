package pl.damianrowinski.flat_manager.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.damianrowinski.flat_manager.assemblers.UserAssembler;
import pl.damianrowinski.flat_manager.config.Role;
import pl.damianrowinski.flat_manager.domain.model.entities.User;
import pl.damianrowinski.flat_manager.domain.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.domain.repositories.UserRepository;

@DisplayName("Unit tests for AuthenticationService")
class AuthenticationServiceTest {

    private AuthenticationService authenticationService;
    private PasswordEncoder passwordEncoder;
    private UserAssembler userAssembler;
    private UserRepository userRepository;

    @BeforeEach
    public void generateTest() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userAssembler = Mockito.mock(UserAssembler.class);
        userRepository = Mockito.mock(UserRepository.class);
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, userAssembler);
    }

    @Nested
    @DisplayName("Unit tests for AuthenticationService::register")
    class Register {

        @Test
        @DisplayName("Should use UserRepository")
        public void shouldUseUserRepository() {
            Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(new User());

            UserAddDTO userAdd = new UserAddDTO();
            authenticationService.register(userAdd);

            Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
        }

        @Test
        @DisplayName("Should use PasswordEncoder")
        public void shouldUsePasswordEncoder() {
            Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(new User());

            UserAddDTO userAdd = new UserAddDTO();
            userAdd.setPassword("testPass");
            authenticationService.register(userAdd);

            Mockito.verify(passwordEncoder, Mockito.times(1))
                    .encode(ArgumentMatchers.any(String.class));
        }

        @Test
        @DisplayName("Should have role USER")
        public void shouldHaveRoleUser() {
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            Mockito.when(userRepository.save(userCaptor.capture())).thenReturn(new User());

            UserAddDTO userAddDTO = new UserAddDTO();
            authenticationService.register(userAddDTO);

            User user = userCaptor.getValue();
            Assertions.assertThat(user).isNotNull();
            Assertions.assertThat(user)
                    .extracting(User::getRole)
                    .isNotNull()
                    .isEqualTo(Role.USER.toString());
        }


        @Test
        @DisplayName("Input data of UserData equals saved User data")
        public void savedDataEqualsInputData() {
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            Mockito.when(userRepository.save(userCaptor.capture())).thenReturn(new User());

            UserAddDTO userAddDTO = new UserAddDTO();
            userAddDTO.setLogin("testLogin");

            authenticationService.register(userAddDTO);

            User user = userCaptor.getValue();
            Assertions.assertThat(user).isNotNull();
            Assertions.assertThat(user)
                    .extracting(User::getLogin)
                    .isNotNull()
                    .isEqualTo(userAddDTO.getLogin());


        }
    }
}