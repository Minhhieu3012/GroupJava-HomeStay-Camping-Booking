package com.example.homestay.service;

import com.example.homestay.entity.Property;
import com.example.homestay.entity.PropertyStatus;

import java.util.List;

public interface PropertyService {
    Property saveProperty(Property property);
    List<Property> getAllProperties();
    List<Property> getByStatus(PropertyStatus status);
    Property getPropertyById(Long id);
}
