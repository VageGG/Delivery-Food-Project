package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AdminConverter;
import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.repository.AdminRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Validated
public class AdminService implements ValidUser<AdminDto> {

    private final AdminRepo adminRepo;

    private final AdminConverter adminConverter;

    @Autowired
    public AdminService(AdminRepo adminRepo, AdminConverter adminConverter) {
        this.adminRepo = adminRepo;
        this.adminConverter = adminConverter;
    }

    @Transactional(readOnly = true)
    public Page<AdminDto> getAllAdmins(Pageable pageable) {
        return adminRepo.findAll(pageable).map(admin ->
                adminConverter.convertToModel(admin, new AdminDto())
        );
    }

    @Transactional(readOnly = true)
    public AdminDto getAdmin(@Min(1) Long id) {
        return adminConverter.convertToModel(adminRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Admin not found")),
                new AdminDto());
    }

    @Override
    @Transactional
    public void addUser(AdminDto adminDto) throws NoSuchAlgorithmException {
        Optional<Admin> existingUser = adminRepo.findByUsername(adminDto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username has already been used");
        }

        if (!adminDto.getRole().equals(User.Role.ADMIN)) {
            throw new RuntimeException("Role mismatch");
        }

        if (!isEmailValid(adminDto.getEmail())) {
            throw new RuntimeException("email is invalid");
        }

        if (!isPasswordValid(adminDto.getPassword())) {
            throw new RuntimeException("password is invalid");
        }

        if (adminDto.getUsername() == null) {
            throw new RuntimeException("name must be specified");
        }
        String pw_hash = BCrypt.hashpw(adminDto.getPassword(), BCrypt.gensalt(12));
        adminDto.setPassword(pw_hash);

        adminRepo.save(adminConverter.convertToEntity(adminDto, new Admin()));
    }

    @Transactional
    public void updateAdmin(@Min(1) Long id, @Valid AdminDto adminDto) {
        Admin adminEntity = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        adminEntity.setUsername(adminDto.getUsername());

        if (isEmailValid(adminDto.getEmail())) {
            adminEntity.setEmail(adminDto.getEmail());
        }

        if (!isPasswordValid(adminDto.getPassword())) {
            String pw_hash = BCrypt.hashpw(adminDto.getPassword(), BCrypt.gensalt(12));
            adminEntity.setPassword(pw_hash);
        }

        adminEntity.setPhoneNumber(adminDto.getPhoneNumber());
        adminEntity.setRole(adminDto.getRole());
        adminRepo.save(adminEntity);
    }

    @Transactional
    public void deleteAdmin(@Min(1) Long id) {
        adminRepo.deleteById(id);
    }
}
