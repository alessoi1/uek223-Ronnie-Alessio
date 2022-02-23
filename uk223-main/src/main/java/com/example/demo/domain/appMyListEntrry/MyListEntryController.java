package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/myListEntry")
@RequiredArgsConstructor
public class MyListEntryController {

    private final MyListEntryService myListEntryService;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<MyListEntry>> findAll() {
        return new ResponseEntity<Collection<MyListEntry>>(myListEntryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyListEntry> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(myListEntryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<Collection<MyListEntry>> findAllByUser(@PathVariable String username) {
        return new ResponseEntity<>(myListEntryService.findAllByUser(username), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<MyListEntry> create(@RequestBody MyListEntry myListEntry) {
        MyListEntry createdEntry = myListEntryService.createMyListEntry(myListEntry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable UUID id) {
        myListEntryService.deleteMyListEntry(id);
    }

  /*  @PutMapping("/update{myListEntry}")
    public MyListEntry updateMyListEntry(@PathVariable MyListEntry myListEntry) {
        return new ResponseEntity<MyListEntry>(myListEntryService, HttpStatus.OK);
    }*/
}
