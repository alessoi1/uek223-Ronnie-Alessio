package com.example.demo.domain.appMyListEntrry;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Collection<MyListEntry>> findAll() {
        return new ResponseEntity<>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get all MyListEntries pageable")
    @GetMapping("/page/{page}")
    public ResponseEntity<Collection<MyListEntry>> findAllPageable(@PathVariable int page) {
        return new ResponseEntity<>(myListEntryService.findAllPageable(page), HttpStatus.OK);
    }

    @Operation(summary = "Get all MyListEntryDTOs")
    @GetMapping("/DTO")
    public ResponseEntity<Collection<MyListEntryDTO>> findAllDTO() {
        return new ResponseEntity<>(myListEntryService.findAllDTO(), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific MyListEntry item")
    @GetMapping("/{id}")
    public ResponseEntity<MyListEntry> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(myListEntryService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all MyListEntries filtered by user")
    @GetMapping("/user/{username}")
    public ResponseEntity<Collection<MyListEntry>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @Operation(summary = "Create MyListEntry")
    @PostMapping()
    public ResponseEntity<MyListEntry> create(@Valid @RequestBody MyListEntry myListEntry) {
        MyListEntry createdEntry = myListEntryService.createMyListEntry(myListEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit MyListEntry")
    @PutMapping("/{id}")
    public ResponseEntity<MyListEntry> updateMyListEntry(@PathVariable UUID id, @Valid @RequestBody MyListEntry myListEntry) {
        return new ResponseEntity<>(myListEntryService.putMyListEntry(myListEntry, id), HttpStatus.OK);
    }

    @Operation(summary = "Delete MyListEntry")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        try {
            myListEntryService.deleteMyListEntry(id);
            return new ResponseEntity(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
