package com.example.homestay.service;

import com.example.homestay.entity.Property;
import com.example.homestay.entity.PropertyStatus;
import com.example.homestay.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public List<Property> getByStatus(PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }

    @Override
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }
}
