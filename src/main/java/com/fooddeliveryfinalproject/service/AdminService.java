package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AdminConverter;
import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.repository.AdminRepo;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService implements ValidUser<AdminDto> {

    private final AdminRepo adminRepo;

    private final AdminConverter adminConverter;

    public AdminService(AdminRepo adminRepo, AdminConverter adminConverter) {
        this.adminRepo = adminRepo;
        this.adminConverter = adminConverter;
    }

    public List<AdminDto> getAllAdmins() {
        return adminRepo.findAll().stream()
                .map(admin -> adminConverter.convertToModel(admin, new AdminDto()))
                .collect(Collectors.toList());
    }

    public AdminDto getAdmin(Long id) {
        return adminConverter.convertToModel(adminRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Admin not found")),
                new AdminDto());
    }

    @Override
    @Transactional
    public void addUser(AdminDto adminDto) throws NoSuchAlgorithmException {
        Optional<Admin> existingUser = adminRepo.findByUsername(adminDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email has already been used");
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
    public void updateAdmin(Long id, AdminDto adminDto) {
        Admin adminEntity = adminRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        adminConverter.convertToEntity(adminDto, adminEntity);
        adminRepo.save(adminEntity);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }
}
