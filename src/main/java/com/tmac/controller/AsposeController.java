package com.tmac.controller;

import com.tmac.aspose.ExcelPreviewService;
import com.tmac.aspose.WordPreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class AsposeController {
    @Autowired
    private WordPreviewService wordPreviewService;
    @Autowired
    private ExcelPreviewService excelPreviewService;

    @PostMapping(value = "/wordPreviewHtml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> wordPreviewHtml(@RequestParam(value = "file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return wordPreviewService.preview(file);
            } catch (Exception exception) {
                throw new RuntimeException("转换失败", exception);
            }

        }
        return null;
    }

    @PostMapping(value = "/excelPreviewHtml", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> excelPreviewHtml(@RequestParam(value = "file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return excelPreviewService.preview(file);
            } catch (Exception exception) {
                throw new RuntimeException("转换失败", exception);
            }

        }
        return null;
    }
}
