package com.dataorb.api;

import com.dataorb.api.impl.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data-orb")
public class DataOrbController {

    @Autowired
    private DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<?> sendRecords(@RequestParam("files") List<MultipartFile> files){
        try {
            boolean processed = false;
            for (MultipartFile file : files) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                List<String> fileLines = new ArrayList<>();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    fileLines.add(line);
                }
                dataService.processData(fileLines);
                reader.close();
            }
            return ResponseEntity.accepted().body("Files processed successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing files: " + e.getMessage());
        }
    }

    @GetMapping("/records")
    public ResponseEntity<?> getRecords() {
        if (dataService.getRecords() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records found");
        }
        return ResponseEntity.ok(dataService.getRecords());
    }
}
