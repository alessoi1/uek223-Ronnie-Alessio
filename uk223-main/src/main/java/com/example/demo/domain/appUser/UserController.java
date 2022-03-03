package com.example.demo.domain.appUser;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    //    ADD YOUR ENDPOINT MAPPINGS HERE
    private final UserService userService;

    @Operation(summary = "Der Endpoint nimmt alle User." +
            " Dieser Endpoint kann nur ein Admin benutzen.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint nimmt einen User." +
            " Dieser Endpoint kann nur ein Admin benutzen.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        boolean isUserAuthorized = userService.checkUserAuthorityForEntry(id);
        if (isUserAuthorized) {
            try {
                return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
            } catch (InstanceNotFoundException e) {
                return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Dieser Endpoint erstellt einen neuen User." +
            " Jeder kann einen neuen User erstellen.")
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (InstanceAlreadyExistsException e) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Der Endpoint löscht User." +
            " Jeder User kann sein eigenes Konto löschen aber nicht ein anders." +
            " Der Admin kann alle Konten löschen.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        boolean isUserAuthorized = userService.checkUserAuthorityForEntry(id);
        if (isUserAuthorized) {
            try {
                userService.deleteUser(id);
                return new ResponseEntity<>(id, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Dieser Endpoint bearbeitet User." +
            "Jeder user kann sein eigenes Konto bearbeiten aber keine andere." +
            " Der Admin kann alle Konten bearbeiten.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> update(@Valid @RequestBody UserUpdateDTO user, @PathVariable UUID id) {
        boolean isUserAuthorized = userService.checkUserAuthorityForEntry(id);
        if (isUserAuthorized) {
            try {
                return new ResponseEntity<>(userService.update(user, id), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
