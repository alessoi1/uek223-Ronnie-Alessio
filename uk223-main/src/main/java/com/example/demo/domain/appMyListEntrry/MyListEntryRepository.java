package com.example.demo.domain.appMyListEntrry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MyListEntryRepository extends JpaRepository<MyListEntry, UUID> {
    MyListEntry findByUsername (String username);
}
