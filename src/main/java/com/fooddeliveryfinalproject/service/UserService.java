package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.AllUserDto;
import com.fooddeliveryfinalproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    private final AdminRepo adminRepo;

    private final DriverRepo driverRepo;

    private final RestaurantManagerRepo restaurantManagerRepo;

    private final AddressRepo addressRepo;

    private final CustomerAddressRepo customerAddressRepo;

    private final AddressConverter addressConverter;

    public UserService(CustomerRepo customerRepo,
                       AdminRepo adminRepo,
                       DriverRepo driverRepo,
                       AddressConverter addressConverter,
                       RestaurantManagerRepo restaurantManagerRepo,
                       AddressRepo addressRepo,
                       CustomerAddressRepo customerAddressRepo) {
        this.customerRepo = customerRepo;
        this.adminRepo = adminRepo;
        this.driverRepo = driverRepo;
        this.restaurantManagerRepo = restaurantManagerRepo;
        this.addressRepo = addressRepo;
        this.customerAddressRepo = customerAddressRepo;
        this.addressConverter = addressConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Проверяем, если пользователь водитель или менеджер, его статус должен быть одобрен
        if (user instanceof Driver) {
            Driver driver = (Driver) user;
            if (driver.getStatus() != RegistrationStatus.APPROVED) {
                throw new RuntimeException("Driver is not approved by admin yet");
            }
        } else if (user instanceof RestaurantManager) {
            RestaurantManager manager = (RestaurantManager) user;
            if (manager.getStatus() != RegistrationStatus.APPROVED) {
                throw new RuntimeException("Restaurant Manager is not approved by admin yet");
            }
        }

        return user;
    }


    protected Optional<? extends User> findUserByUsername(String username) {
        Optional<Customer> customer = customerRepo.findByUsername(username);
        if (customer.isPresent()) {
            return customer;
        }

        Optional<Admin> admin = adminRepo.findByUsername(username);
        if (admin.isPresent()) {
            return admin;
        }

        Optional<Driver> driver = driverRepo.findByUsername(username);
        if (driver.isPresent()) {
            return driver;
        }

        Optional<RestaurantManager> restaurantManager = restaurantManagerRepo.findByUsername(username);
        if (restaurantManager.isPresent()) {
            return restaurantManager;
        }

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<? extends User> getProfile(String username) {
        return findUserByUsername(username);
    }

    @Transactional
    public Optional<? extends User> updateProfile(String username, AllUserDto updatedUser) {
        Optional<? extends User> userOptional = findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(updatedUser.getUsername());

            if (isEmailValid(updatedUser.getEmail())) {
                user.setEmail(updatedUser.getEmail());
            }

            if (isPasswordValid(updatedUser.getPassword())) {
                String pw_hash = BCrypt.hashpw(updatedUser.getPassword(), BCrypt.gensalt(12));
                user.setPassword(pw_hash);
            }

            user.setPhoneNumber(updatedUser.getPhoneNumber());
            saveUser(user);
        }
        return userOptional;
    }

    @Transactional(readOnly = true)
    public List<Address> getCustomerAddressesList(String username) {
        return findUserByUsername(username)
                .filter(user -> user instanceof Customer)
                .map(user -> {
                    Customer customer = (Customer) user;
                    // Получаем список customerAddresses и извлекаем из них адреса
                    return customer.getAddresses().stream()
                            .map(CustomerAddress::getAddress) // Получаем адрес из каждой записи CustomerAddress
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public Optional<Address> getAddress(String username, Long addressId) {
        Optional<Customer> customer = customerRepo.findByUsername(username);
        return customer.flatMap(c ->
                c.getAddresses().stream()
                        .filter(customerAddress -> customerAddress.getAddress().getId().equals(addressId))
                        .map(CustomerAddress::getAddress)
                        .findFirst()
        );
    }

    @Transactional
    public Address addAddress(String username, AddressDto newAddress) {
        Optional<Customer> customerOptional = customerRepo.findByUsername(username);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            Address savedAddress = addressRepo.save(addressConverter.convertToEntity(newAddress, new Address()));

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setCustomer(customer);
            customerAddress.setAddress(savedAddress);

            customerAddressRepo.save(customerAddress);

            return savedAddress;
        }
        throw new EntityNotFoundException("Customer not found");
    }

    @Transactional
    public void updateAddress(String username, Long addressId, AddressDto updatedAddress) {
        Optional<Address> addressOptional = getAddress(username, addressId);

        addressOptional.ifPresent(address -> {
            address.setCountry(updatedAddress.getCountry());
            address.setCity(updatedAddress.getCity());
            address.setState(updatedAddress.getState());
            address.setStreet(updatedAddress.getStreet());
            address.setHouseNumber(updatedAddress.getHouseNumber());
            address.setApartmentNumber(updatedAddress.getApartmentNumber());
            addressRepo.save(address);
        });
    }

    @Transactional
    public void deleteAddress(String username, Long addressId) {
        getAddress(username, addressId).ifPresent(address -> addressRepo.delete(address));
    }

    private void saveUser(User user) {
        if (user instanceof Customer) {
            customerRepo.save((Customer) user);
        } else if (user instanceof Admin) {
            adminRepo.save((Admin) user);
        } else if (user instanceof Driver) {
            driverRepo.save((Driver) user);
        } else if (user instanceof RestaurantManager) {
            restaurantManagerRepo.save((RestaurantManager) user);
        }
    }

    private boolean isEmailValid(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters long");
        }

        Pattern numberPattern = Pattern.compile("[0-9]");
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Pattern lowercasePattern = Pattern.compile("[a-z]");
        Pattern specialCharacterPattern = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");

        Matcher number = numberPattern.matcher(password);
        Matcher uppercase = uppercasePattern.matcher(password);
        Matcher lowercase = lowercasePattern.matcher(password);
        Matcher specialCharacter = specialCharacterPattern.matcher(password);

        return number.find() && uppercase.find() && lowercase.find() && specialCharacter.find();
    }
}

