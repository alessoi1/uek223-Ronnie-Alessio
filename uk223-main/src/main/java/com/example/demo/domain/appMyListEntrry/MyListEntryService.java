package com.example.demo.domain.appMyListEntrry;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.UUID;

public interface MyListEntryService {
    List<MyListEntry> findAll();
    List<MyListEntryDTO> findAllPageable(int page);
    MyListEntryDTO findDTOById(UUID id) throws InstanceNotFoundException;
    List<MyListEntryDTO> findAllDTO();
    MyListEntry findById(UUID id) throws InstanceNotFoundException;
    MyListEntryDTO createMyListEntry(CreateMyListEntryDTO myListEntryDTO);
    void deleteMyListEntry(UUID id);
    MyListEntryDTO putMyListEntry(UpdateMyListEntryDTO myListEntry, UUID id);
    List<MyListEntryDTO> findAllByUser(String username);
    MyListEntry saveMyListEntry(MyListEntry myListEntry);
    boolean checkUserAuthorityForEntry(UUID uuid);
}
