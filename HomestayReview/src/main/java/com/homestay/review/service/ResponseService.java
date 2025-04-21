package com.homestay.review.service;

import com.homestay.review.model.Response;
import com.homestay.review.repository.ResponseRepository;
import com.homestay.review.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    public Response saveResponse(Response response) {
        return responseRepository.save(response);
    }

    public List<ResponseDTO> getResponsesForRating(Long ratingId) {
        List<Response> responses = responseRepository.findByRatingId(ratingId);
        return responses.stream().map(response -> {
            ResponseDTO dto = new ResponseDTO();
            dto.setRatingId(response.getRating().getId());
            dto.setResponseText(response.getResponseText());
            return dto;
        }).collect(Collectors.toList());
    }
}
