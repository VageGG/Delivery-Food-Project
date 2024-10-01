package com.fooddeliveryfinalproject.controller;


import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.service.AdminService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admins")
public class AdminController extends RegisterImplController<AdminService, AdminDto> {

    public AdminController(AdminService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<AdminDto>> getAllAdmins(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getAllAdmins(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getAdmin(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable("id") Long id) {
        service.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    protected User.Role getExpectedRole() {
        return User.Role.ADMIN;
    }
}
