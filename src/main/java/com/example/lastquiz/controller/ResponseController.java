package com.example.lastquiz.controller;

import com.example.lastquiz.entity.Response;
import com.example.lastquiz.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
@CrossOrigin(origins = "*")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping
    public List<Response> getAllResponses() {
        return responseService.getAllResponses();
    }

    @PostMapping
    public Response createResponse(@RequestBody Response response) {
        return responseService.saveResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable Integer id) {
        responseService.deleteResponse(id);
        return ResponseEntity.noContent().build();
    }
}
