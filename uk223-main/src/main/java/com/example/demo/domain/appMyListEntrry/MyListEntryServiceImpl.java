package com.example.demo.domain.appMyListEntrry;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class MyListEntryServiceImpl implements MyListEntryService {

    @Autowired
    private final MyListEntryRepository myListEntryRepository;

    @Override
    public List<MyListEntry> findAll() {
        return myListEntryRepository.findAll();
    }

    @Override
    public MyListEntry findById(UUID id) {
        Optional<MyListEntry> entry = myListEntryRepository.findById(id);
        if (entry.isEmpty()) {
            return null;
        }
        return entry.get();
    }

    @Override
    public void createMyListEntry(MyListEntry myListEntry) {
        myListEntryRepository.save(myListEntry);
    }

    @Override
    public void deleteMyListEntry(UUID id) {
        myListEntryRepository.deleteById(id);
    }

}
