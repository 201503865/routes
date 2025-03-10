package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.Proveedor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LectorExcel {

    public static List<Proveedor> leerExcel(String rutaArchivo) throws IOException {
        List<Proveedor> proveedores = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(rutaArchivo);
        InputStream archivo = resource.getInputStream();
        Workbook workbook = new XSSFWorkbook(archivo);
        Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja

        Iterator<Row> iterator = sheet.iterator();
        iterator.next();

        while (iterator.hasNext()) {
            Row fila = iterator.next();
            Proveedor proveedor = new Proveedor();

            proveedor.setDepartamento(fila.getCell(0).getStringCellValue());
            proveedor.setUbicacion(fila.getCell(1).getStringCellValue());
            proveedor.setUbicacionMaps(fila.getCell(2).getStringCellValue());
            proveedor.setNombreProveedor(fila.getCell(3).getStringCellValue());
            DataFormatter formatter = new DataFormatter();
            String cellValue = formatter.formatCellValue(fila.getCell(4));
            proveedor.setTelefonos(cellValue);
            proveedor.setKilometros(fila.getCell(5).getNumericCellValue());
            proveedor.setPasoCorrienteJumper(fila.getCell(6).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setPasoCorrienteCables(fila.getCell(7).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setEnvioCombustible(fila.getCell(8).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setCambioLlanta(fila.getCell(9).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setCerrajeria(fila.getCell(10).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setGruaSmall(fila.getCell(11).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setGruaMediana(fila.getCell(12).getStringCellValue().equalsIgnoreCase("si"));
            proveedor.setGruaGrande(fila.getCell(13).getStringCellValue().equalsIgnoreCase("si"));
            proveedores.add(proveedor);
        }

        workbook.close();
        archivo.close();
        return proveedores;
    }


}
