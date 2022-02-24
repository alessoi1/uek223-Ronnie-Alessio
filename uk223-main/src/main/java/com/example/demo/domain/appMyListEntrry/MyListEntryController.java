package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/myListEntry")
@RequiredArgsConstructor
public class MyListEntryController {

    private final MyListEntryService myListEntryService;

    @Operation(summary = "Get all MyListEntry item")
    @GetMapping("/getAll")
    public ResponseEntity<Collection<MyListEntry>> findAll() {
        return new ResponseEntity<Collection<MyListEntry>>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific MyListEntry item")
    @GetMapping("/{id}")
    public ResponseEntity<MyListEntry> findById(@Valid @PathVariable UUID id) {
        return new ResponseEntity<MyListEntry>(myListEntryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<Collection<MyListEntry>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @Operation(summary = "Create MyListEntry item")
    @PostMapping("/create")
    public ResponseEntity<MyListEntry> create(@Valid @RequestBody MyListEntry myListEntry) {
        MyListEntry createdEntry = myListEntryService.createMyListEntry(myListEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete MyListEntry item")
    @DeleteMapping("/delete/{id}")
    public void delete(@Valid @PathVariable UUID id) {
        myListEntryService.deleteMyListEntry(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MyListEntry> updateMyListEntry(@PathVariable UUID id, @RequestBody MyListEntry myListEntry) {
        return new ResponseEntity<MyListEntry>(myListEntryService.putMyListEntry(myListEntry, id), HttpStatus.OK);
    }
}
