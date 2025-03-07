package com.innovation.routes.presentation.controller;

import com.innovation.routes.business.service.ExcelService;
import com.innovation.routes.domain.dto.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @PostMapping("/cargar-excel")
    public List<Proveedor> cargarExcel(@RequestParam("archivo") MultipartFile archivoExcel) throws IOException {
        return excelService.leerExcel(archivoExcel);
    }
}
