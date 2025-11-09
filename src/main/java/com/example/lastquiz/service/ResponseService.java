package com.example.lastquiz.service;

import com.example.lastquiz.entity.Response;
import com.example.lastquiz.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    public Response saveResponse(Response response) {
        return responseRepository.save(response);
    }

    public void deleteResponse(Integer id) {
        responseRepository.deleteById(id);
    }
}

