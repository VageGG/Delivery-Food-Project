package com.fooddeliveryfinalproject.controller;


import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.service.AdminService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admins")
public class AdminController extends LoginImplController<AdminService, AdminDto>{
    public AdminController(AuthenticationManager authenticationManager,
                           JWTUtilService jwtUtilService,
                           UserService userService,
                           AdminService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<AdminDto>> getAllCustomers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getAllAdmins(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getDriver(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getAdmin(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") Long id) {
        service.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
