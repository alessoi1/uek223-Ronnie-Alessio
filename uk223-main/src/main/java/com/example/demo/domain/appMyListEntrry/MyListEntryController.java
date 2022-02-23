package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
        return new ResponseEntity<MyListEntry>(myListEntryService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public MyListEntry create(@RequestBody MyListEntry myListEntry) {
        return myListEntryService.createMyListEntry(myListEntry);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable UUID id) {
        myListEntryService.deleteMyListEntry(id);
    }
}
