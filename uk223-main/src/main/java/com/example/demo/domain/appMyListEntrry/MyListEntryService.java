package com.example.demo.domain.appMyListEntrry;
import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    MyListEntry findById(UUID id);
    void createMyListEntry(MyListEntry myListEntry);
    void deleteMyListEntry(UUID id);
}
