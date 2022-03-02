package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;

import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    List<MyListEntry> findAllPageable(int page);
    MyListEntryDTO findDTOById(UUID id);
    List<MyListEntryDTO> findAllDTO();
    MyListEntry findById(UUID id);
    MyListEntryDTO createMyListEntry(MyListEntry myListEntry);
    void deleteMyListEntry(UUID id);
    MyListEntryDTO putMyListEntry(UpdateMyListEntryDTO myListEntry, UUID id);
    List<MyListEntryDTO> findAllByUser(String username);
    MyListEntry saveMyListEntry(MyListEntry myListEntry);
    boolean checkUserAuthorityForEntry(UUID uuid, String role);
}
