package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class MyListEntryServiceImpl implements MyListEntryService {

    @Autowired
    private final MyListEntryRepository myListEntryRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<MyListEntry> findAll() {
        return myListEntryRepository.findAll();
    }

    @Override
    public MyListEntry findById(UUID id) {
        return myListEntryRepository.findById(id).orElse(new MyListEntry());
    }

    @Override
    public MyListEntry createMyListEntry(MyListEntry myListEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myListEntry.setErstellungsdatum(new Date());
        myListEntry.setUser(userRepository.findByUsername(auth.getName()));
        return myListEntryRepository.save(myListEntry);
    }

    @Override
    public void deleteMyListEntry(UUID id) {
        myListEntryRepository.deleteById(id);
    }

    @Override
    public List<MyListEntry> findAllByUser(String username) {
        return myListEntryRepository.findAllByUser(userRepository.findByUsername(username).getId());
    }

}
