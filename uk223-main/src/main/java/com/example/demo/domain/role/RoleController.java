package com.example.demo.domain.role;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

@RestController @RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Der Endpoint zeigt alle Rollen an." +
            " Nur ein Admin kann die Rollen anschauen.")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<Role>> findAll() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint zeigt eine Rolle an." +
            " Nur ein Admin kann die Rolle anschauen.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Der Endpoint erstellt eine Rolle." +
            " Nur ein Admin kann eine neue Rolle erstellen.")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }

    @Operation(summary = "Der Endpoint löscht eine Rolle." +
            " Nur ein Admin kann eine Rolle löschen.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        try {
            roleService.deleteRole(id);
        } catch (InstanceNotFoundException | SQLException infe) {
            return new ResponseEntity<>(infe.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint bearbeitet eine Rolle." +
            " Nur ein Admin kann eine Rolle bearbeiten.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> update(@PathVariable UUID id, @Valid @RequestBody Role role) {
        try {
            return new ResponseEntity<>(roleService.updateRole(id, role), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
