package com.example.homestay.controller;

import com.example.homestay.entity.Property;
import com.example.homestay.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    // Tạo / Đăng ký homestay mới
    @PostMapping("/create")
    public String createProperty(@RequestBody Property property) {
        propertyRepository.save(property);
        return "Homestay created successfully!";
    }

    // Lấy danh sách homestay cho khách xem
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
}
