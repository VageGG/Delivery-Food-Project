package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AdminConverter;
import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.repository.AdminRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private AdminRepo adminRepo;

    private AdminConverter adminConverter;

    public AdminService(AdminRepo adminRepo, AdminConverter adminConverter) {
        this.adminRepo = adminRepo;
        this.adminConverter = adminConverter;
    }

    public AdminDto getAdmin(Long id) {
        return adminConverter.convertToModel(adminRepo.findById(id).orElse(null), new AdminDto());
    }

    @Transactional
    public Admin createAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    @Transactional
    public void updateAdmin(Long id, Admin admin) {
        Admin adminEntity = adminRepo.findById(id).orElse(null);
        if (adminEntity != null) {
            adminRepo.save(adminEntity);
        }
    }

    @Transactional
    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }

    public AdminDto loginAdmin(String username, String password) {
        Admin admin = adminRepo.findByUserAndPassword(username, password);
        return adminConverter.convertToModel(admin, new AdminDto());
    }
}
