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

    @Operation(summary = "Get all MyListEntries")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<MyListEntry>> findAll() {
        return new ResponseEntity<>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get all MyListEntries pageable")
    @GetMapping("/page/{page}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllPageable(@PathVariable int page) {
        return new ResponseEntity<>(myListEntryService.findAllPageable(page), HttpStatus.OK);
    }

    @Operation(summary = "Get single MyListEntryDTO")
    @GetMapping("/DTO/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> findDTOById(@Valid @PathVariable UUID id) {
        try {
            return new ResponseEntity<>(myListEntryService.findDTOById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all MyListEntryDTOs")
    @GetMapping("/DTO")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllDTO() {
        return new ResponseEntity<>(myListEntryService.findAllDTO(), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific MyListEntry item")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> findById(@Valid @PathVariable UUID id) {
        try {
            return new ResponseEntity<>(myListEntryService.findById(id), HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all MyListEntries filtered by user")
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @Operation(summary = "Create MyListEntry")
    @PostMapping()
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') && hasAnyAuthority('CAN_EDIT_MYLISTENTRY', 'ADMIN')")
    public ResponseEntity<MyListEntryDTO> create(@RequestBody CreateMyListEntryDTO createMyListEntryDTO) {
        MyListEntryDTO createdEntry = myListEntryService.createMyListEntry(createMyListEntryDTO);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete MyListEntry")
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

    @Operation(summary = "Update MyListEntry item")
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
