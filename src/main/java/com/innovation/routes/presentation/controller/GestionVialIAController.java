package com.innovation.routes.presentation.controller;

import com.innovation.routes.business.service.AIService;
import com.innovation.routes.business.service.GestionVialService;
import com.innovation.routes.domain.dto.Servicio;
import com.innovation.routes.domain.dto.UbicacionConDistancia;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GestionVialIAController {
    @Autowired
    GestionVialService gestionVialService;
    @Autowired
    AIService aiService;

    @PostMapping("/better-routes")
    public ResponseEntity getBetterRoutes(@RequestBody Servicio servicio) throws Exception {
        return ResponseEntity.ok(gestionVialService.getBetterRoutes(servicio));
    }

    @PostMapping("/get-data")
    public ResponseEntity getGeminiData(@RequestBody Servicio servicio){
        return ResponseEntity.ok(aiService.chat(servicio.getVehicle()));
    }
}
