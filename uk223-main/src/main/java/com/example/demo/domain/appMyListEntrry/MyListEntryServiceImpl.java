package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.Utils.CopyNotNullProps;
import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserDTO;
import com.example.demo.domain.appUser.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Log4j2
public class MyListEntryServiceImpl implements MyListEntryService {

    private final MyListEntryRepository myListEntryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MyListEntry> findAll() {
        return myListEntryRepository.findAll();
    }

    @Override
    public List<MyListEntry> findAllPageable(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("erstellungsdatum"));
        return myListEntryRepository.findAll(pageable).getContent();
    }

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
    public MyListEntry findById(UUID id) {
        return myListEntryRepository.findById(id).orElse(new MyListEntry());
    }

    @Override
    public MyListEntryDTO createMyListEntry(MyListEntry myListEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        myListEntry.setErstellungsdatum(new Date());
        myListEntry.setUser(userRepository.findByUsername(username));

        MyListEntry createdEntry = myListEntryRepository.save(myListEntry);
        log.info("Create MyListEntry with ID " + createdEntry.getId());

        MyListEntryDTO createdMyListEntryDTO = modelMapper.map(createdEntry, MyListEntryDTO.class);
        createdMyListEntryDTO.setUserDTO(modelMapper.map(createdEntry.getUser(), UserDTO.class));
        return createdMyListEntryDTO;
    }

    @Override
    public void deleteMyListEntry(UUID id) {
        log.info("Delete MyListEntry with ID " + id);
        myListEntryRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public MyListEntryDTO putMyListEntry(UpdateMyListEntryDTO updateMyListEntryDTO, UUID id) {
        if (myListEntryRepository.existsById(id)){
            log.info("Overwrite MyListEntry with ID " + id);
            MyListEntry myListEntryFromDB = myListEntryRepository.findById(id).orElse(null);
            CopyNotNullProps copyNotNullProps = new CopyNotNullProps();

            assert myListEntryFromDB != null;
            copyNotNullProps.copyProperties(myListEntryFromDB, updateMyListEntryDTO);
            myListEntryRepository.saveAndFlush(myListEntryFromDB);

            MyListEntryDTO myListEntry = modelMapper.map(myListEntryFromDB, MyListEntryDTO.class);
            myListEntry.setUserDTO(modelMapper.map(myListEntryFromDB.getUser(), UserDTO.class));
            return myListEntry;
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
