package com.homestay.review.services;


import com.homestay.review.model.Response;
import com.homestay.review.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    // Lưu phản hồi
    public Response saveResponse(Response response) {
        return responseRepository.save(response);
    }
}