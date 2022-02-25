package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.UserDTO;
import com.example.demo.domain.appUser.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Log4j2
public class MyListEntryServiceImpl implements MyListEntryService {

    private final MyListEntryRepository myListEntryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MyListEntryDTO> findAllDTO() {
        List<MyListEntryDTO> myListEntryDTOList = new ArrayList<>();
        List<MyListEntry> myListEntryList = myListEntryRepository.findAll();
        for (int i = 0; i < myListEntryList.size(); i++) {
            myListEntryDTOList.add(modelMapper.map(myListEntryList.get(i), MyListEntryDTO.class));
            myListEntryDTOList.get(i).setUserDTO(modelMapper.map(myListEntryList.get(i).getUser(), UserDTO.class));
        }
        return myListEntryDTOList;
    }

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        myListEntry.setErstellungsdatum(new Date());
        myListEntry.setUser(userRepository.findByUsername(username));

        MyListEntry createdEntry = myListEntryRepository.save(myListEntry);
        log.info("Create MyListEntry with ID " + createdEntry.getId());

        return createdEntry;
    }

    @Override
    public void deleteMyListEntry(UUID id) {
        log.info("Delete MyListEntry with ID " + id);
        myListEntryRepository.deleteById(id);
    }

    @Override
    public MyListEntry putMyListEntry(MyListEntry myListEntry, UUID id) {
        myListEntry.setId(id);
        if (!myListEntryRepository.findById(id).isEmpty()){
            log.info("Overwrite MyListEntry with ID " + id);
            return myListEntryRepository.save(myListEntry);
        }
        log.warn("Failed to overwrite: MyListEntry with ID " + id + " doesn't exist");
         return null;
    }

    public List<MyListEntry> findAllByUser(String username) {
        return myListEntryRepository.findAllByUser(userRepository.findByUsername(username).getId());
    }

    @Override
    public MyListEntry saveMyListEntry(MyListEntry myListEntry) {
            return myListEntryRepository.save(myListEntry);
    }
}
