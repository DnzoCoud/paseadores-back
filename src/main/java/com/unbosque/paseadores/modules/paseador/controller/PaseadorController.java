package com.unbosque.paseadores.modules.paseador.controller;
import com.unbosque.paseadores.modules.paseador.service.PaseadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paseador/")
@RequiredArgsConstructor
public class PaseadorController {
    private final PaseadorService paseadorService;
}
