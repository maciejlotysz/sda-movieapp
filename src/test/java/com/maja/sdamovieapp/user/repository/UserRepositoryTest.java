package com.maja.sdamovieapp.user.repository;

import com.maja.sdamovieapp.user.entity.DeliveryAddress;
import com.maja.sdamovieapp.user.entity.User;
import com.maja.sdamovieapp.user.enums.ClientTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository addressRepository;

    @Test
    void shouldSaveListOfAddressesInUserTable() {

        //given
        var lastName = "Syn Gloina";
        var gimli = "Gimli";
        var login = "killerAxe";
        var email = "gimli@erebor.com";
        var password = "password";
        var clientTypeEnum = ClientTypeEnum.STANDARD;

        //set User
        User user = getUser(lastName, gimli, login, email, password, clientTypeEnum);
        //set address
        DeliveryAddress address = getDeliveryAddress(user);
        //set list of addresses
        setAddressesList(user, address);

        Optional<User> foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isEmpty()).isTrue();

        //when
        userRepository.save(user);
        foundUserOptional = userRepository.findUserByLastName(lastName);
        assertThat(foundUserOptional.isPresent()).isTrue();
        User foundUser = foundUserOptional.get();

        List<DeliveryAddress> foundAddresses = addressRepository.findAll();

        //then
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(foundUser.getLogin()).isEqualTo(user.getLogin());

        assertThat(foundAddresses.isEmpty()).isFalse();
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

    private User getUser(String lastName,
                         String gimli,
                         String login,
                         String email,
                         String password,
                         ClientTypeEnum clientTypeEnum) {
        User user = new User();
        user.setFirstName(gimli);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(true);
        user.setClientType(clientTypeEnum);
        return user;
    }
}