package com.example.demo.domain.role;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

@RestController @RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Get all Roles")
    @GetMapping()
    public ResponseEntity<Collection<Role>> findAll() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get single Role by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create new Role")
    @PostMapping()
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.OK);
    }

    @Operation(summary = "Delete Role by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        try {
            roleService.deleteRole(id);
        } catch (InstanceNotFoundException infe) {
            return new ResponseEntity<>(infe.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException sqle) {
            return new ResponseEntity<>(sqle.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id + " deleted", HttpStatus.OK);
    }

}
