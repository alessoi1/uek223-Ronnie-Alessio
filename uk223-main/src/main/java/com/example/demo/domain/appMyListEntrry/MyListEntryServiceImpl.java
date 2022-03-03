package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.Utils.CopyNotNullProps;
import com.example.demo.domain.appUser.UserDTO;
import com.example.demo.domain.appUser.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MyListEntryServiceImpl implements MyListEntryService {

    private final MyListEntryRepository myListEntryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MyListEntry> findAll() {
        return myListEntryRepository.findAll();
    }

    @Override
    public List<MyListEntryDTO> findAllPageable(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("erstellungsdatum"));
        List<MyListEntry> myListEntryList = myListEntryRepository.findAll(pageable).getContent();
        return MapMyListEntryList(myListEntryList);
    }

    private List<MyListEntryDTO> MapMyListEntryList(List<MyListEntry> myListEntryList) {
        List<MyListEntryDTO> myListEntryDTOList = new ArrayList<>();
        for (int i = 0; i < myListEntryList.size(); i++) {
            myListEntryDTOList.add(modelMapper.map(myListEntryList.get(i), MyListEntryDTO.class));
            myListEntryDTOList.get(i).setUserDTO(modelMapper.map(myListEntryList.get(i).getUser(), UserDTO.class));
        }
        return myListEntryDTOList;
    }

    @Override
    public MyListEntryDTO findDTOById(UUID id) throws InstanceNotFoundException {
        MyListEntry myListEntry = findById(id);
        MyListEntryDTO myListEntryDTO = modelMapper.map(myListEntry, MyListEntryDTO.class);
        myListEntryDTO.setUserDTO(modelMapper.map(myListEntry.getUser(), UserDTO.class));
        return myListEntryDTO;
    }

    @Override
    public List<MyListEntryDTO> findAllDTO() {
        List<MyListEntry> myListEntryList = myListEntryRepository.findAll();
        return MapMyListEntryList(myListEntryList);
    }

    @Override
    public MyListEntry findById(UUID id) throws InstanceNotFoundException {
        Optional<MyListEntry> result = myListEntryRepository.findById(id);
        if (myListEntryRepository.existsById(id) && result.isPresent()) {
            return result.get();
        }
        else {
            throw new InstanceNotFoundException("MyListEntry not found");
        }
    }

    @Override
    public MyListEntryDTO createMyListEntry(CreateMyListEntryDTO myListEntryDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        MyListEntry myListEntry = modelMapper.map(myListEntryDTO, MyListEntry.class);
        myListEntry.setUser(userRepository.findByUsername(username));
        myListEntry.setErstellungsdatum(new Date());

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
        if (myListEntryRepository.existsById(id)) {
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

    public List<MyListEntryDTO> findAllByUser(String username) {
        List<MyListEntry> myListEntryListByUser = myListEntryRepository.findAllByUser(userRepository.findByUsername(username).getId());
        return MapMyListEntryList(myListEntryListByUser);
    }

    @Override
    public MyListEntry saveMyListEntry(MyListEntry myListEntry) {
        return myListEntryRepository.save(myListEntry);
    }

    @Override
    public boolean checkUserAuthorityForEntry(UUID uuid) {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName()
                    .equals(findById(uuid).getUser().getUsername());
        } catch (InstanceNotFoundException e) {
            return false;
        }
    }
}
