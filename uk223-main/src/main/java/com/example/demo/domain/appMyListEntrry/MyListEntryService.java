package com.example.demo.domain.appMyListEntrry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    MyListEntry findById(UUID id);
    MyListEntry createMyListEntry(MyListEntry myListEntry);
    void deleteMyListEntry(UUID id);
    MyListEntry putMyListEntry(MyListEntry myListEntry, UUID id);
    List<MyListEntry> findAllByUser(String username);
}
