package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.repository.AdminRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.DriverRepo;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private CustomerRepo userRepository;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private RestaurantManagerRepo restaurantManagerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> customer = Optional.ofNullable(userRepository.findByEmail(username).orElse(null));
        if (customer.isPresent()) {
            Customer userCustomer = userRepository.findByEmail(username).get();
            GrantedAuthority authority = new SimpleGrantedAuthority(userCustomer.getRole().name());
            return new org.springframework.security.core.userdetails.User(userCustomer.getEmail(), userCustomer.getPassword(), Arrays.asList(authority));
        }

        Optional<Admin> admin = Optional.ofNullable(adminRepo.findByEmail(username).orElse(null));
        if (admin.isPresent()) {
            Admin userAdmin = adminRepo.findByEmail(username).get();
            GrantedAuthority authority = new SimpleGrantedAuthority(userAdmin.getRole().name());
            return new org.springframework.security.core.userdetails.User(userAdmin.getEmail(), userAdmin.getPassword(), Arrays.asList(authority));
        }

        Optional<Driver> driver = Optional.ofNullable(driverRepo.findByEmail(username).orElse(null));
        if (driver.isPresent()) {
            Driver userDriver = driverRepo.findByEmail(username).get();
            GrantedAuthority authority = new SimpleGrantedAuthority(userDriver.getRole().name());
            return new org.springframework.security.core.userdetails.User(userDriver.getEmail(), userDriver.getPassword(), Arrays.asList(authority));
        }

        Optional<RestaurantManager> restaurantManager = Optional.ofNullable(restaurantManagerRepo.findByEmail(username).orElse(null));
        if (restaurantManager.isPresent()) {
            RestaurantManager userRestaurantManager = restaurantManagerRepo.findByEmail(username).get();
            GrantedAuthority authority = new SimpleGrantedAuthority(userRestaurantManager.getRole().name());
            return new org.springframework.security.core.userdetails.User(userRestaurantManager.getEmail(), userRestaurantManager.getPassword(), Arrays.asList(authority));
        }

        throw new RuntimeException("Not found user");
    }

}