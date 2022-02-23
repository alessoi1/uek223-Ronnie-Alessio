package com.example.demo.domain.appMyListEntrry;
import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    MyListEntry findById(UUID id);
    MyListEntry createMyListEntry(MyListEntry myListEntry);
    void deleteMyListEntry(UUID id);
}
