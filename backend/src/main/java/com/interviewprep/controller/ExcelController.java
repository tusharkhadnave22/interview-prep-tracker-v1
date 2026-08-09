package com.interviewprep.controller;

import com.interviewprep.service.ExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users/{userId}/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@PathVariable Long userId) throws IOException {
        byte[] bytes = excelService.exportQuestions(userId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=interview-questions-" + userId + ".xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importExcel(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        int count = excelService.importQuestions(userId, file);
        return ResponseEntity.ok("Imported " + count + " questions");
    }
}
