package com.maja.sdamovieapp.user.repository;

import com.maja.sdamovieapp.config.ContainersEnvironment;
import com.maja.sdamovieapp.user.entity.DeliveryAddress;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import com.maja.sdamovieapp.user.enums.RoleNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryTest extends ContainersEnvironment {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository addressRepository;

    @Test
    @DisplayName("Zapisuje listę adresów w encji User")
    void shouldSaveListOfAddressesInUserTable() {

        //given
        var user = getUser();
        var address = getDeliveryAddress(user);
        setAddressesList(user, address);
        var foundUserOptional = userRepository.findUserByEmail(getUser().getEmail());
        assertThat(foundUserOptional.isEmpty()).isTrue();

        //when
        userRepository.save(user);
        foundUserOptional = userRepository.findUserByEmail(user.getEmail());
        assertThat(foundUserOptional.isPresent()).isTrue();
        var foundUser = foundUserOptional.get();
        var foundAddresses = addressRepository.findAll();

        //then
        Assertions.assertAll(
                () -> assertThat(foundUser.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(foundUser.getFirstName()).isEqualTo(user.getFirstName()),
                () ->assertThat(foundUser.getLogin()).isEqualTo(user.getLogin()),
                () ->assertThat(foundAddresses.isEmpty()).isFalse()
        );
    }

    private void setAddressesList(User user, DeliveryAddress address) {
        List<DeliveryAddress> addresses = new ArrayList<>();
        addresses.add(address);
        user.setAddresses(addresses);
    }

    private DeliveryAddress getDeliveryAddress(User user) {
        DeliveryAddress address = new DeliveryAddress();
        address.setUser(user);
        address.setStreet("Konwaliowa");
        address.setBuildingNumber("10");
        address.setZipCode("02-002");
        address.setCity("Minas Tirith");
        return address;
    }

    private User getUser() {
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Testowy");
        user.setLogin("testowy");
        user.setEmail("test@test.pl");
        user.setPassword("test123");
        user.setCreatedAt(now());
        user.setActive(true);
        user.setClientType(ClientTypeEnum.STANDARD);
        user.setRole(RoleNameEnum.ROLE_USER);
        return user;
    }
}