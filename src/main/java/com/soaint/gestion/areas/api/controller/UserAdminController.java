package com.soaint.gestion.areas.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.gestion.areas.api.dto.AreaUsuarioDto;
import com.soaint.gestion.areas.api.dto.SimpleApiResponse;
import com.soaint.gestion.areas.api.service.ConstantesService;
import com.soaint.gestion.areas.api.service.UserAdminService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserAdminController {

    private final UserAdminService userAdminService;
    private final ConstantesService constantesSvc;

    public UserAdminController(UserAdminService userAdminService, ConstantesService constantesSvc) {
        this.userAdminService = userAdminService;
        this.constantesSvc = constantesSvc;
    }


    @GetMapping("/{username}/area")
    public ResponseEntity<SimpleApiResponse<AreaUsuarioDto>> obtenerAreaUsuario(
            @PathVariable String username) {

        AreaUsuarioDto dto = userAdminService.obtenerAreaUsuario(username);

        SimpleApiResponse<AreaUsuarioDto> resp = SimpleApiResponse.<AreaUsuarioDto>builder()
                .body(dto)
                .businessStatus("200")
                .timeResponse(constantesSvc.nowString())
                .message("Área consultada correctamente.")
                .build();

        return ResponseEntity.ok(resp);
    }
    
}
