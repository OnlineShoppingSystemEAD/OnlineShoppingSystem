package com.example.product_management.controller;

import com.example.product_management.model.Item;
import com.example.product_management.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/addItem")
    public String addItem(@RequestBody Item item) {
        return adminService.addItemToCategory(item.getCategory().getId(), item);
    }

    @PutMapping("/updateItem/{id}")
    public String updateItem(@PathVariable Long id, @RequestBody Item item) {
        return adminService.updateItem(id, item);
    }

    @DeleteMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable Long id) {
        return adminService.deleteItem(id);
    }
}
