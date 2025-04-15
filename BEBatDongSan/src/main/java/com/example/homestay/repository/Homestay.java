package com.example.homestay.repository;

import com.example.models.Homestay;
import com.example.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    List<Homestay> findByStatus(Status status); // Lấy danh sách homestay theo trạng thái
}