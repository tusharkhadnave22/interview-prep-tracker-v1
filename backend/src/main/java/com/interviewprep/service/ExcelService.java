package com.interviewprep.service;

import com.interviewprep.entity.Question;
import com.interviewprep.entity.QuestionStatus;
import com.interviewprep.entity.User;
import com.interviewprep.repository.QuestionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public ExcelService(QuestionRepository questionRepository, UserService userService) {
        this.questionRepository = questionRepository;
        this.userService = userService;
    }

    public byte[] exportQuestions(Long userId) throws IOException {
        User user = userService.getEntity(userId);
        List<Question> questions = questionRepository.findByUserIdOrderByIdAsc(userId);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Questions");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Question");
            header.createCell(1).setCellValue("Status");

            int rowIndex = 1;
            for (Question q : questions) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(q.getQuestionText());
                row.createCell(1).setCellValue(q.getStatus().name());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            workbook.write(output);
            return output.toByteArray();
        }
    }

    public int importQuestions(Long userId, MultipartFile file) throws IOException {
        User user = userService.getEntity(userId);
        List<Question> questions = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String text = getCellText(row.getCell(0)).trim();
                if (text.isBlank()) continue;

                String statusText = getCellText(row.getCell(1)).trim();
                QuestionStatus status = parseStatus(statusText);

                Question question = new Question(text, user);
                question.setStatus(status);
                questions.add(question);
            }
        }

        questionRepository.saveAll(questions);
        return questions.size();
    }

    private String getCellText(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    private QuestionStatus parseStatus(String value) {
        if (value == null || value.isBlank()) return QuestionStatus.NOT_STARTED;

        try {
            return QuestionStatus.valueOf(
                    value.trim().toUpperCase().replace(" ", "_")
            );
        } catch (IllegalArgumentException ex) {
            return QuestionStatus.NOT_STARTED;
        }
    }
}
