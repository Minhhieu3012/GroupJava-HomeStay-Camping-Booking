package com.example.homestay.service;

import com.example.homestay.entity.Homestay;
import com.example.homestay.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private HomestayRepository homestayRepository;

    public Homestay registerHomestay(Homestay homestay, List<MultipartFile> images) {
        if (homestay.getName() == null || homestay.getName().isEmpty()) {
            throw new IllegalArgumentException("Tên homestay không được để trống");
        }
        if (homestay.getPricePerNight() <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }

        List<String> imageUrls = saveImages(images);
        homestay.setImageUrls(imageUrls);
        homestay.setStatus("PENDING");

        return homestayRepository.save(homestay);
    }

    private List<String> saveImages(List<MultipartFile> images) {
        return images.stream().map(file -> "/images/" + file.getOriginalFilename()).toList();
    }
}