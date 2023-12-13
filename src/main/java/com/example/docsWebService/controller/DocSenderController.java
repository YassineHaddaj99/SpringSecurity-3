package com.example.docsWebService.controller;


import com.example.docsWebService.service.DocSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/sender")
@RequiredArgsConstructor
public class DocSenderController {
    @Autowired
    private DocSenderService connector;

    @PostMapping(value= "/upload",consumes = {"multipart/form-data"} )
    public ResponseEntity<String> uploadFile(
            @RequestParam MultipartFile file
    ) {
        try {
            // Call the service to upload the file
            String result = connector.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

}
