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

    @Operation(summary = "Get all MyListEntry item")
    @GetMapping("/")
    public ResponseEntity<Collection<MyListEntryDTO>> findAll() {
        return new ResponseEntity<>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific MyListEntry item")
    @GetMapping("/{id}")
    public ResponseEntity<MyListEntry> findById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<>(myListEntryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Collection<MyListEntry>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @Operation(summary = "Create MyListEntry item")
    @PostMapping("/")
    public ResponseEntity<MyListEntry> create(@Valid @RequestBody MyListEntry myListEntry) {
        MyListEntry createdEntry = myListEntryService.createMyListEntry(myListEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete MyListEntry item")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable UUID id) {
        try {
            myListEntryService.deleteMyListEntry(id);
            return new ResponseEntity(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyListEntry> updateMyListEntry(@PathVariable UUID id, @RequestBody MyListEntry myListEntry) {
        return new ResponseEntity<>(myListEntryService.putMyListEntry(myListEntry, id), HttpStatus.OK);
    }
}
