package com.example.homestay.repository;

import com.example.homestay.entity.Property;
import com.example.homestay.entity.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByStatus(PropertyStatus status);
}
