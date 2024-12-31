package org.auth.controller;

import org.auth.model.RoleDto;
import org.auth.model.User;
import org.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<RoleDto>> getUserRoles() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RoleDto> payload = roleService.findAllByUser(principal.getUserId());
        return ResponseEntity.ok(payload);
    }
}
