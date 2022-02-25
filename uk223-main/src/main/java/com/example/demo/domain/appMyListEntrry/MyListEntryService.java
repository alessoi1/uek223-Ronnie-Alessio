package com.example.demo.domain.appMyListEntrry;

import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    List<MyListEntry> findAllPageable(int page);
    List<MyListEntryDTO> findAllDTO();
    MyListEntry findById(UUID id);
    MyListEntry createMyListEntry(MyListEntry myListEntry);
    void deleteMyListEntry(UUID id);
    MyListEntry putMyListEntry(MyListEntry myListEntry, UUID id);
    List<MyListEntry> findAllByUser(String username);
    MyListEntry saveMyListEntry(MyListEntry myListEntry);
}
