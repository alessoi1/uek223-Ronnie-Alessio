package com.example.demo.domain.appMyListEntrry;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/myListEntry")
@RequiredArgsConstructor
public class MyListEntryController {

    private final MyListEntryService myListEntryService;

    @Operation(summary = "Der Endpoint gibt alle MyListEntry zurück. Die Methode kann " +
            "nur von einem Admin aufgreufen werden, weil es sensible Daten enthält")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<MyListEntry>> findAll() {
        return new ResponseEntity<>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint gibt alle MyListEntry zurück. " +
            " Mit pageable kann angegeben werden, wie viele Einträge man möchte. " +
            " Der Endpoint ist für alle aufrufbar, weil ein DTO zurückgegeben wird, dass die sensiblen Daten versteckt.")
    @GetMapping("/page/{page}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllPageable(@PathVariable int page) {
        return new ResponseEntity<>(myListEntryService.findAllPageable(page), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint gibt ein bestimmtes MyListEntry zurück. " +
            " Der Endpoint kann von allen Users und Admins aufgreufenwerden, " +
            "weil die sensiblen Daten mit einem DTO verstekct werden.")
    @GetMapping("/DTO/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> findDTOById(@Valid @PathVariable UUID id) {
        try {
            return new ResponseEntity<>(myListEntryService.findDTOById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Der Endpoint gibt alle MyListEntry zurück. " +
            " Dieser endpoint kann von allen Users und Admins aufgreufen werden. " +
            " Die sensiblen Daten werden mit Hilfe von einem DTO versteckt.")
    @GetMapping("/DTO")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllDTO() {
        return new ResponseEntity<>(myListEntryService.findAllDTO(), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint gibt ein bestimmtes MyListentry zurück." +
            " Kann nur von einem Damin aufgrufen werden, " +
            "weil sensible Daten darin vorhanden sind.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> findById(@Valid @PathVariable UUID id) {
        try {
            return new ResponseEntity<>(myListEntryService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Gibt alle MyListEntry zurück." +
            " Diese werden nach einem Usernamen gefiltert." +
            " Der endpoint kann von allen User aufgreufen werden.")
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @Operation(summary = "Der Endpoint erstellt ein neues MyLisEntry." +
            " Das erstellte Element wird direkt dem eingelogten User hinzugefügt." +
            " Jeder User kann neue Elemente erstellen.")
    @PostMapping()
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && hasAnyAuthority('CAN_EDIT_MYLISTENTRY', 'ADMIN')")
    public ResponseEntity<MyListEntryDTO> create(@RequestBody CreateMyListEntryDTO createMyListEntryDTO) {
        MyListEntryDTO createdEntry = myListEntryService.createMyListEntry(createMyListEntryDTO);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @Operation(summary = "Der Endpoint kann MyListEntry löschen." +
            "Jeder User kann nur seine eigenen Einträge löschen." +
            " Ein Admin kann alle einträge löschen.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && hasAnyAuthority('CAN_EDIT_MYLISTENTRY', 'ADMIN')")
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        boolean isUserAuthorized = myListEntryService.checkUserAuthorityForEntry(id);
        if (!isUserAuthorized)
            return new ResponseEntity<>(id, HttpStatus.FORBIDDEN);
        try {
            myListEntryService.deleteMyListEntry(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Der Endpoint kann MyListEntry bearbeiten." +
            " Jeder User kann nur seine eigenen Einträge bearbeiten." +
            " Ein Admin kann alle bearbeiten.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && hasAnyAuthority('CAN_EDIT_MYLISTENTRY', 'ADMIN')")
    public ResponseEntity<Object> updateMyListEntry(@PathVariable UUID id, @Valid @RequestBody UpdateMyListEntryDTO updateMyListEntryDTO) {
        boolean isUserAuthorized = myListEntryService.checkUserAuthorityForEntry(id);
        if (!isUserAuthorized) {
            return new ResponseEntity<>(id, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(myListEntryService.putMyListEntry(updateMyListEntryDTO, id), HttpStatus.OK);
    }
}
